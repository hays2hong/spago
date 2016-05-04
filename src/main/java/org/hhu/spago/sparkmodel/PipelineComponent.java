package org.hhu.spago.sparkmodel;

import java.util.Map;

public class PipelineComponent {
	protected String name;  //spark中的名字
	protected String showName; //展示的名字  中文或英文
	protected String className; //class名
	protected Map<String,Parameter<?>> parameters; //参数信息
	
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public PipelineComponent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Map<String, Parameter<?>> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Parameter<?>> parameters) {
		this.parameters = parameters;
	}


}
