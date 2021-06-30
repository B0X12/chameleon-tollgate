#include "CPatternAuthThrd.h"
#include <strsafe.h>
#include <atlstr.h>
#include "RestClient.h"



CPatternAuthThrd::CPatternAuthThrd(void)
{
    _pCredential = NULL;
}

CPatternAuthThrd::~CPatternAuthThrd(void)
{
    if (_pCredential != NULL)
    {
        _pCredential->Release();
        _pCredential = NULL;
    }
}

// Performs the work required to spin off our message so we can listen for events.
HRESULT CPatternAuthThrd::StartPatternAuthThread(CTollgateCredential* pCredential)
{
    HRESULT hr = S_OK;

    if (_pCredential != NULL)
    {
        _pCredential->Release();
    }
    _pCredential = pCredential;
    _pCredential->AddRef();

    // Create and launch the window thread.
    HANDLE hThread = ::CreateThread(NULL, 0, CPatternAuthThrd::_ThreadProc, (LPVOID)this, 0, NULL);
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
DWORD WINAPI CPatternAuthThrd::_ThreadProc(LPVOID lpParameter)
{
    CPatternAuthThrd* pThis = static_cast<CPatternAuthThrd*>(lpParameter);
    if (pThis == NULL)
    {
        // TODO: What's the best way to raise this error?
        return 0;
    }


    // --------------- Credential Provider �� ��ư ��Ȱ��ȭ �� �޽��� ���� ---------------
    pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, FALSE);
    pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��û�ϰ� �ֽ��ϴ�..");


    // --------------- �����κ��� ���� ���� ��û ---------------
    RestClient* rc = new RestClient();
    wchar_t wcStatusFromServer[30];
    wchar_t wcMessageFromServer[2048];

    if (rc->RequestPatternVerification(L"user02"))
    {
        rc->GetRestClientStatusCode(wcStatusFromServer, 30);
        rc->GetRestClientMessage(wcMessageFromServer, 2048);
    }
    
    delete rc;

    // --------------- �����κ��� ���� �޽��� �˻� �� ���� ���� �Ǻ� ---------------
    if (!wcscmp(wcStatusFromServer, L"OK"))
    {
        if (!wcscmp(wcMessageFromServer, L"true"))
        {
            pThis->_pCredential->SetCurrentAuthStage(AUTH_FACTOR_PASSWORD);
        }
        else
        {
            //MessageBox(NULL, L"���� ����", L"���� ����", 0);
            pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"���� ������ ��ġ���� �ʽ��ϴ�");
        }
    }
    else
    {
        pThis->_pCredential->SetAuthMessage(SFI_PATTERN_MESSAGE, L"������ �߻��Ͽ����ϴ�");
    }

    pThis->_pCredential->EnableAuthStartButton(SFI_PATTERN_REQUEST, TRUE);
    return 0;
}
