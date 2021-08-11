
#include "CPatternAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"


#define W_TIMEOUT						60

// Custom messages for managing the behavior of the window thread.
#define WM_EXIT_THREAD					WM_USER + 1
#define WM_TIMER_ELAPSE					WM_USER + 2


const TCHAR* g_wszPatternClassName = L"Pattern Auth Window Class";
static HWND hTimerWnd = NULL;
static RestClient* rc = NULL;


CPatternAuth::CPatternAuth(void)
{
	_hInst = NULL;
	_pCred = NULL;
}

CPatternAuth::~CPatternAuth(void)
{
	// If we have an active window, we want to post it an exit message.
	if (hTimerWnd != NULL)
	{
		::PostMessage(hTimerWnd, WM_EXIT_THREAD, 0, 0);
		hTimerWnd = NULL;
	}

	if (_pCred != NULL)
	{
		_pCred->Release();
		_pCred = NULL;
	}
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CPatternAuth::InitAuthThread(CTollgateCredential* pCredential)
{
	HRESULT hr = S_OK;

	if (_pCred != NULL)
	{
		_pCred->Release();
	}
	_pCred = pCredential;
	_pCred->AddRef();


	HANDLE hThreads[2] = { 0, };
	rc = new RestClient();

	// 타이머 스레드 생성
	HANDLE hTimerThread = ::CreateThread(NULL, 0, CPatternAuth::_TimerThreadProc, (LPVOID)this, 0, NULL);
	if (hTimerThread == NULL)
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
		return hr;
	}
	else
	{
		hThreads[0] = hTimerThread;
	}

	// 인증 스레드 생성
	HANDLE hAuthThread = ::CreateThread(NULL, 0, CPatternAuth::_AuthThreadProc, (LPVOID)this, 0, NULL);
	if (hAuthThread == NULL)
	{
		CloseHandle(hThreads[0]);
		hr = HRESULT_FROM_WIN32(::GetLastError());
		return hr;
	}
	else
	{
		hThreads[1] = hAuthThread;
	}

	// 타이머 스레드, 인증 스레드 종료 후 스레드 핸들 반환 및 RestClient 할당 해제
	WaitForMultipleObjects(2, hThreads, TRUE, INFINITE);
	CloseHandle(hThreads[0]);
	CloseHandle(hThreads[1]);
	delete rc;

	return hr;
}


HRESULT CPatternAuth::_MyRegisterClass()
{
	HRESULT hr = S_OK;

	WNDCLASS wc = { 0 };
	wc.lpfnWndProc = CPatternAuth::_WndProc;  // 윈proc 설정
	wc.hInstance = GetModuleHandle(NULL);   // 인스턴스 널값 설정
	wc.lpszClassName = ::g_wszPatternClassName;    // 클래스 네임명 설정

	if (!RegisterClass(&wc))
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}

	return hr;
}


HRESULT CPatternAuth::_InitInstance()
{
	HRESULT hr = S_OK;

	// Create our window to receive events.
	hTimerWnd = ::CreateWindow(
		::g_wszPatternClassName,           // Class name
		::g_wszPatternClassName,           // Title bar text
		WS_DLGFRAME,
		200, 200, 200, 80,
		NULL,                       // Parent window 
		NULL, _hInst, NULL);        // Menu, Instance handle, Additional Application Data
	if (hTimerWnd == NULL)
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}
	else
	{
		::ShowWindow(hTimerWnd, SW_HIDE);
	}

	return hr;
}


BOOL CPatternAuth::_ProcessNextMessage()
{
	// Grab, translate, and process the message.
	MSG msg;
	(void) ::GetMessage(&(msg), hTimerWnd, 0, 0);
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
		StringCchPrintf(wszTimeoutMessage, ARRAYSIZE(wszTimeoutMessage), L"패턴 인증 진행 중.. %d", nTime);
		_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, wszTimeoutMessage);
		break;

	case WM_EXIT_THREAD:
		DestroyWindow(hTimerWnd);
		return FALSE;   // 메시지 해석 루프 End
	}

	return TRUE;
}


// Manages window messages on the window thread.
LRESULT CALLBACK CPatternAuth::_WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
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
DWORD WINAPI CPatternAuth::_TimerThreadProc(LPVOID lpParameter)
{
	CPatternAuth* pThis = static_cast<CPatternAuth*>(lpParameter);
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
		if (hTimerWnd != NULL)
		{
			hTimerWnd = NULL;
		}
	}

	return 0;
}


DWORD WINAPI CPatternAuth::_AuthThreadProc(LPVOID lpParameter)
{
	CPatternAuth* pThis = static_cast<CPatternAuth*>(lpParameter);
	if (pThis == NULL)
	{
		// TODO: What's the best way to raise this error?
		return 0;
	}


	// --------------- 서버로부터 패턴 정보 요청 ---------------
	if (rc->RequestPatternInformation(pThis->_pCred->wszUserName, pThis->_pCred->wszSystemIdentifier))
	{
		// 타이머 윈도우 종료 이벤트 보냄
		PostMessage(hTimerWnd, WM_EXIT_THREAD, 0, 0);

		// --------------- 인증 서버로부터 검증 결과 값 비교하여 인증 성공 여부 판단 ---------------
		DWORD retCode = rc->GetRestClientExitCode();
		wchar_t wcMessageFromServer[2048] = { 0, };

		switch (retCode)
		{
			// 서버와 연결 성공
		case rc->RESULT_CONNECTION_SUCCESS:
			rc->GetRestClientMessage(wcMessageFromServer, 2048);

			// 인증 성공
			if (!wcscmp(wcMessageFromServer, L"Verified"))
			{
				pThis->_pCred->GoToNextAuthStage();
			}
			// 인증 실패
			else
			{
				pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"패턴 정보가 일치하지 않습니다");
				pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			}
			break;

			// 패턴 입력 시간 타임아웃
		case rc->RESULT_CONNECTION_TIMEOUT:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"패턴 입력 시간이 초과되었습니다");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// 서버와 연결 실패
		case rc->RESULT_CONNECTION_FAILED:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"서버로부터 응답이 없습니다");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// 설정 파일이 존재하지 않음
		case rc->RESULT_CONFIG_FILE_COMPROMISED:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"설정 파일이 손상되어 서버로 연결할 수 없습니다");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// 정상적인 클라이언트 프로그램이 아님 / 타임 스탬프 일치하지 않음
		case rc->RESULT_UNAUTHORIZED_ACCESS:
		case rc->RESULT_TIMESTAMP_MISMATCH:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"서버에서 비정상적인 응답이 반환되었습니다");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

		case rc->RESULT_UNKNOWN_ERROR:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"알 수 없는 오류가 발생하였습니다");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;
		}
	}
	

	return 0;
}
