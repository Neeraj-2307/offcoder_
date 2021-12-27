package com.example.offcodercyberquest.Beans;

public class Code {
    private String code;
    private  Language language;

    public Code(String code, Language selectedLanguage) {
        this.language = selectedLanguage;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
