/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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
public class WSDL2DCAT {

    static String inputDir;
    static String outputDir;
    static String stylesheetDir;
    static String messageDescriptionDir;

    InputStream is = null;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("WSDL2DCAT tool");
        String currentPath = System.getProperty("user.dir") + "\\src\\files";
        inputDir = currentPath + "\\WSDL\\";
        outputDir = currentPath + "\\DCAT\\";
        stylesheetDir = currentPath + "\\XSL\\";
        messageDescriptionDir = currentPath + "\\XSD\\";
        createDirectoryIfNeeded(inputDir);
        createDirectoryIfNeeded(outputDir);

        File[] WSDLfiles = new File(inputDir).listFiles();
        File[] XSDfiles = new File(messageDescriptionDir).listFiles();

        int wsdlCount = getCountOfType(WSDLfiles, "wsdl");
        int xsdCount = getCountOfType(XSDfiles, "xsd");

        if (wsdlCount == 0 || xsdCount == 0) {
            System.out.println("No WSDL file found in directory: " + inputDir);
            System.out.println("No XSD file found in directory: " + messageDescriptionDir);
        } else {
            System.out.println("Found " + wsdlCount + " WSDL file(s).");
            System.out.println("Found " + xsdCount + " XSD file(s).");
            System.out.println("Conversion has been started.");
            convertToDCAT(WSDLfiles, "wsdl");
            convertToDCAT(XSDfiles, "xsd");
            // (TODO) Validate each DCAT file

            System.out.println("File(s) have been converted to DCAT.");
            System.out.println("DCAT files can be found in: \n" + outputDir);
        }
    }

    /**
     * Counts the amount of files according to given type
     *
     * @param files Files to work with
     * @param type The type of files
     * @return Amount of files according to type
     */
    private static int getCountOfType(File[] files, String type) {
        int count = 0;
        for (File file : files) {
            if (file.isFile() && getExtension(file).equals(type)) {
                count++;
            }
        }
        return count;
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
     * Creates directory if the directory doesn't exist
     *
     * @param directoryName Directory name to check
     */
    private static void createDirectoryIfNeeded(String directoryName) {
        File theDir = new File(directoryName);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("Needed directory not found.");
            System.out.println("Creating directory: " + directoryName);
            theDir.mkdir();
        }
    }

    /**
     * Converts collection of files to DCAT files
     *
     * @param files Files to work with
     * @param fileType The type of files
     */
    private static void convertToDCAT(File[] files, String fileType) {
        OutputStream DCATfile = null;
        try {
            for (File file : files) {
                if (file.isFile() && getExtension(file).equals(fileType)) {
                    String DCATfileName = outputDir + "\\" + removeExtension(file) + "_" + fileType + ".dcat";
                    String StylesheetFileName = stylesheetDir + "\\" + fileType + "2dcat.xsl";
                    TransformerFactory tFactory = TransformerFactory.newInstance();
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


