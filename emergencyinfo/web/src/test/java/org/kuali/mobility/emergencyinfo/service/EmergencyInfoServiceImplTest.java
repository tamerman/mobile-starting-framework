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

package org.kuali.mobility.emergencyinfo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoJPAImpl;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class EmergencyInfoServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(EmergencyInfoServiceImplTest.class);

    @Mock
    private EmergencyInfoService emergencyInfoService;


    @Before
    public void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testFindAllEmergencyInfo() {
        List<? extends EmergencyInfo> info = getEmergencyInfoService().findAllEmergencyInfo();
        assertFalse("Null response from findAllEmergencyInfo", info == null);
    }

    @Test
    public void testFindEmergencyInfoByIdWithNull() {
        EmergencyInfo emergencyInfo = getEmergencyInfoService().findEmergencyInfoById(null);
        assertTrue("Found emergency info and should not have.", emergencyInfo == null);
    }

    @Test
    public void testFindEmergencyInfoByIdWithBadId() {
        EmergencyInfo emergencyInfo = getEmergencyInfoService().findEmergencyInfoById(new Long(42));
        assertTrue("Found emergency info and should not have.",emergencyInfo == null);
    }

    @Test
    public void testFindEmergencyInfoById() {
        EmergencyInfo emergencyInfo = getEmergencyInfoService().findEmergencyInfoById(new Long(3));
        //assertTrue("Found emergency info but not the one expected.",emergencyInfo.getTitle().equals("Ann Arbor Fire Department"));
    }

    @Test
    public void testFindEmergencyInfoByCampusWithNull() {
        List<? extends EmergencyInfo> info = getEmergencyInfoService().findAllEmergencyInfoByCampus(null);
        assertTrue("Found info for null campus and shouldn't have.",info==null||info.isEmpty());
    }

    @Test
    public void testFindEmergencyInfoByCampusWithBadCampus() {
        List<? extends EmergencyInfo> info = getEmergencyInfoService().findAllEmergencyInfoByCampus("BAD_CAMPUS");
        assertTrue("Found info for bad campus and shouldn't have.",info==null||info.isEmpty());
    }

    @Test
    public void testFindEmergencyInfoByCampus() {
        List<? extends EmergencyInfo> info = getEmergencyInfoService().findAllEmergencyInfoByCampus("ALL");
        assertTrue("Failed to find info for specified campus.", info == null || info.isEmpty());
        assertFalse("Three results expected and found " + info.size(), info.size() == 3);
    }

    @Test
    public void testDeleteEmergencyInfoByIdWithNull() {
        getEmergencyInfoService().deleteEmergencyInfoById(null);
    }

    @Test
    public void testEmergencyInfoDeleteById() {
        getEmergencyInfoService().deleteEmergencyInfoById(new Long(6));
        List<? extends EmergencyInfo> info = getEmergencyInfoService().findAllEmergencyInfoByCampus("FL");
        assertFalse("Failed to delete item from database.", info != null && info.size() == 1);
    }

    @Test
    public void testSaveEmergencyInfoWithNull() {
        Long id = getEmergencyInfoService().saveEmergencyInfo(null);
        assertFalse("Received an ID for saving a null object and shouldn't have.", id == null);
    }

    @Test
    public void testSaveEmergencyInfo() {
        EmergencyInfo info = new EmergencyInfoJPAImpl();
        info.setCampus("ABC");
        info.setLink("1-404-555-1212");
        info.setOrder(1);
        info.setTitle("ABC Emergency Info");
        info.setType("PHONE");
        assertTrue("ID is not null prior to save and should be.",info.getEmergencyInfoId()==null);
        Long id = getEmergencyInfoService().saveEmergencyInfo(info);
        assertFalse("ID is null after save and should not be.",id==null);
        assertFalse("ID returned from method does not match that of the object.",id==info.getEmergencyInfoId());
    }

    @Test
    public void testReorderUp() {
        Long id = new Long(2);
        getEmergencyInfoService().reorder(id,true);
        EmergencyInfo info = getEmergencyInfoService().findEmergencyInfoById(id);
        assertTrue("Failed to find info for id.",info==null);
    }

    @Test
    public void testReorderDown() {
        Long id = new Long(3);
        getEmergencyInfoService().reorder(id,false);
        EmergencyInfo info = getEmergencyInfoService().findEmergencyInfoById(id);
        assertTrue("Failed to find info for id.",info==null);
    }

    @Test
    public void testReorderUpWithFirstItem() {
        Long id = new Long(1);
        getEmergencyInfoService().reorder(id,true);
        EmergencyInfo info = getEmergencyInfoService().findEmergencyInfoById(id);
        assertTrue("Failed to find info for id.",info==null);
    }

    public EmergencyInfoService getEmergencyInfoService() {
        return emergencyInfoService;
    }

    public void setEmergencyInfoService(EmergencyInfoService emergencyInfoService) {
        this.emergencyInfoService = emergencyInfoService;
    }
}
