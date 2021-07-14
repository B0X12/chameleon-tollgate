#include "RestClient.h"
#include <atlstr.h>
#include <sstream>

RestClient::RestClient()
{
    // ---------- 읽기/쓰기 파이프 생성 ----------
    _saAttr.nLength = sizeof(SECURITY_ATTRIBUTES);
    _saAttr.bInheritHandle = TRUE;               // 파이프가 핸들을 상속해서 사용 가능하게 함
    _saAttr.lpSecurityDescriptor = NULL;

    if (CreatePipe(&_hReadPipe, &_hWritePipe, &_saAttr, 0))
    {
        // 자식에게 핸들 값 상속 : _hReadPipe
        SetHandleInformation(_hReadPipe, HANDLE_FLAG_INHERIT, 0);
    }

    // ---------- 자식 프로세스 생성에 필요한 정보 세팅: Rest Client ----------
    SecureZeroMemory(&_stStartupInfo, sizeof(_stStartupInfo));
    SecureZeroMemory(&_stProcessInfo, sizeof(_stProcessInfo));

    _stStartupInfo.cb = sizeof(_stStartupInfo);
    _stStartupInfo.hStdError = _hWritePipe;           // Rest Client는 표준 에러 출력으로 hWritePipe 사용
    _stStartupInfo.hStdOutput = _hWritePipe;          // Rest Client는 표준 출력으로 hWritePipe 사용
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

    // Rest Client 프로그램 인자 초기화: --verify-usb [user] [usb_info]
    wcscpy_s(wcCommandLine, 2048, L" --verify-usb ");
    wcscat_s(wcCommandLine, 2048, user);
    wcscat_s(wcCommandLine, 2048, L" ");
    wcscat_s(wcCommandLine, 2048, usb_info);
    
    // Client 프로세스 실행
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::RequestPatternVerification(wchar_t* user)
{
    wchar_t wcCommandLine[2048] = { 0, };

    // Rest Client 프로그램 인자 초기화: --request-pattern [user]
    wcscpy_s(wcCommandLine, 2048, L" --request-pattern ");
    wcscat_s(wcCommandLine, 2048, user);

    // Client 프로세스 실행
    return _ExecuteRestClientProcess(wcCommandLine);
}


BOOL RestClient::_ExecuteRestClientProcess(wchar_t* wcCommandLine)
{
    // Client 프로세스 생성
    if (::CreateProcessW(_wcAppName, wcCommandLine, NULL, NULL, TRUE, CREATE_NO_WINDOW, NULL, _wcPath, &_stStartupInfo, &_stProcessInfo))
    {
        // RestClient 종료 시 시그널 발생
        if (WaitForSingleObject(_stProcessInfo.hProcess, INFINITE) == WAIT_OBJECT_0)
        {
            // 리턴 코드 세팅
            GetExitCodeProcess(_stProcessInfo.hProcess, &_dwRestClientExitCode);

            CloseHandle(_stProcessInfo.hProcess);
            CloseHandle(_stProcessInfo.hThread);

            // 종료 발생 시 hWritePipe(STDOUT)에 기록된 내용을 한번에 읽음
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

                    // --------------- 데이터 추출 및 세팅 ---------------
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

                    // 리턴 코드 세팅 및 클라이언트 프로그램 결과 값 세팅 완료
                    return TRUE;
                }
            }
        }
    }

    return FALSE;
}