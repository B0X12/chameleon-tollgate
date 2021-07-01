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
        //PDEV_BROADCAST_DEVICEINTERFACE pDevInf; // ��� ������ vid,pid �� ��� ����

        switch (pHdr->dbch_devicetype)
        {
        case DBT_DEVTYP_DEVICEINTERFACE:        // ��ġ Ŭ������ ���� ����
            return TRUE;
            break;
        }
    }
    return FALSE;
}


BOOL CUSB::RecognizeAndVerifyUSB(WPARAM wParam, LPARAM lParam)
{
    if (IsDeviceTypeUSB(wParam, lParam))
    {
        PDEV_BROADCAST_HDR pHdr = (PDEV_BROADCAST_HDR)lParam;
        PDEV_BROADCAST_DEVICEINTERFACE pDevInf = (PDEV_BROADCAST_DEVICEINTERFACE)pHdr;
        return _VerifyUSB(pDevInf);
    }
    return FALSE;
}


BOOL CUSB::_VerifyUSB(PDEV_BROADCAST_DEVICEINTERFACE pDevInf)
{
    // --------------- �߰��� ����̽��� ID �з� ---------------
    CString szDevId = pDevInf->dbcc_name + 4;   // �տ� \\\\?\\�ڸ�
    int idx = szDevId.ReverseFind(_T('#'));     // �ڿ��� ã�� # �ε��� ã��.
    szDevId.Truncate(idx);                      // {������} �κ� �����
    szDevId.Replace(_T('#'), _T('\\'));         // #�� \\�� ����
    szDevId.MakeUpper();                        // ��� ���ڿ��� �빮�ڷ� ��ȯ

    szDevId.Remove(L'\\');
    szDevId.Remove(L'&');


    // --------------- ���� �����κ��� USB ���� ���� ---------------
    RestClient* rc = new RestClient();

    CString user = L"user02";           // Test
    rc->RequestUSBVerification(user.GetBuffer(), szDevId.GetBuffer());

    // --------------- ���� �����κ��� ���� ��� �� ���Ͽ� ���� ���� ���� �Ǵ� ---------------
    wchar_t wcStatusFromServer[30];
    wchar_t wcMessageFromServer[2048];
    //wchar_t wcExitCode[10];

    rc->GetRestClientStatusCode(wcStatusFromServer, 30);
    rc->GetRestClientMessage(wcMessageFromServer, 2048);
    //wsprintf(wcExitCode, L"%d", rc->GetRestClientExitCode());

    delete rc;

    if (!wcscmp(wcStatusFromServer, L"OK"))
    {
        if (!wcscmp(wcMessageFromServer, L"true"))
        {
            return TRUE;
        }
    }

    return FALSE;
}

