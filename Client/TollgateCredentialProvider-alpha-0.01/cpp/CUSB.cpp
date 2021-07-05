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
        // 메모리 초기화
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
        //PDEV_BROADCAST_DEVICEINTERFACE pDevInf; // 멤버 참조후 vid,pid 값 얻기 가능

        switch (pHdr->dbch_devicetype)
        {
        case DBT_DEVTYP_DEVICEINTERFACE:        // 장치 클래스에 대한 정보
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
    // --------------- 추가된 디바이스의 ID 분류 ---------------
    CString szDevId = pDevInf->dbcc_name + 4;   // 앞에 \\\\?\\자름
    int idx = szDevId.ReverseFind(_T('#'));     // 뒤에서 찾은 # 인덱스 찾음.
    szDevId.Truncate(idx);                      // {레지값} 부분 지우기
    szDevId.Replace(_T('#'), _T('\\'));         // #을 \\로 변경
    szDevId.MakeUpper();                        // 모든 문자열을 대문자로 변환

    szDevId.Remove(L'\\');
    szDevId.Remove(L'&');


    // --------------- 인증 서버로부터 USB 정보 검증 ---------------
    RestClient* rc = new RestClient();

    CString user = L"user02";           // Test
    rc->RequestUSBVerification(user.GetBuffer(), szDevId.GetBuffer());

    // --------------- 인증 서버로부터 검증 결과 값 비교하여 인증 성공 여부 판단 ---------------
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

