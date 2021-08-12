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
    _stStartupInfo.dwFlags |= STARTF_USESTDHANDLES;
    _stStartupInfo.hStdError = _hWritePipe;           // Rest Client�� ǥ�� ���� ������� hWritePipe ���
    _stStartupInfo.hStdOutput = _hWritePipe;          // Rest Client�� ǥ�� ������� hWritePipe ���

    wcscpy_s(_wcPath, 2048, L"C:\\Tollgate\\");
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


void RestClient::GetRestClientMessage(WCHAR* wcBuffer, rsize_t nBufferSizeInWords)
{
    wcscpy_s(wcBuffer, nBufferSizeInWords, _wcRestClientMessage);
}


BOOL RestClient::GetUserBySystemIdentifier(WCHAR* sys_id)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --get-user [sys_uid]
    wcscpy_s(wcCommandLine, 2048, L" --get-user ");
    wcscat_s(wcCommandLine, 2048, sys_id);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::GetAuthFactorByUser(WCHAR* user)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --get-auth-factor [user]
    wcscpy_s(wcCommandLine, 2048, L" --get-auth-factor ");
    wcscat_s(wcCommandLine, 2048, user);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestUSBVerification(WCHAR* user, WCHAR* sys_id, WCHAR* usb_info)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --verify-usb [user] [sys_id] [usb_info]
    wcscpy_s(wcCommandLine, 2048, L" --verify-usb ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, usb_info);
    
    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestPatternInformation(WCHAR* user, WCHAR* sys_id)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --request-pattern [user] [sys_id]
    wcscpy_s(wcCommandLine, 2048, L" --request-pattern ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestFaceInformation(WCHAR* user, WCHAR* sys_id)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --request-face [user] [sys_id]
    wcscpy_s(wcCommandLine, 2048, L" --request-face ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}

BOOL RestClient::RequestFingerprintInformation(WCHAR* user, WCHAR* sys_id)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --request-fingerprint [user] [sys_id]
    wcscpy_s(wcCommandLine, 2048, L" --request-fingerprint ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestQRIssue(WCHAR* user, WCHAR* sys_id)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --issue-qrcode [user] [sys_id]
    wcscpy_s(wcCommandLine, 2048, L" --issue-qrcode ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestOTPVerification(WCHAR* user, WCHAR* sys_id, WCHAR* otp)
{
    WCHAR wcCommandLine[2048] = { 0, };

    // Rest Client ���α׷� ���� �ʱ�ȭ: --verify-otp [user] [sys_id] [otp]
    wcscpy_s(wcCommandLine, 2048, L" --verify-otp ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, sys_id);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, otp);

    // Client ���μ��� ����
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::_ExecuteRestClientProcess(WCHAR* wcCommandLine)
{
    ZeroMemory(_wcRestClientMessage, sizeof(_wcRestClientMessage));

    // Client ���μ��� ����
    if (::CreateProcessW(_wcAppName, wcCommandLine, NULL, NULL, TRUE, CREATE_NO_WINDOW, NULL, _wcPath, &_stStartupInfo, &_stProcessInfo))
    {
        DWORD wait = WaitForSingleObject(_stProcessInfo.hProcess, INFINITE);

        // RestClient ���� �� �ñ׳� �߻�
        if (wait == WAIT_OBJECT_0)
        {
            // ���� �ڵ� ����
            GetExitCodeProcess(_stProcessInfo.hProcess, &_dwRestClientExitCode);

            CloseHandle(_stProcessInfo.hProcess);
            CloseHandle(_stProcessInfo.hThread);

            // ���� �߻� �� hWritePipe(STDOUT)�� ��ϵ� ������ �ѹ��� ����
            DWORD bytesInPipe = 0;
            PeekNamedPipe(_hReadPipe, NULL, 0, NULL, &bytesInPipe, NULL);

            if (bytesInPipe != 0) {
                DWORD dwRead;
                CHAR* pipeContents = new CHAR[bytesInPipe];
                BOOL bResult = ReadFile(_hReadPipe, pipeContents, bytesInPipe, &dwRead, NULL);

                if (!(!bResult || dwRead == 0))
                {
                    std::stringstream stream(pipeContents);
                    std::string str;
                    wchar_t buf[2048] = { 0, };
                    //size_t cn;

                    // --------------- ������ ���� �� ���� ---------------
                    getline(stream, str);
                    size_t idx = std::string::npos;

                    // Carriage Return ����
                    if ((idx = str.find(L'\r')) != std::string::npos)
                    {
                        str.erase(idx, 1);
                    }

                    // Line Feed ����
                    if ((idx = str.find(L'\n')) != std::string::npos)
                    {
                        str.erase(idx, 1);
                    }

                    MultiByteToWideChar(CP_ACP, 0, str.c_str(), strlen(str.c_str()) + 1, buf, 2048);
                    wcscpy_s(_wcRestClientMessage, 2048, buf);
                }   
            }
            // ���� �ڵ� ���� �� Ŭ���̾�Ʈ ���α׷� ��� �� ���� �Ϸ�
            return TRUE;
        }
        else
        {
            _dwRestClientExitCode = RESULT_CONNECTION_FAILED;

            CloseHandle(_stProcessInfo.hProcess);
            CloseHandle(_stProcessInfo.hThread);

            return TRUE;
        }
    }

    return FALSE;
}