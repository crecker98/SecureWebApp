package com.soriani.securewebapp.business;

import java.io.Serializable;

/**
 * @author christiansoriani on 25/10/22
 * @project SecureWebApp
 */
public class CustomCookie implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2094568555308107344L;

    private byte[] key;

    private byte[] value;

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
