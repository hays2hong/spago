package org.hhu.spago.filledmodel;

import org.hhu.spago.sparkmodel.Parameter;
import org.hhu.spago.sparkmodel.ParameterType;


public class FilledParameter<T> {

	private Parameter<?> desc;	
	private String valueType;//是不是固定值  还是范围  或集
	//固定值
	private T value;
	
	//候选集
	private T[] tuningCollection; //率订集
	
	//范围
	private String tuningScope;  //参数滤定范围
	private double step ;// 步长	
	
	public FilledParameter(Parameter<?> desc) {
		this.desc = desc;
	}

	
//	//检查用户输入值的合法性 在DTO方法里做好 这层就不管了
//	public boolean validate(){
//		switch(desc.getType()) {
//		case ParameterType.STRING_TYPE : {
//			if((String)value != null && ((String)value).equals("")) {
//				return true;
//			}
//		}
//		case ParameterType.BOOLEAN_TYPE : {
//			
//		}
//		case ParameterType.DOUBLE_TYPE : {
//			
//		}
//		case ParameterType.INT_TYPE : {
//			
//		}
//		}
//		return false;	
//	}
	
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	//生成参数选择集
	public T[] getParamGrid() {
		return null;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public T[] getTuningCollection() {
		return tuningCollection;
	}

	public void setTuningCollection(T[] tuningCollection) {
		this.tuningCollection = tuningCollection;
	}

	public String getTuningScope() {
		return tuningScope;
	}

	public void setTuningScope(String tuningScope) {
		this.tuningScope = tuningScope;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}


	public Parameter<?> getDesc() {
		return desc;
	}

	public void setDesc(Parameter<?> desc) {
		this.desc = desc;
	}
}

