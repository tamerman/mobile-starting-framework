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

package org.kuali.mobility.writer.entity;

import org.junit.Test;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Unit test for writer permissions.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public class WriterPermissionsTest {

	private static final String instance1 = "paper1";
	private static final String PERM_JOURNALIST_PAPER_1 = WriterPermissions.JOURNALIST_PREFIX + "PAPER1";
	private static final String PERM_EDITOR_PAPER_1 = WriterPermissions.EDITOR_PREFIX + "PAPER1";
	private static final String PERM_ADMIN_PAPER_1 = WriterPermissions.ADMIN_PREFIX + "PAPER1";
	private static final String PERM_SPAMMER_PAPER_1 = WriterPermissions.SPAMMER_PREFIX + "PAPER1";

	private static final String instance2 = "paper2";
	private static final String PERM_JOURNALIST_PAPER_2 = WriterPermissions.JOURNALIST_PREFIX + "PAPER2";
	private static final String PERM_EDITOR_PAPER_2 = WriterPermissions.EDITOR_PREFIX + "PAPER2";
	private static final String PERM_ADMIN_PAPER_2 = WriterPermissions.ADMIN_PREFIX + "PAPER2";
	private static final String PERM_SPAMMER_PAPER_2 = WriterPermissions.SPAMMER_PREFIX + "PAPER2";



	private User buildUser(String ...groups){
		User user = new UserImpl();
		for(String group : groups){
			user.addGroup(mkGroup(group));
		}
		return user;
	}

	private static Group mkGroup(String key){
		Group g = new GroupImpl();
		g.setName(key);
		return g;
	}


	@Test
	public void testJournalist(){
		User testUser = buildUser(PERM_JOURNALIST_PAPER_1);
		assertTrue("User expected to be seen as journalist", WriterPermissions.getJournalistExpression(instance1).evaluate(testUser));
	}

	@Test
	public void testNotJournalist(){
		User testUser = buildUser(PERM_JOURNALIST_PAPER_1);
		assertFalse("User NOT expected to be seen as journalist", WriterPermissions.getJournalistExpression(instance2).evaluate(testUser));
	}

	@Test
	public void testEditor(){
		User testUser = buildUser(PERM_EDITOR_PAPER_1);
		assertTrue("User expected to be seen as editor", WriterPermissions.getEditorExpression(instance1).evaluate(testUser));
	}

	@Test
	public void testNotEditor(){
		User testUser = buildUser(PERM_EDITOR_PAPER_1);
		assertFalse("User NOT expected to be seen as editor", WriterPermissions.getEditorExpression(instance2).evaluate(testUser));
	}

	@Test
	public void testSpammer(){
		User testUser = buildUser(PERM_SPAMMER_PAPER_1);
		assertTrue("User expected to be seen as spammer", WriterPermissions.getSpammerExpression(instance1).evaluate(testUser));
	}

	@Test
	public void testNotSpammer(){
		User testUser = buildUser(PERM_SPAMMER_PAPER_1);
		assertFalse("User NOT expected to be seen as spammer", WriterPermissions.getSpammerExpression(instance2).evaluate(testUser));
	}

	@Test
	public void testAdmin(){
		User testUser = buildUser(PERM_ADMIN_PAPER_1);
		assertTrue("User expected to be seen as admin", WriterPermissions.getAdminExpression(instance1).evaluate(testUser));
	}

	@Test
	public void testNotAdmin(){
		User testUser = buildUser(PERM_ADMIN_PAPER_1);
		assertFalse("User NOT expected to be seen as admin", WriterPermissions.getAdminExpression(instance2).evaluate(testUser));
	}

	@Test
	public void testEditorOrAdmin1(){
		User testUser = buildUser(PERM_ADMIN_PAPER_1);
		assertTrue("User expected to be seen as editor or admin", WriterPermissions.getEditorOrAdminExpression(instance1).evaluate(testUser));
	}

	@Test
	public void testEditorOrAdmin2(){
		User testUser = buildUser(PERM_EDITOR_PAPER_1);
		assertTrue("User expected to be seen as editor or admin", WriterPermissions.getEditorOrAdminExpression(instance1).evaluate(testUser));
	}

    @Test
    public void testJournalistOrEditor1(){
        User testUser = buildUser(PERM_JOURNALIST_PAPER_1);
        assertTrue("User expected to be seen as journalist or editor", WriterPermissions.getJournalistOrEditorExpression(instance1).evaluate(testUser));
    }

    @Test
    public void testJournalistOrEditor2(){
        User testUser = buildUser(PERM_EDITOR_PAPER_1);
        assertTrue("User expected to be seen as journalist or editor", WriterPermissions.getJournalistOrEditorExpression(instance1).evaluate(testUser));
    }


	@Test
	public void testAllRoles(){
		User testUser = buildUser(PERM_JOURNALIST_PAPER_1, PERM_EDITOR_PAPER_1, PERM_ADMIN_PAPER_1, PERM_SPAMMER_PAPER_1);
		assertTrue("User expected to be seen as journalist",			WriterPermissions.getJournalistExpression(instance1).evaluate(testUser));
		assertTrue("User expected to be seen as editor",				WriterPermissions.getEditorExpression(instance1).evaluate(testUser));
		assertTrue("User expected to be seen as admin",					WriterPermissions.getAdminExpression(instance1).evaluate(testUser));
		assertTrue("User expected to be seen as spammer",				WriterPermissions.getSpammerExpression(instance1).evaluate(testUser));
		assertTrue("User expected to be seen as editor or admin",		WriterPermissions.getEditorOrAdminExpression(instance1).evaluate(testUser));
		assertTrue("User expected to be seen as journalist or editor",	WriterPermissions.getJournalistOrEditorExpression(instance1).evaluate(testUser));
	}

}
