/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final String WSDLPATH = "files\\Input\\WSDL\\";
    private static final String XSDPATH = "files\\Input\\XSD\\";
    private static final String DCATPATH = "files\\Output\\DCAT\\";
    private static final String XSLPATH = "files\\Transformation\\XSL\\";
    private static final String FAMILIESPATH = "files\\Input\\FAMILIES\\";

    private String outputDir;
    private String fileType;
    private String inputDir;
    private String stylesheetDir;
    private String inputDirXsd;
    private String currentPath;
    private String inputDirFamilies;
    private String fileTypeXsd;

    private final String fileTypeFamilies;

    /**
     * Default constructor. Sets default input, output and stylesheet directory.
     */
    public Converter() {
        currentPath = "";
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
     * (overload) Converts files to DCAT using default settings
     *
     * @param withConfigFile true if a configuration file needs to be used
     */
    public void convertToDCAT(boolean withConfigFile) {
        convertToDCAT(withConfigFile, this.inputDir, this.fileType, this.outputDir, this.stylesheetDir, this.fileType);
        if(fileTypeXsd != fileType ){
                    convertToDCAT(withConfigFile, this.inputDirXsd, this.fileTypeXsd, this.outputDir, this.stylesheetDir, this.fileTypeXsd);

        }
    }

    /**
     * Method used for converting files to DCAT
     *
     * @param withConfigFile true if a configuration file needs to be used
     * @param inputDir directory from where the files will be read
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     * @param stylesheetFileName name of the stylesheet that should be used
     */
    public void convertToDCAT(boolean withConfigFile, String inputDir, String fileType, String outputDir, String stylesheetDir, String stylesheetFileName) {
        OutputStream DCATfile = null;
        try {
            /**
             * *
             * Checks if the files folder is a directory
             */
            if (!currentPath.equals("") && !new File(currentPath).isDirectory()) {
                throw new IllegalArgumentException("The files folder directory " + currentPath + " is not a directory");
            }
            /**
             * *
             * Checks if the output file is a directory
             */
            if (!new File(outputDir).isDirectory()) {
                throw new IllegalArgumentException("The output directory " + outputDir + " is not a directory");
            }

            /**
             * *
             * Checks if the output file is a directory
             */
            if (!new File(stylesheetDir).isDirectory()) {
                throw new IllegalArgumentException("The stylesheet directory " + stylesheetDir + " is not a directory");
            }
            /**
             * *
             * Checks if the input file is a directory
             */
            File inputDirectory = new File(inputDir);
            if (!inputDirectory.isDirectory()) {
                throw new IllegalArgumentException("The input directory " + inputDir + " is no directory");
            }
            Collection<File> files = FileUtils.listFiles(inputDirectory, new String[]{fileType}, true);
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
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "DCAT files can be found in: {0} \n", outputDir);
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, "--------------------------------------------");
            }
        } catch (FileNotFoundException | IllegalArgumentException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
            if (withConfigFile) {
                System.exit(1);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
            if (withConfigFile) {
                System.exit(1);
            }
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
            if (withConfigFile) {
                System.exit(1);
            }
        }

    }

    /**
     * (overload) Converts families to DCAT using default settings
     *
     * @param withConfigFile true if a configuration file is used
     */
    public void convertFamiliesToDCAT(boolean withConfigFile) {
        convertFamiliesToDCAT(withConfigFile, this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir);
    }

    /**
     * Method used for converting families to DCAT using provided settings
     *
     * @param withConfigFile true if a configuration file is used
     * @param inputDir directory from where the files will be read
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     */
    public void convertFamiliesToDCAT(boolean withConfigFile, String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(withConfigFile, inputDir, fileType, outputDir, stylesheetDir, "prefix_catalog_xml");
        convertToDCAT(withConfigFile, inputDir, fileType, outputDir, stylesheetDir, "dataset_xml");
        convertToDCAT(withConfigFile, inputDir, fileType, outputDir, stylesheetDir, "distribution_xml");
    }

    /**
     * Creates directory if the directory doesn't exist
     *
     * @param directoryName name of the directory to check
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

    /**
     * Getter method for output directory
     *
     * @return the output directory where the files are located
     */
    public String getOutputDir() {
        return outputDir;
    }

    /**
     * Setter method for output directory
     *
     * @param outputDir the directory that should be used for output
     */
    public void setOutputDir(String outputDir) {
        if (!outputDir.equals("")) {
            this.outputDir = outputDir;
        }
    }

    /**
     * Getter method for file type
     *
     * @return the file type
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Setter method for file type
     *
     * @param fileType the file type
     */
    public void setFileType(String fileType) {
        if (!fileType.equals("")) {
            this.fileType = fileType;
            this.fileTypeXsd = fileType;
        }
    }

    /**
     * Getter method for input directory where files are located
     *
     * @return the input directory
     */
    public String getInputDir() {
        return inputDir;
    }

    /**
     * Setter method for input directory for changing the location for reading
     * files
     *
     * @param inputDir the input directory
     */
    public void setInputDir(String inputDir) {
        if (!inputDir.equals("")) {
            this.inputDir = inputDir;
            this.inputDirXsd = inputDir;
        }
    }

    /**
     * Getter method for input directory of xsd files
     *
     * @return the input directory
     */
    public String getInputDirXsd() {
        return inputDirXsd;
    }

    /**
     * Setter method for input directory of xsd files
     *
     * @param inputDirXsd the input directory
     */
    public void setInputDirXsd(String inputDirXsd) {
        this.inputDirXsd = inputDirXsd;
    }

    /**
     * Getter method for file type that's used by xsd
     *
     * @return the file type
     */
    public String getFileTypeXsd() {
        return fileTypeXsd;
    }

    /**
     * Setter method for setting the file type used by xsd
     *
     * @param fileTypeXsd the file type
     */
    public void setFileTypeXsd(String fileTypeXsd) {
        if (!fileTypeXsd.equals("")) {
            this.fileTypeXsd = fileTypeXsd;
        }
    }

    /**
     * Setter method for changing the root path for input/output/...
     *
     * @param currentPath the path to use
     */
    public void setCurrentPath(String currentPath) {
        if (!currentPath.equals("")) {
            this.inputDir = currentPath + "\\" + WSDLPATH;
            this.outputDir = currentPath + "\\" + DCATPATH;
            this.stylesheetDir = currentPath + "\\" + XSLPATH;
            this.inputDirFamilies = currentPath + "\\" + FAMILIESPATH;
            this.inputDirXsd = currentPath + "\\" + XSDPATH;
            this.currentPath = currentPath;
        }
    }

    /**
     * Getter method for directory of stylesheets
     *
     * @return the directory of stylesheets
     */
    public String getStylesheetDir() {
        return stylesheetDir;
    }

    /**
     * Setter method for directory of stylesheets
     *
     * @param stylesheetDir the directory of stylesheets
     */
    public void setStylesheetDir(String stylesheetDir) {
        if (!stylesheetDir.equals("")) {
            this.stylesheetDir = stylesheetDir;
        }
    }
}
