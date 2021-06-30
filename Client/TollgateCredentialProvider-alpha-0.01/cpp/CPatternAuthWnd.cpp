#include "CPatternAuthWnd.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"

#define W_TIMEOUT                   60

// Custom messages for managing the behavior of the window thread.
#define WM_EXIT_THREAD              WM_USER + 1
#define WM_TIMER_ELAPSE             WM_USER + 2
#define WM_TIMER_TIMEOUT            WM_USER + 3
#define WM_AUTHENTICATION_GRANT     WM_USER + 4


const TCHAR* g_wszPatternClassName = L"Pattern Auth Window Class";


CPatternAuthWnd::CPatternAuthWnd(void)
{
    _hWnd = NULL;
    _hInst = NULL;
    _pCredential = NULL;
}

CPatternAuthWnd::~CPatternAuthWnd(void)
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
HRESULT CPatternAuthWnd::Initialize(CTollgateCredential* pCredential)
{
    HRESULT hr = S_OK;

    if (_pCredential != NULL)
    {
        _pCredential->Release();
    }
    _pCredential = pCredential;
    _pCredential->AddRef();

    // Create and launch the window thread.
    HANDLE hThread = ::CreateThread(NULL, 0, CPatternAuthWnd::_ThreadProc, (LPVOID)this, 0, NULL);
    if (hThread == NULL)
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }
    else
    {
        CloseHandle(hThread);
    }

    return hr;
}


HRESULT CPatternAuthWnd::_MyRegisterClass(void)
{
    HRESULT hr = S_OK;

    WNDCLASS wc = { 0 };
    wc.lpfnWndProc = CPatternAuthWnd::_WndProc;  // 윈proc 설정
    wc.hInstance = GetModuleHandle(NULL);   // 인스턴스 널값 설정
    wc.lpszClassName = ::g_wszPatternClassName;    // 클래스 네임명 설정

    if (!RegisterClass(&wc))
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }

    return hr;
}


HRESULT CPatternAuthWnd::_InitInstance()
{
    HRESULT hr = S_OK;

    // Create our window to receive events.
    _hWnd = ::CreateWindow(
        ::g_wszPatternClassName,           // Class name
        ::g_wszPatternClassName,           // Title bar text
        WS_OVERLAPPED,
        200, 200, 300, 300,         // X, Y, Width, Height
        NULL,                       // Parent window 
        NULL, _hInst, NULL);        // Menu, Instance handle, Additional Application Data
    if (_hWnd == NULL)
    {
        hr = HRESULT_FROM_WIN32(::GetLastError());
    }

    // Dialog Test
    /*
    long style = ::GetWindowLong(_hWnd, GWL_STYLE);
    style &= ~WS_CAPTION;
    ::SetWindowLong(_hWnd, GWL_STYLE, style);
    */
    ::SetWindowPos(_hWnd, HWND_TOPMOST, 200, 200, 250, 250, SWP_NOMOVE);
    ::ShowWindow(_hWnd, SW_SHOW);
    ::UpdateWindow(_hWnd);

    return hr;
}


BOOL CPatternAuthWnd::_ProcessNextMessage()
{
    // Grab, translate, and process the message.
    MSG msg;
    (void) ::GetMessage(&(msg), _hWnd, 0, 0);
    (void) ::TranslateMessage(&(msg));
    (void) ::DispatchMessage(&(msg));


    CString str = L"인증이 실패하였습니다 - 타임아웃 테스트";
    int nTime;
    wchar_t szTimeoutMessage[256] = { 0, };

    // This section performs some "post-processing" of the message. It's easier to do these
    // things here because we have the handles to the window, its button, and the provider
    // handy.
    switch (msg.message)
    {
    case WM_TIMER_ELAPSE:
        nTime = (unsigned int)msg.wParam;
        StringCchPrintf(szTimeoutMessage, ARRAYSIZE(szTimeoutMessage), L"패턴 인증 진행 중.. %d", nTime);
        _pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, szTimeoutMessage);
        break;

    case WM_TIMER_TIMEOUT:
        _pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, str.GetBuffer());
        break;

    case WM_AUTHENTICATION_GRANT:
        _pCredential->SetCurrentAuthStage(AUTH_FACTOR_OTP | AUTH_FACTOR_PASSWORD);
        return FALSE;       // Return to the thread loop and let it know to exit.
        break;

    case WM_EXIT_THREAD:
        _pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
        return FALSE;
        break;
    }

    return TRUE;
}


// Manages window messages on the window thread.
LRESULT CALLBACK CPatternAuthWnd::_WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    static unsigned int nTimeout;
    static BOOL bAuthenticated = FALSE;

    //static TCHAR string[100] = { 0, };
    HDC hdc, memdc;
    PAINTSTRUCT ps = { 0, };
    HBITMAP hbmp;

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
            ::ShowWindow(hWnd, SW_HIDE);
            ::KillTimer(hWnd, 0);
            ::PostMessage(hWnd, WM_EXIT_THREAD, 0, 0);
        }
        break;

    case WM_PAINT:
        hdc = BeginPaint(hWnd, &ps);
        memdc = CreateCompatibleDC(hdc);

        
        hbmp = (HBITMAP)LoadImage(HINST_THISDLL, L"C:\\pattern_icon.bmp", IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE | LR_CREATEDIBSECTION);
        SelectObject(memdc, hbmp);
        StretchBlt(hdc, 50, 20, 100, 100, memdc, 0, 0, 200, 200, SRCCOPY);
        DeleteDC(memdc);
        
        TextOut(hdc, 20, 200, _T("패턴 정보를 입력받는 중입니다.."), _tcslen(_T("패턴 정보를 입력받는 중입니다..")));
        
        EndPaint(hWnd, &ps);
        break;

    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}


// Our thread procedure. We actually do a lot of work here that could be put back on the 
// main thread, such as setting up the window, etc.
DWORD WINAPI CPatternAuthWnd::_ThreadProc(LPVOID lpParameter)
{
    CPatternAuthWnd* pCommandWindow = static_cast<CPatternAuthWnd*>(lpParameter);
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

    return 0;
}
