#include "RestClient.h"
#include <atlstr.h>
#include <sstream>

RestClient::RestClient()
{
    // ---------- �б�/���� ������ ���� ----------
    _saAttr.nLength = sizeof(SECURITY_ATTRIBUTES);
    _saAttr.bInheritHandle = TRUE;               // �������� �ڵ��� ����ؼ� ��� �����ϰ� ��
    _saAttr.lpSecurityDescriptor = NULL;

    if (CreatePipe(&_hReadPipe, &_hWritePipe, &_saAttr, 0))
    {
        // �ڽĿ��� �ڵ� �� ��� : _hReadPipe
        SetHandleInformation(_hReadPipe, HANDLE_FLAG_INHERIT, 0);
    }

    // ---------- �ڽ� ���μ��� ������ �ʿ��� ���� ����: Rest Client ----------
    SecureZeroMemory(&_stStartupInfo, sizeof(_stStartupInfo));
    SecureZeroMemory(&_stProcessInfo, sizeof(_stProcessInfo));

    _stStartupInfo.cb = sizeof(_stStartupInfo);
    _stStartupInfo.hStdError = _hWritePipe;           // Rest Client�� ǥ�� ���� ������� hWritePipe ���
    _stStartupInfo.hStdOutput = _hWritePipe;          // Rest Client�� ǥ�� ������� hWritePipe ���
    _stStartupInfo.dwFlags |= STARTF_USESTDHANDLES;

    wcscpy_s(_wcPath, 2048, L"C:\\");
    wcscat_s(_wcAppName, 2048, _wcPath);
    wcscat_s(_wcAppName, 2048, L"RestClient.exe");
}


RestClient::~RestClient()
{
    if (_hReadPipe != nullptr)
    {   
        CloseHandle(_hReadPipe);
    }

    if (_hWritePipe != nullptr)
    {
        CloseHandle(_hWritePipe);
    }
}


DWORD RestClient::GetRestClientExitCode()
{
    return this->_dwRestClientExitCode;
}


void RestClient::GetRestClientMessage(wchar_t* wcBuffer, rsize_t nBufferSizeInWords)
{
    wcscpy_s(wcBuffer, nBufferSizeInWords, _wcRestClientMessage);
}


BOOL RestClient::RequestUSBVerification(wchar_t* user, wchar_t* usb_info)
{
    wchar_t wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --verify-usb [user] [usb_info]
    wcscpy_s(wcCommandLine, 2048, L" --verify-usb ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, usb_info);
    
    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestPatternVerification(wchar_t* user)
{
    wchar_t wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --request-pattern [user]
    wcscpy_s(wcCommandLine, 2048, L" --request-pattern ");
    wcscat_s(wcCommandLine, 2048, user);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::_ExecuteRestClientProcess(wchar_t* wcCommandLine)
{
    // Client ���μ��� ����
    if (::CreateProcessW(_wcAppName, wcCommandLine, NULL, NULL, TRUE, CREATE_NO_WINDOW, NULL, _wcPath, &_stStartupInfo, &_stProcessInfo))
    {
        // RestClient ���� �� �ñ׳� �߻�
        if (WaitForSingleObject(_stProcessInfo.hProcess, INFINITE) == WAIT_OBJECT_0)
        {
            // ���� �ڵ� ����
            GetExitCodeProcess(_stProcessInfo.hProcess, &_dwRestClientExitCode);

            CloseHandle(_stProcessInfo.hProcess);
            CloseHandle(_stProcessInfo.hThread);

            // ���� �߻� �� hWritePipe(STDOUT)�� ��ϵ� ������ �ѹ��� ����
            DWORD bytesInPipe = 0;
            BOOL bResult = TRUE;

            while (bResult && (bytesInPipe == 0))
            {
                bResult = PeekNamedPipe(_hReadPipe, NULL, 0, NULL, &bytesInPipe, NULL);
            }

            if (bytesInPipe != 0) {
                DWORD dwRead;
                CHAR* pipeContents = new CHAR[bytesInPipe];
                bResult = ReadFile(_hReadPipe, pipeContents, bytesInPipe, &dwRead, NULL);

                if (!(!bResult || dwRead == 0))
                {
                    std::stringstream stream(pipeContents);
                    std::string str;
                    wchar_t buf[2048] = { 0, };
                    size_t cn;

                    // --------------- ������ ���� �� ���� ---------------
                    getline(stream, str);
                    size_t idx = std::string::npos;

                    if ((idx = str.find(L'\r')) != std::string::npos)
                    {
                        str.erase(idx, 1);
                    }

                    if ((idx = str.find(L'\n')) != std::string::npos)
                    {
                        str.erase(idx, 1);
                    }

                    mbstowcs_s(&cn, buf, 2048, str.c_str(), strlen(str.c_str()) + 1);
                    wcscpy_s(_wcRestClientMessage, 2048, buf);

                    // ���� �ڵ� ���� �� Ŭ���̾�Ʈ ���α׷� ��� �� ���� �Ϸ�
                    return TRUE;
                }
            }
        }
    }

    return FALSE;
}