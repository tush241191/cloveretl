/*
 * jETeL/CloverETL - Java based ETL application framework.
 * Copyright (c) Javlin, a.s. (info@cloveretl.com)
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jetel.util.property;

import org.jetel.test.CloverTestCase;

/**
 * @author Kokon (info@cloveretl.com)
 *         (c) Javlin, a.s. (www.cloveretl.com)
 *
 * @created 6.6.2013
 */
public class RefResFlagTest extends CloverTestCase {

	public void test() {
		assertTrue(RefResFlag.ALL_OFF.resolveSpecCharacters() == false);
		
		assertTrue(RefResFlag.SPEC_CHARACTERS_OFF.resolveSpecCharacters() == false);

		assertTrue(RefResFlag.REGULAR.resolveSpecCharacters() == true);

		assertTrue(RefResFlag.URL.resolveSpecCharacters() == false);
		
		RefResFlag flag;

		flag = RefResFlag.REGULAR.resolveSpecCharacters(false);
		assertTrue(flag.resolveSpecCharacters() == false);
		assertTrue(RefResFlag.REGULAR.resolveSpecCharacters() == true);
		flag = flag.resolveSpecCharacters(true);
		assertTrue(flag.resolveSpecCharacters() == true);
		assertTrue(RefResFlag.REGULAR.resolveSpecCharacters() == true);
	}
	
}
