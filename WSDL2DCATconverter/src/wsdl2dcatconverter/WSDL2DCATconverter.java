/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsdl2dcatconverter;

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
public class WSDL2DCATconverter {

    static String inputDir;
    static String outputDir;
    static String stylesheetDir;
    static String messageDescriptionDir;

    InputStream is = null;

    public static void main(String[] args) {
        System.out.println("WSDL2DCAT tool");
        String currentPath = System.getProperty("user.dir")+"\\src\\files";
        inputDir = currentPath + "\\WSDL\\";
        outputDir = currentPath + "\\DCAT\\";
        stylesheetDir = currentPath + "\\XSL\\";
        messageDescriptionDir = currentPath + "\\XSD\\";
        createDirectoryIfNeeded(inputDir);
        createDirectoryIfNeeded(outputDir);

        int wsdlCount = 0;
        int xsdCount = 0;

        File[] WSDLfiles = new File(inputDir).listFiles();
        File[] XSDfiles = new File(messageDescriptionDir).listFiles();
        for (File file : WSDLfiles) {
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (file.isFile() && ext.equals("wsdl")) {
                wsdlCount++;
            }
        }

        for (File file : XSDfiles) {
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (file.isFile() && ext.equals("xsd")) {
                xsdCount++;
            }
        }
        if (wsdlCount == 0 || xsdCount == 0) {
            System.out.println("No WSDL file found in directory: " + inputDir);
            System.out.println("No XSD file found in directory: " + messageDescriptionDir);
        } else {
            System.out.println("Found " + wsdlCount + " WSDL file(s).");
            System.out.println("Found " + xsdCount + " XSD file(s).");
            System.out.println("Conversion has been started.");
            for (File file : WSDLfiles) {
                String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if (file.isFile() && ext.equals("wsdl")) {
                    // Convert each WSDL file to DCAT
                    convertWSDLToDCAT(file);
                    // (TODO) Validate each DCAT file
                }

            }
            for (File file : XSDfiles) {
                String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if (file.isFile() && ext.equals("xsd")) {
                    // Convert each WSDL file to DCAT
                    convertXSDToDCAT(file);
                    // (TODO) Validate each DCAT file
                }

            }
            System.out.println("File(s) have been converted to DCAT.");
            System.out.println("DCAT files can be found in: \n" + outputDir);
        }
    }

    private static void createDirectoryIfNeeded(String directoryName) {
        File theDir = new File(directoryName);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("Needed directory not found.");
            System.out.println("Creating directory: " + directoryName);
            theDir.mkdir();
        }
    }

    private static void convertWSDLToDCAT(File WSDLfile) {
        OutputStream DCATfile = null;
        try {
            String DCATfileName = outputDir + "\\" + removeExtension(WSDLfile) + "_wsdl.dcat";
            String StylesheetFileName = stylesheetDir + "\\" + "wsdl2dcat.xsl";
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource(StylesheetFileName);
            Source xmlDoc = new StreamSource(WSDLfile);
            DCATfile = new FileOutputStream(DCATfileName);
            Transformer trasform = tFactory.newTransformer(xslDoc);
            trasform.transform(xmlDoc, new StreamResult(DCATfile));
            System.out.println("DEBUG: File should be made: " + DCATfileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void convertXSDToDCAT(File XSDfile) {
        OutputStream DCATfile = null;
        try {
            String DCATfileName = outputDir + "\\" + removeExtension(XSDfile) + "_xsd.dcat";
            String StylesheetFileName = stylesheetDir + "\\" + "xsd2dcat.xsl";
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource(StylesheetFileName);
            Source xmlDoc = new StreamSource(XSDfile);
            DCATfile = new FileOutputStream(DCATfileName);
            Transformer trasform = tFactory.newTransformer(xslDoc);
            trasform.transform(xmlDoc, new StreamResult(DCATfile));
            System.out.println("DEBUG: File should be made: " + DCATfileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCATconverter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String removeExtension(File file) {
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        return fname;
    }
}
