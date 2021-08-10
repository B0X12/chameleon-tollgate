package com.chameleon.tollgate.viewitem;

import com.chameleon.tollgate.R;

import lombok.Getter;

@Getter
public enum HistoryIcon {
    FACE(R.drawable.main_log_faceid_ic_group),
    PATTERN(R.drawable.main_log_pattern_ic_group),
    FINGERPRINT(R.drawable.main_log_finger_ic_group),
    OTP(R.drawable.main_log_otp_ic_group),
    USB(R.drawable.main_log_usb_ic_group),
    QR(R.drawable.main_log_qr_ic_group);

    private final int authResource;
    private HistoryIcon(int authResource) {
        this.authResource = authResource;
    }
}
