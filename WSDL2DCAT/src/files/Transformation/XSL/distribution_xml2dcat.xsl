<?xml version='1.0' encoding='utf-8'?>
<!--
    Document   : wsdl2rdf.xsl.xsl
    Created on : 5 juli 2016, 19:53
    Author     : Miguel
    Description:
        Purpose of transformation follows.
-->


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"

>
    <xsl:output method="text" />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:apply-templates select="fsb/family/service" />      
    </xsl:template>
    
    <xsl:template match="fsb/family/service">
        <xsl:variable name="languageTag" select="'@en'" />
        <xsl:text>&lt;</xsl:text>  
        <xsl:value-of select="@uri" /> 
        <xsl:text>&gt;</xsl:text>    
        <xsl:text>&#10;&#x9;a dcat:Distribution ;&#10;&#x9;dc:description "</xsl:text>
        <xsl:apply-templates select="desc" />      
        <xsl:text>"</xsl:text>
        <xsl:value-of select="$languageTag"/> 
        <xsl:text> .&#10;&#10;</xsl:text>       
        
                  
    </xsl:template>

</xsl:stylesheet>
