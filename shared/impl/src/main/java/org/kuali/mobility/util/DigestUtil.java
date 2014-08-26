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

package org.kuali.mobility.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.shared.EncodingTypes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implementation of the <code>AuthDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
public class DigestUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DigestUtil.class);

	public static byte[] getMD5(final String text) throws NoSuchAlgorithmException {
		MessageDigest md;
		byte[] digest = null;

		md = MessageDigest.getInstance(DigestConstants.MD5.toString());
		md.update(text.getBytes());
		digest = md.digest();

		return digest;
	}

	public static byte[] getSHA1Digest(final String text) throws NoSuchAlgorithmException {
		byte[] digest = null;

		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(text.getBytes());
//	    sha1.update( text.getBytes(), 0, key.length() );
		digest = sha1.digest();

		return digest;
	}

	public static String getDigest(final String text, final DigestConstants digestType, final EncodingTypes encodingType) {
		String hash;
		byte[] digest;
		try {
			switch (digestType) {
				case MD5:
					digest = getMD5(text);
					break;
				case SHA1:
				default:
					digest = getSHA1Digest(text);
					break;
			}
		} catch (NoSuchAlgorithmException nsae) {
			digest = null;
		}
		if (digest == null) {
			hash = text;
		} else {
			switch (encodingType) {
				case BASE64:
					hash = new String(Base64.encodeBase64(digest));
					break;
				case HEX:
				default:
					hash = new String(Hex.encodeHex(digest));
					break;
			}
		}
		return hash;
	}
}
