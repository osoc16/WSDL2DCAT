<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : teststylesheet.xsl
    Created on : 10 juli 2016, 14:42
    Author     : Miguel
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>

    
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="tr">
        <xsl:for-each select="td">
            <xsl:value-of select="position()" />
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
