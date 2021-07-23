#pragma once

#include <Windows.h>
#include <Dbt.h>

class CUSB
{
public:
    enum USB_VERIFY_RESULT
    {
        USB_VERIFICATION_SUCCESS,
        USB_VERIFICATION_FAILED,
        USB_SERVER_NOT_ALIVE,
        USB_ANOMALY_DETECTED,
        USB_CONFIG_FILE_COMPROMISED,
        USB_CLIENT_PROGRAM_COMPROMISED,
        USB_UNKNOWN_ERROR,
    };

	void RegisterDeviceNotify(HWND hwnd);
	BOOL IsDeviceTypeUSB(WPARAM wParam, LPARAM lParam);
	DWORD VerifyUSB(PDEV_BROADCAST_HDR pHdr, WCHAR* wszUserName, WCHAR* wszSystemIdentifier);
};

