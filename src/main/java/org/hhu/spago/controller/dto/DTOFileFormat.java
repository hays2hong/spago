package org.hhu.spago.controller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class DTOFileFormat implements Serializable{

	private static final long serialVersionUID = -7112328997299088846L;
	
	private String delim;
	private List<DTOStructField> structFields;
	
	private String filePath; //路径名  需要在手动设置
	
	private String fileType; //现在只知道libsvm  其他的以后添加
	
	//structFields -> StructType
	public StructType getStructType() throws CantConverException {
		StructField[] fields = new StructField[structFields.size()];
		for(int i=0;i<structFields.size();i++) {
			fields[i] = structFields.get(i).convert();
		}
		
		return DataTypes.createStructType(fields);
	}
	
	//line -> Row
	public Row call(String line) throws Exception {
		String[] strArr;
		if(delim == null || delim.equals("")) {
			strArr = new String[]{line};
		} else {
			strArr = line.split(delim);
		}
		
		Object[] objs = new  Object[structFields.size()];
		
		for(int i=0;i<structFields.size();i++) {
			if(structFields.get(i).getEnd() == structFields.get(i).getStart()) {
				objs[i] = structFields.get(i).call(strArr[i]);
			} else {
				int tmpSize = structFields.get(i).getEnd()-structFields.get(i).getStart()+1;
				String[] tmp = new String[tmpSize];
				System.arraycopy(strArr, structFields.get(i).getStart()-1, tmp, 0, tmpSize);
				objs[i] = structFields.get(i).call(tmp);
			}
		}
		return new GenericRow(objs);
	}
	
	

	public String getDelim() {
		return delim;
	}
	public void setDelim(String delim) {
		this.delim = delim;
	}

	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public List<DTOStructField> getStructFields() {
		return structFields;
	}
	public void setStructFields(List<DTOStructField> structFields) {
		this.structFields = structFields;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
