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
package org.jetel.component.aggregate;

/**
 * Indicates error in aggregator initialization.
 * 
 * @author Jaroslav Urban (jaroslav.urban@javlinconsulting.cz)
 *         (c) Javlin Consulting (www.javlinconsulting.cz)
 */
public class AggregationException extends Exception {

	/**
	 * Allocates a new <tt>ProcessorInitializationException</tt> object.
	 * @param arg0 error message
	 */
	public AggregationException(String arg0) {
		super(arg0);
	}

	/**
	 * 
	 * Allocates a new <tt>ProcessorInitializationException</tt> object.
	 *
	 * @param cause
	 */
	public AggregationException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * Allocates a new <tt>ProcessorInitializationException</tt> object.
	 *
	 * @param arg0
	 * @param cause
	 */
	public AggregationException(String arg0, Throwable cause) {
		super(arg0, cause);
	}

}
