/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xslx2xmlconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Miguel
 */
public class XSLX2XMLconverter {

    static String csvDir;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir") + "\\src\\files";
        csvDir = currentPath + "\\CSV\\";
        convertCSVtoXML(csvDir + "families.csv", ";");
    }

    /**
     * Creates directory if the directory doesn't exist ToDo: used
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

    private static void convertCSVtoXML(String csvFile, String delimeter)  {
        BufferedReader br = null;
        String line = "";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            if ((line = br.readLine()) != null) {
                String[] header = line.split(delimeter);
                String webserviceFamily = header[0].replaceAll(" ", "");
                String webservice = header[1];
                String backend = header[2];
                String discription = header[3];
                String uri = header[4];

            }
            while ((line = br.readLine()) != null) {

                // use delimeter
                String[] row = line.split(delimeter);
                         

            }
               DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Family");
                doc.appendChild(rootElement);
                
                Element serviceElement = doc.createElement("Service");
                rootElement.appendChild(serviceElement);
                
                Attr serviceNameAttribute = doc.createAttribute("name");
                serviceNameAttribute.setValue("serviceName");
                serviceElement.appendChild(serviceNameAttribute);
               

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
               // StreamResult result = new StreamResult(new File("c:\\Users\\Miguel\\convertTest.xml"));
                StreamResult result = new StreamResult(System.out);
                transformer.transform(source,result);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XSLX2XMLconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XSLX2XMLconverter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        System.out.println("Done");

    }

}
