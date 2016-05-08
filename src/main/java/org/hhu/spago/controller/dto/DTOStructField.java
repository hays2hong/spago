package org.hhu.spago.controller.dto;

import java.io.Serializable;

import org.apache.spark.mllib.linalg.VectorUDT;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

public class DTOStructField implements Serializable{
	private String name;

	private String dataType;
	private boolean nullable = false;
	
	private int start;  //开始列
	private int end;  //结束列
	
	public StructField convert() throws CantConverException {
		if(dataType == null) {
			throw new CantConverException("dataType 不能为空");
		}
		//VectorUDT
		if(start != end) {			
			return DataTypes.createStructField(name, new VectorUDT(), nullable);
		}
		
		DataType type = SupportDataType.getDataType(dataType);
		if(type == null) {
			throw new CantConverException("dataType不支持");
		}
		return DataTypes.createStructField(name, type, nullable);		
	}
	
	
	public Object call(String[] arr) throws Exception {
		if(arr.length == 0){
			if(nullable) {
				return Vectors.dense(new double[]{});
			} else {
				throw new Exception(name + "列为空");
			}
		}
		double[] vect = new double[arr.length];
		try {
			for(int i=0;i<arr.length;i++) {
				vect[i] = Double.valueOf(arr[i]);
			}
		} catch(Exception e ) {
			throw new Exception(e.getMessage());
		}
		return Vectors.dense(vect);
		
	}
	
	public Object call(String value) throws Exception {
		System.out.println(value);
		
		
		if(value==null || value.equals("")){
			if(nullable) {
				return null;
			} else {
				throw new Exception(name + "列为空");
			}
		}
		try{
			switch(dataType) {
			case SupportDataType.BOOLEAN_TYPE :{
				return Boolean.valueOf(value);
			}
			case SupportDataType.STRING_TYPE :{
				return value;
			}
			case SupportDataType.DOUBLE_TYPE :{
				return Double.valueOf(value);
			}
			case SupportDataType.INTEGER_TYPE :{
				return Integer.valueOf(value);
			}
			case SupportDataType.LONG_TYPE :{
				return Long.valueOf(value);
			}
			case SupportDataType.TIMESTAMP_TYPE :{
				return Long.valueOf(value);
			}
			case SupportDataType.NULL_TYPE :{
				return null;
			}
			default: {
				return value;
			}
			}
		} catch (Exception e) {
			throw new Exception(value +"->"+dataType+" error");
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
