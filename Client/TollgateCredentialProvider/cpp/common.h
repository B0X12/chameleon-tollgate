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

    /* --------------- USB ���� ���� --------------- */
    SFI_USB_MESSAGE             ,
    SFI_USB_VERIFY              ,

    /* --------------- ���� ���� ���� --------------- */
    SFI_PATTERN_MESSAGE         ,
    SFI_PATTERN_REQUEST         ,

    /* --------------- QR ���� ���� --------------- */
    SFI_QR_MESSAGE,
    SFI_QR_REQUEST,

    /* --------------- ���� ���� ���� --------------- */
    SFI_FINGERPRINT_MESSAGE     ,
    SFI_FINGERPRINT_REQUEST     ,

    /* --------------- �ȸ� ���� ���� --------------- */
    SFI_FACE_MESSAGE            ,
    SFI_FACE_REQUEST            ,

    /* --------------- �н����� ���� �� OTP ���� --------------- */
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

    /* --------------- USB ���� ���� --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_USB_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_USB_VERIFY

    /* --------------- ���� ���� ���� --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_PATTERN_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_PATTERN_REQUEST

    /* --------------- QR ���� ���� --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_QR_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_QR_REQUEST

    /* --------------- ���� ���� ���� --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_FINGERPRINT_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_FINGERPRINT_REQUEST

    /* --------------- �ȸ� ���� ���� --------------- */
    { CPFS_HIDDEN,            CPFIS_NONE       },      // SFI_FACE_MESSAGE
    { CPFS_HIDDEN,            CPFIS_DISABLED       },      // SFI_FACE_REQUEST

    /* --------------- �н����� ���� �� OTP ���� --------------- */
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

    { SFI_STAGE_STATUS,         CPFT_LARGE_TEXT,        L"���� �޽���"                                     },

    /* --------------- USB ���� --------------- */
    { SFI_USB_MESSAGE,          CPFT_LARGE_TEXT,        L"USB �޽���"                                     },
    { SFI_USB_VERIFY,           CPFT_COMMAND_LINK,      L"USB ����",       CPFG_STYLE_LINK_AS_BUTTON      },
    
    /* --------------- ���� ���� ���� --------------- */
    { SFI_PATTERN_MESSAGE,      CPFT_LARGE_TEXT,        L"���� �޽���"                                     },
    { SFI_PATTERN_REQUEST,      CPFT_COMMAND_LINK,      L"���� ��û",       CPFG_STYLE_LINK_AS_BUTTON      },

    /* --------------- QR ���� ���� --------------- */
    { SFI_QR_MESSAGE,           CPFT_LARGE_TEXT,        L"QR �޽���"                                     },
    { SFI_QR_REQUEST,           CPFT_COMMAND_LINK,      L"QR ��û",       CPFG_STYLE_LINK_AS_BUTTON      },

    /* --------------- ���� ���� ���� --------------- */
    { SFI_FINGERPRINT_MESSAGE,  CPFT_LARGE_TEXT,        L"���� �޽���"                                     },
    { SFI_FINGERPRINT_REQUEST,  CPFT_COMMAND_LINK,      L"���� ��û",       CPFG_STYLE_LINK_AS_BUTTON     },

    /* --------------- �ȸ� ���� ���� --------------- */
    { SFI_FACE_MESSAGE,         CPFT_LARGE_TEXT,        L"�ȸ� �޽���"                                     },
    { SFI_FACE_REQUEST,         CPFT_COMMAND_LINK,      L"�ȸ� ��û",       CPFG_STYLE_LINK_AS_BUTTON     },

    /* --------------- �н����� ���� �� OTP ���� --------------- */
    { SFI_OTP_MESSAGE,          CPFT_LARGE_TEXT,        L"OTP �޽���"                                     },
    { SFI_PASSWORD_INPUT,       CPFT_PASSWORD_TEXT,     L"�н����� �Է�"                                   },
    { SFI_PASSWORD_VERIFY,      CPFT_SUBMIT_BUTTON,     L"Submit"                                        },
    { SFI_OTP_INPUT,            CPFT_EDIT_TEXT,         L"OTP �Է�",                                      },
};

static const PWSTR s_rgComboBoxStrings[] =
{
    L"First",
    L"Second",
    L"Third",
};
