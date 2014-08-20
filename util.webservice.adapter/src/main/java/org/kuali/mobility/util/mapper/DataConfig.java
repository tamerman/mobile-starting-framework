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

package org.kuali.mobility.util.mapper;

import com.thoughtworks.xstream.XStream;
import org.kuali.mobility.util.mapper.entity.DataMapping;
import org.kuali.mobility.util.mapper.entity.MappingElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DataConfig {
	private static final Logger logger = LoggerFactory.getLogger(DataConfig.class);

	public DataMapping loadConfiguation(final String fileName) throws IOException {
		final XStream xstream = configureXstream();
		return (DataMapping) xstream.fromXML(getClass().getClassLoader().getResourceAsStream(fileName));
	}

	public DataMapping loadConfiguation(final URL url, Integer connectionTimeout, Integer readTimeout) throws IOException {
		final XStream xstream = configureXstream();

		URLConnection con = url.openConnection();
		con.setConnectTimeout(connectionTimeout);
		con.setReadTimeout(readTimeout);
		InputStream in = con.getInputStream();

		return (DataMapping) xstream.fromXML(in);
	}

	private XStream configureXstream() {
		final XStream xstream = new XStream();
		xstream.alias("dataMapping", DataMapping.class);
		xstream.addImplicitCollection(DataMapping.class, "mappings", "mapping", MappingElement.class);
		xstream.aliasAttribute(MappingElement.class, "mapTo", "mapTo");
		xstream.aliasAttribute(MappingElement.class, "mapFrom", "mapFrom");
		xstream.aliasAttribute(MappingElement.class, "isAttribute", "attribute");
		xstream.aliasAttribute(MappingElement.class, "list", "list");
		xstream.aliasAttribute(MappingElement.class, "type", "type");
		xstream.processAnnotations(DataMapping.class);
		return xstream;
	}
}
