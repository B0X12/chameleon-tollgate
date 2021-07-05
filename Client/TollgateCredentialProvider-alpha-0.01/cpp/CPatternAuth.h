#pragma once

#include <Windows.h>
#include "CTollgateCredential.h"


class CPatternAuth
{
public:
    CPatternAuth(void);
    ~CPatternAuth(void);
    HRESULT InitAuthThread(CTollgateCredential* pCredential);

private:
    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);

    CTollgateCredential* _pCredential;
};

