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
    
  
    <xsl:template match="fsb">
        <xsl:variable name="dcat" select="'http://www.w3.org/ns/dcat#'" />
        <xsl:variable name="dc" select="'http://purl.org/dc/terms/'" />
        <xsl:variable name="foaf" select="'http://xmlns.com/foaf/0.1/'" />
        <xsl:variable name="rdfs" select="'http://www.w3.org/2000/01/rdf-schema#'" />

        <!--Prefixes-->
        <xsl:text>@prefix dcat: &lt;</xsl:text>
        <xsl:value-of select="$dcat"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix dc: &lt;</xsl:text>
        <xsl:value-of select="$dc"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix foaf: &lt;</xsl:text>
        <xsl:value-of select="$foaf"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix rdfs: &lt;</xsl:text>
        <xsl:value-of select="$rdfs"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        
        <!--Dataset-->
    
        <xsl:for-each-group select="family" group-by="@uri">

        
            <xsl:variable name="family" select="current-grouping-key()"/>
            <xsl:variable name="languageTag" select="'@en'" />
            <xsl:variable name="id" select="@id" />
            <xsl:variable name="language" select="'http://publications.europa.eu/resource/authority/language/ENG'" />
            <xsl:variable name="theme" select="'http://publications.europa.eu/resource/authority/data-theme/GOVE'" />
            

            
            <!--Family -->
            <xsl:text>&lt;</xsl:text>
            <xsl:value-of select="$family"/>
            <xsl:text>&gt;&#10;</xsl:text>
            <xsl:text>&#x9;a dcat:Dataset ;&#10;</xsl:text>
            <xsl:text>&#x9;dc:title "Family name </xsl:text>
            <xsl:value-of select="$family"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$languageTag"/> 
            <xsl:text>;&#10;</xsl:text>
            <xsl:text>&#x9;dc:description "This is a fedict web service family named: </xsl:text>
            <xsl:value-of select="$family"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$languageTag"/> 
            <xsl:text>;&#10;</xsl:text>
            <xsl:text>&#x9;dc:identifier "</xsl:text>
            <xsl:value-of select="$id"/>
            <xsl:text>"</xsl:text>
            <xsl:value-of select="$languageTag"/> 
            <xsl:text>;&#10;</xsl:text>
            <!--<xsl:text>&#x9;dc:issued "</xsl:text>        
            <xsl:text>" ;&#10;</xsl:text> -->
            <xsl:text>&#x9;dc:modified "</xsl:text>   
            <xsl:value-of select="current-date()"/> 
            <xsl:text>" ;&#10;</xsl:text>
            <xsl:text>&#x9;dc:language &lt;</xsl:text>
            <xsl:value-of select="$language"/>
            <xsl:text  >&gt; ;&#10;</xsl:text>
            <xsl:text>&#x9;dc:theme &lt;</xsl:text>
            <xsl:value-of select="$theme"/>
            <xsl:text  >&gt; ;&#10;</xsl:text>
            <xsl:text>&#x9;dcat:distribution </xsl:text> 
            <xsl:apply-templates select="service" />  
            <xsl:text> .&#10;&#10;</xsl:text>
     
        </xsl:for-each-group>
    </xsl:template>
    
    <xsl:template match="service">
        <xsl:if test="not(position()=1)">
            <xsl:text>, </xsl:text>
        </xsl:if>
        <xsl:text  >&lt;</xsl:text>
        <xsl:value-of select="@uri"/>
        <xsl:text  >&gt;</xsl:text>
    </xsl:template>
</xsl:stylesheet>
