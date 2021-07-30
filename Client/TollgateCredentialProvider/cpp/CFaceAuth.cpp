#include "CFaceAuth.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"



CFaceAuth::CFaceAuth(void)
{
	_pCred = NULL;
}

CFaceAuth::~CFaceAuth(void)
{
	if (_pCred != NULL)
	{
		_pCred->Release();
		_pCred = NULL;
	}
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CFaceAuth::InitAuthThread(CTollgateCredential* pCredential)
{
	HRESULT hr = S_OK;

	if (_pCred != NULL)
	{
		_pCred->Release();
	}
	_pCred = pCredential;
	_pCred->AddRef();

	// Create and launch the window thread.
	HANDLE hThread = ::CreateThread(NULL, 0, CFaceAuth::_ThreadProc, (LPVOID)this, 0, NULL);
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
DWORD WINAPI CFaceAuth::_ThreadProc(LPVOID lpParameter)
{
	CFaceAuth* pThis = static_cast<CFaceAuth*>(lpParameter);
	if (pThis == NULL)
	{
		// TODO: What's the best way to raise this error?
		return 0;
	}


	// --------------- Credential Provider �� �޽��� ���� ---------------
	pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�� �ν� ������ ��û�ϰ� �ֽ��ϴ�..");


	// --------------- �����κ��� �� �ν� ���� ��û ---------------
	RestClient* rc = new RestClient();

	if (rc->RequestFaceInformation(pThis->_pCred->wszUserName, pThis->_pCred->wszSystemIdentifier))
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
				pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�ȸ� ������ �����Ͽ����ϴ�");
				pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			}
			break;

			// �ȸ� ���� �ð� Ÿ�Ӿƿ�
		case rc->RESULT_CONNECTION_TIMEOUT:
			pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�ȸ� ������ �����Ͽ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			break;

			// ������ ���� ����
		case rc->RESULT_CONNECTION_FAILED:
			pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�������� ������ �����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			break;

			// ���� ������ �������� ����
		case rc->RESULT_CONFIG_FILE_COMPROMISED:
			pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"���� ������ �ջ�Ǿ� ������ ������ �� �����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			break;

			// �������� Ŭ���̾�Ʈ ���α׷��� �ƴ� / Ÿ�� ������ ��ġ���� ����
		case rc->RESULT_UNAUTHORIZED_ACCESS:
		case rc->RESULT_TIMESTAMP_MISMATCH:
			pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�������� ���������� ������ ��ȯ�Ǿ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			break;

		case rc->RESULT_UNKNOWN_ERROR:
			pThis->_pCred->SetAuthMessage(SFI_FACE_MESSAGE, L"�� �� ���� ������ �߻��Ͽ����ϴ�");
			pThis->_pCred->EnableAuthStartButton(SFI_FACE_REQUEST, TRUE);
			break;
		}
	}

	delete rc;

	return 0;
}
