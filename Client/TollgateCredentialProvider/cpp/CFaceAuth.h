#pragma once

#include <Windows.h>
#include "CTollgateCredential.h"


class CFaceAuth
{
public:
    CFaceAuth(void);
    ~CFaceAuth(void);
    HRESULT InitAuthThread(CTollgateCredential* pCredential);

private:
    static DWORD WINAPI     _ThreadProc(LPVOID lpParameter);

    CTollgateCredential* _pCred;
};

