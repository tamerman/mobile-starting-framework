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
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.kuali.mobility.util.mapper.entity.DataMapping;
import org.kuali.mobility.util.mapper.entity.MappingElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DataMapperImpl implements DataMapper {

	private static final Logger LOG = LoggerFactory.getLogger(DataMapperImpl.class);

	private Integer connectionTimeoutMs = new Integer(5000);

	private Integer readTimeoutMs = new Integer(10000);;

	@Override
	public <B extends Object> B mapData(B responseObject, final URL source, final String mappingFile) throws ClassNotFoundException, IOException {
		return mapData(responseObject, source, mappingFile, null);
	}

	@Override
	public <B extends Object> B mapData(B responseObject, final URL source, final URL mappingFileUrl) throws ClassNotFoundException, IOException {
		return mapData(responseObject, source, mappingFileUrl, null);
	}

	@Override
	public <B extends Object> B mapData(B responseObject, final String dataFile, final String mappingFile) throws ClassNotFoundException {
		return mapData(responseObject, dataFile, mappingFile, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B> B mapData(B responseObject, URL source, String mappingFile, String listName) throws ClassNotFoundException, IOException {
		DataConfig dc = new DataConfig();
		DataMapping mapping = null;
		try {
			mapping = dc.loadConfiguation(mappingFile);
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		}
		XStream xstream = loadMapper(mapping, listName);
		if (xstream != null) {
			URLConnection con = source.openConnection();
			con.setConnectTimeout(connectionTimeoutMs);
			con.setReadTimeout(readTimeoutMs);
			InputStream in = con.getInputStream();

			responseObject = (B) xstream.fromXML(in);
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B> B mapData(B responseObject, String dataFile, String mappingFile, String listName) throws ClassNotFoundException {
		DataConfig dc = new DataConfig();
		DataMapping mapping = null;
		try {
			mapping = dc.loadConfiguation(mappingFile);
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		}
		XStream xstream = loadMapper(mapping, listName);
		if (xstream != null) {
			try {
				responseObject = (B) xstream.fromXML(this.getClass().getClassLoader().getResourceAsStream(dataFile));
			}
			catch( NullPointerException npe ) {
				LOG.error( npe.getLocalizedMessage(), npe );
			}
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B extends Object> B mapData(B responseObject, final URL source, final URL mappingFileUrl, String listName) throws ClassNotFoundException, IOException {
		DataConfig dc = new DataConfig();
		DataMapping mapping = null;
		try {
			mapping = dc.loadConfiguation(mappingFileUrl, connectionTimeoutMs, readTimeoutMs);
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		}
		XStream xstream = loadMapper(mapping, listName);
		if (xstream != null) {
			URLConnection con = source.openConnection();
			con.setConnectTimeout(connectionTimeoutMs);
			con.setReadTimeout(readTimeoutMs);
			InputStream in = con.getInputStream();
			responseObject = (B) xstream.fromXML(in);
		}
		return responseObject;
	}

	private XStream loadMapper(DataMapping mapping, String listName) throws ClassNotFoundException {
		if (mapping != null) {
			final List<String> fields = new ArrayList<String>();

			for (MappingElement map : mapping.getMappings()) {
				fields.add(map.getMapTo());
			}
			final String objectClass = mapping.getClassName();

			XStream xstream;
			if( mapping.getMimeType() != null && "application/json".equalsIgnoreCase(mapping.getMimeType()) )
			{
				LOG.debug( "Loading xstream jettison mapped xml driver.");
				xstream = new XStream(new JettisonMappedXmlDriver()) {
					@Override
					protected MapperWrapper wrapMapper(MapperWrapper next) {
						return new MapperWrapper(next) {
							@Override
							public boolean shouldSerializeMember(Class definedIn, String fieldName) {
								try {
									if (!fields.contains(fieldName) && definedIn == Class.forName(objectClass)) {
										return false;
									}
								} catch (ClassNotFoundException e) {
									return false;
								}
								return super.shouldSerializeMember(definedIn, fieldName);
							}
						};
					}
				};
			}
			else
			{
				xstream = new XStream() {
					@Override
					protected MapperWrapper wrapMapper(MapperWrapper next) {
						return new MapperWrapper(next) {
							@Override
							public boolean shouldSerializeMember(Class definedIn, String fieldName) {
								try {
									if (!fields.contains(fieldName) && definedIn == Class.forName(objectClass)) {
										return false;
									}
								} catch (ClassNotFoundException e) {
									return false;
								}
								return super.shouldSerializeMember(definedIn, fieldName);
							}
						};
					}
				};
			}
			xstream.alias(mapping.getId(), Class.forName(mapping.getClassName()));
			if (mapping.getRootElement() != null && !"".equalsIgnoreCase(mapping.getRootElement())) {
				if (mapping.getRootElementClassName() != null && !"".equals(mapping.getRootElementClassName().trim())) {
					if (listName != null && !"".equals(listName.trim())) {
						xstream.addImplicitCollection(Class.forName(mapping.getRootElementClassName()), listName);
					}
					xstream.alias(mapping.getRootElement(), Class.forName(mapping.getRootElementClassName()));
				} else {
					xstream.alias(mapping.getRootElement(), (mapping.isList() ? List.class : Object.class));
				}
			}
			for (MappingElement map : mapping.getMappings()) {
				if (map.isAttribute()) {
					xstream.aliasAttribute(Class.forName(mapping.getClassName()), map.getMapTo(), map.getMapFrom());
				} else {
					xstream.aliasField(map.getMapFrom(), Class.forName(mapping.getClassName()), map.getMapTo());
				}
			}
			return xstream;
		} else {
			return null;
		}
	}

	public Integer getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public Integer getReadTimeoutMs() {
		return readTimeoutMs;
	}

	public void setReadTimeoutMs(Integer readTimeoutMs) {
		this.readTimeoutMs = readTimeoutMs;
	}

}
