
#include "CUSBAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "CUSB.h"


#define W_TIMEOUT						60

// Custom messages for managing the behavior of the window thread.
#define WM_EXIT_THREAD					WM_USER + 1
#define WM_TIMER_ELAPSE					WM_USER + 2
#define WM_TIMER_TIMEOUT				WM_USER + 3

#define WM_AUTHENTICATION_GRANT			WM_USER + 4
#define WM_AUTHENTICATION_DENY			WM_USER + 5
#define WM_CONFIG_FILE_COMPROMISED		WM_USER + 6
#define WM_CLIENT_PROGRAM_COMPROMISED	WM_USER + 7
#define WM_SERVER_NOT_RESPOND			WM_USER + 8
#define WM_ANOMALY_DETECTED				WM_USER + 9
#define WM_UNKNOWN_ERROR				WM_USER + 10



const TCHAR* g_wszUSBClassName = L"USB Auth Window Class";

CUSB* g_pUSBModule = nullptr;
WCHAR* g_wszUser = nullptr;
WCHAR* g_wszSystemIdentifier = nullptr;


CUSBAuth::CUSBAuth(void)
{
	_hWnd = NULL;
	_hInst = NULL;
	_pCred = NULL;
}

CUSBAuth::~CUSBAuth(void)
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
HRESULT CUSBAuth::InitAuthThread(CTollgateCredential* pCredential)
{
	HRESULT hr = S_OK;

	if (_pCred != NULL)
	{
		_pCred->Release();
	}
	_pCred = pCredential;
	_pCred->AddRef();

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
	g_wszUser = _pCred->wszUserName;
	g_wszSystemIdentifier = _pCred->wszSystemIdentifier;

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
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, wszTimeoutMessage);
		break;

	case WM_TIMER_TIMEOUT:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"인증 시간이 만료되었습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_AUTHENTICATION_GRANT:
		_pCred->GoToNextAuthStage();
		break;

	case WM_AUTHENTICATION_DENY:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"등록되지 않은 USB입니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_CONFIG_FILE_COMPROMISED:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"설정 파일이 손상되어 서버로 연결할 수 없습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_CLIENT_PROGRAM_COMPROMISED:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"서버와의 통신 모듈이 손상되었습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_SERVER_NOT_RESPOND:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"서버로부터 응답이 없습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_ANOMALY_DETECTED:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"서버에서 비정상적인 응답이 반환되었습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_UNKNOWN_ERROR:
		_pCred->SetAuthMessage(SFI_USB_MESSAGE, L"알 수 없는 오류가 발생하였습니다");
		_pCred->EnableAuthStartButton(SFI_USB_VERIFY, TRUE);
		break;

	case WM_EXIT_THREAD:
		return FALSE;   // Return to the thread loop and let it know to exit.
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
		// Device Type 검사
		if (g_pUSBModule->IsDeviceTypeUSB(wParam, lParam))
		{
			::KillTimer(hWnd, 0);

			// 해당 USB가 User에 의해 등록된 USB인지 검사
			switch (g_pUSBModule->VerifyUSB((PDEV_BROADCAST_HDR)lParam, g_wszUser, g_wszSystemIdentifier))
			{
			case g_pUSBModule->USB_VERIFICATION_SUCCESS:
				::PostMessage(hWnd, WM_AUTHENTICATION_GRANT, 0, 0);
				break;

			case g_pUSBModule->USB_VERIFICATION_FAILED:
				::PostMessage(hWnd, WM_AUTHENTICATION_DENY, 0, 0);
				break;

			case g_pUSBModule->USB_SERVER_NOT_ALIVE:
				::PostMessage(hWnd, WM_SERVER_NOT_RESPOND, 0, 0);
				break;

			case g_pUSBModule->USB_ANOMALY_DETECTED:
				::PostMessage(hWnd, WM_ANOMALY_DETECTED, 0, 0);
				break;

			case g_pUSBModule->USB_CONFIG_FILE_COMPROMISED:
				::PostMessage(hWnd, WM_CONFIG_FILE_COMPROMISED, 0, 0);
				break;

			case g_pUSBModule->USB_CLIENT_PROGRAM_COMPROMISED:
				::PostMessage(hWnd, WM_CLIENT_PROGRAM_COMPROMISED, 0, 0);
				break;

			case g_pUSBModule->USB_UNKNOWN_ERROR:
				::PostMessage(hWnd, WM_UNKNOWN_ERROR, 0, 0);
				break;
			}

			::PostMessage(hWnd, WM_EXIT_THREAD, 0, 0);
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
