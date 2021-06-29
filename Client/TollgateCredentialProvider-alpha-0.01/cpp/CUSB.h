#pragma once

#include <Windows.h>
#include <Dbt.h>

class CUSB
{
public:
	void RegisterDeviceNotify(HWND hwnd);
	BOOL IsDeviceTypeUSB(WPARAM wParam, LPARAM lParam);
	BOOL RecognizeAndVerifyUSB(WPARAM wParam, LPARAM lParam);
private:
	BOOL _VerifyUSB(PDEV_BROADCAST_DEVICEINTERFACE pDevInf);
};

