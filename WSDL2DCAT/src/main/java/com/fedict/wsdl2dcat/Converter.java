/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

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
    private final String inputDir;
    private final String stylesheetDir;
    private final String fileType;

    private final String inputDirFamilies;
    private final String fileTypeFamilies;

    /**
     * Default constructor. Sets default output and stylesheet directory.
     */
    public Converter() {
        String currentPath = System.getProperty("user.dir") + "\\src\\files";
        this.inputDir = currentPath + "\\Input\\WSDL\\";
        this.outputDir = currentPath + "\\Output\\DCAT\\";
        this.stylesheetDir = currentPath + "\\Transformation\\XSL\\";
        this.fileType = "wsdl";

        this.inputDirFamilies = currentPath + "\\Input\\FAMILIES\\";
        this.fileTypeFamilies = "xml";
    }

    public void convertToDCAT() {
        convertToDCAT(this.inputDir, this.fileType, this.outputDir, this.stylesheetDir);
    }

    /**
     * Convert files with the file type to output directory
     *
     * @param inputDir
     * @param fileType file type of the converted files
     */
    public void convertToDCAT(String inputDir) {
        convertToDCAT(inputDir, this.fileType, this.outputDir, this.stylesheetDir);
    }

    /**
     * Convert files with the file type to output directory
     *
     * @param inputDir
     * @param fileType file type of the converted files
     */
    public void convertToDCAT(String inputDir, String fileType) {
        convertToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir);
    }

    /**
     * Convert files with the file type to output directory
     *
     * @param inputDir
     * @param fileType file type of the converted files
     */
    public void convertToDCAT(String inputDir, String fileType, String outputDir) {
        convertToDCAT(inputDir, fileType, outputDir, this.stylesheetDir);
    }

    /**
     * *
     *
     * @param inputDir
     * @param fileType
     * @param outputDir
     * @param stylesheetDir
     */
    public void convertToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, fileType);
    }

    public void convertFamiliesToDCAT() {
        convertToDCAT(this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "prefix_catalog_xml");
        convertToDCAT(this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "dataset_xml");
        convertToDCAT(this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "distribution_xml");

    }

    public void convertFamiliesToDCAT(String inputDir) {
        convertToDCAT(inputDir, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "xmla");
        convertToDCAT(inputDir, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "xmlb");
        convertToDCAT(inputDir, this.fileTypeFamilies, this.outputDir, this.stylesheetDir, "xmlc");
    }

    public void convertFamiliesToDCAT(String inputDir, String fileType) {
        convertToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir, "xmla");
        convertToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir, "xmlb");
        convertToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir, "xmlc");
    }

    public void convertFamiliesToDCAT(String inputDir, String fileType, String outputDir) {
        convertToDCAT(inputDir, fileType, outputDir, this.stylesheetDir, "xmla");
        convertToDCAT(inputDir, fileType, outputDir, this.stylesheetDir, "xmlb");
        convertToDCAT(inputDir, fileType, outputDir, this.stylesheetDir, "xmlc");
    }

    public void convertFamiliesToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "xmla");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "xmlb");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "xmlc");
    }

    /**
     * Converts files with file type to DCAT files
     *
     * @param inputDir
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     */
    /**
     * Converts files with file type to DCAT files
     *
     * @param inputDir
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     * @param stylesheetFileName
     */
    public void convertToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir, String stylesheetFileName) {
        OutputStream DCATfile = null;
        try {
            createDirectoryIfNeeded(inputDir);
            createDirectoryIfNeeded(outputDir);
            File[] files = new File(inputDir).listFiles();
            int count = getCountOfType(files, fileType);
            if (count == 0) {
                System.out.println("No " + fileType + " file found in directory: " + inputDir);
            } else {
                System.out.println("Found " + count + " " + fileType + " file(s).");
                System.out.println("Conversion has been started.");

                for (File file : files) {
                    if (file.isFile() && getExtension(file).equals(fileType)) {
                        String DCATfileName = outputDir + "\\" + removeExtension(file) + "_" + stylesheetFileName + ".dcat";
                        String StylesheetFileName = stylesheetDir + "\\" + stylesheetFileName + "2dcat.xsl";
                        TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
                        Source xslDoc = new StreamSource(StylesheetFileName);
                        Source xmlDoc = new StreamSource(file);
                        DCATfile = new FileOutputStream(DCATfileName);
                        Transformer trasform = tFactory.newTransformer(xslDoc);
                        trasform.transform(xmlDoc, new StreamResult(DCATfile));
                        System.out.println("DEBUG: File should be made: " + DCATfileName);
                    }
                }
                System.out.println("File(s) have been converted to DCAT.");
                System.out.println("DCAT files can be found in: \n" + outputDir);
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
     * Counts the amount of files according to given type
     *
     * @param files Files to work with
     * @param type The type of files
     * @return Amount of files according to type
     */
    private int getCountOfType(File[] files, String type) {
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
     * @param file file to get the extension
     * @return Extension of the file
     */
    private String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * Removes extension from a file
     *
     * @param file file to remove the extension from
     * @return name of the file without the extension
     */
    private String removeExtension(File file) {
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        return fname;
    }
}
