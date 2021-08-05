//
// THIS CODE AND INFORMATION IS PROVIDED "AS IS" WITHOUT WARRANTY OF
// ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO
// THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// Copyright (c) Microsoft Corporation. All rights reserved.
//
// CTollgateCredential is our implementation of ICredentialProviderCredential.
// ICredentialProviderCredential is what LogonUI uses to let a credential
// provider specify what a user tile looks like and then tell it what the
// user has entered into the tile.  ICredentialProviderCredential is also
// responsible for packaging up the users credentials into a buffer that
// LogonUI then sends on to LSA.

#pragma once

#include <windows.h>
#include <strsafe.h>
#include <shlguid.h>
#include <propkey.h>
#include "common.h"
#include "dll.h"
#include "resource.h"
#include "Util.h"

/*
 *  Credential Provider <-> 인증 스레드 간 상호 참조를 위한 클래스 포인터
 */
class CUSBAuth;
class CPatternAuth;
class CFaceAuth;
//class CFingerprintAuth;
//class COTPAuth;
class CQRAuth;


class CTollgateCredential : public ICredentialProviderCredential2, ICredentialProviderCredentialWithFieldOptions
{
public:
    // IUnknown
    IFACEMETHODIMP_(ULONG) AddRef()
    {
        return ++_cRef;
    }

    IFACEMETHODIMP_(ULONG) Release()
    {
        long cRef = --_cRef;
        if (!cRef)
        {
            delete this;
        }
        return cRef;
    }

    IFACEMETHODIMP QueryInterface(_In_ REFIID riid, _COM_Outptr_ void **ppv)
    {
        static const QITAB qit[] =
        {
            QITABENT(CTollgateCredential, ICredentialProviderCredential), // IID_ICredentialProviderCredential
            QITABENT(CTollgateCredential, ICredentialProviderCredential2), // IID_ICredentialProviderCredential2
            QITABENT(CTollgateCredential, ICredentialProviderCredentialWithFieldOptions), //IID_ICredentialProviderCredentialWithFieldOptions
            {0},
        };
        return QISearch(this, qit, riid, ppv);
    }
  public:
    // ICredentialProviderCredential
    IFACEMETHODIMP Advise(_In_ ICredentialProviderCredentialEvents *pcpce);
    IFACEMETHODIMP UnAdvise();

    IFACEMETHODIMP SetSelected(_Out_ BOOL *pbAutoLogon);
    IFACEMETHODIMP SetDeselected();

    IFACEMETHODIMP GetFieldState(DWORD dwFieldID,
                                 _Out_ CREDENTIAL_PROVIDER_FIELD_STATE *pcpfs,
                                 _Out_ CREDENTIAL_PROVIDER_FIELD_INTERACTIVE_STATE *pcpfis);

    IFACEMETHODIMP GetStringValue(DWORD dwFieldID, _Outptr_result_nullonfailure_ PWSTR *ppwsz);
    IFACEMETHODIMP GetBitmapValue(DWORD dwFieldID, _Outptr_result_nullonfailure_ HBITMAP *phbmp);
    IFACEMETHODIMP GetCheckboxValue(DWORD dwFieldID, _Out_ BOOL *pbChecked, _Outptr_result_nullonfailure_ PWSTR *ppwszLabel);
    IFACEMETHODIMP GetComboBoxValueCount(DWORD dwFieldID, _Out_ DWORD *pcItems, _Deref_out_range_(<, *pcItems) _Out_ DWORD *pdwSelectedItem);
    IFACEMETHODIMP GetComboBoxValueAt(DWORD dwFieldID, DWORD dwItem, _Outptr_result_nullonfailure_ PWSTR *ppwszItem);
    IFACEMETHODIMP GetSubmitButtonValue(DWORD dwFieldID, _Out_ DWORD *pdwAdjacentTo);

    IFACEMETHODIMP SetStringValue(DWORD dwFieldID, _In_ PCWSTR pwz);
    IFACEMETHODIMP SetCheckboxValue(DWORD dwFieldID, BOOL bChecked);
    IFACEMETHODIMP SetComboBoxSelectedValue(DWORD dwFieldID, DWORD dwSelectedItem);
    IFACEMETHODIMP CommandLinkClicked(DWORD dwFieldID);

