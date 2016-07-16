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
                    + "2: change location of required files folder\n"
                    + "3: start conversion with custom settings\n"
                    + "4: exit application\n"
                    + "Option (default 1):");
            String option = sc.nextLine();
            if (!option.equals("4")) {
                if (option.equals("3")) {
                    customConvert(converter, sc);
                } else if (option.equals("2")) {
                    changeLocationFilesFolder(converter, sc);
                    converter.convertFamiliesToDCAT(false);
                } else {
                    converter.convertFamiliesToDCAT(false);
                }
                converter.convertToDCAT(false);

            } else {
                stop = true;
            }

        }
    }

    private static void customConvert(Converter converter, Scanner sc) {
        System.out.println("Input folder (default: files/Input/WSDL):");
        converter.setInputDir(sc.nextLine());
        System.out.println("File type (options:wsdl/xsd :: default: wsdl):");
        String fileType = sc.nextLine();
        while (!fileType.equalsIgnoreCase("wsdl") && !fileType.equalsIgnoreCase("xsd") && !fileType.equals("")) {
            System.out.println(fileType);
            System.out.println("Please provide one of the following filetypes: wsdl/xsd");
            fileType = sc.nextLine();
        }
        converter.setFileType(fileType);
        System.out.println("outputfolder=Output folder (default: files/Output/DCAT):");
        converter.setOutputDir(sc.nextLine());
        System.out.println("transformationfolder=Transformation folder (default: files/transformation/XSL):");
        converter.setStylesheetDir(sc.nextLine());
    }

    private static void changeLocationFilesFolder(Converter converter, Scanner sc) {
        System.out.println("filesfolder=Files folder (default: files):");
        converter.setCurrentPath(sc.nextLine());

    }

}
