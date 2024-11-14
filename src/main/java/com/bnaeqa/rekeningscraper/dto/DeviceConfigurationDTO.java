package com.bnaeqa.rekeningscraper.dto;

import com.bnaeqa.rekeningscraper.util.Constants;

public class DeviceConfigurationDTO {
    public String perangkat;
    public String cryptoType;
    public String referralCode;

    public String getPerangkat() {
        return perangkat;
    }

    public void setPerangkat(String perangkat) {
        this.perangkat = perangkat;
    }

    public String getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(String cryptoType) {
        this.cryptoType = cryptoType;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_password() {
        return email_password;
    }

    public void setEmail_password(String email_password) {
        this.email_password = email_password;
    }

    public String getDc_x_username() {
        return dc_x_username;
    }

    public void setDc_x_username(String dc_x_username) {
        this.dc_x_username = dc_x_username;
    }

    public String getDc_x_password() {
        return dc_x_password;
    }

    public void setDc_x_password(String dc_x_password) {
        this.dc_x_password = dc_x_password;
    }

    public String email;
    public String email_password;
    public String dc_x_username;
    public String dc_x_password;

    public DeviceConfigurationDTO(String perangkat, String cryptoType, String referralCode, String email, String email_password) {
        this.perangkat = perangkat;
        this.cryptoType = cryptoType;
        this.referralCode = referralCode;
        this.email = email;
        this.email_password = email_password;
        this.dc_x_username = referralCode + email.split("@")[0] + Constants.currentDate();
        this.dc_x_password = email_password + "123!";
    }
}
