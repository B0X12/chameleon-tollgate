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


	// --------------- Credential Provider �� �޽��� ���� ---------------
	pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��û�ϰ� �ֽ��ϴ�..");


	// --------------- �����κ��� ���� ���� ��û ---------------
	RestClient* rc = new RestClient();

	if (rc->RequestPatternInformation(pThis->_pCred->wszUserName, pThis->_pCred->wszSystemIdentifier))
	{
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
			pThis->_pCred->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�������� ������ �����ϴ�");
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

	delete rc;

	return 0;
}
