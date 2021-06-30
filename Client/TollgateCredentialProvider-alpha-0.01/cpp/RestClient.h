#pragma once

#include <Windows.h>


class RestClient
{
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
    //wchar_t _wcCommandLine[2048] = { 0, };

    // ���μ��� ���� �ڵ� �� ��ȯ ���ڿ�
    DWORD _dwRestClientExitCode = 4444;
    wchar_t _wcRestClientStatusCode[30] = { 0, };
    wchar_t _wcRestClientMessage[2048] = { 0, };

public:
    RestClient();
    RestClient(wchar_t wcPath, wchar_t wcAppName);
    ~RestClient();
    DWORD GetRestClientExitCode();
    void GetRestClientStatusCode(wchar_t* wcBuffer, rsize_t nBufferSizeInWords);
    void GetRestClientMessage(wchar_t* wcBuffer, rsize_t nBufferSizeInWords);

    BOOL RequestUSBVerification(wchar_t* user, wchar_t* usb_info);
    BOOL RequestPatternVerification(wchar_t* user);

private:
    BOOL _ExecuteRestClientProcess(wchar_t* wcCommandLine);
};

