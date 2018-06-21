/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.configuration;

/**
 *
 * @author dmalahov
 */
public enum Property {

    CONSOLE_MODE("console.mode", "1"),
    CHECK_SUBDIR("check.subdir", "0"),
    OUT_FILE("out.filename", "list.txt"),
    PARSE_MODE("parse.mode", ParseMode.SAX.name()),
    CHECK_ZIP("check.zip", "0");

    private final String key, defaultValue;

    private Property(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
