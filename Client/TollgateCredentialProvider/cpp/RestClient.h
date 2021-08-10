#pragma once

#include <Windows.h>



class RestClient
{
public:
    enum CLIENT_RETURN_CODE
    {
        // 프로그램 리턴 코드 - 성공
        RESULT_CONNECTION_SUCCESS = 100,

        // 프로그램 리턴 코드 - 실패
        RESULT_CONNECTION_FAILED,
        RESULT_CONFIG_FILE_COMPROMISED,
        RESULT_UNAUTHORIZED_ACCESS,
        RESULT_TIMESTAMP_MISMATCH,
        RESULT_CONNECTION_TIMEOUT,
        RESULT_UNKNOWN_ERROR,

        RESULT_CLIENT_PROGRAM_COMPROMISED,
    };

private:
    // 파이프 생성 관련
    HANDLE _hReadPipe = nullptr;
    HANDLE _hWritePipe = nullptr;
    SECURITY_ATTRIBUTES _saAttr = { 0, };

    // 프로세스 생성 및 종료 관련
    STARTUPINFO _stStartupInfo = { 0, };             // 프로세스 속성 지정
    PROCESS_INFORMATION _stProcessInfo = { 0, };     // 생성된 프로세스와 기본 스레드에 대한 정보

    wchar_t _wcPath[2048] = { 0, };
    wchar_t _wcAppName[2048] = { 0, };

    // 프로세스 종료 코드 및 반환 문자열
    DWORD _dwRestClientExitCode = RESULT_CLIENT_PROGRAM_COMPROMISED;
    wchar_t _wcRestClientMessage[2048] = { 0, };

public:
    RestClient();
    ~RestClient();

    // --------------- RestClient 프로세스 제어 ---------------
    DWORD GetRestClientExitCode();
    void GetRestClientMessage(WCHAR* wcBuffer, rsize_t nBufferSizeInWords);
    

    // --------------- RestClient 요청 목록 ---------------
    BOOL GetUserBySystemIdentifier(WCHAR* sys_id);
    BOOL GetAuthFactorByUser(WCHAR* user);

    BOOL RequestUSBVerification(WCHAR* user, WCHAR* sys_id, WCHAR* usb_info);
    BOOL RequestPatternInformation(WCHAR* user, WCHAR* sys_id);
    BOOL RequestFaceInformation(WCHAR* user, WCHAR* sys_id);
    BOOL RequestFingerprintInformation(WCHAR* user, WCHAR* sys_id);
    BOOL RequestQRIssue(WCHAR* user, WCHAR* sys_id);
    BOOL RequestOTPVerification(WCHAR* user, WCHAR* sys_id, WCHAR* otp);
    
private:
    BOOL _ExecuteRestClientProcess(WCHAR* wcCommandLine);
};

