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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.kuali.mobility.shared.EncodingTypes;

import static org.junit.Assert.assertTrue;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class DigestUtilTest {
	private static final Logger LOG = LoggerFactory.getLogger(DigestUtil.class);

	@Test
	public void testMD5() {
		String[] text = {"AbCdEfGhIjKlMnOpQrStUvWxYz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"};
		String[] expectedHex = {"bca0b5815a4dbee5fa9946e9cd8356cb", "437bba8e0bf58337674f4539e75186ac"};
		String[] expectedBase64 = {"vKC1gVpNvuX6mUbpzYNWyw==", "Q3u6jgv1gzdnT0U551GGrA=="};

		assertTrue("HEX encoded MD5 does not match for case 0", expectedHex[0].equals(DigestUtil.getDigest(text[0], DigestConstants.MD5, EncodingTypes.HEX)));
		assertTrue("HEX encoded MD5 does not match for case 1", expectedHex[1].equals(DigestUtil.getDigest(text[1], DigestConstants.MD5, EncodingTypes.HEX)));

		assertTrue("Base64 encoded MD5 does not match for case 0", expectedBase64[0].equals(DigestUtil.getDigest(text[0], DigestConstants.MD5, EncodingTypes.BASE64)));
		assertTrue("Base64 encoded MD5 does not match for case 1", expectedBase64[1].equals(DigestUtil.getDigest(text[1], DigestConstants.MD5, EncodingTypes.BASE64)));
	}

	@Test
	public void testSHA1() {
		String[] text = {"AbCdEfGhIjKlMnOpQrStUvWxYz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"};
		String[] expectedHex = {"4d665b6ee0d689b32bcc7083fee366f76cbd817d", "80256f39a9d308650ac90d9be9a72a9562454574"};
		String[] expectedBase64 = {"TWZbbuDWibMrzHCD/uNm92y9gX0=", "gCVvOanTCGUKyQ2b6acqlWJFRXQ="};

//        System.out.println(DigestUtil.getDigest("mojojojomojojojoM0joJ0j0mojojojo0j0Joj0M",DigestConstants.SHA1,EncodingTypes.HEX));
//        System.out.println(DigestUtil.getDigest("fuzzyfuzzyM0joJ0j0fuzzy0j0Joj0M",DigestConstants.SHA1,EncodingTypes.HEX));

		assertTrue("HEX encoded SHA-1 does not match for case 0", expectedHex[0].equals(DigestUtil.getDigest(text[0], DigestConstants.SHA1, EncodingTypes.HEX)));
		assertTrue("HEX encoded SHA-1 does not match for case 1", expectedHex[1].equals(DigestUtil.getDigest(text[1], DigestConstants.SHA1, EncodingTypes.HEX)));

		assertTrue("Base64 encoded SHA-1 does not match for case 0", expectedBase64[0].equals(DigestUtil.getDigest(text[0], DigestConstants.SHA1, EncodingTypes.BASE64)));
		assertTrue("Base64 encoded SHA-1 does not match for case 1", expectedBase64[1].equals(DigestUtil.getDigest(text[1], DigestConstants.SHA1, EncodingTypes.BASE64)));
	}
}
