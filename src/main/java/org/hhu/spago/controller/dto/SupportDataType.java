package org.hhu.spago.controller.dto;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

public class SupportDataType {
	
	public static final String DOUBLE_TYPE = "float";
	public static final String BOOLEAN_TYPE = "boolean";
	public static final String INTEGER_TYPE = "int";
	public static final String STRING_TYPE = "string";
	public static final String TIMESTAMP_TYPE = "timestamp";
	public static final String LONG_TYPE = "long";
	public static final String NULL_TYPE = "null";
	
	public static DataType getDataType(String type) {
		switch(type) {
		case DOUBLE_TYPE : {
			return DataTypes.DoubleType;
		}
		case BOOLEAN_TYPE : {
			return DataTypes.BooleanType;
		}
		case INTEGER_TYPE : {
			return DataTypes.IntegerType;
		}
		case STRING_TYPE : {
			return DataTypes.StringType;
		}
		case TIMESTAMP_TYPE : {
			return DataTypes.TimestampType;
		}
		case LONG_TYPE : {
			return DataTypes.LongType;
		}
		case NULL_TYPE : {
			return DataTypes.NullType;
		}
		default : {
			return null;
		}
		}
		
	}

}
