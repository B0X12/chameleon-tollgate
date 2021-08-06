#pragma once


#include <Windows.h>
#include "CTollgateCredential.h"

class CQRAuth
{
public:
    CQRAuth(void);
    ~CQRAuth(void);
    HRESULT InitAuthThread(CTollgateCredential* pCredential);

private:
    // --------------- ������ ���� ---------------
    HWND            _hWnd;                      // Handle to this window
    HINSTANCE       _hInst;                     // Current instance

    HRESULT _MyRegisterClass(void);         // ������ Ŭ���� ���
    HRESULT _InitInstance();                // ������ ���� �� ����̽� ���� �̺�Ʈ�� ���� ������ ���
    BOOL _ProcessNextMessage();             // �������� �޽��� ó��

    static DWORD WINAPI     _TimerThreadProc(LPVOID lpParameter);            // ������ Ŭ���� ���/���� �� �޽��� ó��
    //static DWORD WINAPI     _AuthThreadProc(LPVOID lpParameter);            // ������ Ŭ���� ���/���� �� �޽��� ó��
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);       // Device Changed �޽��� ó��


    // --------------- Credential Provider�� ������ ���� ������ ���� ---------------
    CTollgateCredential* _pCred;
};

