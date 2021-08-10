package com.chameleon.tollgate.viewitem;

import lombok.Getter;

@Getter
public enum HistoryTitle {
    FACE("얼굴 인증"),
    PATTERN("패턴 인증"),
    FINGERPRINT("지문 인증"),
    USB("USB 인증"),
    OTP("OTP 인증"),
    QR("QR 인증");

    private final String text;
    private HistoryTitle(String text) {
        this.text = text;
    }
}
