/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Miguel
 * @author Umut
 */
public class WSDL2DCAT {

    static String inputDir;
    static String outputDir;
    static String stylesheetDir;
    static String messageDescriptionDir;

    InputStream is = null;

    /**
     * Main method
     *
     * @param args input, output, stylesheet directory
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Converter converter = new Converter();
        System.out.println("WSDL2DCAT tool \n");
        boolean stop = false;
        while (!stop) {
            System.out.println("Select one of the following options:\n"
                    + "1: start conversion with standard settings (default)\n"
                    + "2: change required files folder location\n"
                    + "3: start conversion with custom settings\n"
                    + "4: exit application\n"
                    + "Option (default 1):");
            String option = sc.nextLine();
            if (option.equals("3")) {

                System.out.println("Input folder (default: files/Input/WSDL):");
                String inputDir = sc.nextLine();
                System.out.println(inputDir);
                if (!inputDir.equals("")) {
                    converter.setInputDir(inputDir);
                }

                System.out.println("File type (options:wsdl/xsd :: default: wsdl):");
                String fileType = sc.nextLine();
                while (!fileType.equalsIgnoreCase("wsdl") && !fileType.equalsIgnoreCase("xsd") && !fileType.equals("")) {
                    System.out.println(fileType);
                    System.out.println("Please provide one of the following filetypes: wsdl/xsd");
                    fileType = sc.nextLine();
                }

                if (!fileType.equals("")) {
                    converter.setFileType(fileType);

                }
                System.out.println("outputfolder=Output folder (default: files/Output/DCAT):");
                String outputDir = sc.nextLine();
                if (!outputDir.equals("")) {
                    converter.setInputDir(outputDir);
                }
                System.out.println("transformationfolder=Transformation folder (default: files/transformation/XSL):");
                String stylesheetDir = sc.nextLine();
                if (!stylesheetDir.equals("")) {
                    converter.setStylesheetDir(stylesheetDir);
                }
                converter.convertToDCAT();
            } else if (option.equals("2")) {
                System.out.println("filesfolder=Files folder (default: files):");
                String filesDir = sc.nextLine();
                if (!filesDir.equals("")) {
                    converter.setCurrentPath(filesDir);
                }
                 converter.convertToDCAT();
                converter.convertFamiliesToDCAT();
            } else if (option.equals("4")) {
                stop = true;
            } else {
                converter.convertToDCAT();
                converter.convertFamiliesToDCAT();
            }
        }
    }

}
