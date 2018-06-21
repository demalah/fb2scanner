/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.parsers;

import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import java.util.List;

/**
 *
 * @author dmalahov
 */
public interface Parser {

    void parse(List<FileStorage> files) throws FB2ScannerException;
}
