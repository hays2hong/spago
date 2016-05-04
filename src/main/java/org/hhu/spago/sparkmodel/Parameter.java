package org.hhu.spago.sparkmodel;

public class Parameter<T> {	
	protected String name;
	protected String showName; //显示的属性名
	protected String type; //int boolean string double	
	protected T[] selection; //选择集 ，如果为空则可以不在选择集内，但是必须符合scope
	protected T def; //默认值
	protected boolean mustExists;  //如果
	protected String scope; //只面向int和double类型  使用~隔开两个范围  lnf代表无穷 出现在两端
	
	public Parameter(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public T[] getSelection() {
		return selection;
	}
	public void setSelection(T[] selection) {
		this.selection = selection;
	}
	public T getDef() {
		return def;
	}
	public void setDef(T def) {
		this.def = def;
	}
	public boolean isMustExists() {
		return mustExists;
	}
	public void setMustExists(boolean mustExists) {
		this.mustExists = mustExists;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	public Class getTypeClass() {
		switch (this.type) {
		case ParameterType.BOOLEAN_TYPE : {
			return Boolean.class;
		}
		case ParameterType.STRING_TYPE : {
			return String.class;
		}
		case ParameterType.INT_TYPE : {
			return Integer.class;
		}
		case ParameterType.DOUBLE_TYPE : {
			return Double.class;
		}
		}
		
		return String.class;
	}
	
}
