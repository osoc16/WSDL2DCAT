/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class PropertiesFileReader {

    public boolean readPropertiesFile(Converter converter, String filename) {
        Properties prop = new Properties();
        InputStream input = null;
        boolean propertyHasValue=true;
        try {
            input = this.getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, filename + "Sorry, unable to find properties file {0}", filename);
                return false;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and add it to the converter
         
            
           String outputdir = prop.getProperty("outputdir","");
            String inputdir = prop.getProperty("inputdir","");
            String transformationdir = prop.getProperty("transformationdir","");
            
            if(outputdir.equals("") && !inputdir.equals("") && !transformationdir.equals("")){
                propertyHasValue = false;
            }

            System.out.println(inputdir);
            System.out.println(outputdir);
            System.out.println(transformationdir);
            converter.setInputDir(inputdir);
            converter.setOutputDir(outputdir);
            converter.setStylesheetDir(transformationdir);
            

        } catch (IOException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return propertyHasValue;
    }
}
