/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fb2scanner.parsers;

import com.mycompany.fb2scanner.exceptions.FB2ScannerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author dmalahov
 */
public class ZIPUtils {

    public static File getZIPContent(File f) throws FB2ScannerException {
        int fileSignature = 0;

        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            fileSignature = raf.readInt();
        } catch (IOException e) {
            throw new FB2ScannerException("Error during checking on ZIP", e);
        }

        if (fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708) {
            try (ZipInputStream stream = new ZipInputStream(new FileInputStream(f))) {
                ZipEntry entry;
                while ((entry = stream.getNextEntry()) != null) {
                    String outpath = "./" + entry.getName();
                    try (FileOutputStream output = new FileOutputStream(outpath)) {
                        byte[] buffer = new byte[2048];
                        int len;
                        while ((len = stream.read(buffer)) > 0) {
                            output.write(buffer, 0, len);
                        }
                    } catch (IOException ex) {
                        throw new FB2ScannerException("Error during reading ZIP", ex);
                    }

                    return new File(outpath);
                }
            } catch (IOException e) {
                throw new FB2ScannerException("Error during reading ZIP", e);
            }
        }
        return f;
    }
}
