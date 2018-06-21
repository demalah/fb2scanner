/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.parsers;

import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import com.mycompany.fb2scanner.format.FictionBook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author dmalahov
 */
public class JAXBParser implements Parser {

    private Unmarshaller jaxbUnmarshaller;

    public JAXBParser() throws FB2ScannerException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FictionBook.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException ex) {
            throw new FB2ScannerException("Error during init JAXB-context", ex);
        }
    }

    public FictionBook getBook(File f) throws FB2ScannerException {
        try {
            return (FictionBook) jaxbUnmarshaller.unmarshal(f);
        } catch (JAXBException ex) {
            throw new FB2ScannerException("Error during unmarshal file " + f.getName(), ex);
        }
    }

    @Override
    public void parse(List<FileStorage> files) throws FB2ScannerException {
        for (FileStorage f : files) {
            try {
                File file = ZIPUtils.getZIPContent(f.getFile());
                FictionBook fb = getBook(file);
                String title = fb.getDescription().getTitleInfo().getBookTitle().getValue();
                String genre = fb.getDescription().getTitleInfo().getGenre().get(0).getValue().name();
                System.out.println("<" + genre + ">.<" + title + ">");
                if (f.fromZIP()) {
                    Files.delete(file.toPath());
                }
            } catch (FB2ScannerException ex) {
                throw ex;
            } catch (IOException ex) {
                throw new FB2ScannerException("Error during delete unZIP file " + f.getFile().getName(), ex);
            }
        }
    }
}
