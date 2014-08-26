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

package org.kuali.mobility.shared.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Vector;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class Bootables {

	/**
	 * A list of bootables
	 * The order of the bootables can not be guaranteed
	 */
	private final List<ServletContextListener> bootables = new Vector<ServletContextListener>();

	/**
	 * Registers a boot listener
	 *
	 * @param bootable
	 */
	public void registeredBootable(ServletContextListener bootable) {
		if (bootable != null) {
			bootables.add(bootable);
		}
	}


	void contextInitialized(ServletContextEvent sce) {
		for (ServletContextListener listeners : bootables) {
			listeners.contextInitialized(sce);
		}
	}

	void contextDestroyed(ServletContextEvent sce) {
		for (ServletContextListener listeners : bootables) {
			listeners.contextDestroyed(sce);
		}
	}


}
