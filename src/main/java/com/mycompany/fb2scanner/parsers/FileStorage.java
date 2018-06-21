/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.parsers;

import java.io.File;

/**
 *
 * @author dmalahov
 */
public class FileStorage {
    private final Boolean fromZIP;
    private final File file;

    public FileStorage(Boolean fromZIP, File file) {
        this.fromZIP = fromZIP;
        this.file = file;
    }
    
    public Boolean fromZIP() {
        return fromZIP;
    }
    
    public File getFile() {
        return file;
    }
}
