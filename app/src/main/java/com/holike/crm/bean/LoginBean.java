package com.holike.crm.bean;

import java.io.Serializable;

/**
 * Created by wqj on 2017/9/28.
 */

public class LoginBean implements Serializable {

    /**
     * userId : HAO0000150626379455189770
     * cliId : 303a9dafb1d445559d78d0b03336fec0
     */

    private String userId;
    private String cliId;
    private String psw;

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCliId() {
        return cliId;
    }

    public void setCliId(String cliId) {
        this.cliId = cliId;
    }
}
