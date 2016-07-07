import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;


public class Main {

    static String inputDir;
    static String outputDir;
    InputStream is = null;

    public static void main(String[] args){
        System.out.println("WSDL2DCAT tool");
        String currentPath = System.getProperty("user.dir");
        inputDir = currentPath + "\\WSDL\\";
        outputDir = currentPath + "\\DCAT\\";
        createDirectoryIfNeeded(inputDir);
        createDirectoryIfNeeded(outputDir);

        int wsdlCount = 0;

        File[] files = new File(inputDir).listFiles();
        for (File file : files) {
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (file.isFile() && ext.equals("wsdl")) {
                wsdlCount++;
            }
        }

        if (wsdlCount < 1){
            System.out.println("No WSDL file found in directory: " + inputDir);
        } else {
            System.out.println("Found " + wsdlCount + " WSDL file(s).");
            System.out.println("Conversion has been started.");
            int id = wsdlCount;
            for (File file : files) {
                String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if (file.isFile() && ext.equals("wsdl")) {
                    // Convert each WSDL file to DCAT
                    convertToDCAT(id, file);
                    // (TODO) Validate each DCAT file
                }
                id--;
            }
            System.out.println("File(s) have been converted to DCAT.");
            System.out.println("DCAT files can be found in: \n" + outputDir);
        }
    }

    private static void createDirectoryIfNeeded(String directoryName)
    {
        File theDir = new File(directoryName);

        // if the directory does not exist, create it
        if (!theDir.exists())
        {
            System.out.println("Needed directory not found.");
            System.out.println("Creating directory: " + directoryName);
            theDir.mkdir();
        }
    }
    private static void convertToDCAT(int id, File WSDLfile) {
        // Create empty DCAT file first
        File DCATfile = new File(outputDir + "\\" + id + ".dcat");
        String DCATcontent = "Hello File!";

        // (Todo) Set DCATcontent


        System.out.println("DEBUG: File should be made: " + DCATfile.getName());
        try {
            DCATfile.createNewFile();
            Files.write(DCATfile.toPath(), DCATcontent.getBytes(), StandardOpenOption.CREATE);

        } catch (IOException ioe) {
            System.out.println("Error while creating file" + ioe);
        }

    }
}
