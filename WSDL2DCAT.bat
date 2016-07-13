@ECHO off
ECHO Welcome to WSDL2DCAT app
SET jarname=uber-WSDL2DCAT-1.0-SNAPSHOT.jar
SET mainclass=com.fedict.wsdl2dcat.WSDL2DCAT
SET inputfolder=Input
SET filetype=wsdl
SET outputfolder=Output
SET option="1"
SET /p jarname=Please provide the required JAR file: (uber-WSDL2DCAT-1.0-SNAPSHOT.jar) 
IF exist "%jarname%" (
	GOTO Start
) ELSE (
	GOTO Errorjar
)
:Start
ECHO Select from the following options:
ECHO 1: start conversion with standard settings (default)
ECHO 2: start conversion with custom settings
ECHO 3: exit application
SET /p option=Option (default 1):
ECHO Your option was: %option%
IF "%option%" EQU "1" GOTO StartConvert
IF "%option%" EQU "2" GOTO Customoption
IF "%option%" EQU "3" GOTO End

ECHO Please provide valid option!
PAUSE
GOTO Start
:StartConvert
	java -cp %jarname% %mainclass% %inputfolder% %filetype% %outputfolder%
	PAUSE
	GOTO Start
:Customoption
	SET /p mainclass=Main class (default: com.fedict.wsdl2dcat.WSDL2DCAT):
	SET /p inputfolder=Input folder (default: Input):
	SET /p filetype=File type (default: wsdl):
	SET /p filetype=Output folder (default: Output):

	GOTO StartConvert
:Errorjar
	ECHO No JAR file provided. 
	PAUSE
:End
	ECHO Exiting application.