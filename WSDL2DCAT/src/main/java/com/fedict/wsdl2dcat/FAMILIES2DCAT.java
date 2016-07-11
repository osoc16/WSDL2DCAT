/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

/**
 *
 * @author Miguel
 */
public class FAMILIES2DCAT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("FAMILIES2DCAT tool");
        Converter converter = new Converter();
        switch (args.length) {
            case 1:
                //arguments: input directory
                converter.convertFamiliesToDCAT(args[0]);
                break;
            case 2:
                //arguments: input directory, file type
                converter.convertFamiliesToDCAT(args[0], args[1]);
                break;
            case 3:
                //arguments: input directory, file type , output directory
                converter.convertFamiliesToDCAT(args[0], args[1], args[2]);
                break;
            case 4:
                //arguments: input directory, file type , output directory , style sheet directory
                converter.convertFamiliesToDCAT(args[0], args[1], args[2], args[3]);
                break;
            default:
                converter.convertFamiliesToDCAT();
                break;
        }
    }

}
