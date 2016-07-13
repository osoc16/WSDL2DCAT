@ECHO off
ECHO Welcome to WSDL2DCAT app
SET mainclass=com.fedict.wsdl2dcat.WSDL2DCAT
SET inputfolder=files/Input/WSDL
SET filetype=wsdl
SET outputfolder=files/Output/DCAT
:Prerequire
SET jarname=uber-WSDL2DCAT-1.0-SNAPSHOT.jar
SET /p jarname=Please provide the required JAR file: (uber-WSDL2DCAT-1.0-SNAPSHOT.jar) 
IF exist "%jarname%" (
	GOTO Start
) ELSE (
	ECHO No JAR file provided. 
	SET jarname=uber-WSDL2DCAT-1.0-SNAPSHOT.jar
	GOTO Prerequire
)
:Start
SET option=1
SET mainclass=com.fedict.wsdl2dcat.WSDL2DCAT
SET inputfolder=files/Input/WSDL
SET filetype=wsdl
SET outputfolder=files/Output/DCAT
ECHO Select from the following options:
ECHO 1: start conversion with standard settings (default)
ECHO 2: start conversion with custom settings
ECHO 3: choose another JAR file
ECHO 4: exit application
SET /p option=Option (default 1):
ECHO Your option was: %option%
IF "%option%" EQU "1" GOTO DefaultConvert
IF "%option%" EQU "2" GOTO CustomConvert
IF "%option%" EQU "3" GOTO Prerequire
IF "%option%" EQU "4" GOTO End
ECHO Please provide valid option!
GOTO Start
:DefaultConvert
	java -cp %jarname% %mainclass%
	GOTO Start
:CustomConvert
	SET /p mainclass=Main class (default: com.fedict.wsdl2dcat.WSDL2DCAT):
	SET /p inputfolder=Input folder (default: files/Input/WSDL):
	:CheckValidType
	SET /p filetype=File type (options:wsdl/xsd :: default: wsdl):
	IF /I "%filetype%" EQU "wsdl" GOTO TypeIsValid
	IF /I "%filetype%" EQU "xsd" GOTO TypeIsValid
	ECHO Please provide one of the following filetypes: wsdl/xsd
	GOTO CheckValidType
	:TypeIsValid
	SET /p outputfolder=Output folder (default: files/Output/DCAT):
	java -cp %jarname% %mainclass% %inputfolder% %filetype% %outputfolder%
	PAUSE
	GOTO Start
:End
	ECHO Exiting application.