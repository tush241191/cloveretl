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
package org.jetel.copy.error;

import org.jetel.data.DataField;
import org.jetel.mapping.element.ReturnCodeMappingElement.RetCode;

/**
 * The class provides support for copying an return code into a clover fields.
 * 
 * @author Jan Ausperger (jan.ausperger@javlinconsulting.cz)
 *         (c) Javlin, a.s. (www.javlin.eu)
 */
public abstract class CopyReturnCode {

	// retCode enum
	protected RetCode retCode;
	
	// target data field
	protected DataField dataField;
	
	/**
	 * Creates copy object.
	 * 
	 * @param retCode - retCode enum
	 * @param dataField - target data field
	 */
	protected CopyReturnCode(RetCode retCode, DataField dataField) {
		this.retCode = retCode;
		this.dataField = dataField;
	}
	
	/**
	 * Gets retCode enum (RETURN_CODE).
	 * 
	 * @return
	 */
	public RetCode getRetCode() {
		return retCode;
	}
	
	/**
	 * Sets value into target data field.
	 * 
	 * @param object
	 */
	public abstract void setValue(Object object);
}
