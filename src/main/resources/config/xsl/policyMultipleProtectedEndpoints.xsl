<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:L7p="http://www.layer7tech.com/ws/policy"
	xmlns:wsp="http://schemas.xmlsoap.org/ws/2002/12/policy" xmlns:tw="http://www.denhaag.nl/xslt/extensions/tw"
	exclude-result-prefixes="L7p wsp xsi tw">
	
		<xsl:output method="html" indent="yes" version="4.0" encoding="UTF-8" />
	<xsl:template match="/wsp:Policy">
			<endpoints>
					<xsl:apply-templates select="//L7p:HttpRoutingAssertion"/>
			</endpoints>
	</xsl:template>
	
	<xsl:template match="L7p:HttpRoutingAssertion">
		<xsl:choose>
 			<xsl:when test="not(./L7p:Enabled/@booleanValue='false')">
 			<endpoint>
				<xsl:value-of select="./L7p:ProtectedServiceUrl/@stringValue" />
		     </endpoint>
 			</xsl:when>
		</xsl:choose>
	</xsl:template>
			
</xsl:stylesheet>
