<?xml version="1.0"?>
<xsl:stylesheet xmlns:t="gem_xsd"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				xsi:schemaLocation="gem_xsd gem.xsd"
version="1.0">

<xsl:template match="t:Gem">
<html>
<head><title>HTML GEM</title></head>
<body>
<table border="1">
<tr>
<th>Name</th>
<th>Preciousness</th>
<th>Origin</th>
<th>Color</th>
<th>Transparency</th>
<th>Cut</th>
<th>Value</th>
</tr>
<xsl:for-each select="t:jewel">
<tr>
<td><xsl:value-of select="t:Name"/></td>
<td><xsl:value-of select="t:Preciousness"/></td>
<td><xsl:value-of select="t:Origin"/></td>
<xsl:for-each select="t:Visual_Parameters">
	<td><xsl:value-of select="t:Color"/></td>
	<td><xsl:value-of select="t:Transparency"/></td>
	<td><xsl:value-of select="t:Cut"/></td>
</xsl:for-each>
<td><xsl:value-of select="t:Value"/></td>
</tr>
</xsl:for-each>
</table>
</body></html>
</xsl:template>
</xsl:stylesheet>