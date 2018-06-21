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
public interface PropertyAccessor {
    default String get(Property p) {
        return p.getDefaultValue();
    }
}
