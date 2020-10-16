<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" omit-xml-declaration="yes" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>

	<xsl:param name="username" select="'?'" />
	<xsl:param name="products" select="'?'" />
	<xsl:param name="totalamount" select="'?'" />
	<xsl:template match="/">
	<html>
	    <body left_margin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0">
	    <div> Hi <xsl:value-of select="$username"/></div>
	    	<table cellpadding="9">
	    	 	<tr>
	               	<td>Please find the order details below</td>
	            </tr>
	        	<tr>
	        		<table style="padding-left:0px" >
	        		<tr>
	        			<th>ProductName</th>
	        			<th>Quantity</th>
	        			<th>Price</th>
	        		</tr>
	        		<xsl:for-each select="$products">
    					<tr>
      						<td><xsl:value-of select="products.productName"/></td>
      						<td><xsl:value-of select="products.quantity"/></td>
      						<td><xsl:value-of select="products.price"/></td>
    					</tr>
    				</xsl:for-each>
					<tr>
	        				<td>Your total Price is:
									<xsl:value-of select="$totalamount"/>
						    </td>
					</tr>
	        		</table>
	            </tr>
				<tr>
	        		<table style="padding-left:0px" >
	        			<tr>Thanks,</tr>
	        			<tr>Team</tr>
	        		</table>
	        	</tr>
	        </table>
	    </body>
	</html>

	</xsl:template>
</xsl:stylesheet>