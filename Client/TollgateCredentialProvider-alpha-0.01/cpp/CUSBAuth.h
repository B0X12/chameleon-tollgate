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
    // --------------- ������ ���� ---------------
    HWND            _hWnd;                      // Handle to this window
    HINSTANCE       _hInst;                     // Current instance

    HRESULT _MyRegisterClass(void);         // ������ Ŭ���� ���
    HRESULT _InitInstance();                // ������ ���� �� ����̽� ���� �̺�Ʈ�� ���� ������ ���
    BOOL _ProcessNextMessage();             // �������� �޽��� ó��

    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);            // ������ Ŭ���� ���/���� �� �޽��� ó��
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);       // Device Changed �޽��� ó��


    // --------------- Credential Provider�� ������ ���� ������ ���� ---------------
    CTollgateCredential*  _pCredential;
};

