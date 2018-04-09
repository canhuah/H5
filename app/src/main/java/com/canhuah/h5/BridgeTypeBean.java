package com.canhuah.h5;

import android.text.TextUtils;

public class BridgeTypeBean {


    public static final String LOGIN = "login";

    private String bridgeType;
    private String pram;
    private String message;

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getPram() {
        return pram;
    }

    public void setPram(String pram) {
        this.pram = pram;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    //回调结果成功,回调分为成功以及失败,失败需要toast对应message
    public boolean isSuccess() {
        return TextUtils.equals(pram,"1");
    }

}
