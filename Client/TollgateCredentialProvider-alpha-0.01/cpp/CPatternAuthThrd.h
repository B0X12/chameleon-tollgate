#pragma once

#include <Windows.h>
#include "CTollgateCredential.h"


class CPatternAuthThrd
{
public:
    CPatternAuthThrd(void);
    ~CPatternAuthThrd(void);
    HRESULT StartPatternAuthThread(CTollgateCredential* pCredential);

private:
    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);

    CTollgateCredential* _pCredential;
};

