/* Generated By:JavaCC: Do not edit this line. DDL2Clover.java */
package org.jetel.util.ddl2clover;

import java.util.*;
import java.io.*;
import org.jetel.metadata.*;
import java.math.*;
import org.jetel.data.*;

public class DDL2Clover implements DDL2CloverConstants {

        /**
		 * The class parses sql script containing only create statements. Output is a DataRecordMetadata List.
		 */

        private List<DataRecordMetadata> list = new LinkedList<DataRecordMetadata>();
        private String fieldDelimiter;
        private String recordDelimiter;

        private static final Long booleanLenght = Long.valueOf(5); // false / true
        private static final Long byteLenght = Long.valueOf(String.valueOf(Byte.MIN_VALUE).length());
        private static final Long charLenght = Long.valueOf(1); //
        private static final Long integerLenght = Long.valueOf(String.valueOf(Integer.MIN_VALUE).length());
        private static final Long longLenght = Long.valueOf(String.valueOf(Long.MIN_VALUE).length());
        private static final Long floatLenght = Long.valueOf(String.valueOf(7));
        private static final Long doubleLenght = Long.valueOf(String.valueOf(15));
        private static final Long dateLenght = Long.valueOf(10); // "10.10.2007"
        private static final Long dateTimeLenght = Long.valueOf(19); // "10.10.2007 23:10:10"
        private static final Long textLenght = Long.valueOf(256);

        public static void main(String args[]) throws ParseException, FileNotFoundException {
                DDL2Clover parser = new DDL2Clover(new FileInputStream(new File("./src/org/jetel/util/ddl2clover/ddl_statements.txt")));
                parser.getDataRecordMetadataList(";", "\u005cn");
                parser.testPrint(parser.list);
                System.out.println("Ok");
        }

        public List<DataRecordMetadata> getDataRecordMetadataList() throws ParseException {
                return getDataRecordMetadataList(null, null);
        }

        public List<DataRecordMetadata> getDataRecordMetadataList(String fieldDelimiter, String recordDelimiter) throws ParseException {
                this.fieldDelimiter = fieldDelimiter;
                this.recordDelimiter = recordDelimiter;
                while (!isEOF()) {
                        list.add(createTableStatement());
                }
                return list;
        }

