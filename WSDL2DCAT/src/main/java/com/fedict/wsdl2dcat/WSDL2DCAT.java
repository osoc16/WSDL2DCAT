/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.File;
import java.io.InputStream;

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
        System.out.println("WSDL2DCAT tool");
        Converter converter = new Converter();
        switch (args.length) {
            case 1:
                //arguments: input directory
                converter.convertToDCAT(args[0]);
                break;
            case 2:
                //arguments: input directory, file type
                converter.convertToDCAT(args[0], args[1]);
                break;
            case 3:
                //arguments: input directory, file type , output directory
                converter.convertToDCAT(args[0], args[1], args[2]);
                break;
            case 4:
                //arguments: input directory, file type , output directory , style sheet directory
                converter.convertToDCAT(args[0], args[1], args[2], args[3]);
                break;
            default:
                break;
        }

    }

}
