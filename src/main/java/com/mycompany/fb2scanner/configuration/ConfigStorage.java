/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dmalahov
 */
public class ConfigStorage implements PropertyAccessor {

    public static final String PROPS_FILE_NAME = "app.properties";

    private static final Properties PROPS = new Properties();

    static {
        try {
            PROPS.load(new FileInputStream(new File(PROPS_FILE_NAME)));
        } catch (IOException | NullPointerException e) {
            throw new ExceptionInInitializerError("Error during loading " + PROPS_FILE_NAME);
        }
    }

    @Override
    public String get(Property p) {
        return !PROPS.containsKey(p.getKey()) ? PropertyAccessor.super.get(p) : PROPS.getProperty(p.getKey());
    }
}
