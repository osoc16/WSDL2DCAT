<?xml version='1.0' encoding='utf-8'?>
<!--
    Document   : wsdl2rdf.xsl.xsl
    Created on : 5 juli 2016, 19:53
    Author     : Miguel
    Description:
        Purpose of transformation follows.
-->


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:str="http://exslt.org/strings"
                  xmlns:dtyf="http://www.datypic.com/functions"
>
    <xsl:output method="text" />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="wsdl:definitions">
        <xsl:variable name="catalog" select="namespace::fsb" />
        <xsl:variable name="service" select="namespace::tns" />
        <xsl:variable name="distribution" select="namespace::mes" />
        
        <xsl:variable name="dcat" select="'http://www.w3.org/ns/dcat#'" />
        <xsl:variable name="dc" select="'http://purl.org/dc/terms/'" />
        <xsl:variable name="foaf" select="'http://xmlns.com/foaf/0.1/'" />
        <xsl:variable name="rdfs" select="'http://www.w3.org/2000/01/rdf-schema#'" />
        
        <xsl:variable name="baseUri" select="'http://fsb.belgium.be/'" />
        <xsl:variable name="language" select="'http://lexvo.org/id/iso639-3/eng'" />
        <xsl:variable name="homepage" select="'http://registry.fsb.belgium.be/web/service-catalog/partner/homepage'" />
        <xsl:variable name="publisher" select="'https://opencorporates.com/companies/be/0367302178'" />
        <xsl:variable name="theme" select="'http://ns.thedatatank.com/dcat/themes#Government'" />

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
        
        <!--Catalog-->
        <xsl:text>&lt;</xsl:text>
        <xsl:value-of select="$catalog"/>
        <xsl:text>&gt;&#10;</xsl:text>
        <xsl:text>&#x9;a dcat:Catalog ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:title "The FSB web service catalogue" ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:description "Fedict provides to the administrations and the public a platform allowing the consultation and a standardized data exchange, from one application to another application, through the Internet.&#10;The Federal Service Bus offers a safe and secured access to the Web Services connected, among others, to authentic sources.&#10;For more details about:&#10;- The FSB;&#10;- The access procedure to the Web Services;&#10;- The site navigation;&#10;Please consult the list of available Web Services by using the search tool or browse through the catalogue.&#10;Please note that this site contains only information and technical documents.&#10;The content of this site is only available in English."  </xsl:text>
        <xsl:text>&#x9;dc:issued "..." ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:language &lt;</xsl:text>
        <xsl:value-of select="$language"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;foaf:homepage &lt;</xsl:text>
        <xsl:value-of select="$homepage"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;foaf:homepage &lt;</xsl:text>
        <xsl:value-of select="$homepage"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;dc:license ... ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:publisher &lt;</xsl:text>
        <xsl:value-of select="$publisher"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;dc:modified ... ;&#10;</xsl:text>
        <xsl:text>&#x9;dcat:dataset &lt;</xsl:text>
        <xsl:value-of select="$service"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>

        <!--Service -->
        <xsl:text>&lt;</xsl:text>
        <xsl:value-of select="$service"/>
        <xsl:text>&gt;&#10;</xsl:text>
        <xsl:text>&#x9;a dcat:Dataset ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:title "Service name </xsl:text>
        <xsl:value-of select="$service"/>
        <xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:description "This is a fedict webservice named: </xsl:text>
        <xsl:value-of select="$service"/>
        <xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:identifier "</xsl:text>
        <xsl:value-of select="substring-after($service,$baseUri)"/>
        <xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:issued "..." ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:modified "..." ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:language &lt;</xsl:text>
        <xsl:value-of select="$language"/>
        <xsl:text  >&gt; &#10;</xsl:text>
        <xsl:text>&#x9;dc:theme &lt;</xsl:text>
        <xsl:value-of select="$theme"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;dcat:distribution </xsl:text> 
        <xsl:variable name="distributionList">
            <xsl:apply-templates select="wsdl:message">
                <xsl:with-param name="distribution" select="$distribution" />
            </xsl:apply-templates>       
        </xsl:variable>
        <xsl:value-of select="$distributionList"/>
        <xsl:text> .&#10;</xsl:text>
        
 
       
      
    </xsl:template>
    
    <xsl:template match="wsdl:message">
        <xsl:param name = "distribution" />
        <xsl:if test="not(position()=1)">
            <xsl:text>,</xsl:text>
        </xsl:if>
        
        <xsl:text> &lt;</xsl:text>
        <xsl:value-of select="concat($distribution,'/',@name)"/>
        <xsl:text>&gt;</xsl:text>           

                  
    </xsl:template>
    
    
</xsl:stylesheet>
