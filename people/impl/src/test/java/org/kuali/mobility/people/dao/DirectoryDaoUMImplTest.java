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

package org.kuali.mobility.people.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:DirectorySpringBeans.xml")

public class DirectoryDaoUMImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryDaoUMImplTest.class);

    @Resource(name="directoryDao")
    private DirectoryDao dao;

    @Test
    public void testFindEntries() {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setSearchText("joe");
        SearchResult results = getDao().findEntries(criteria);
        assertTrue("failed to find directory entries for search criteria: " + criteria.getSearchText(), results != null && !results.getPeople().isEmpty());

        assertTrue( "failed to find groups for criteria.", results.getGroups() != null && results.getGroups().size() > 0);

        criteria = null;
        criteria = new SearchCriteria();
        criteria.setSearchText("ews");
        results = null;
        results = getDao().findEntries(criteria);
        
        assertTrue("failed to find directory entries for search criteria: " + criteria.getSearchText(), results != null && !results.getPeople().isEmpty());

        assertTrue( "Should have found too many groups and gotten an error.", results.getGroups() != null && results.getGroups().size() == 1 );
        
        criteria = null;
        criteria = new SearchCriteria();
        criteria.setSearchText("smith");
        results = null;
        results = getDao().findEntries(criteria);
        
        LOG.debug( "Person size is: "+results.getPeople().size() );
        for( Person p : results.getPeople() ) {
            LOG.debug( "Person is:" + p.getDisplayName() );
        }
        assertTrue("Failed to get an error for too many results: " + criteria.getSearchText(), results != null && results.getPeople().size() == 2 );
        assertTrue("No error found in person list.", "error".equalsIgnoreCase( results.getPeople().get(1).getDisplayName() ) );
        
        criteria = new SearchCriteria();
        criteria.setExactness("starts");
        criteria.setLastName("bouffor");

        results = getDao().findEntries(criteria);
        assertTrue("Found no people and should have.",results.getPeople()!=null && !results.getPeople().isEmpty());
        assertTrue("Found no groups and should have.",results.getGroups()!=null && !results.getGroups().isEmpty());
        /*
         * //Case: no result criteria.setSearchText("vvvv");
         * List<DirectoryEntry> results1 = dao.findEntries( criteria );
         * assertTrue( "failed on the case of no resuls, find directory entries
         * for search criteria: " + criteria.getSearchText(), results != null &&
         * results1.isEmpty() );
         *
         */
    }

    @Test
    public void testlookupGroup() {
        Group grp1 = getDao().lookupGroup("cn=ITS Android Dev,ou=User Groups,ou=Groups,dc=umich,dc=edu");
        assertTrue("failed to find the dn in the directory", grp1 != null);

        Group grp2 = getDao().lookupGroup("cn=MSuite Main Contact,ou=User Groups,ou=Groups,dc=umich,dc=edu");
        assertTrue("failed to find the dn in the directory", grp2 != null);

        Group grp3 = getDao().lookupGroup("cn=embafax,ou=User Groups,ou=Groups,dc=umich,dc=edu");
        assertTrue("failed to find the dn in the directory", grp3 != null);
    }

    @Test
    public void testfindSimpleGroup() {
        // no groupname case
        //List<Group> group = dao.findSimpleGroup("bdandamu");
        List<Group> group = getDao().findSimpleGroup("ITS Android Deployment");
        assertTrue("Found group in directory", group != null);


        //cn=ITS Android Dev,ou=User Groups,ou=Groups,dc=umich,dc=edu
        //yes groupname with one description
        List<Group> group1 = getDao().findSimpleGroup("android");
//        System.out.println("group1 size:" + group1.size());
//        for (Group g : group1) {
//            System.out.println("group DN :" + g.getDN() + ", Displayname :" + g.getDisplayName());
//        }
        assertTrue("failed to find group  in the directory", group1 != null && group1.size() > 0);

        //yes groupname with more than one description
        List<Group> group2 = getDao().findSimpleGroup("xin");
//        System.out.println("group2 size:" + group2.size());
//        for (Group g : group2) {
//            System.out.println("group DN :" + g.getDN() + ", Displayname :" + g.getDisplayName());
//        }
        assertTrue("failed to find group  in the directory", group2 != null && group2.size() > 0);

        /*
         * //yes groupname with too many List<Group> groupmore =
         * dao.findSimpleGroup("car"); System.out.println("group size:" +
         * groupmore.size()); for( Group g : groupmore ) {
         * System.out.println("group DN :" + g.getDN() + ", Displayname :" +
         * g.getDisplayName()); } assertTrue( "failed to find group in the
         * directory", groupmore != null );
         *
         * //yes groupname with too many List<Group> groupno =
         * dao.findSimpleGroup("vvv"); System.out.println("group size:" +
         * groupmore.size()); for( Group g : groupno ) {
         * System.out.println("group DN :" + g.getDN() + ", Displayname :" +
         * g.getDisplayName()); } assertTrue( "failed to find group in the
         * directory", groupno != null );
         */

        List<Group> groupme1 = getDao().findSimpleGroup("MSuite Main Contact");
//        System.out.println("groupme1 size:" + groupme1.size());
//        for (Group g : groupme1) {
//            System.out.println("group DN :" + g.getDN() + ", Displayname :" + g.getDisplayName());
//        }
        assertTrue("failed to find group  in the directory", groupme1 != null && groupme1.size() > 0);

        List<Group> groupme = getDao().findSimpleGroup("embafax");
//        System.out.println("groupme size:" + group2.size());
//        for (Group g : groupme) {
//            System.out.println("group DN :" + g.getDN() + ", Displayname :" + g.getDisplayName() + " , phone:" + g.getTelephoneNumber() + ", fax:" + g.getFacsimileTelephoneNumber());
//        }
        assertTrue("failed to find group  in the directory", groupme != null && groupme.size() > 0);

    }

    @Test
    public void testSearch() {
        // exactness=starts&firstName=&lastName=&location=undefined&searchText=&status=Any&username=joseswan
        SearchCriteria criteria = new SearchCriteria();
        criteria.setExactness("starts");
        criteria.setFirstName(null);
        criteria.setLastName(null);
        criteria.setLocation("undefined");
        criteria.setSearchText(null);
        criteria.setStatus("Any");
        criteria.setUserName("joseswan");

        SearchResult result = getDao().findEntries(criteria);

        assertFalse("Result is null and should not be.",result==null);

        assertFalse("Result has no people and should.",result.getPeople()==null);

        assertTrue("Result should have one person only.",result.getPeople().size()==1);
    }

    public DirectoryDao getDao() {
        return dao;
    }

    public void setDao(DirectoryDao dao) {
        this.dao = dao;
    }
}
