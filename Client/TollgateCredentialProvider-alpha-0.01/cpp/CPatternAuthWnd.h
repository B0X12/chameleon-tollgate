#pragma once

#include <Windows.h>
#include "CTollgateCredential.h"


class CPatternAuthWnd
{
public:
    CPatternAuthWnd(void);
    ~CPatternAuthWnd(void);
    HRESULT Initialize(CTollgateCredential* pCredential);

private:
    HRESULT _MyRegisterClass(void);         // ������ Ŭ���� ���
    HRESULT _InitInstance();                // ������ ���� �� ����̽� ���� �̺�Ʈ�� ���� ������ ���
    BOOL _ProcessNextMessage();             // �������� �޽��� ó��

    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);            // ������ Ŭ���� ���/���� �� �޽��� ó��
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);

    HWND            _hWnd;                      // Handle to this window
    HINSTANCE       _hInst;                     // Current instance

    CTollgateCredential* _pCredential;
};

