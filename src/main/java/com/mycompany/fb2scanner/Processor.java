/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner;

import com.mycompany.fb2scanner.configuration.ParseMode;
import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import com.mycompany.fb2scanner.parsers.FileStorage;
import com.mycompany.fb2scanner.parsers.JAXBParser;
import com.mycompany.fb2scanner.parsers.Parser;
import com.mycompany.fb2scanner.parsers.SAXParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author dmalahov
 */
public class Processor {

    public static final String ROOT = ".";
    public static final String EXTENSION = ".fb2";

    private List<FileStorage> files;
    private Parser parser;

    private Processor() {
    }

    public void run() throws FB2ScannerException {
        if (files.isEmpty()) {
            System.out.println("FB2 files are absent");
        } else {
            parser.parse(files);
        }
    }

    public static Builder newBuilder() {
        return new Processor().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setOut(String consoleMode, String outFilename) throws FB2ScannerException {
            if ("0".equals(consoleMode)) {
                try {
                    PrintStream out = new PrintStream(new FileOutputStream(outFilename));
                    System.setOut(out);
                } catch (FileNotFoundException ex) {
                    throw new FB2ScannerException(ex.getMessage());
                }
            }
            return this;
        }

        public Builder setFileList(String checkSubdir, String checkZIP) throws FB2ScannerException {
            try {
                Boolean toSubdir = "1".equals(checkSubdir);
                files = getFiles(".fb2", toSubdir, false);
                if ("1".equals(checkZIP)) {
                    files.addAll(getFiles(".fb2.zip", toSubdir, true));
                }
            } catch (IOException ex) {
                throw new FB2ScannerException(ex.getMessage());
            }
            return this;
        }

        private List<FileStorage> getFiles(String ext, Boolean checkSubdir, Boolean checkZIP) throws IOException {
            if (checkSubdir) {
                return Files.walk(Paths.get(ROOT)).filter(p -> p.toString().toLowerCase().endsWith(ext))
                        .map(Path::toFile)
                        .map(f -> new FileStorage(checkZIP, f))
                        .collect(Collectors.toList());
            } else {
                return Arrays.stream(new File(ROOT).listFiles(f -> f.getName().toLowerCase().endsWith(ext)))
                        .map(f -> new FileStorage(checkZIP, f))
                        .collect(Collectors.toList());
            }
        }

        public Builder setParser(String parseMode) throws FB2ScannerException {
            Processor.this.parser = parseMode.equals(ParseMode.JAXB.name()) ? new JAXBParser() : new SAXParser();
            return this;
        }

        public Processor build() {
            return Processor.this;
        }
    }
}
