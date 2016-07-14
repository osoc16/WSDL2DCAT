/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Miguel
 * @author Umut
 */
public class Converter {

    // Default paths
    private static final String WSDLPATH = "\\files\\Input\\WSDL\\";
    private static final String XSDPATH = "\\files\\Input\\XSD\\";
    private static final String DCATPATH = "\\files\\Output\\DCAT\\";
    private static final String XSLPATH = "\\files\\Transformation\\XSL\\";
    private static final String FAMILIESPATH = "\\files\\Input\\FAMILIES\\";

    private String outputDir;
    private String fileType;
    private String inputDir;
    private String currentPath;
    private String stylesheetDir;

    private final String inputDirFamilies;
    private final String fileTypeFamilies;
    private final String inputDirXsd;
    private final String fileTypeXsd;

    /**
     * Default constructor. Sets default input, output and stylesheet directory.
     */
    public Converter() {

        this.inputDir = currentPath + WSDLPATH;
        this.outputDir = currentPath + DCATPATH;
        this.stylesheetDir = currentPath + XSLPATH;
        this.fileType = "wsdl";

        this.inputDirFamilies = currentPath + FAMILIESPATH;
        this.fileTypeFamilies = "xml";

        this.inputDirXsd = currentPath + XSDPATH;
        this.fileTypeXsd = "xsd";

        createDirectoryIfNeeded(inputDir);
        createDirectoryIfNeeded(outputDir);
        createDirectoryIfNeeded(stylesheetDir);

    }

    /**
     * (overload) Converts files in default folder to DCAT files
     */
    public void convertToDCAT() {
        convertToDCAT(this.inputDir, this.fileType, this.outputDir, this.stylesheetDir, this.fileType);
        convertToDCAT(this.inputDirXsd, this.fileTypeXsd, this.outputDir, this.stylesheetDir, this.fileType);
    }

    /**
     * Method used for converting files to DCAT
     *
     * @param inputDir directory from where the files will be read
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     * @param stylesheetFileName name of the stylesheet that should be used
     */
    public void convertToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir, String stylesheetFileName) {
        OutputStream DCATfile = null;
        try {
            String[] extensions = {fileType};
            /**
             * *
             * Checks if the output file is a directory
             */
            if (!new File(outputDir).isDirectory()) {
                throw new IllegalArgumentException("The output directory " + outputDir + " is not a directory");
            }
            /**
             * *
             * Checks if the input file is a directory
             */
            File inputDirectory = new File(inputDir);
            if (!inputDirectory.isDirectory()) {
                throw new IllegalArgumentException("The input directory " + inputDir + " is no directory");
            }
            Collection<File> files = FileUtils.listFiles(inputDirectory, extensions, true);
            int count = files.size();
            if (count == 0) {
                throw new IllegalArgumentException("No " + fileType + " file found in directory: " + inputDir);
            } else {
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "Found {0} {1} file(s).", new Object[]{count, fileType});
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "Conversion has been started.");
                /**
                 * *
                 * Loops over file and convert to dcat with the stylesheet for
                 * the file's format
                 */
                for (File file : files) {
                    if (file.isFile() && getExtension(file).equals(fileType)) {
                        String DCATfileName = outputDir + "\\" + removeExtension(file) + "_" + stylesheetFileName + ".dcat";
                        Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "DCATFilename: {0}", DCATfileName);
                        String StylesheetFileName = stylesheetDir + "\\" + stylesheetFileName + "2dcat.xsl";
                        TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
                        Source xslDoc = new StreamSource(StylesheetFileName);
                        Source xmlDoc = new StreamSource(file);
                        DCATfile = new FileOutputStream(DCATfileName);
                        Transformer trasform = tFactory.newTransformer(xslDoc);
                        trasform.transform(xmlDoc, new StreamResult(DCATfile));
                        Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "DEBUG: File should be made: {0}", DCATfileName);
                    }
                }
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "File(s) have been converted to DCAT.");
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "DCAT files can be found in: \n{0}", outputDir);
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "--------------------------------------------");
            }
        } catch (FileNotFoundException | IllegalArgumentException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * (overload) Converts families to DCAT using default settings
     *
     */
    public void convertFamiliesToDCAT() {
        convertFamiliesToDCAT(this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir);
    }

    public void convertFamiliesToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "prefix_catalog_xml");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "dataset_xml");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "distribution_xml");
    }

    /**
     * Creates directory if the directory doesn't exist
     *
     * @param directoryName Directory name to check
     */
    private static void createDirectoryIfNeeded(String directoryName) {
        Path path = Paths.get(directoryName);

        // if the directory does not exist, create it
        if (!Files.exists(path)) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, "Needed directory not found.");
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, "Creating directory: {0}", directoryName);
            try {
                Files.createDirectories(path);

            } catch (IOException ex) {
                Logger.getLogger(Converter.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
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

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getInputDir() {
        return inputDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getStylesheetDir() {
        return stylesheetDir;
    }

    public void setStylesheetDir(String stylesheetDir) {
        this.stylesheetDir = stylesheetDir;
    }

}
