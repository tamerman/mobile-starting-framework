/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.icons.service;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.kuali.mobility.icons.dao.IconsDao;
import org.kuali.mobility.icons.entity.WebIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * Implementation of the <code>IconsService</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@Service
public class IconsServiceImpl implements IconsService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(IconsServiceImpl.class);

	public static final String CONFIG_AUTO_IMPORT = "icons.autoImport";
	public static final String CONFIG_AUTO_IMPORT_OVERRIDE = "icons.autoImportOverride";


	/**
	 * Locking map used to limit the number of threads that can generate temp files.
	 * This is to avoid the same icon file being converted to png by more than one
	 * thread, that can cause the png file to get corrupted.
	 */
	private static final ConcurrentMap<String, Semaphore> lockingMap = new ConcurrentHashMap<String, Semaphore>();


	/**
	 * If this flag is set to true, all icon configuration files (*-icons.xml) will be imported automatically
	 * icons.autoImport=true
	 */
	private boolean autoImport = true;

	/**
	 * If this flag is set to true, all icons that exist in the database, will be overridden with the
	 * config read from the file when autoImport is performed
	 * icons.autoImportOverride=true
	 */
	private boolean autoImportOverride = true;

	/**
	 * A reference to the <code>IconsDao</code>.
	 */
	private IconsDao dao;

	/**
	 * A reference to the iconProperties
	 */
	@javax.annotation.Resource(name = "iconsProperties")
	private Properties iconsProperties;


	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Sets the reference to the <code>IconsDao</code>.
	 *
	 * @param dao The reference to the <code>IconsDao</code>.
	 */
	public void setDao(IconsDao dao) {
		this.dao = dao;
	}

	/**
	 * Gets the reference to the <code>IconsDao</code>.
	 *
	 * @returns The reference to the <code>IconsDao</code>.
	 */
	public IconsDao getDao() {
		return this.dao;
	}

	@Override
	public List<WebIcon> getIcons() {
		return this.dao.getIcons();
	}


	@Override
	public WebIcon getIcon(String icon, String theme) {
		return this.dao.getIcon(icon, theme);
	}

	@Override
	public boolean iconExists(String iconName, String theme) {
		return this.dao.iconExists(iconName, theme);
	}

	@Override
	public WebIcon saveWebIcon(WebIcon icon) {
		return this.dao.saveWebIcon(icon);
	}

	/**
	 * Method called once the bean has been loaded
	 * Here we set all the config and perform the auto import of icons
	 * if configured
	 */
	private void init() {
        /*
         * Set the config.
         * We do not catch casting exceptions, if a setting has an invalid value, the server not start up.
         */
		this.autoImportOverride = Boolean.parseBoolean(iconsProperties.getProperty(CONFIG_AUTO_IMPORT_OVERRIDE, "true"));
		this.autoImport = Boolean.parseBoolean(iconsProperties.getProperty(CONFIG_AUTO_IMPORT, "true"));

		if (this.autoImport) {
			try {
				this.autoImport();
			} catch (Exception e) {
				throw new RuntimeException("Failed to autoImport icons", e);
			}
		} else {
			LOG.info("AutoImport is set to false, not searching for any icon configuration files");
		}
	}

	/**
	 * Import all icon configurations found in the classpath.
	 * Any files matching the pattern "*IconsConfig.xml" will
	 * be loaded as icon configuration files
	 */
	private void autoImport() throws Exception {
		PathMatchingResourcePatternResolver m = new PathMatchingResourcePatternResolver();
		Resource[] resources = m.getResources("*IconsConfig.xml");
		for (Resource r : resources) {
			LOG.debug(r.getFilename());
			importIcons(r.getInputStream());
		}

	}

	/**
	 * Some quick validation on fields
	 *
	 * @param value     The value to do validation on.
	 * @param allowNull Flag if null values are allowed
	 * @throws IllegalArgumentException Thrown if the validation failed.
	 */
	private void validateField(String value, boolean allowNull, boolean allowDash) throws IllegalArgumentException {

		if (value == null && !allowNull) {
			throw new IllegalArgumentException("A null value is given where it was not allowed");
		}

        /*
         * Values should not contain dashes, the icons controller uses dashes for attributes of
         * the icon being retrieved
         */
		if (value != null && !allowDash && value.indexOf('-') >= 0) {
			throw new IllegalArgumentException("Icon name may not contain a dash '-', name was '" + value + "'");
		}

        /*
         * Values should not contain spaces, it becomes a problem when using in css
         */
		if (value != null && value.indexOf(' ') >= 0) {
			throw new IllegalArgumentException("Icon name may not contain a spaces, name was '" + value + "'");
		}
	}

	/**
	 * Import icons from the input stream that contains the xml configuration file
	 *
	 * @param inputStream Input stream to the xml configuration
	 */
	public void importIcons(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = dombuilder.parse(inputStream);
		DOMBuilder builder = new DOMBuilder();
		Document doc = builder.build(w3cDocument);
		Element root = doc.getRootElement();
		List<Element> icons = root.getChildren("icon");
		for (int i = 0; i < icons.size(); i++) {
			Element iconElement = (Element) icons.get(i);
			String name, path, theme = null;
			name = iconElement.getAttribute("name").getValue();
			path = iconElement.getAttribute("path").getValue();
			if (iconElement.getAttribute("theme") != null) {
				theme = iconElement.getAttribute("theme").getValue();
			}

			// Validate values , throws exception if something is wrong
			validateField(name, false, false);
			validateField(path, false, true);
			validateField(theme, true, false);

			// Retrieve the icon with the same name and theme, to check if it exists
			WebIcon wi = this.getIcon(name, theme);
            /*
             * If we should not override existing icons,
             * we must first check if the icon already exist, and then ignore
             * the new one if there was one
             */
			boolean shouldPersist = true;

			// If there was an existing icon, we must device if we must override
			if (wi != null) {
                /*
                 * If we have to override, we only need to update the path
                 * because it is the only field that can change. An icon
                 * is defined by its name and theme
                 */
				if (autoImportOverride) {
					wi.setPath(path);
				} else {
					shouldPersist = false;
				}
			}
            /*
             * If there was no existing icon, we will create a new one
             * and persist it.
             */
			else {
				wi = new WebIcon(name, theme, path);
			}
			// If nothing wanted us to stop, continue and save
			if (shouldPersist) {
				this.saveWebIcon(wi);
			}
		}
	}


	/**
	 * Gets the image file, creating it if it does not exist
	 *
	 * @param iconName The icon to create a file for.
	 * @param size     The size the icon should be
	 * @return A reference to the file created for the icon.
	 */
	public File getImageFile(String iconName, String iconTheme, int size) {
		File workingDir = getTempdir();
		WebIcon icon = this.getIcon(iconName, iconTheme);


		StringBuilder sb = new StringBuilder();
		sb.append(icon.getName()).append('-');
		if (!StringUtils.isEmpty(icon.getTheme())) {
			sb.append(icon.getTheme()).append('-');
		}
		sb.append(size).append(".png");

		File imageFile = new File(workingDir, sb.toString());
		if (!imageFileExists(imageFile)) {
			imageFile = createFile(imageFile, icon, size);
		}
		return imageFile;
	}


	/**
	 * Creates the required file on the filesystem
	 *
	 * @param file File to write too
	 * @param icon WebIcon that we need to create a file of
	 * @return size The size of the icon (this size must have the multiplier applied already)
	 */
	private File createFile(final File file, WebIcon icon, int size) {
		Semaphore oneToAdd = new Semaphore(1);
        /*
         * If there currently is a semaphore for the filename, the existing sema will be returned,
         * else the new semaphore will be added and return null (because there is no old value)
         */
		Semaphore sema = lockingMap.putIfAbsent(file.getName(), oneToAdd);
		if (sema == null) {
			sema = oneToAdd;
		}
		try {

			sema.acquire(); // Second concurrent user will wait here
			if (imageFileExists(file)) {
				return file;
			}
			file.createNewFile();

			Resource iconResource = applicationContext.getResource(icon.getPath());
			PNGTranscoder t = new PNGTranscoder();
			t.addTranscodingHint(PNGTranscoder.KEY_MAX_HEIGHT, new Float(size));
			t.addTranscodingHint(PNGTranscoder.KEY_MAX_WIDTH, new Float(size));
			t.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, new Color(0, 0, 0, 0));
			OutputStream ostream = new FileOutputStream(file);

			// Create the transcoder input.
			TranscoderInput input = new TranscoderInput(iconResource.getInputStream());

			// Create the transcoder output.
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			t.transcode(input, output);

			// Flush and close the stream.
			ostream.flush();
			ostream.close();
		} catch (Exception ex) {
			LOG.warn("Exception while creating file", ex);
			if (file.exists()) {
				file.delete();
			}
		} finally {
			sema.release();
		}
		return file;
	}

	/**
	 * Returns true if the image file exists, and had a size greater than 0bytes
	 *
	 * @param file
	 * @return
	 */
	private static final boolean imageFileExists(File file) {
		return file != null && file.exists() && file.length() > 0;
	}

	/**
	 * Get the temp directory where icons can get generated.
	 * For tomcat this will commonly be ${CATALINA_HOME}/temp
	 *
	 * @return A file object to the temp directory.
	 */
	private File getTempdir() {
		String tempdir = System.getProperty("java.io.tmpdir");
		if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
			tempdir = tempdir + System.getProperty("file.separator");
		}
		return new File(tempdir);
	}

}
