package com.SemiColon.Hmt.elengaz.API.Model;

import java.io.Serializable;

/**
 * Created by Elashry on 2/10/2018.
 */

public class Services implements Serializable{
    private String service_id;
    private String service_title;
    private Integer success;

    public Services(String service_id, String service_title) {
        this.service_id = service_id;
        this.service_title = service_title;
    }

    public Services() {
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }
}
