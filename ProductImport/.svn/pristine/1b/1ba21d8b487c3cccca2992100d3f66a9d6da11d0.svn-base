package com.shinetech.test;
import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class TestHtml2Doc2 {
    public static void main(String[] args) {
        // Load documents
        String loadUrlHtml = "data/2.html";
        String loadUrlOdt  = "file:///c:/dev/netbeans/oootest/my.odt";
        String loadUrlOds  = "file:///c:/dev/netbeans/oootest/my.ods";

        // Store documents
        String storeUrlHtml = "file:///c:/dev/netbeans/oootest/my.htm.pdf";
        String storeUrlOdt  = "file:///c:/dev/netbeans/oootest/my.odt.pdf";
        String storeUrlOds  = "file:///c:/dev/netbeans/oootest/my.ods.pdf";

        try {
            XComponentContext xContext = Bootstrap.bootstrap();
            XMultiComponentFactory xMultiComponentFactory = xContext.getServiceManager();
            XComponentLoader xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xContext));

            convertDocumentToPdf(xcomponentloader,loadUrlHtml,storeUrlHtml);
            convertDocumentToPdf(xcomponentloader,loadUrlOdt,storeUrlOdt);
            convertDocumentToPdf(xcomponentloader,loadUrlOds,storeUrlOds);
        }
        catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

    private static void convertDocumentToPdf(XComponentLoader xcomponentloader, String loadUrlHtml, String storeUrl) throws IOException, InterruptedException, IllegalArgumentException {
        Object document = xcomponentloader.loadComponentFromURL(loadUrlHtml, "_blank", 0, new PropertyValue[0]);

        // Sometimes loading needs some time. 4000 waits for 4 seconds. Try different settings if needed.
        Thread.sleep(4000);

        storePDF(document, storeUrl);
    }

    private static void storePDF(Object document,String storeUrl) throws IOException {
        // Determine suitable filter name for PDF export by asking XServiceInfo.
        // Source: OOo Developer's Guide - 7 Office Development - 7.1.5 Handling Documents - Storing Documents
        // http://api.openoffice.org/docs/DevelopersGuide/OfficeDev/OfficeDev.xhtml#1_1_5_3_Storing_Documents
        //
        // Filter names are listed at http://wiki.services.openoffice.org/wiki/Framework/Article/Filter/FilterList_OOo_2_1
        XServiceInfo xInfo = (XServiceInfo) UnoRuntime.queryInterface(XServiceInfo.class,document);
        String storeFilter = null;
        if(xInfo!=null) {
            if(xInfo.supportsService("com.sun.star.text.TextDocument")) {
              storeFilter = "writer_pdf_Export";
            }
            else if(xInfo.supportsService("com.sun.star.text.WebDocument")) {
              storeFilter = "writer_web_pdf_Export";
            }
            else if(xInfo.supportsService("com.sun.star.sheet.SpreadsheetDocument")) {
              storeFilter = "calc_pdf_Export";
            }
        }

        if (storeFilter != null) {
            PropertyValue[] conversionProperties = new PropertyValue[2];
            conversionProperties[0] = new PropertyValue();
            conversionProperties[0].Name = "FilterName";
            conversionProperties[0].Value = storeFilter;
            conversionProperties[1] = new PropertyValue();
            conversionProperties[1].Name = "Overwrite ";
            conversionProperties[1].Value = new Boolean(true);

            XStorable xstorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,document);
            xstorable.storeToURL(storeUrl, conversionProperties);
        }
    }
}