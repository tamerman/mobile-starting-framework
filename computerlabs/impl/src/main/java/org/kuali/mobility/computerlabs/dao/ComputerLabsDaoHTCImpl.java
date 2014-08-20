/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.computerlabs.dao;

import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/*
 * This is HTC's Test implementation to feed data via Google Spreadsheet.
 */
public class ComputerLabsDaoHTCImpl implements ComputerLabsDao, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(ComputerLabsDaoHTCImpl.class);

    private ApplicationContext applicationContext;
    private List<? extends LabGroup> labGroups;

    @Override
    public Lab getLab( String labUid ) {
        Lab myLab = null;
        return myLab;
    }

    @Override
    public List<? extends Lab> getLabs( String locationId, String buildingCode ) {
        List<? extends Lab> myLabs = new ArrayList<Lab>();
        return myLabs;
    }

    @Override
    public List<? extends Location> getLocations( String groupId ) {
        List<? extends Location> myLocations = null;
        return myLocations;
    }

    @Override
    public List<? extends LabGroup> getLabGroups() {
        return labGroups;
    }

    @Override
    public LabGroup getLabGroup(String groupId) {
        LabGroup myGroup = null;
        return myGroup;
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setLabGroups(List<? extends LabGroup> labGroups) {
        this.labGroups = labGroups;
    }
    
    /*
     * 	retrieveAndSaveSpreadsheetDataAsXML() method reads the feed url, parses the xml and writes the whole content to a xml
     *  file namely SpreadsheetData.xml under docs part of computerlabs tool. This xml can be used by computerlabs tool.
     *  
     *  NOTE: To get the feed url, create a spreadsheet, share it publicly(public on the web) and click on "Publish to the Web"
     *  under File section.
     */
    public void retrieveAndSaveSpreadsheetDataAsXML(String feedURL) {
        try {
            URL url = new URL(feedURL);
            URLConnection conn = url.openConnection();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer transformer = tfactory.newTransformer();

            transformer.transform(new DOMSource(doc), new StreamResult("../computerlabs/docs/SpreadsheetData.xml"));
        }
        catch( IOException ioe) {
            LOG.info( "Exception:"+ioe.getMessage() );
        }
        catch( ParserConfigurationException pce) {
            LOG.info( "Exception:"+pce.getMessage() );
        }
        catch( SAXException se) {
            LOG.info( "Exception:"+se.getMessage() );
        }
        catch( TransformerConfigurationException tce) {
            LOG.info( "Exception:"+tce.getMessage() );
        }
        catch( TransformerException te) {
            LOG.info( "Exception:"+te.getMessage() );
        }
    }
}
