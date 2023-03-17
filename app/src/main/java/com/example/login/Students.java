package com.example.login;

import java.util.Date;

public class Students {
    String name;



    String username;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEdit_cnfrm_password() {
        return edit_cnfrm_password;
    }

    public String getCrstartdate() {
        return crstartdate;
    }

    public String  getCrsenddate() {
        return crsenddate;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEdit_cnfrm_password(String edit_cnfrm_password) {
        this.edit_cnfrm_password = edit_cnfrm_password;
    }

    public void setCrstartdate(String crstartdate) {
        this.crstartdate = crstartdate;
    }

    public void setCrsenddate(String crsenddate) {
        this.crsenddate = crsenddate;
    }

    public Students(String name, String email, String password, String edit_cnfrm_password, String  crstartdate, String crsenddate,String username) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.edit_cnfrm_password = edit_cnfrm_password;
        this.crstartdate = crstartdate;
        this.crsenddate = crsenddate;
        this.username=username;
    }

    String email;
    String password;
    String edit_cnfrm_password;
    String crstartdate;
    String  crsenddate;

}
