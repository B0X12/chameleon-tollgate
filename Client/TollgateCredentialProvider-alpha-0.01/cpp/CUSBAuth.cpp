
#include "CUSBAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "USB.h"


#define W_TIMEOUT                   60

// Custom messages for managing the behavior of the window thread.
#define WM_EXIT_THREAD              WM_USER + 1
#define WM_TIMER_ELAPSE             WM_USER + 2
#define WM_TIMER_TIMEOUT            WM_USER + 3
#define WM_AUTHENTICATION_GRANT     WM_USER + 4
#define WM_AUTHENTICATION_DENY      WM_USER + 5



const TCHAR* g_wszUSBClassName = L"USB Auth Window Class";

CUSB* g_pUSBModule = nullptr;


CUSBAuth::CUSBAuth(void)
{
    _hWnd = NULL;
    _hInst = NULL;
    _pCredential = NULL;
}

CUSBAuth::~CUSBAuth(void)
{
    // If we have an active window, we want to post it an exit message.
    if (_hWnd != NULL)
    {
        ::PostMessage(_hWnd, WM_EXIT_THREAD, 0, 0);
        _hWnd = NULL;
    }

    if (_pCredential != NULL)
    {
        _pCredential->Release();
        _pCredential = NULL;
    }
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CUSBAuth::InitAuthThread(CTollgateCredential* pCredential)
{
    HRESULT hr = S_OK;

    if (_pCredential != NULL)
    {
        _pCredential->Release();
    }
    _pCredential = pCredential;
    _pCredential->AddRef();

    // Create and launch the window thread.
    HANDLE hThread = ::CreateThread(NULL, 0, CUSBAuth::_ThreadProc, (LPVOID)this, 0, NULL);
    if (hThread == NULL)
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }
    else
    {
        CloseHandle(hThread);
    }

    g_pUSBModule = new CUSB();

    return hr;
}


HRESULT CUSBAuth::_MyRegisterClass(void)
{
    HRESULT hr = S_OK;

    WNDCLASS wc = { 0 };
    wc.lpfnWndProc = CUSBAuth::_WndProc;  // 윈proc 설정
    wc.hInstance = GetModuleHandle(NULL);   // 인스턴스 널값 설정
    wc.lpszClassName = ::g_wszUSBClassName;    // 클래스 네임명 설정

    if (!RegisterClass(&wc))
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }

    return hr;
}


HRESULT CUSBAuth::_InitInstance()
{
    HRESULT hr = S_OK;

    // Create our window to receive events.
    _hWnd = ::CreateWindow(
        ::g_wszUSBClassName,           // Class name
        ::g_wszUSBClassName,           // Title bar text
        WS_DLGFRAME,
        200, 200, 200, 80,
        NULL,                       // Parent window 
        NULL, _hInst, NULL);        // Menu, Instance handle, Additional Application Data
    if (_hWnd == NULL)
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }
    else
    {
        ::ShowWindow(_hWnd, SW_HIDE);
        g_pUSBModule->RegisterDeviceNotify(_hWnd);
    }
    
    return hr;
}


