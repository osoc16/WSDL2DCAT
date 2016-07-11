/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import static com.fedict.wsdl2dcat.WSDL2DCAT.outputDir;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Miguel
 */
public class Converter {

    private final String outputDir;
    private final String stylesheetDir;

    public Converter() {
        String currentPath = System.getProperty("user.dir") + "\\src\\files";
        this.outputDir = currentPath + "\\DCAT\\";
        this.stylesheetDir = currentPath + "\\XSL\\";
    }

    public Converter(String outputDir, String stylesheetDir) {
        this.outputDir = outputDir;
        this.stylesheetDir = stylesheetDir;
    }

    public void convertToDCAT(File[] files, String fileType) {
        convertToDCAT(files, fileType, this.outputDir, this.stylesheetDir);
    }

    /**
     * Converts collection of files to DCAT files
     *
     * @param files Files to convert
     * @param fileType The type of files
     */
    public void convertToDCAT(File[] files, String fileType, String outputDir, String stylesheetDir) {
        OutputStream DCATfile = null;
        try {
            for (File file : files) {
                if (file.isFile() && getExtension(file).equals(fileType)) {
                    String DCATfileName = outputDir + "\\" + removeExtension(file) + "_" + fileType + ".dcat";
                    String StylesheetFileName = stylesheetDir + "\\" + fileType + "2dcat.xsl";
                    TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
                    Source xslDoc = new StreamSource(StylesheetFileName);
                    Source xmlDoc = new StreamSource(file);
                    DCATfile = new FileOutputStream(DCATfileName);
                    Transformer trasform = tFactory.newTransformer(xslDoc);
                    trasform.transform(xmlDoc, new StreamResult(DCATfile));
                    System.out.println("DEBUG: File should be made: " + DCATfileName);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Gets the extension from a file
     *
     * @param file File to work with
     * @return Extension of the file
     */
    private static String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * Removes extension from a file
     *
     * @param file File to work with
     * @return Name of the file without the extension
     */
    private static String removeExtension(File file) {
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        return fname;
    }
}
