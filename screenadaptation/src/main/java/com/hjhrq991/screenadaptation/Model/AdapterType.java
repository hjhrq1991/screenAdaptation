package com.hjhrq991.screenadaptation.Model;

/**
 * @author hjhrq1991 created at 2017/9/11 15 42.
 * @Package com.hjhrq991.screenadaptation.Model
 * @Description:
 */

public class AdapterType {

    private String title;
    private int type;

    public AdapterType(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public AdapterType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
