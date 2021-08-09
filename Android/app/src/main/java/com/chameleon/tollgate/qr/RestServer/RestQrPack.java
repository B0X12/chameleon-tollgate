package com.chameleon.tollgate.qr.RestServer;

import lombok.Getter;

@Getter
public class RestQrPack
{
    private String userId;
    private long timestamp;
    private String data;

    public RestQrPack(final String userId, final long timestamp,final String data)
    {
        this.userId = userId;
        this.timestamp = timestamp;
        this.data = data;
    }
}
