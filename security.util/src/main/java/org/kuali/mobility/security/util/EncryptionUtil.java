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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionUtil implements EncryptionConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

	private SecretKeySpec keySpec;
	private Cipher encryptionCipher, decryptionCipher;

	private String encodedKey;
	private String algorithm;

	Properties kmeProperties;

	/**
	 * @param propertiesFile - the file which contains entry for key, algorithm and its sub type.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public EncryptionUtil(Properties properties) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		algorithm = properties.getProperty(ENCRYPTION_ALGORITHM);
		if (algorithm == null) {
			throw new RuntimeException("No encryption algorithm provided");
		}

		encodedKey = properties.getProperty(ENCRYPTION_KEY);

		this.kmeProperties = properties;

		keySpec = new SecretKeySpec(Base64.decodeBase64(encodedKey), algorithm);

		encryptionCipher = Cipher.getInstance(algorithm);
		decryptionCipher = Cipher.getInstance(algorithm);

		encryptionCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		decryptionCipher.init(Cipher.DECRYPT_MODE, keySpec);

	}

	public synchronized String encrypt(String value) throws IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedBytes = encryptionCipher.doFinal(value.getBytes());
		return Base64.encodeBase64String(encryptedBytes);
	}

	public synchronized String decrypt(String value) throws IllegalBlockSizeException, BadPaddingException {
		byte[] decodedBytes = Base64.decodeBase64(value);
		byte[] decryptedBytes = decryptionCipher.doFinal(decodedBytes);
		return new String(decryptedBytes);
	}

	public Properties encryptProperties(List<String> propertyNames) throws IllegalBlockSizeException, BadPaddingException {
		Properties encryptedProperties = new Properties();

		for (String property : propertyNames) {
			String encryptedValue = encrypt(kmeProperties.getProperty(property));
			encryptedProperties.put(property, encryptedValue);
			LOGGER.info(property + "=" + encryptedValue);
		}

		return encryptedProperties;
	}
}
