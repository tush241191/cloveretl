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
package org.jetel.connection.jdbc.specific.impl;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.Types;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetel.connection.jdbc.AbstractCopySQLData.CopyOracleXml;
import org.jetel.connection.jdbc.specific.conn.OracleConnection;
import org.jetel.data.DataRecord;
import org.jetel.database.sql.CopySQLData;
import org.jetel.database.sql.DBConnection;
import org.jetel.database.sql.JdbcDriver;
import org.jetel.database.sql.SqlConnection;
import org.jetel.exception.JetelException;
import org.jetel.metadata.DataFieldMetadata;

/**
 * Oracle specific behaviour.
 * 
 * @author Martin Zatopek (martin.zatopek@javlinconsulting.cz)
 *         (c) Javlin Consulting (www.javlinconsulting.cz)
 *
 * @created Jun 3, 2008
 */
public class OracleSpecific extends AbstractJdbcSpecific {

	/** the SQL comments pattern conforming to the SQL standard */
	private static final Pattern COMMENTS_PATTERN = Pattern.compile("--[^\r\n]*|/\\*(?!\\+).*?\\*/", Pattern.DOTALL);

	private static final String ORACLE_TYPES_CLASS_NAME = "oracle.jdbc.OracleTypes";

	private static final String ORACLE_RESULT_SET_PARAMETER_TYPE_FIELD = "CURSOR";

	private static final OracleSpecific INSTANCE = new OracleSpecific();
	
	public static OracleSpecific getInstance() {
		return INSTANCE;
	}

	protected OracleSpecific() {
		super();
	}

	@Override
	public AutoGeneratedKeysType getAutoKeyType() {
		return AutoGeneratedKeysType.MULTI;
	}
	
	@Override
	public SqlConnection createSQLConnection(DBConnection dbConnection, Connection connection, OperationType operationType) throws JetelException {
		return new OracleConnection(dbConnection, connection, operationType);
	}

	@Override
	public String quoteIdentifier(String identifier) {
        return ('"' + identifier + '"');
    }

	/* (non-Javadoc)
	 * @see org.jetel.connection.jdbc.specific.impl.AbstractJdbcSpecific#jetelType2sql(org.jetel.metadata.DataFieldMetadata)
	 */
	@Override
	public int jetelType2sql(DataFieldMetadata field){
		switch (field.getDataType()) {
		case DATE:
			boolean isDate = field.isDateFormat();
			boolean isTime = field.isTimeFormat();
			if (isDate && !isTime)
				return Types.DATE;
			return Types.TIMESTAMP;
		case BYTE:
		case CBYTE:
			return Types.VARBINARY;
		}
		return super.jetelType2sql(field);
	}
	
	@Override
	public char sqlType2jetel(int sqlType) {
		//OracleResultSetMetadata.getColumnType(int column) returns 100 for BINARY_FLOAT and 101 for BINARY_DOUBLE type
		//100 and 101 are not constants from java.sql.Types
		if (sqlType == 100 || sqlType == 101) {
			return DataFieldMetadata.NUMERIC_FIELD;
		}
		return super.sqlType2jetel(sqlType);
	}

	@Override
	public String sqlType2str(int sqlType) {
		switch(sqlType) {
		case Types.BOOLEAN :
			return "SMALLINT";
		case Types.VARCHAR :
			return "VARCHAR2";
		case Types.NUMERIC :
			return "FLOAT";
		case Types.BIGINT :
			return "NUMBER";
		case Types.VARBINARY :
		case Types.BINARY :
			return "RAW";
		}
		return super.sqlType2str(sqlType);
	}

	@Override
	public String jetelType2sqlDDL(DataFieldMetadata field) {
		int type = jetelType2sql(field); 
		switch(type) {
		case Types.BOOLEAN :
			return sqlType2str(type);
		case Types.BIGINT :
			return sqlType2str(type) + "(11,0)";
		}
		return super.jetelType2sqlDDL(field);
	}
    
	/* (non-Javadoc)
	 * @see org.jetel.connection.jdbc.specific.impl.AbstractJdbcSpecific#createCopyObject(int, org.jetel.metadata.DataFieldMetadata, org.jetel.data.DataRecord, int, int)
	 */
	@Override
	public CopySQLData createCopyObject(int sqlType, DataFieldMetadata fieldMetadata, DataRecord record, int fromIndex, int toIndex) {
		CopySQLData obj = null;

		switch (sqlType) {
	    case CopyOracleXml.XML_TYPE:
	        obj = new CopyOracleXml(record, fromIndex, toIndex);
	        break;
		default:
			return super.createCopyObject(sqlType, fieldMetadata, record, fromIndex, toIndex);
		}
	
		obj.setSqlType(sqlType);
		return obj;
	}

	/* (non-Javadoc)
	 * @see org.jetel.connection.jdbc.specific.impl.AbstractJdbcSpecific#getResultSetParameterTypeField()
	 */
	@Override
	public String getResultSetParameterTypeField() {
		return ORACLE_RESULT_SET_PARAMETER_TYPE_FIELD;
	}
	
	/* (non-Javadoc)
	 * @see org.jetel.connection.jdbc.specific.impl.AbstractJdbcSpecific#getTypesClassName()
	 */
	@Override
	public String getTypesClassName() {
		return ORACLE_TYPES_CLASS_NAME;
	}

	@Override
	public Pattern getCommentsPattern() {
		return COMMENTS_PATTERN;
	}

	@Override
	public boolean isSchemaRequired() {
		return true;
	}

	@Override
	public boolean isJetelTypeConvertible2sql(int sqlType, DataFieldMetadata field) {
		switch (field.getDataType()) {
		case BOOLEAN:
		case DECIMAL:
		case NUMBER:
		case LONG:
		case INTEGER:
			switch (sqlType) {
			case Types.NUMERIC:
				return true;
			}
		case DATE:
			switch (sqlType) {
			case Types.TIMESTAMP:
				return true;
			}
		default:
			return super.isJetelTypeConvertible2sql(sqlType, field);
		}
	}

	@Override
	public void unloadDriver(JdbcDriver driver) {
		super.unloadDriver(driver);
		
		if (driver == null) {
			return;
		}
		
		ClassLoader classLoader = driver.getClassLoader();
		if (classLoader != null) {
			// Oracle driver registers a MBean for each classloader
			try {
				Hashtable<String,String> table = new Hashtable<String, String>();
				table.put("type", "diagnosability");
				String classLoaderId = classLoader.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(classLoader)); 
				table.put("name", classLoaderId);
				ObjectName name = new ObjectName("com.oracle.jdbc", table);
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				if (server.isRegistered(name)) {
					server.unregisterMBean(name);
				}
			} catch (Exception ex) {
				Log logger = LogFactory.getLog(OracleSpecific.class);
				if (logger.isDebugEnabled()) {
					logger.debug("Exception on deregistering the Oracle driver MBean", ex);
				}
			}
		}
	}
	
	@Override
	public boolean supportsTerminatingSemicolons() {
		return false;
	}
	
}