BOOL CUSBAuth::_ProcessNextMessage()
{
    // Grab, translate, and process the message.
    MSG msg;
    (void) ::GetMessage(&(msg), _hWnd, 0, 0);
    (void) ::TranslateMessage(&(msg));
    (void) ::DispatchMessage(&(msg));


    CString strTimeoutMsg = L"인증 시간이 만료되었습니다";
    CString strInvalidUSBMsg = L"등록되지 않은 USB입니다";
    int nTime;
    wchar_t wszTimeoutMessage[256] = { 0, };

    // This section performs some "post-processing" of the message. It's easier to do these
    // things here because we have the handles to the window, its button, and the provider
    // handy.
    switch (msg.message)
    {
    case WM_TIMER_ELAPSE:
        nTime = (unsigned int)msg.wParam;
        StringCchPrintf(wszTimeoutMessage, ARRAYSIZE(wszTimeoutMessage), L"USB 인식 중.. %d", nTime);
        _pCredential->SetAuthMessage(SFI_USB_MESSAGE, wszTimeoutMessage);
        break;

    case WM_TIMER_TIMEOUT:
        _pCredential->SetAuthMessage(SFI_USB_MESSAGE, strTimeoutMsg.GetBuffer());
        _pCredential->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
        break;

    case WM_AUTHENTICATION_GRANT:
        //_pCredential->SetCurrentAuthStage(AUTH_FACTOR_OTP | AUTH_FACTOR_PASSWORD);
        //_pCredential->SetCurrentAuthStage(AUTH_FACTOR_PATTERN);
        _pCredential->GoToNextAuthStage();
        break;

    case WM_AUTHENTICATION_DENY:
        _pCredential->SetAuthMessage(SFI_USB_MESSAGE, strInvalidUSBMsg.GetBuffer());
        _pCredential->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
        break;

    case WM_EXIT_THREAD:
        return FALSE;   // Return to the thread loop and let it know to exit.
        break;
    }

    return TRUE;
}


// Manages window messages on the window thread.
LRESULT CALLBACK CUSBAuth::_WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    static unsigned int nTimeout;

    switch (message)
    {
    case WM_CREATE:
        nTimeout = W_TIMEOUT;
        ::SetTimer(hWnd, 0, 1000, NULL);        // 1초 카운트
        break;

    case WM_TIMER:
        nTimeout--;
        if (nTimeout > 0)
        {
            ::PostMessage(hWnd, WM_TIMER_ELAPSE, nTimeout, 0);
            break;
        }
        else
        {
            ::PostMessage(hWnd, WM_TIMER_TIMEOUT, 0, 0);
            ::KillTimer(hWnd, 0);
            ::PostMessage(hWnd, WM_EXIT_THREAD, 0, 0);
        }

        break;

    case WM_DEVICECHANGE:
        // 인증 성공
        if (g_pUSBModule->IsDeviceTypeUSB(wParam, lParam))
        {
            if (g_pUSBModule->RecognizeAndVerifyUSB(wParam, lParam))
            {
                ::KillTimer(hWnd, 0);
                ::PostMessage(hWnd, WM_AUTHENTICATION_GRANT, 0, 0);
                ::PostMessage(hWnd, WM_EXIT_THREAD, 0, 0);
            }
            else
            {
                ::KillTimer(hWnd, 0);
                ::PostMessage(hWnd, WM_AUTHENTICATION_DENY, 0, 0);
                ::PostMessage(hWnd, WM_EXIT_THREAD, 0, 0);
            }
        }

        break;

    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}


// Our thread procedure. We actually do a lot of work here that could be put back on the 
// main thread, such as setting up the window, etc.
DWORD WINAPI CUSBAuth::_ThreadProc(LPVOID lpParameter)
{
    CUSBAuth* pCommandWindow = static_cast<CUSBAuth*>(lpParameter);
    if (pCommandWindow == NULL)
    {
        // TODO: What's the best way to raise this error?
        return 0;
    }

    HRESULT hr = S_OK;

    // Create the window.
    pCommandWindow->_hInst = ::GetModuleHandle(NULL);
    if (pCommandWindow->_hInst != NULL)
    {
        pCommandWindow->_MyRegisterClass();
        hr = pCommandWindow->_InitInstance();
    }
    else
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }

    // ProcessNextMessage will pump our message pump and return false if it comes across
    // a message telling us to exit the thread.
    if (SUCCEEDED(hr))
    {
        while (pCommandWindow->_ProcessNextMessage())
        {
        }
    }
    else
    {
        if (pCommandWindow->_hWnd != NULL)
        {
            pCommandWindow->_hWnd = NULL;
        }
    }

    if (g_pUSBModule != nullptr)
    {
        delete g_pUSBModule;
    }

    return 0;
}
