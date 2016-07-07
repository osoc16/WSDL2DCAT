<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : xsd2rdf.xsl
    Created on : 6 juli 2016, 16:09
    Author     : Miguel
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet 
    xmlns:xs="http://www.w3.org/2001/XMLSchema" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
     <xsl:output method="text" />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->

    <xsl:template match="xs:schema">
        <xsl:variable name="distribution" select="namespace::mes" />
        <xsl:apply-templates select="xs:element">
            <xsl:with-param name="distribution" select="$distribution" />
        </xsl:apply-templates>
        
    </xsl:template>

     

    <xsl:template match="xs:element">
         <xsl:param name = "distribution" />
         <xsl:text>&lt;</xsl:text>  
         <xsl:value-of select="concat($distribution,'/',@name)" /> 
         <xsl:text>&gt;</xsl:text>    
         <xsl:text>
         &#x9;a dcat:Distribution ;&#10;&#x9;dc:description "
         </xsl:text>
        <xsl:apply-templates select="xs:annotation/xs:documentation" />   
        <xsl:text>";&#10;</xsl:text>              
    </xsl:template>
    
    <xsl:template match="xs:annotation/xs:documentation">
        <xsl:value-of select="." />
        
    </xsl:template>

</xsl:stylesheet>
