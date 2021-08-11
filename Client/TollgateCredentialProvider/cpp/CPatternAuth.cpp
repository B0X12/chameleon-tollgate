
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

	// Ÿ�̸� ������ ����
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

	// ���� ������ ����
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

	// Ÿ�̸� ������, ���� ������ ���� �� ������ �ڵ� ��ȯ �� RestClient �Ҵ� ����
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
	wc.lpfnWndProc = CPatternAuth::_WndProc;  // ��proc ����
	wc.hInstance = GetModuleHandle(NULL);   // �ν��Ͻ� �ΰ� ����
	wc.lpszClassName = ::g_wszPatternClassName;    // Ŭ���� ���Ӹ� ����

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
		StringCchPrintf(wszTimeoutMessage, ARRAYSIZE(wszTimeoutMessage), L"���� ���� ���� ��.. %d", nTime);
		_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, wszTimeoutMessage);
		break;

	case WM_EXIT_THREAD:
		DestroyWindow(hTimerWnd);
		return FALSE;   // �޽��� �ؼ� ���� End
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
		::SetTimer(hWnd, 0, 1000, NULL);        // 1�� ī��Ʈ
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



// Ÿ�̸ӿ� ������ ���� ������
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


	// --------------- �����κ��� ���� ���� ��û ---------------
	if (rc->RequestPatternInformation(pThis->_pCred->wszUserName, pThis->_pCred->wszSystemIdentifier))
	{
		// Ÿ�̸� ������ ���� �̺�Ʈ ����
		PostMessage(hTimerWnd, WM_EXIT_THREAD, 0, 0);

		// --------------- ���� �����κ��� ���� ��� �� ���Ͽ� ���� ���� ���� �Ǵ� ---------------
		DWORD retCode = rc->GetRestClientExitCode();
		wchar_t wcMessageFromServer[2048] = { 0, };

		switch (retCode)
		{
			// ������ ���� ����
		case rc->RESULT_CONNECTION_SUCCESS:
			rc->GetRestClientMessage(wcMessageFromServer, 2048);

			// ���� ����
			if (!wcscmp(wcMessageFromServer, L"Verified"))
			{
				pThis->_pCred->GoToNextAuthStage();
			}
			// ���� ����
			else
			{
				pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��ġ���� �ʽ��ϴ�");
				pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			}
			break;

			// ���� �Է� �ð� Ÿ�Ӿƿ�
		case rc->RESULT_CONNECTION_TIMEOUT:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� �Է� �ð��� �ʰ��Ǿ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// ������ ���� ����
		case rc->RESULT_CONNECTION_FAILED:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�����κ��� ������ �����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// ���� ������ �������� ����
		case rc->RESULT_CONFIG_FILE_COMPROMISED:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ �ջ�Ǿ� ������ ������ �� �����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// �������� Ŭ���̾�Ʈ ���α׷��� �ƴ� / Ÿ�� ������ ��ġ���� ����
		case rc->RESULT_UNAUTHORIZED_ACCESS:
		case rc->RESULT_TIMESTAMP_MISMATCH:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�������� ���������� ������ ��ȯ�Ǿ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

		case rc->RESULT_UNKNOWN_ERROR:
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�� �� ���� ������ �߻��Ͽ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;
		}
	}
	

	return 0;
}
