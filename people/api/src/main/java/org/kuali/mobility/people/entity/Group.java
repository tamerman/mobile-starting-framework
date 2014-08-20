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

package org.kuali.mobility.people.entity;

import java.util.List;

/**
 * Interface for a group
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
public interface Group extends DirectoryEntry {

	/**
	 * 
	 * @return
	 */
	public String  getHashedDN();

	/**
	 * 
	 * @return
	 */
	public String getDN();
	
	/**
	 * 
	 * @param userName
	 */
	public void setDN(String userName);
	
	/**
	 * 
	 * @return
	 */
	public String getDisplayName();
	
	/**
	 * 
	 * @param displayName
	 */
	public void setDisplayName(String displayName);

	/**
	 * 
	 * @return
	 */
	public List<String> getDescriptions();
	
	/**
	 * 
	 * @param descriptions
	 */
	public void setDescriptions(List<String> descriptions);

	/**
	 * 
	 * @return
	 */
	public String getEmail();
	
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email);

	/**
	 * 
	 * @return
	 */
	public String getTelephoneNumber();
	
	/**
	 * 
	 * @param telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber);

	/**
	 * 
	 * @return
	 */
	public String getFacsimileTelephoneNumber();
	
	/**
	 * 
	 * @param facsimileTelephoneNumber
	 */
	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber);

	/**
	 * 
	 * @return
	 */
	public List<? extends Person> getMembers();
	
	/**
	 * 
	 * @param members
	 */
	public void setMembers( List<? extends Person> members );

	/**
	 * 
	 * @return
	 */
	public List<? extends Person> getOwners();
	
	/**
	 * 
	 * @param owners
	 */
	public void setOwners( List<? extends Person> owners );

	/**
	 * 
	 * @return
	 */
	public List<? extends Group> getSubGroups();
	
	/**
	 * 
	 * @param subGroups
	 */
	public void setSubGroups( List<? extends Group> subGroups );
}