        private void testPrint(List<DataRecordMetadata> list) {
                DataRecordMetadataXMLReaderWriter writer = new DataRecordMetadataXMLReaderWriter();
                for (DataRecordMetadata dataRecordMetadata : list) {
                        writer.write(dataRecordMetadata, System.out);
                        System.out.println();
                }
        }

/*******************************************************************
 * The SQL syntatic grammar starts here
 *******************************************************************/
  final public DataRecordMetadata createTableStatement() throws ParseException {
        DataRecordMetadata dataRecordMetadata;
        List<DataFieldMetadata> list;
    jj_consume_token(CREATE);
    switch (jj_nt.kind) {
    case GLOBAL:
    case LOCAL:
    case TEMPORARY:
      tableScope();
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    jj_consume_token(TABLE);
    if (jj_2_1(2)) {
      jj_consume_token(IDENTIFIER);
      jj_consume_token(DOT);
    } else {
      ;
    }
                        String tableName = identifier();
                        // the record name will be constructed from the label
                        dataRecordMetadata = new DataRecordMetadata(DataRecordMetadata.EMPTY_NAME);
                        dataRecordMetadata.setLabel(tableName);
                        list = tableElementList();
                        for (DataFieldMetadata field : list) {
                            dataRecordMetadata.addField(field);
                        }
    switch (jj_nt.kind) {
    case ON:
      jj_consume_token(ON);
      jj_consume_token(COMMIT);
      switch (jj_nt.kind) {
      case DELETE:
      case PRESERVE:
        switch (jj_nt.kind) {
        case PRESERVE:
          jj_consume_token(PRESERVE);
          break;
        case DELETE:
          jj_consume_token(DELETE);
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      jj_consume_token(ROWS);
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    jj_consume_token(SEMICOLON);
                if (fieldDelimiter != null && recordDelimiter != null) {
                        dataRecordMetadata.setRecType(DataRecordMetadata.DELIMITED_RECORD);
                } else if (fieldDelimiter == null && recordDelimiter == null) {
                        dataRecordMetadata.setRecType(DataRecordMetadata.FIXEDLEN_RECORD);
                }
          dataRecordMetadata.normalize();
          {if (true) return dataRecordMetadata;}
    throw new Error("Missing return statement in function");
  }

  final public List<DataFieldMetadata> tableElementList() throws ParseException {
        List<DataFieldMetadata> list = new LinkedList<DataFieldMetadata>();
        DataFieldMetadata ret;
    jj_consume_token(OPENPAREN);
    ret = tableElement();
                ret.setDelimiter(fieldDelimiter);
                list.add(ret);
                skipAllUptoComaOrCloseParen();
    label_1:
    while (true) {
      switch (jj_nt.kind) {
      case COMA:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_1;
      }
      jj_consume_token(COMA);
      ret = tableElement();
                if (ret != null) {
                        ret.setDelimiter(fieldDelimiter);
                        list.add(ret);
                        skipAllUptoComaOrCloseParen();
                }
    }
                if (ret != null) {
                        ret.setDelimiter(recordDelimiter);
                }
    jj_consume_token(CLOSEPAREN);
          {if (true) return list;}
    throw new Error("Missing return statement in function");
  }

  void skipAllUptoComaOrCloseParen() throws ParseException {
        Token tok;
        int nesting = 0;
        boolean strStr = false;
        while (true) {
                tok = getToken(1);
        if (tok.kind == STRSTR && strStr) strStr=false;
        else if (tok.kind == STRSTR && !strStr) strStr=true;
                else if (!strStr) {
                        if (tok.kind == OPENPAREN) nesting++;
                else if (tok.kind == CLOSEPAREN && nesting > 0) nesting--;
                        else if (tok.kind == COMA && nesting == 0) return;
                        else if (tok.kind == CLOSEPAREN && nesting == 0) return;
                }
        tok = getNextToken();
        if (tok.image.equals("")) return;
        }
  }

  final public void tableScope() throws ParseException {
    switch (jj_nt.kind) {
    case GLOBAL:
    case LOCAL:
      switch (jj_nt.kind) {
      case GLOBAL:
        jj_consume_token(GLOBAL);
        break;
      case LOCAL:
        jj_consume_token(LOCAL);
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    jj_consume_token(TEMPORARY);
  }

  final public boolean isEOF() throws ParseException {
        boolean isEof = false;
    switch (jj_nt.kind) {
    case 0:
      jj_consume_token(0);
      isEof = t();
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
         {if (true) return isEof;}
    throw new Error("Missing return statement in function");
  }

  final public boolean t() throws ParseException {
         {if (true) return true;}
    throw new Error("Missing return statement in function");
  }

//+
  final public ColumnConstraint columnConstraintDefinition() throws ParseException {
        ColumnConstraint value = null;
    switch (jj_nt.kind) {
    case NOT:
      jj_consume_token(NOT);
      jj_consume_token(NULL);
                              value = ColumnConstraint.NOT_NULL;
      break;
    case NULL:
      jj_consume_token(NULL);
                        value = ColumnConstraint.NULL;
      break;
    case UNIQUE:
      jj_consume_token(UNIQUE);
                          value = ColumnConstraint.UNIQUE;
      break;
    case PRIMARY:
      jj_consume_token(PRIMARY);
      jj_consume_token(KEY);
                                 value = ColumnConstraint.PRIMARY_KEY;
      break;
    case REFERENCES:
      jj_consume_token(REFERENCES);
      tableName();
      jj_consume_token(OPENPAREN);
      identifierList();
      jj_consume_token(CLOSEPAREN);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
         {if (true) return value;}
    throw new Error("Missing return statement in function");
  }

  final public ColumnConstraint tableConstraintSubDefinition() throws ParseException {
        ColumnConstraint value = null;
    switch (jj_nt.kind) {
    case UNIQUE:
      jj_consume_token(UNIQUE);
      jj_consume_token(OPENPAREN);
                                      identifierList();
      jj_consume_token(CLOSEPAREN);
      break;
    case PRIMARY:
      jj_consume_token(PRIMARY);
      jj_consume_token(KEY);
      jj_consume_token(OPENPAREN);
                                             identifierList();
      jj_consume_token(CLOSEPAREN);
      break;
    case FOREIGN:
      jj_consume_token(FOREIGN);
      jj_consume_token(KEY);
      jj_consume_token(OPENPAREN);
                                             identifierList();
      jj_consume_token(CLOSEPAREN);
      break;
    case REFERENCES:
      jj_consume_token(REFERENCES);
      tableName();
      jj_consume_token(OPENPAREN);
      identifierList();
      jj_consume_token(CLOSEPAREN);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
         {if (true) return value;}
    throw new Error("Missing return statement in function");
  }

  final public void tableConstraintDefinition() throws ParseException {
    switch (jj_nt.kind) {
    case CONSTRAINT:
      jj_consume_token(CONSTRAINT);
      identifier();
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    tableConstraintSubDefinition();
  }

//+
  final public DataFieldMetadata columnDefinition() throws ParseException {
        DataFieldMetadata dataFieldMetadata;
        String name;
        DataType type;
        Object value = null;
        ColumnConstraint columnConstraint = null;
    name = columnName();
    type = dataType();
    switch (jj_nt.kind) {
    case DEFAULT_:
      jj_consume_token(DEFAULT_);
      value = defaultValue();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    label_2:
    while (true) {
      switch (jj_nt.kind) {
      case NOT:
      case NULL:
      case PRIMARY:
      case REFERENCES:
      case UNIQUE:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_2;
      }
      columnConstraint = columnConstraintDefinition();
    }
          if (type.length == null) {
                dataFieldMetadata = new DataFieldMetadata(DataFieldMetadata.EMPTY_NAME, type.type, null);
          } else {
                //dataFieldMetadata = new DataFieldMetadata(name, type.type, type.length.shortValue());
                dataFieldMetadata = new DataFieldMetadata(DataFieldMetadata.EMPTY_NAME, type.type, type.length.shortValue());
          }
          dataFieldMetadata.setLabel(name);
          if (value != null) {
                dataFieldMetadata.setDefaultValueStr(value.toString());
          }
          if (columnConstraint != null) {
                if (ColumnConstraint.NOT_NULL == columnConstraint ||
                    ColumnConstraint.UNIQUE == columnConstraint ||
                    ColumnConstraint.PRIMARY_KEY == columnConstraint)
                dataFieldMetadata.setNullable(false);
          }
          if (type.format != null) {
                dataFieldMetadata.setFormatStr(type.format);
          }
          {if (true) return dataFieldMetadata;}
    throw new Error("Missing return statement in function");
  }

  final public DataFieldMetadata tableElement() throws ParseException {
        DataFieldMetadata ret = null;
    switch (jj_nt.kind) {
    case IDENTIFIER:
      ret = columnDefinition();
      break;
    case CONSTRAINT:
    case FOREIGN:
    case PRIMARY:
    case REFERENCES:
    case UNIQUE:
      tableConstraintDefinition();
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

//+
  final public String columnName() throws ParseException {
        String ret;
    if (jj_2_2(2)) {
      identifier();
      jj_consume_token(DOT);
    } else {
      ;
    }
    ret = identifier();
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

//+TODO dodelat dalsi typu jako DATE, VARCHAR,...
  final public DataType dataType() throws ParseException {
        char type;
        Long temp = null;
        Long lenght = null;
        Long scale = null;
        String format = null;
    switch (jj_nt.kind) {
    case BIGINT:
      jj_consume_token(BIGINT);
                           type = DataFieldMetadata.INTEGER_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[14] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : longLenght;
      break;
    case BINARY:
      jj_consume_token(BINARY);
                           type = DataFieldMetadata.BYTE_FIELD;
      jj_consume_token(OPENPAREN);
      temp = integerLiteral();
      jj_consume_token(CLOSEPAREN);
                                                                                                                                    lenght = temp;
      break;
    case BLOB:
      jj_consume_token(BLOB);
                         type = DataFieldMetadata.BYTE_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
                                                                                                                                    lenght = temp;
        break;
      default:
        jj_la1[15] = jj_gen;
        ;
      }
      break;
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
                            type = DataFieldMetadata.BOOLEAN_FIELD;             lenght = integerLenght;
      break;
    case CHAR:
      jj_consume_token(CHAR);
                         type = DataFieldMetadata.STRING_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[16] = jj_gen;
        ;
      }
                                                                                                                                       lenght = temp != null ? temp : charLenght;
      break;
    case CHARACTER:
      jj_consume_token(CHARACTER);
                              type = DataFieldMetadata.STRING_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[17] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : charLenght;
      break;
    case CLOB:
      jj_consume_token(CLOB);
                         type = DataFieldMetadata.BYTE_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
                                                                                                                                    lenght = temp;
        break;
      default:
        jj_la1[18] = jj_gen;
        ;
      }
      break;
    case DATE:
      jj_consume_token(DATE);
                         type = DataFieldMetadata.DATE_FIELD;                   lenght = dateLenght; format = Defaults.DEFAULT_DATE_FORMAT;
      break;
    case DATETIME:
      jj_consume_token(DATETIME);
                             type = DataFieldMetadata.DATE_FIELD;           lenght = dateTimeLenght; format = Defaults.DEFAULT_DATETIME_FORMAT;
      break;
    case DEC:
      jj_consume_token(DEC);
                        type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[19] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[20] = jj_gen;
        ;
      }
                                                                                                                                                                           lenght = temp != null ? temp : doubleLenght;
      break;
    case DECIMAL:
      jj_consume_token(DECIMAL);
                            type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[21] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[22] = jj_gen;
        ;
      }
                                                                                                                                                                   lenght = temp != null ? temp : doubleLenght;
      break;
    case DOUBLE:
      jj_consume_token(DOUBLE);
                           type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[23] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[24] = jj_gen;
        ;
      }
                                                                                                                                                                   lenght = temp != null ? temp : doubleLenght;
      break;
    case FLOAT:
      jj_consume_token(FLOAT);
                          type = DataFieldMetadata.NUMERIC_FIELD;               lenght = floatLenght;
      break;
    case INT:
      jj_consume_token(INT);
                        type = DataFieldMetadata.INTEGER_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[25] = jj_gen;
        ;
      }
                                                                                                                                       lenght = temp != null ? temp : integerLenght;
      break;
    case INTEGER:
      jj_consume_token(INTEGER);
                            type = DataFieldMetadata.INTEGER_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[26] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : integerLenght;
      break;
    case REAL:
      jj_consume_token(REAL);
                         type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[27] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[28] = jj_gen;
        ;
      }
                                                                                                                                                                           lenght = temp != null ? temp : doubleLenght;
      break;
    case SMALLINT:
      jj_consume_token(SMALLINT);
                             type = DataFieldMetadata.INTEGER_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[29] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : integerLenght;
      break;
    case STRING:
      jj_consume_token(STRING);
                           type = DataFieldMetadata.STRING_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[30] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : textLenght;
      break;
    case TEXT:
      jj_consume_token(TEXT);
                         type = DataFieldMetadata.STRING_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[31] = jj_gen;
        ;
      }
                                                                                                                                       lenght = temp != null ? temp : textLenght;
      break;
    case TIME:
      jj_consume_token(TIME);
                         type = DataFieldMetadata.DATE_FIELD;                   lenght = dateTimeLenght; format = Defaults.DEFAULT_TIME_FORMAT;
      break;
    case TIMESTAMP:
      jj_consume_token(TIMESTAMP);
                              type = DataFieldMetadata.DATE_FIELD;              lenght = dateTimeLenght; format = Defaults.DEFAULT_DATETIME_FORMAT;
      break;
    case NUMBER:
      jj_consume_token(NUMBER);
                           type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[32] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[33] = jj_gen;
        ;
      }
                                                                                                                                                                   lenght = temp != null ? temp : doubleLenght;
      break;
    case NUMERIC:
      jj_consume_token(NUMERIC);
                            type = DataFieldMetadata.DECIMAL_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        switch (jj_nt.kind) {
        case COMA:
          jj_consume_token(COMA);
          scale = integerLiteral();
          break;
        default:
          jj_la1[34] = jj_gen;
          ;
        }
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[35] = jj_gen;
        ;
      }
                                                                                                                                                                   lenght = temp != null ? temp : doubleLenght;
      break;
    case TINYINT:
      jj_consume_token(TINYINT);
                            type = DataFieldMetadata.INTEGER_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[36] = jj_gen;
        ;
      }
                                                                                                                               lenght = temp != null ? temp : integerLenght;
      break;
    case VARBINARY:
      jj_consume_token(VARBINARY);
                              type = DataFieldMetadata.BYTE_FIELD;
      jj_consume_token(OPENPAREN);
      temp = integerLiteral();
      jj_consume_token(CLOSEPAREN);
                                                                                                                            lenght = temp;
      break;
    case VARCHAR:
      jj_consume_token(VARCHAR);
                            type = DataFieldMetadata.STRING_FIELD;
      switch (jj_nt.kind) {
      case OPENPAREN:
        jj_consume_token(OPENPAREN);
        temp = integerLiteral();
        jj_consume_token(CLOSEPAREN);
        break;
      default:
        jj_la1[37] = jj_gen;
        ;
      }
                                                                                                                              lenght = temp;
      break;
    case VARCHAR2:
      jj_consume_token(VARCHAR2);
                             type = DataFieldMetadata.STRING_FIELD;
      jj_consume_token(OPENPAREN);
      temp = integerLiteral();
      jj_consume_token(CLOSEPAREN);
                                                                                                                           lenght = temp;
      break;
    default:
      jj_la1[38] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return new DataType(type, lenght, scale, format);}
    throw new Error("Missing return statement in function");
  }

//+
  final public Object defaultValue() throws ParseException {
        Object ret;
    switch (jj_nt.kind) {
    case STRING_LITERAL:
    case INTEGER_LITERAL:
    case FLOAT_LITERAL:
      ret = literal();
      break;
    case NULL:
      ret = nullLiteral();
      break;
    default:
      jj_la1[39] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

//+
  final public BigDecimal floatLiteral() throws ParseException {
    jj_consume_token(FLOAT_LITERAL);
                          {if (true) return new BigDecimal(token.image);}
    throw new Error("Missing return statement in function");
  }

//+
  final public String identifier() throws ParseException {
    jj_consume_token(IDENTIFIER);
          {if (true) return token.image;}
    throw new Error("Missing return statement in function");
  }

//+
  final public void identifierList() throws ParseException {
    identifier();
    label_3:
    while (true) {
      switch (jj_nt.kind) {
      case COMA:
        ;
        break;
      default:
        jj_la1[40] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMA);
      identifier();
    }
  }

//+
  final public Object literal() throws ParseException {
        Object ret;
    switch (jj_nt.kind) {
    case INTEGER_LITERAL:
      ret = integerLiteral();
      break;
    case FLOAT_LITERAL:
      ret = floatLiteral();
      break;
    case STRING_LITERAL:
      ret = stringLiteral();
      break;
    default:
      jj_la1[41] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

//+
  final public Long integerLiteral() throws ParseException {
    jj_consume_token(INTEGER_LITERAL);
                            {if (true) return new Long(token.image);}
    throw new Error("Missing return statement in function");
  }

//+
  final public Object nullLiteral() throws ParseException {
    jj_consume_token(NULL);
                 {if (true) return null;}
    throw new Error("Missing return statement in function");
  }

//+
  final public String stringLiteral() throws ParseException {
    jj_consume_token(STRING_LITERAL);
                String value = token.image.intern();
                {if (true) return value.substring(1, value.length() - 1);}
    throw new Error("Missing return statement in function");
  }

//+
  final public void tableName() throws ParseException {
    jj_consume_token(IDENTIFIER);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_1() {
    if (jj_scan_token(IDENTIFIER)) return true;
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  private boolean jj_3R_4() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_4()) return true;
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public DDL2CloverTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[42];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
      jj_la1_init_2();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0x10000000,0x10000000,0x0,0x0,0x0,0x0,0x1,0x0,0x80000000,0x200000,0x8000000,0x0,0x80200000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x678fe000,0x0,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x200011,0x400,0x400,0x200,0x0,0x11,0x11,0x0,0x801860,0x801800,0x0,0x0,0x801860,0x80801800,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x75da186,0x38000040,0x0,0x38000000,};
   }
   private static void jj_la1_init_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x20,0x20,0x20,0x20,0x20,0x8,0x20,0x8,0x20,0x8,0x20,0x20,0x20,0x8,0x20,0x20,0x20,0x20,0x8,0x20,0x8,0x20,0x20,0x20,0x0,0x0,0x8,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public DDL2Clover(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public DDL2Clover(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new DDL2CloverTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public DDL2Clover(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new DDL2CloverTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public DDL2Clover(DDL2CloverTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(DDL2CloverTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[73];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 42; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 73; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
