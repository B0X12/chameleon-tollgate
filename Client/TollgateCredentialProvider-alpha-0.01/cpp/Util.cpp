#include "Util.h"
#include <WTypesbase.h>
#include <stdio.h>


void Log(LOG::ALERT alert, LOG::AUTH_METHOD method, wchar_t* message)
{
	wchar_t wszLogContents[1024] = { 0, };

	// --------------- 시간 정보 쓰기 ---------------
	SYSTEMTIME st;
	GetSystemTime(&st);
	swprintf_s(wszLogContents, ARRAYSIZE(wszLogContents), L"%04d/%02d/%02d %02d:%02d:%02d", st.wYear, st.wMonth, st.wDay, st.wHour, st.wMinute, st.wSecond);
	wcscat_s(wszLogContents, L";");

	// --------------- 경고 종류 쓰기 ---------------
	switch (alert)
	{
	case LOG::ALERT::ALERT_INFO:
		wcscat_s(wszLogContents, L"INFO");
		break;
	case LOG::ALERT::ALERT_WARN:
		wcscat_s(wszLogContents, L"WARN");
		break;
	case LOG::ALERT::ALERT_ERROR:
		wcscat_s(wszLogContents, L"ERROR");
		break;
	}
	wcscat_s(wszLogContents, L";");

	// --------------- 인증 종류 쓰기 ---------------
	switch (method)
	{
	case LOG::AUTH_METHOD::AUTH_METHOD_USB:
		wcscat_s(wszLogContents, L"USB");
		break;
	case LOG::AUTH_METHOD::AUTH_METHOD_OTP:
		wcscat_s(wszLogContents, L"OTP");
		break;
	case LOG::AUTH_METHOD::AUTH_METHOD_PATTERN:
		wcscat_s(wszLogContents, L"PATTERN");
		break;
	case LOG::AUTH_METHOD::AUTH_METHOD_FINGERPRINT:
		wcscat_s(wszLogContents, L"FINGERPRINT");
		break;
	case LOG::AUTH_METHOD::AUTH_METHOD_FACE:
		wcscat_s(wszLogContents, L"FACE");
		break;
	case LOG::AUTH_METHOD::AUTH_METHOD_PASSWORD:
		wcscat_s(wszLogContents, L"PASSWORD");
		break;
	}
	wcscat_s(wszLogContents, L";");

	// --------------- 메시지 종류 쓰기 ---------------
	wcscat_s(wszLogContents, message);

	WriteLogFile(wszLogContents);
}

void WriteLogFile(const wchar_t* szString)
{
	FILE* pFile;
	if (fopen_s(&pFile, LOGFILE_NAME, "a") == 0)
	{
		fwprintf(pFile, L"%s\n", szString);
		fclose(pFile);
	}
}