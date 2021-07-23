#include "CUSB.h"

#include <Windows.h>
#include <Dbt.h>
#include <atlstr.h>
#include "RestClient.h"


static const GUID GUID_DEVINTERFACE_LIST[] = {
	// GUID_DEVINTERFACE_USB_DEVICE
	{ 0xA5DCBF10, 0x6530, 0x11D2, { 0x90, 0x1F, 0x00, 0xC0, 0x4F, 0xB9, 0x51, 0xED } },
};


void CUSB::RegisterDeviceNotify(HWND hwnd)
{
    HDEVNOTIFY hDevNotify;
    DEV_BROADCAST_DEVICEINTERFACE NotificationFilter;

    for (int i = 0; i < sizeof(GUID_DEVINTERFACE_LIST) / sizeof(GUID); i++)
    {
        // �޸� �ʱ�ȭ
        ZeroMemory(&NotificationFilter, sizeof(NotificationFilter));

        NotificationFilter.dbcc_size = sizeof(DEV_BROADCAST_DEVICEINTERFACE);
        NotificationFilter.dbcc_devicetype = DBT_DEVTYP_DEVICEINTERFACE;
        NotificationFilter.dbcc_classguid = GUID_DEVINTERFACE_LIST[i];
        hDevNotify = RegisterDeviceNotification(hwnd, &NotificationFilter, DEVICE_NOTIFY_WINDOW_HANDLE);
    }
}


BOOL CUSB::IsDeviceTypeUSB(WPARAM wParam, LPARAM lParam)
{
    if (DBT_DEVICEARRIVAL == wParam)
    {
        PDEV_BROADCAST_HDR pHdr = (PDEV_BROADCAST_HDR)lParam;

        switch (pHdr->dbch_devicetype)
        {
        case DBT_DEVTYP_DEVICEINTERFACE:        // ��ġ Ŭ������ ���� ����
            return TRUE;
            break;
        }
    }
    return FALSE;
}


DWORD CUSB::VerifyUSB(PDEV_BROADCAST_HDR pHdr, WCHAR* wszUser, WCHAR* wszSystemIdentifier)
{
    PDEV_BROADCAST_DEVICEINTERFACE pDevInf = (PDEV_BROADCAST_DEVICEINTERFACE)pHdr;


    // --------------- �߰��� ����̽��� ID �з� ---------------
    CString szDevId = pDevInf->dbcc_name + 4;   // �տ� \\\\?\\ �ڸ�
    int idx = szDevId.ReverseFind(_T('#'));     // �ڿ��� ã�� # �ε��� ã��.
    szDevId.Truncate(idx);                      // {������} �κ� �����
    szDevId.Replace(_T('#'), _T('\\'));         // #�� \\�� ����
    szDevId.MakeUpper();                        // ��� ���ڿ��� �빮�ڷ� ��ȯ

    szDevId.Remove(L'\\');
    szDevId.Remove(L'&');


    // --------------- ���� �����κ��� USB ���� ���� ---------------
    RestClient* rc = new RestClient();

    if (rc->RequestUSBVerification(wszUser, wszSystemIdentifier, szDevId.GetBuffer()))
    {
        // --------------- ���� �����κ��� ���� ��� �� ���Ͽ� ���� ���� ���� �Ǵ� ---------------
        wchar_t wcMessageFromServer[2048] = { 0, };
        DWORD retCode = rc->GetRestClientExitCode();
        

        switch (retCode)
        {
            // ������ ���� ����
        case rc->RESULT_CONNECTION_SUCCESS:
            rc->GetRestClientMessage(wcMessageFromServer, 2048);

            if (!wcscmp(wcMessageFromServer, L"Verified"))
            {
                return USB_VERIFICATION_SUCCESS;
            }
            else
            {
                return USB_VERIFICATION_FAILED;
            }
            break;

            // ������ ���� ����
        case rc->RESULT_CONNECTION_FAILED:
            return USB_SERVER_NOT_ALIVE;

            // ���� ������ �������� ����
        case rc->RESULT_CONFIG_FILE_COMPROMISED:
            return USB_CONFIG_FILE_COMPROMISED;

            // �������� Ŭ���̾�Ʈ ���α׷��� �ƴ� / Ÿ�� ������ ��ġ���� ����
        case rc->RESULT_UNAUTHORIZED_ACCESS:
        case rc->RESULT_TIMESTAMP_MISMATCH:
            return USB_ANOMALY_DETECTED;

        case rc->RESULT_UNKNOWN_ERROR:
            return USB_UNKNOWN_ERROR;
        }
    }

    delete rc;

    return USB_CLIENT_PROGRAM_COMPROMISED;
}
