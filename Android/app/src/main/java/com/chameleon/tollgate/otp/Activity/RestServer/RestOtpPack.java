package com.chameleon.tollgate.otp.Activity.RestServer;
import lombok.Getter;

@Getter
public class RestOtpPack
{
    private String userId;
    private long timestamp;
    private String data;

    public RestOtpPack(final String userId, final long timestamp, final String data)
    {
        this.userId = userId;
        this.timestamp = timestamp;
        if(data == null)
            this.data = null;
        else
            this.data = data;
    }
}
