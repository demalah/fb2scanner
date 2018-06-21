/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.exceptions;

/**
 *
 * @author dmalahov
 */
public class FB2ScannerException extends Exception {

    public FB2ScannerException(String message) {
        super(message);
    }

    public FB2ScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
