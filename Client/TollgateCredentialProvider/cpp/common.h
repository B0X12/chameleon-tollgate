//
// THIS CODE AND INFORMATION IS PROVIDED "AS IS" WITHOUT WARRANTY OF
// ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO
// THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// Copyright (c) Microsoft Corporation. All rights reserved.
//
// This file contains some global variables that describe what our
// sample tile looks like.  For example, it defines what fields a tile has
// and which fields show in which states of LogonUI. This sample illustrates
// the use of each UI field type.

#pragma once
#include "helpers.h"

// The indexes of each of the fields in our credential provider's tiles. Note that we're
// using each of the nine available field types here.
enum SAMPLE_FIELD_ID
{
    SFI_TILEIMAGE               = 0,
    SFI_LABEL                   ,

    SFI_STAGE_STATUS            ,

    /* --------------- USB 인증 관련 --------------- */
    SFI_USB_MESSAGE             ,
    SFI_USB_VERIFY              ,

    /* --------------- 패턴 인증 관련 --------------- */
    SFI_PATTERN_MESSAGE         ,
    SFI_PATTERN_REQUEST         ,

    /* --------------- QR 인증 관련 --------------- */
    SFI_QR_MESSAGE,
    SFI_QR_REQUEST,

    /* --------------- 지문 인증 관련 --------------- */
    SFI_FINGERPRINT_MESSAGE     ,
    SFI_FINGERPRINT_REQUEST     ,

    /* --------------- 안면 인증 관련 --------------- */
    SFI_FACE_MESSAGE            ,
    SFI_FACE_REQUEST            ,

    /* --------------- 패스워드 인증 및 OTP 관련 --------------- */
    SFI_OTP_MESSAGE             ,
    SFI_PASSWORD_INPUT          ,
    SFI_PASSWORD_VERIFY         ,
    SFI_OTP_INPUT               ,

    SFI_NUM_FIELDS              ,  // Note: if new fields are added, keep NUM_FIELDS last.  This is used as a count of the number of fields
};

// The first value indicates when the tile is displayed (selected, not selected)
// the second indicates things like whether the field is enabled, whether it has key focus, etc.
struct FIELD_STATE_PAIR
{
    CREDENTIAL_PROVIDER_FIELD_STATE cpfs;
    CREDENTIAL_PROVIDER_FIELD_INTERACTIVE_STATE cpfis;
};

// These two arrays are seperate because a credential provider might
// want to set up a credential with various combinations of field state pairs
// and field descriptors.

// The field state value indicates whether the field is displayed
// in the selected tile, the deselected tile, or both.
// The Field interactive state indicates when
static const FIELD_STATE_PAIR s_rgFieldStatePairs[] =
{
    { CPFS_DISPLAY_IN_BOTH,   CPFIS_NONE       },      // SFI_TILEIMAGE
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_LABEL

    { CPFS_DISPLAY_IN_BOTH,   CPFIS_NONE       },      // SFI_STAGE_STATUS

    /* --------------- USB 인증 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_USB_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_USB_VERIFY

    /* --------------- 패턴 인증 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_PATTERN_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_PATTERN_REQUEST

    /* --------------- QR 인증 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_QR_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_QR_REQUEST

    /* --------------- 지문 인증 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_FINGERPRINT_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_FINGERPRINT_REQUEST

    /* --------------- 안면 인증 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_FACE_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_FACE_REQUEST

    /* --------------- 패스워드 인증 및 OTP 관련 --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_OTP_MESSAGE
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_PASSWORD_INPUT
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_PASSWORD_VERIFY
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_OTP_INPUT
};

// Field descriptors for unlock and logon.
// The first field is the index of the field.
// The second is the type of the field.
// The third is the name of the field, NOT the value which will appear in the field.
static const CREDENTIAL_PROVIDER_FIELD_DESCRIPTOR s_rgCredProvFieldDescriptors[] =
{
    { SFI_TILEIMAGE,            CPFT_TILE_IMAGE,        L"Image",          CPFG_CREDENTIAL_PROVIDER_LOGO  },
    { SFI_LABEL,                CPFT_SMALL_TEXT,        L"Tooltip",        CPFG_CREDENTIAL_PROVIDER_LABEL },

    { SFI_STAGE_STATUS,         CPFT_LARGE_TEXT,        L"패턴 메시지"                                     },

    /* --------------- USB 관련 --------------- */
    { SFI_USB_MESSAGE,          CPFT_LARGE_TEXT,        L"USB 메시지"                                     },
    { SFI_USB_VERIFY,           CPFT_COMMAND_LINK,      L"USB 인증",       CPFG_STYLE_LINK_AS_BUTTON      },
    
    /* --------------- 패턴 인증 관련 --------------- */
    { SFI_PATTERN_MESSAGE,      CPFT_LARGE_TEXT,        L"패턴 메시지"                                     },
    { SFI_PATTERN_REQUEST,      CPFT_COMMAND_LINK,      L"패턴 요청",       CPFG_STYLE_LINK_AS_BUTTON      },

    /* --------------- QR 인증 관련 --------------- */
    { SFI_QR_MESSAGE,           CPFT_LARGE_TEXT,        L"QR 메시지"                                     },
    { SFI_QR_REQUEST,           CPFT_COMMAND_LINK,      L"QR 요청",       CPFG_STYLE_LINK_AS_BUTTON      },

    /* --------------- 지문 인증 관련 --------------- */
    { SFI_FINGERPRINT_MESSAGE,  CPFT_LARGE_TEXT,        L"지문 메시지"                                     },
    { SFI_FINGERPRINT_REQUEST,  CPFT_COMMAND_LINK,      L"지문 요청",       CPFG_STYLE_LINK_AS_BUTTON     },

    /* --------------- 안면 인증 관련 --------------- */
    { SFI_FACE_MESSAGE,         CPFT_LARGE_TEXT,        L"안면 메시지"                                     },
    { SFI_FACE_REQUEST,         CPFT_COMMAND_LINK,      L"안면 요청",       CPFG_STYLE_LINK_AS_BUTTON     },

    /* --------------- 패스워드 인증 및 OTP 관련 --------------- */
    { SFI_OTP_MESSAGE,          CPFT_LARGE_TEXT,        L"OTP 메시지"                                     },
    { SFI_PASSWORD_INPUT,       CPFT_PASSWORD_TEXT,     L"패스워드 입력"                                   },
    { SFI_PASSWORD_VERIFY,      CPFT_SUBMIT_BUTTON,     L"Submit"                                        },
    { SFI_OTP_INPUT,            CPFT_EDIT_TEXT,         L"OTP 입력",                                      },
};

static const PWSTR s_rgComboBoxStrings[] =
{
    L"First",
    L"Second",
    L"Third",
};
