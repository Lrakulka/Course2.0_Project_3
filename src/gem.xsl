<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0">
<xsl:template match="/">
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
<xsl:for-each select="Gem/jewel">
<tr>
<td><xsl:value-of select="Name"/></td>
<td><xsl:value-of select="Preciousness"/></td>
<td><xsl:value-of select="Origin"/></td>
<xsl:for-each select="Visual_Parameters">
	<td><xsl:value-of select="Color"/></td>
	<td><xsl:value-of select="Transparency"/></td>
	<td><xsl:value-of select="Cut"/></td>
</xsl:for-each>
<td><xsl:value-of select="Value"/></td>
</tr>
</xsl:for-each>
</table>
</body></html>
</xsl:template>
</xsl:stylesheet>