#include "CPatternAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"



CPatternAuth::CPatternAuth(void)
{
	_pCred = NULL;
}

CPatternAuth::~CPatternAuth(void)
{
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

	// Create and launch the window thread.
	HANDLE hThread = ::CreateThread(NULL, 0, CPatternAuth::_ThreadProc, (LPVOID)this, 0, NULL);
	if (hThread == NULL)
	{
		hr = HRESULT_FROM_WIN32(::GetLastError());
	}
	else
	{
		::CloseHandle(hThread);
	}

	return hr;
}


// Our thread procedure. We actually do a lot of work here that could be put back on the 
// main thread, such as setting up the window, etc.
DWORD WINAPI CPatternAuth::_ThreadProc(LPVOID lpParameter)
{
	CPatternAuth* pThis = static_cast<CPatternAuth*>(lpParameter);
	if (pThis == NULL)
	{
		// TODO: What's the best way to raise this error?
		return 0;
	}


	// --------------- Credential Provider 내 메시지 변경 ---------------
	pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"패턴 정보를 요청하고 있습니다..");


	// --------------- 서버로부터 패턴 정보 요청 ---------------
	RestClient* rc = new RestClient();

	if (rc->RequestPatternInformation(pThis->_pCred->wszUserName, pThis->_pCred->wszSystemIdentifier))
	{
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
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"서버에서 응답이 없습니다");
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

	delete rc;

	return 0;
}
