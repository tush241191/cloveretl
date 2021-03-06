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
package org.jetel.ctl.ASTnode;

import org.jetel.ctl.TransformLangExecutorRuntimeException;
import org.jetel.ctl.TransformLangParser;
import org.jetel.ctl.TransformLangParserVisitor;
import org.jetel.ctl.data.Scope;

public class CLVFForeachStatement extends SimpleNode implements ScopeHolder {
	private Scope scope;
	private int[] typeSafeFields;
	
	
	public CLVFForeachStatement(int id) {
		super(id);
	}

	public CLVFForeachStatement(TransformLangParser p, int id) {
		super(p, id);
	}

	public CLVFForeachStatement(CLVFForeachStatement node) {
		super(node);
		this.scope = node.scope;
		if (node.typeSafeFields != null) {
			this.typeSafeFields = new int[node.typeSafeFields.length];
			System.arraycopy(node.typeSafeFields, 0, this.typeSafeFields, 0, node.typeSafeFields.length);
		}
	}
	
	@Override
	public boolean isBreakable(){
		return true;
	}
	
	/** Accept the visitor. This method implementation is identical in all SimpleNode descendants. */
	@Override
	public Object jjtAccept(TransformLangParserVisitor visitor, Object data) {
		try {
			return visitor.visit(this, data);
		} catch (TransformLangExecutorRuntimeException e) {
			if (e.getNode() == null) {
				e.setNode(this);
			}
			throw e;
		} catch (RuntimeException e) {
			throw new TransformLangExecutorRuntimeException(this, null, e);
		}
	}

	@Override
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	
	@Override
	public Scope getScope() {
		return scope;
	}

	public void setTypeSafeFields(int[] typeSafeFields) {
		this.typeSafeFields = typeSafeFields;
	}

	public int[] getTypeSafeFields() {
		return typeSafeFields;
	}
	
	@Override
	public SimpleNode duplicate() {
		return new CLVFForeachStatement(this);
	}

}
