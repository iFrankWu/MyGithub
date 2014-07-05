/*
 * TestHtml2Doc.java	 <br>
 * 2011-11-1<br>
 * com.shinetech.test <br>
 * Copyright (C), 2011-2012, ShineTech. Co., Ltd.<br>
 * 
 */
package com.shinetech.test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

/**
 * Class description goes here.
 * 
 * @author <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
 * @version 1.0.0
 */

public class TestHtml2Doc {
	public static void main(String[] args) {
//		TestHtml2Doc.bootstrap();

        
        // Loading HTML creates a Writer/Web document! It does NOT create a Writer document!
        String loadUrl      = "http://www.xyz.com";

        // Store web page as HTML and PDF
        String storeUrlHtml = "file:///c:/dev/netbeans/oootest/xyz.html";
        String storeUrlPdf  = "file:///c:/dev/netbeans/oootest/xyz.pdf";

        try {
            XComponentContext xContext = Bootstrap.bootstrap();
            XMultiComponentFactory xMultiComponentFactory = xContext.getServiceManager();
            XComponentLoader xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xContext));

            Object objectDocumentToStore = xcomponentloader.loadComponentFromURL(loadUrl, "_blank", 0, new PropertyValue[0]);

            // Sometimes loading from the web needs some time.
            // 4000 waits for 4 seconds. Try different settings.
            Thread.sleep(4000);

            XStorable xstorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,objectDocumentToStore);

            // Filter names are listed at http://wiki.services.openoffice.org/wiki/Framework/Article/Filter/FilterList_OOo_2_1
            PropertyValue[] conversionProperties = new PropertyValue[1];
            conversionProperties[0] = new PropertyValue();
            conversionProperties[0].Name = "FilterName";

            // Store Writer/Web document as HTML
            conversionProperties[0].Value = "HTML";
            xstorable.storeToURL(storeUrlHtml,conversionProperties);

            // Store Writer/Web document as PDF
            conversionProperties[0].Value = "writer_web_pdf_Export";
            xstorable.storeToURL(storeUrlPdf,conversionProperties);
        }
        catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
	}

	public static void bootstrap() {
		// String loadUrl = "file:///c:/dev/netbeans/oootest/viewtopic.php.htm";
		String loadUrl = "data/2.html";
		String storeUrl = "file:///c:/dev/netbeans/oootest/mydocoutputboot.doc";

		try {
			XComponentContext xContext = com.sun.star.comp.helper.Bootstrap
					.bootstrap();
			XMultiComponentFactory xMultiComponentFactory = xContext
					.getServiceManager();
			XComponentLoader xcomponentloader = (XComponentLoader) UnoRuntime
					.queryInterface(XComponentLoader.class,
							xMultiComponentFactory.createInstanceWithContext(
									"com.sun.star.frame.Desktop", xContext));

			PropertyValue[] conversionProperties = new PropertyValue[2];
			conversionProperties[0] = new PropertyValue();
			conversionProperties[0].Name = "FilterName";
			conversionProperties[0].Value = "writer_web_StarOffice_XML_Writer";

			conversionProperties[1] = new PropertyValue();
			conversionProperties[1].Name = "Hidden";
			conversionProperties[1].Value = new Boolean(true);

			 Object objectDocumentToStore =
			 xcomponentloader.loadComponentFromURL(loadUrl, "_blank", 1, new
			 PropertyValue[0]);
//			Object objectDocumentToStore = xcomponentloader
//					.loadComponentFromURL(loadUrl, "_blank", 1,
//							conversionProperties);

			XStorable xstorable = (XStorable) UnoRuntime.queryInterface(
					XStorable.class, objectDocumentToStore);
			// xstorable.storeToURL(storeUrl,conversionProperties);
			xstorable.storeToURL(storeUrl, conversionProperties);
			// Getting the method dispose() for closing the document
			// XComponent xcomponent =
			// ( XComponent ) UnoRuntime.queryInterface( XComponent.class,
			// xstorable );
			// System.exit(0);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		} finally {
			// System.exit(0);
		}

	}

}
