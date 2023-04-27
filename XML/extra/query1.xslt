<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>

<xsl:template match="/">
  <xsl:text>Headphones with type "On-Ear":&#10;</xsl:text>
  <xsl:apply-templates select="//headphone[type='On-Ear']"/>
</xsl:template>

<xsl:template match="headphone">
  <xsl:text>ID: </xsl:text>
  <xsl:value-of select="headphoneID"/>
  <xsl:text>, Name: </xsl:text>
  <xsl:value-of select="headphoneName"/>
  <xsl:text>, Type: </xsl:text>
  <xsl:value-of select="type"/>
  <xsl:text>, Colour: </xsl:text>
  <xsl:value-of select="colour"/>
  <xsl:text>, Price: </xsl:text>
  <xsl:value-of select="price"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet>
