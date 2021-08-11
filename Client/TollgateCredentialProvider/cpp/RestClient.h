#pragma once

#include <Windows.h>



class RestClient
{
public:
    enum CLIENT_RETURN_CODE
    {
        // ���α׷� ���� �ڵ� - ����
        RESULT_CONNECTION_SUCCESS = 100,

        // ���α׷� ���� �ڵ� - ����
        RESULT_CONNECTION_FAILED,
        RESULT_CONFIG_FILE_COMPROMISED,
        RESULT_UNAUTHORIZED_ACCESS,
        RESULT_TIMESTAMP_MISMATCH,
        RESULT_CONNECTION_TIMEOUT,
        RESULT_UNKNOWN_ERROR,

        RESULT_CLIENT_PROGRAM_COMPROMISED,
    };

private:
    // ������ ���� ����
    HANDLE _hReadPipe = nullptr;
    HANDLE _hWritePipe = nullptr;
    SECURITY_ATTRIBUTES _saAttr = { 0, };

    // ���μ��� ���� �� ���� ����
    STARTUPINFO _stStartupInfo = { 0, };             // ���μ��� �Ӽ� ����
    PROCESS_INFORMATION _stProcessInfo = { 0, };     // ������ ���μ����� �⺻ �����忡 ���� ����

    wchar_t _wcPath[2048] = { 0, };
    wchar_t _wcAppName[2048] = { 0, };

    // ���μ��� ���� �ڵ� �� ��ȯ ���ڿ�
    DWORD _dwRestClientExitCode = RESULT_CLIENT_PROGRAM_COMPROMISED;
    wchar_t _wcRestClientMessage[2048] = { 0, };

public:
    RestClient();
    ~RestClient();

    // --------------- RestClient ���μ��� ���� ---------------
    DWORD GetRestClientExitCode();
    void GetRestClientMessage(WCHAR* wcBuffer, rsize_t nBufferSizeInWords);
    

    // --------------- RestClient ��û ��� ---------------
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

