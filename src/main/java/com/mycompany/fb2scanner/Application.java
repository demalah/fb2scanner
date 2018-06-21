/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner;

import com.mycompany.fb2scanner.configuration.ConfigStorage;
import com.mycompany.fb2scanner.configuration.Property;
import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmalahov
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private final ConfigStorage cfg;

    public Application() {
        this.cfg = new ConfigStorage();
    }

    public void run() {
        try {
            Processor proc = Processor.newBuilder()
                    .setOut(cfg.get(Property.CONSOLE_MODE), cfg.get(Property.OUT_FILE))
                    .setFileList(cfg.get(Property.CHECK_SUBDIR), cfg.get(Property.CHECK_ZIP))
                    .setParser(cfg.get(Property.PARSE_MODE))
                    .build();

            proc.run();
        } catch (FB2ScannerException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex.getCause());
        }
    }
}
