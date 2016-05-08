package org.hhu.spago.controller.dto;

import org.hhu.spago.filledmodel.FilledParameter;
import org.hhu.spago.filledmodel.FilledParameterValueType;
import org.hhu.spago.sparkmodel.AllPipelineComponents;
import org.hhu.spago.sparkmodel.Parameter;
import org.hhu.spago.sparkmodel.ParameterType;
import org.hhu.spago.sparkmodel.PipelineComponent;

public class DTOParameter {
	private String name;
	
	private String value;
	
	private String[] tuningCollection;
	
	private String tuningScope;	
	private double step;
	
	public FilledParameter<?> convert(String pcName, int pcType) throws Exception {		
		return filledFilledParameter(pcName,pcType);
	}
	
	//缺少必要的错误判断
	private FilledParameter<?> filledFilledParameter(String pcName, int pcType) throws Exception {
		Parameter<?> desc;
		if(pcType == 1) {
			desc = AllPipelineComponents.getTransformer(pcName).getParameters().get(this.name);
		} else if(pcType == 0) {
			desc = AllPipelineComponents.getEstimator(pcName).getParameters().get(this.name);
		} else {
			throw new Exception("PipelineComponent类型不支持");
		}
		if(desc == null) {
			throw new Exception(name+":参数无法找到描述类");
		}
		
		switch(desc.getType()) {
		case ParameterType.BOOLEAN_TYPE : {
			FilledParameter<Boolean> fp = new FilledParameter<Boolean>(desc);
			
			if(this.value != null && !this.value.equals("")) {
				fp.setValueType(FilledParameterValueType.FIXED);
				fp.setValue(Boolean.parseBoolean(value));	
			} else if(tuningCollection != null && tuningCollection.length > 0) {
				fp.setValueType(FilledParameterValueType.COLLECTION);
				fp.setTuningCollection(new Boolean[]{true,false});	
			} else {
				throw new Exception(name+":参数没有赋值");
			}
			return fp;			
		}
		case ParameterType.INT_TYPE : {
			FilledParameter<Integer> fp = new FilledParameter<Integer>(desc);
			
			if(this.value != null && !this.value.equals("")) {
				fp.setValueType(FilledParameterValueType.FIXED);
				fp.setValue(Integer.parseInt(value));	
			} else if(tuningCollection != null && tuningCollection.length > 0) {
				fp.setValueType(FilledParameterValueType.COLLECTION);
				Integer[] collections = new Integer[tuningCollection.length];
				for(int i=0;i<collections.length;i++) {
					collections[i] = Integer.parseInt(tuningCollection[i]);
				}
				fp.setTuningCollection(collections);
			} else if(tuningScope != null && !tuningScope.equals("")&&tuningScope.split("~").length != 2 && step >= 0.0000001) {
				fp.setTuningScope(tuningScope);
				fp.setStep(step);			
			} else {
				throw new Exception(name+":参数没有赋值或赋值错误");
			}
			return fp;
		}
		case ParameterType.DOUBLE_TYPE : {
			FilledParameter<Double> fp = new FilledParameter<Double>(desc);
			
			if(this.value != null && !this.value.equals("")) {
				fp.setValueType(FilledParameterValueType.FIXED);
				fp.setValue(Double.parseDouble(value));	
			} else if(tuningCollection != null && tuningCollection.length > 0) {
				fp.setValueType(FilledParameterValueType.COLLECTION);
				Double[] collections = new Double[tuningCollection.length];
				for(int i=0;i<collections.length;i++) {
					collections[i] = Double.parseDouble(tuningCollection[i]);
				}
				fp.setTuningCollection(collections);
			} else if(tuningScope != null && !tuningScope.equals("")&&tuningScope.split("~").length != 2 && step >= 0.0000001) {
				fp.setTuningScope(tuningScope);
				fp.setStep(step);			
			} else {
				throw new Exception(name+":参数没有赋值或赋值错误");
			}
			return fp;
		}
		case ParameterType.STRING_TYPE : {
			FilledParameter<String> fp = new FilledParameter<String>(desc);
			
			if(this.value != null && !this.value.equals("")) {
				fp.setValueType(FilledParameterValueType.FIXED);
				fp.setValue(value);	
			} else if(tuningCollection != null && tuningCollection.length > 0) {
				fp.setValueType(FilledParameterValueType.COLLECTION);
				fp.setTuningCollection(tuningCollection);	
			} else {
				throw new Exception(name+":参数没有赋值或赋值错误");
			}
			return fp;			
		}
		default : {
			throw new Exception(name+":不能判断的类型");
		}
		}	
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String[] getTuningCollection() {
		return tuningCollection;
	}
	public void setTuningCollection(String[] tuningCollection) {
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
	

}
