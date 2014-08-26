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

package org.kuali.mobility.grades.entity;

import java.io.Serializable;

/**
 * A class representing the Marks for a Module
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class ModuleResults implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2815401561684361236L;

	/**
	 * Name of the module this result is for e.g. MATH 101
	 */
	private String moduleName;

	/**
	 * Result for the exam contribution for this module e.g. 70 %
	 */
	private String examMark;

	/**
	 * Comment for the exam mark e.g. Pass
	 */
	private String examMarkComment;

	/**
	 * Participation contribution fro this module e.g 70%
	 */
	private String participationMark;

	/**
	 * Comment for the participation mark e.g Fail - Need to rewrite
	 */
	private String participationMarkComment;

	/**
	 * Final result mark for this Module e.g 98%
	 */
	private String finalMark;

	/**
	 * Comment for the final module mark e.g Pass com laude
	 */
	private String finalMarkComment;

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return this.moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the examMark
	 */
	public String getExamMark() {
		return this.examMark;
	}

	/**
	 * @param examMark the examMark to set
	 */
	public void setExamMark(String examMark) {
		this.examMark = examMark;
	}

	/**
	 * @return the examMarkComment
	 */
	public String getExamMarkComment() {
		return this.examMarkComment;
	}

	/**
	 * @param examMarkComment the examMarkComment to set
	 */
	public void setExamMarkComment(String examMarkComment) {
		this.examMarkComment = examMarkComment;
	}

	/**
	 * @return the participationMark
	 */
	public String getParticipationMark() {
		return this.participationMark;
	}

	/**
	 * @param participationMark the participationMark to set
	 */
	public void setParticipationMark(String participationMark) {
		this.participationMark = participationMark;
	}

	/**
	 * @return the participationMarkComment
	 */
	public String getParticipationMarkComment() {
		return this.participationMarkComment;
	}

	/**
	 * @param participationMarkComment the participationMarkComment to set
	 */
	public void setParticipationMarkComment(String participationMarkComment) {
		this.participationMarkComment = participationMarkComment;
	}

	/**
	 * @return the finalMark
	 */
	public String getFinalMark() {
		return this.finalMark;
	}

	/**
	 * @param finalMark the finalMark to set
	 */
	public void setFinalMark(String finalMark) {
		this.finalMark = finalMark;
	}

	/**
	 * @return the finalMarkComment
	 */
	public String getFinalMarkComment() {
		return this.finalMarkComment;
	}

	/**
	 * @param finalMarkComment the finalMarkComment to set
	 */
	public void setFinalMarkComment(String finalMarkComment) {
		this.finalMarkComment = finalMarkComment;
	}


}
