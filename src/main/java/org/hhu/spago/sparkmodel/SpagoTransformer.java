package org.hhu.spago.sparkmodel;

public class SpagoTransformer extends PipelineComponent{
	
	protected String type;
	protected String featureTransformerType; //如果type为LEARNED_MODEL 该属性为null
	
	public SpagoTransformer(String name) {
		super(name);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFeatureTransformerType() {
		return featureTransformerType;
	}
	public void setFeatureTransformerType(String featureTransformerType) {
		this.featureTransformerType = featureTransformerType;
	}
}


