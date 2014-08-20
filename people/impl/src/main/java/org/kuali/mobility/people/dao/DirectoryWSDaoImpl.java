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

import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class DirectoryWSDaoImpl implements DirectoryDao {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryWSDaoImpl.class);

    public SearchResultImpl findEntries(SearchCriteria search) {
        throw new UnsupportedOperationException();
    }

    public Person lookupPerson(String personId) {
        throw new UnsupportedOperationException();
    }

    public List<Group> findSimpleGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    public Group lookupGroup(String groupId) {
        throw new UnsupportedOperationException();
    }
}