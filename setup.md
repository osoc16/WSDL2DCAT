# Welcome to the WSDL2DCAT setup page
## Properties file

The config file in the directory WSDL2DCAT can be used to predefine the input, output and transformation directory. When the properties has no value or the properties file doesn't exist the interactive mode will start.

This is an example of a file config_example.properties (you can find it in the same directory):
![config file example](https://s31.postimg.org/hnr4v5naz/example_config.png)

When the filedir location in the properties file has value the other values will be ignored. The input, output, and transformation directory will be child directories of the new file dir. 

When there is something wrong the logger will log it as explained on the exception page and the exit code will be 1.

## Interactive mode
If you start the application you'll be welcomed by oldskool CMD UI.   
![BAT CMD example](https://s31.postimg.org/jt9wlmx23/cmd_begin.png)

### Pre-requirements
To use this tool you need an environment with an up-to-date Java Development Kit (JDK).

### Using compiled JAR

Inside the folder Fedict\WSDL2DCAT\target you'll find **uber-WSDL2DCAT-1.0-SNAPSHOT.jar**     
Open CMD or bash depending on your OS.   
Use the following command:    
`java -cp uber-WSDL2DCAT-1.0-SNAPSHOT.jar com.fedict.wsdl2dcat.WSDL2DCAT`   


### Using provided BAT and BASH file 
To make it easier for you, we've made a BAT and Bash file respectivelly called **WSDL2DCAT.bat** (Windows only :zap:) and **WSDL2DCAT.sh**.   
It can be found in the root: Fedict\WSDL2DCAT. 
If you want to use it elsewhere copy the file together with **uber-WSDL2DCAT-1.0-SNAPSHOT.jar** +   
**files** folder.

### Inside IDE
**Steps for using (default)**

1.  Put the WSDL, XSD or FAMILIES which you want to convert in their respective folders.
2.  Run the tool.
3.  If everything goes well, you'll find DCAT files in the DCAT folder.

```

WSDL2DCAT
└───files
    ├───Input
    │   ├───FAMILIES
    │   ├───WSDL
    │   └───XSD
    ├───Output
    │   └───DCAT
    └───Transformation
        └───XSL


```
