/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.parsers;

import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author dmalahov
 */
public class SAXParser implements Parser {

    private javax.xml.parsers.SAXParser parser;
    private FB2Handler handler;

    public SAXParser() throws FB2ScannerException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            handler = new FB2Handler();
        } catch (ParserConfigurationException | SAXException ex) {
            throw new FB2ScannerException("Error during init SAX-factory", ex);
        }
    }

    @Override
    public void parse(List<FileStorage> files) throws FB2ScannerException {
        for (FileStorage f : files) {
            try {
                File file = ZIPUtils.getZIPContent(f.getFile());
                parser.parse(file, handler);
                handler.printBook();
                if (f.fromZIP()) {
                    Files.delete(file.toPath());
                }
            } catch (SAXException | IOException ex) {
                throw new FB2ScannerException("Error during parsing file " + f.getFile().getName(), ex);
            }
        }
    }

    public class FB2Handler extends DefaultHandler {

        public class Book {

            private String title;
            private final List<String> authors;

            public Book() {
                authors = new ArrayList<>();
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void addAuthor(String author) {
                authors.add(author);
            }

            public void clearAuthors() {
                authors.clear();
            }

            @Override
            public String toString() {
                return "<" + getAuthors() + ">.<" + title + ">";
            }

            private String getAuthors() {
                return String.join(", ", authors);
            }
        }

        String firstName;
        Boolean bBookTitle = false, bFirstName = false, bLastName = false, eAuthor = false;
        Book book = new Book();

        public void printBook() {
            System.out.println(book.toString());
            book.clearAuthors();
            eAuthor = false;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("book-title")) {
                bBookTitle = true;
            }
            if (qName.equalsIgnoreCase("first-name")) {
                bFirstName = true;
            }
            if (qName.equalsIgnoreCase("last-name")) {
                bLastName = true;
            }
            if (qName.equalsIgnoreCase("document-info")) {
                eAuthor = true;
            }
        }

        @Override
        public void characters(char[] c, int start, int length) throws SAXException {
            if (bBookTitle) {
                book.setTitle(new String(c, start, length));
                bBookTitle = false;
            }
            if (bFirstName) {
                firstName = new String(c, start, length);
                bFirstName = false;
            }
            if (bLastName) {
                if (!eAuthor) {
                    book.addAuthor(firstName + " " + new String(c, start, length));
                }
                bLastName = false;
            }
        }
    }
}
