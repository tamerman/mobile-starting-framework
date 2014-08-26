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

package org.kuali.mobility.shared;

import java.util.HashMap;
import java.util.Map;

public class LoginServiceImpl implements LoginService {

	private static final Map<String, String> userMap = new HashMap<String, String>();

	/*
	    Here, there are two set of HashMap's entry. First one is for MD5 encryption and 
	    2nd one(commented) is for SHA-1 encryption. So, When You will plan to use MD5 encryption,
	    use the 1st HashMap's entry and comment the 2nd set of HashMap's entry and vice-versa.
    */
	/*
	    Here, both userName and password fields are same. So, the below users can log in
	    using same userName and password. Password fields are stored in encrypted format.
	    Use the below HashMap entry if you want to use MD5 encryption
	*/
	static {
		userMap.put("nurul", "6968a2c57c3a4fee8fadc79a80355e4d");
		userMap.put("joe", "8ff32489f92f33416694be8fdc2d4c22");
		userMap.put("nate", "b396645fffbeb1379510ab1fccadea5d");
		userMap.put("charl", "36ee71a98f85221446f6b12965952c94");
		userMap.put("aniruddha", "6da97bc1d43d22875f1cbcc47cd05d33");
		userMap.put("mitch", "fae53351b9effc708e764e871bef3119");
	}
    /*
	    If You want to use SHA-1 hashing encryption, use the below commented
	    Hash Map's entry.
    */
//    static{
//    	userMap.put("nurul", "06d6571dfa4a4ee157b900f3b11a44034339f2d2");
//        userMap.put("joe", "16a9a54ddf4259952e3c118c763138e83693d7fd");
//        userMap.put("nate", "4f3407de78bccc8cc160ee4d278d5efe7162e6b5");
//        userMap.put("charl", "d0fdfa50ba93c55e700837650403d8c576e9dd22");
//        userMap.put("aniruddha", "16bef47ba68b0e0a74efc3286dc0590b5d7d2c26");
//        userMap.put("mitch", "b2b9d047cb15a4ee2f66e3be0a97e512d2f22473");
//    }


	@Override
	public boolean isValidUser(String userId) {
		if (userMap.containsKey(userId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidLogin(String userId, String passwordHash) {
		if (isValidUser(userId)) {
			if (userMap.get(userId).equalsIgnoreCase(passwordHash)) {
				return true;
			}
		}
		return false;
	}

}
