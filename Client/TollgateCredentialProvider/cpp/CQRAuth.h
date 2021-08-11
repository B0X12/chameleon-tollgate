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
    // --------------- Ÿ�̸� ������ ���� ������ ���� ---------------
    static DWORD WINAPI     _TimerThreadProc(LPVOID lpParameter);

    HINSTANCE       _hInst;                     // Current instance
    HRESULT _MyRegisterClass();         // ������ Ŭ���� ���
    HRESULT _InitInstance();                // ������ ���� �� ����̽� ���� �̺�Ʈ�� ���� ������ ���
    BOOL _ProcessNextMessage();             // �������� �޽��� ó��
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);       // Device Changed �޽��� ó��


    // --------------- ���� ������ ���� ---------------
    static DWORD WINAPI     _AuthThreadProc(LPVOID lpParameter);


    // --------------- Credential Provider ���� ������ ---------------
    CTollgateCredential* _pCred;
};

