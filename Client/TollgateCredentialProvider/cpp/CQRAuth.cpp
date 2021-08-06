
#include "CQRAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"


#define W_TIMEOUT						60

// Custom messages for managing the behavior of the window thread.
#define WM_EXIT_THREAD					WM_USER + 1
#define WM_TIMER_ELAPSE					WM_USER + 2
#define WM_TIMER_TIMEOUT				WM_USER + 3


const TCHAR* g_wszQRClassName = L"QR Auth Window Class";

static RestClient* rc = nullptr;

CQRAuth::CQRAuth(void)
{
	_hWnd = NULL;
	_hInst = NULL;
	_pCred = NULL;
}

CQRAuth::~CQRAuth(void)
{
	// If we have an active window, we want to post it an exit message.
	if (_hWnd != NULL)
	{
		::PostMessage(_hWnd, WM_EXIT_THREAD, 0, 0);
		_hWnd = NULL;
	}

	if (_pCred != NULL)
	{
		_pCred->Release();
		_pCred = NULL;
	}
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CQRAuth::InitAuthThread(CTollgateCredential* pCredential)
{
	HRESULT hr = S_OK;

	if (_pCred != NULL)
	{
		_pCred->Release();
	}
	_pCred = pCredential;
	_pCred->AddRef();


	// QR 생성
	rc = new RestClient();

	if (rc->RequestQRIssue(NULL, NULL))
	{
		// --------------- 인증 서버로부터 검증 결과 값 비교하여 인증 성공 여부 판단 ---------------
		DWORD retCode = rc->GetRestClientExitCode();

		switch (retCode)
		{
		case rc->RESULT_CONNECTION_SUCCESS:
			pCredential->GoToNextAuthStage();
			break;
		default:
			return hr;
		}
	}


	// 타이머 스레드 생성
	HANDLE hTimerThread = ::CreateThread(NULL, 0, CQRAuth::_TimerThreadProc, (LPVOID)this, 0, NULL);
	if (hTimerThread == NULL)
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}
	else
	{
		CloseHandle(hTimerThread);
	}


	// QR 인증


	return hr;
}


HRESULT CQRAuth::_MyRegisterClass(void)
{
	HRESULT hr = S_OK;

	WNDCLASS wc = { 0 };
	wc.lpfnWndProc = CQRAuth::_WndProc;  // 윈proc 설정
	wc.hInstance = GetModuleHandle(NULL);   // 인스턴스 널값 설정
	wc.lpszClassName = ::g_wszQRClassName;    // 클래스 네임명 설정

	if (!RegisterClass(&wc))
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}

	return hr;
}


HRESULT CQRAuth::_InitInstance()
{
	HRESULT hr = S_OK;

	// Create our window to receive events.
	_hWnd = ::CreateWindow(
		::g_wszQRClassName,           // Class name
		::g_wszQRClassName,           // Title bar text
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
	}

	return hr;
}


BOOL CQRAuth::_ProcessNextMessage()
{
	// Grab, translate, and process the message.
	MSG msg;
	(void) ::GetMessage(&(msg), _hWnd, 0, 0);
	(void) ::TranslateMessage(&(msg));
	(void) ::DispatchMessage(&(msg));

	int nTime;
	wchar_t wszTimeoutMessage[256] = { 0, };

	// This section performs some "post-processing" of the message. It's easier to do these
	// things here because we have the handles to the window, its button, and the provider
	// handy.
	switch (msg.message)
	{
	case WM_TIMER_ELAPSE:
		nTime = (unsigned int)msg.wParam;
		StringCchPrintf(wszTimeoutMessage, ARRAYSIZE(wszTimeoutMessage), L"QR 코드 만료까지 %d 초", nTime);
		_pCred->SetAuthMessage(SFI_QR_MESSAGE, wszTimeoutMessage);
		break;

	case WM_TIMER_TIMEOUT:
		// 해당 윈도우가 인증 프로세스를 종료
		
		_pCred->SetAuthMessage(SFI_QR_MESSAGE, L"인증 시간이 만료되었습니다");
		_pCred->EnableAuthStartButton(SFI_QR_REQUEST, TRUE);
		break;

	case WM_EXIT_THREAD:
		return FALSE;   // Return to the thread loop and let it know to exit.
	}

	return TRUE;
}


// Manages window messages on the window thread.
LRESULT CALLBACK CQRAuth::_WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
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

	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}


// 타이머용 윈도우 생성 스레드
DWORD WINAPI CQRAuth::_TimerThreadProc(LPVOID lpParameter)
{
	CQRAuth* pThis = static_cast<CQRAuth*>(lpParameter);
	if (pThis == NULL)
	{
		// TODO: What's the best way to raise this error?
		return 0;
	}

	HRESULT hr = S_OK;

	// Create the window.
	pThis->_hInst = ::GetModuleHandle(NULL);
	if (pThis->_hInst != NULL)
	{
		pThis->_MyRegisterClass();
		hr = pThis->_InitInstance();
	}
	else
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}

	// ProcessNextMessage will pump our message pump and return false if it comes across
	// a message telling us to exit the thread.
	if (SUCCEEDED(hr))
	{
		while (pThis->_ProcessNextMessage())
		{
		}
	}
	else
	{
		if (pThis->_hWnd != NULL)
		{
			pThis->_hWnd = NULL;
		}
	}

	return 0;
}

