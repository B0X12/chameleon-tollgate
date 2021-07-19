#pragma once

#define LOGFILE_NAME		"C:\\Tollgate\\log\\log.txt"

namespace LOG {
	enum class ALERT {
		ALERT_INFO,
		ALERT_WARN,
		ALERT_ERROR,
	};

	enum class AUTH_METHOD {
		AUTH_METHOD_USB,
		AUTH_METHOD_OTP,
		AUTH_METHOD_PATTERN,
		AUTH_METHOD_FINGERPRINT,
		AUTH_METHOD_FACE,
		AUTH_METHOD_PASSWORD,
	};
}


void Log(LOG::ALERT alert, LOG::AUTH_METHOD method, wchar_t* message);
void WriteLogFile(const wchar_t* szString);