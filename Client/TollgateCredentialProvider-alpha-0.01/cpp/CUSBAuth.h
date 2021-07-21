#pragma once


#include <Windows.h>
#include "CTollgateCredential.h"


static const GUID GUID_DEVINTERFACE_LIST[] = {
    // GUID_DEVINTERFACE_USB_DEVICE
    { 0xA5DCBF10, 0x6530, 0x11D2, { 0x90, 0x1F, 0x00, 0xC0, 0x4F, 0xB9, 0x51, 0xED } },
};

class CUSBAuth
{
public:
    CUSBAuth(void);
	~CUSBAuth(void);
    HRESULT InitAuthThread(CTollgateCredential* pCredential);

private:
    // --------------- 윈도우 관련 ---------------
    HWND            _hWnd;                      // Handle to this window
    HINSTANCE       _hInst;                     // Current instance

    HRESULT _MyRegisterClass(void);         // 윈도우 클래스 등록
    HRESULT _InitInstance();                // 윈도우 생성 및 디바이스 변경 이벤트를 받을 윈도우 등록
    BOOL _ProcessNextMessage();             // 스레드의 메시지 처리

    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);            // 윈도우 클래스 등록/생성 및 메시지 처리
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);       // Device Changed 메시지 처리


    // --------------- Credential Provider에 접근을 위한 포인터 변수 ---------------
    CTollgateCredential*  _pCredential;
};