    IFACEMETHODIMP GetSerialization(_Out_ CREDENTIAL_PROVIDER_GET_SERIALIZATION_RESPONSE *pcpgsr,
                                    _Out_ CREDENTIAL_PROVIDER_CREDENTIAL_SERIALIZATION *pcpcs,
                                    _Outptr_result_maybenull_ PWSTR *ppwszOptionalStatusText,
                                    _Out_ CREDENTIAL_PROVIDER_STATUS_ICON *pcpsiOptionalStatusIcon);
    IFACEMETHODIMP ReportResult(NTSTATUS ntsStatus,
                                NTSTATUS ntsSubstatus,
                                _Outptr_result_maybenull_ PWSTR *ppwszOptionalStatusText,
                                _Out_ CREDENTIAL_PROVIDER_STATUS_ICON *pcpsiOptionalStatusIcon);


    // ICredentialProviderCredential2
    IFACEMETHODIMP GetUserSid(_Outptr_result_nullonfailure_ PWSTR *ppszSid);

    // ICredentialProviderCredentialWithFieldOptions
    IFACEMETHODIMP GetFieldOptions(DWORD dwFieldID,
                                   _Out_ CREDENTIAL_PROVIDER_CREDENTIAL_FIELD_OPTIONS *pcpcfo);

  public:
    HRESULT Initialize(CREDENTIAL_PROVIDER_USAGE_SCENARIO cpus,
                       _In_ CREDENTIAL_PROVIDER_FIELD_DESCRIPTOR const *rgcpfd,
                       _In_ FIELD_STATE_PAIR const *rgfsp,
                       _In_ ICredentialProviderUser *pcpUser);
    CTollgateCredential();

    void EnableAuthStartButton(DWORD dwFieldID, BOOL bEnable);
    void SetAuthMessage(DWORD dwFieldID, LPCWSTR strMessage);
    void GoToNextAuthStage();

  private:
    void InitializeAuthStage();
    void SetCurrentAuthStage(BYTE bFlag);           // for GoToNextAuthStage()


  private:
    virtual ~CTollgateCredential();
    long                                    _cRef;
    CREDENTIAL_PROVIDER_USAGE_SCENARIO      _cpus;                                          // The usage scenario for which we were enumerated.
    CREDENTIAL_PROVIDER_FIELD_DESCRIPTOR    _rgCredProvFieldDescriptors[SFI_NUM_FIELDS];    // An array holding the type and name of each field in the tile.
    FIELD_STATE_PAIR                        _rgFieldStatePairs[SFI_NUM_FIELDS];             // An array holding the state of each field in the tile.
    PWSTR                                   _rgFieldStrings[SFI_NUM_FIELDS];                // An array holding the string value of each field. This is different from the name of the field held in _rgCredProvFieldDescriptors.
    PWSTR                                   _pszUserSid;
    PWSTR                                   _pszQualifiedUserName;                          // The user name that's used to pack the authentication buffer
    ICredentialProviderCredentialEvents2*    _pCredProvCredentialEvents;                    // Used to update fields.
                                                                                            // CredentialEvents2 for Begin and EndFieldUpdates.
    BOOL                                    _fChecked;                                      // Tracks the state of our checkbox.
    DWORD                                   _dwComboIndex;                                  // Tracks the current index of our combobox.
    bool                                    _fShowControls;                                 // Tracks the state of our show/hide controls link.
    bool                                    _fIsLocalUser;                                  // If the cred prov is assosiating with a local user tile
    

    // --------------- 인증 관련 정보 ---------------

    /*
    *  Offset: 0x80  0x40  0x20  0x10         0x08     0x04  0x02  0x01
    *  Field : -     QR    Face  Fingerprint  Pattern  OTP   USB   Password
    */
    enum AUTH_FACTOR
    {
        AUTH_FACTOR_INVALID = 0x00,
        AUTH_FACTOR_PASSWORD = 0x01,
        AUTH_FACTOR_USB = 0x02,
        AUTH_FACTOR_OTP = 0x04,
        AUTH_FACTOR_PATTERN = 0x08,
        AUTH_FACTOR_FINGERPRINT = 0x10,
        AUTH_FACTOR_FACE = 0x20,
        AUTH_FACTOR_QR = 0x40,
    };

  public:
    WCHAR wszUserName[100] = { 0, };
    WCHAR wszSystemIdentifier[100] = { 0, };

  private:
    BYTE _bAuthFactorFlag = 0;
    int _nAuthFactorCount = 0;
    AUTH_FACTOR _eCurrentAuthStage = AUTH_FACTOR_INVALID;
    int _nAuthFactorProcessCount = 0;


    CUSBAuth*            _pUSBAuth = nullptr;
    CPatternAuth*        _pPatternAuth = nullptr;
    CFaceAuth*           _pFaceAuth = nullptr;
    //CFingerprintAuth*  _pFingerprintAuth = nullptr;
    //COTPAuth*          _pOTPAuth = nullptr;
    CQRAuth*           _pQRAuth = nullptr;
};


