#include "CPatternAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"



CPatternAuth::CPatternAuth(void)
{
	_pCredential = NULL;
}

CPatternAuth::~CPatternAuth(void)
{
	if (_pCredential != NULL)
	{
		_pCredential->Release();
		_pCredential = NULL;
	}
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CPatternAuth::InitAuthThread(CTollgateCredential* pCredential)
{
	HRESULT hr = S_OK;

	if (_pCredential != NULL)
	{
		_pCredential->Release();
	}
	_pCredential = pCredential;
	_pCredential->AddRef();

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
	pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��û�ϰ� �ֽ��ϴ�..");


	// --------------- �����κ��� ���� ���� ��û ---------------
	RestClient* rc = new RestClient();

	CString user = L"user02";

	if (rc->RequestPatternInformation(user.GetBuffer()))
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
				pThis->_pCredential->GoToNextAuthStage();
			}
			// ���� ����
			else
			{
				pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��ġ���� �ʽ��ϴ�");
				pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			}
			break;

			// ���� �Է� �ð� Ÿ�Ӿƿ�
		case rc->RESULT_CONNECTION_TIMEOUT:
			pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� �Է� �ð��� �ʰ��Ǿ����ϴ�");
			pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);

			// ������ ���� ����
		case rc->RESULT_CONNECTION_FAILED:
			pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�������� ������ �����ϴ�");
			pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// ���� ������ �������� ����
		case rc->RESULT_CONFIG_FILE_COMPROMISED:
			pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ �ջ�Ǿ� ������ ������ �� �����ϴ�");
			pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

			// �������� Ŭ���̾�Ʈ ���α׷��� �ƴ� / Ÿ�� ������ ��ġ���� ����
		case rc->RESULT_UNAUTHORIZED_ACCESS:
		case rc->RESULT_TIMESTAMP_MISMATCH:
			pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�������� ���������� ������ ��ȯ�Ǿ����ϴ�");
			pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;

		case rc->RESULT_UNKNOWN_ERROR:
			pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"�� �� ���� ������ �߻��Ͽ����ϴ�");
			pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
			break;
		}
	}

	delete rc;

	return 0;
}
