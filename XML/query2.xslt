<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>

<xsl:template match="/">
  <xsl:text>Which headphones are wired only?&#10;</xsl:text>
  <xsl:apply-templates select="//headphone[wireless='No']"/>
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
  <xsl:text>, Model: </xsl:text>
  <xsl:value-of select="model"/>
  <xsl:text>, Weight: </xsl:text>
  <xsl:value-of select="weight"/>
  <xsl:text>, Price: </xsl:text>
  <xsl:value-of select="price"/>
  <xsl:text>, Year ID: </xsl:text>
  <xsl:value-of select="yearID"/>
  <xsl:text>, Company ID: </xsl:text>
  <xsl:value-of select="companyID"/>
  <xsl:text>, Country ID: </xsl:text>
  <xsl:value-of select="countryID"/>
  <xsl:text>, Material: </xsl:text>
  <xsl:value-of select="material"/>
  <xsl:text>, Wireless: </xsl:text>
  <xsl:value-of select="wireless"/>
  <xsl:text>, Noise Cancellation: </xsl:text>
  <xsl:value-of select="noise_cancellation"/>
  <xsl:text>, Microphone: </xsl:text>
  <xsl:value-of select="microphone"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet>
