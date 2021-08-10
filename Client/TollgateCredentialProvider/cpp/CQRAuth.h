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
    // --------------- 타이머 윈도우 생성 스레드 관련 ---------------
    static DWORD WINAPI     _TimerThreadProc(LPVOID lpParameter);

    HINSTANCE       _hInst;                     // Current instance
    HRESULT _MyRegisterClass();         // 윈도우 클래스 등록
    HRESULT _InitInstance();                // 윈도우 생성 및 디바이스 변경 이벤트를 받을 윈도우 등록
    BOOL _ProcessNextMessage();             // 스레드의 메시지 처리
    static LRESULT CALLBACK _WndProc(HWND, UINT, WPARAM, LPARAM);       // Device Changed 메시지 처리


    // --------------- 인증 스레드 관련 ---------------
    static DWORD WINAPI     _AuthThreadProc(LPVOID lpParameter);


    // --------------- Credential Provider 접근 포인터 ---------------
    CTollgateCredential* _pCred;
};

