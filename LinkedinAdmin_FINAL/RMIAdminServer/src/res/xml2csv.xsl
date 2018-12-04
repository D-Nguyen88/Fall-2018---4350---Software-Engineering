<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">
User Id, First Name, Last Name, Industry, Position, State
<xsl:for-each select="//profile">
   <xsl:value-of select="@userId" />
   <xsl:value-of select="concat(',' , firstName, ',' , lastName, ',' , industry, ',' , position, ',' , state, '&#xA;')"/>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>