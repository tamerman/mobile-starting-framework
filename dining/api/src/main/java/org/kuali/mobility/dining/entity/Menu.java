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

package org.kuali.mobility.dining.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@XmlRootElement( name="menu" )
@XmlAccessorType(XmlAccessType.FIELD)
public class Menu implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(Menu.class);

	private static final long serialVersionUID = -2096908398187406294L;

    private String name;
    private String description;
	private Date date;
    private String formattedDate;

	private int ratingCount;
	private double ratingScore;

    @XmlElement( name="category" )
	private List<? extends MenuItemGroup> itemGroups;
	
    public Date getDate() {
		return date;
	}

    public void setDate(Date date) {
		this.date = date;
        if (date != null) {
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
                setFormattedDate( sdf.format(new Date(this.getDate().getTime()) ) );
            } catch( Exception e ) {
                LOG.error(e.getLocalizedMessage(),e);
            }
        }
	}

    public int getRatingCount() {
		return ratingCount;
	}

    public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

    public double getRatingScore() {
		return ratingScore;
	}

    public void setRatingScore(double ratingScore) {
		this.ratingScore = ratingScore;
	}

	public List<? extends MenuItemGroup> getItemGroups() {
		return itemGroups;
	}

    public void setItemGroups(List<? extends MenuItemGroup> itemGroups) {
		this.itemGroups = itemGroups;
	}
	
    public String getFormattedRating() {
		DecimalFormat score = new DecimalFormat("0.0");
		return score.format(this.getRatingScore());
	}
	
    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
