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
    HRESULT _MyRegisterClass(void);         // 윈도우 클래스 등록
    HRESULT _InitInstance();                // 윈도우 생성 및 디바이스 변경 이벤트를 받을 윈도우 등록
    BOOL _ProcessNextMessage();             // 스레드의 메시지 처리

    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);            // 윈도우 클래스 등록/생성 및 메시지 처리
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);

    HWND            _hWnd;                      // Handle to this window
    HINSTANCE       _hInst;                     // Current instance

    CTollgateCredential* _pCredential;
};

