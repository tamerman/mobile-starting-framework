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

package org.kuali.mobility.security.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EncryptionUtilTest {

	Properties properties = new Properties();
	EncryptionUtil encryptionUtil;

	@Before
	public void setUp() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		properties.load(this.getClass().getResourceAsStream("kme.config.properties"));
		encryptionUtil = new EncryptionUtil(properties);
	}

	@After
	public void tearDown() {
		properties = null;
		encryptionUtil = null;
	}

	@Test
	public void testEncryptProperties() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		List<String> propsToEncrypt = new ArrayList<String>();
		propsToEncrypt.add("encryption.password");
		Properties encryptedProps = encryptionUtil.encryptProperties(propsToEncrypt);
		for (String propName : propsToEncrypt) {
			assertFalse("Plain password and encrypted password should not be same.", properties.getProperty(propName).equals(encryptedProps.getProperty(propName)));
		}
	}

	@Test
	public void testEncrypt() throws IllegalBlockSizeException, BadPaddingException {
		String plainPassword = properties.getProperty("encryption.password");
		String encryptedPassword = encryptionUtil.encrypt(plainPassword);
		assertFalse("Plain password and encrypted password should not be same.", plainPassword.equals(encryptedPassword));
	}

	@Test
	public void testDecrypt() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String plainPassword = properties.getProperty("encryption.password");
		String encryptedPassword = encryptionUtil.encrypt(plainPassword);
		String decryptedPassword = encryptionUtil.decrypt(encryptedPassword);
		assertTrue("Plain password and decrypted should be same.", plainPassword.equals(decryptedPassword));
	}

}
