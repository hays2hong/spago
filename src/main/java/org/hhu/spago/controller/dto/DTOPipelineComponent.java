package org.hhu.spago.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.hhu.spago.filledmodel.FilledEstimator;
import org.hhu.spago.filledmodel.FilledParameter;
import org.hhu.spago.filledmodel.FilledTransformer;
import org.hhu.spago.sparkmodel.AllPipelineComponents;

public class DTOPipelineComponent {
	private String name;
	private int type; //0：estimator 1:transformer
	
	private List<DTOParameter> params;
	
	public FilledTransformer convertToFilledTransformer() throws Exception{
		if(this.type != 1) {
			throw new CantConverException();
		}
		FilledTransformer ft = new FilledTransformer(AllPipelineComponents.getTransformers().get(this.name));
		
	    List<FilledParameter<?>> fps = new ArrayList<FilledParameter<?>>();
	    for(DTOParameter param : params) {
	    	fps.add(param.convert(name,type));
	    }    
	    ft.setFilledParameters(fps);
	    
		return ft;
	}
	
	public FilledEstimator convertToFilledEstimator() throws Exception{
		if(this.type != 0) {
			throw new CantConverException();
		}
		FilledEstimator fe = new FilledEstimator(AllPipelineComponents.getEstimator("name"));
		
	    List<FilledParameter<?>> fps = new ArrayList<FilledParameter<?>>();
	    for(DTOParameter param : params) {
	    	fps.add(param.convert(name,type));
	    }    
	    fe.setFilledParameters(fps);
	    
		return fe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DTOParameter> getParams() {
		return params;
	}

	public void setParams(List<DTOParameter> params) {
		this.params = params;
	}

	public int getType() {
		return type;
	}
	
	 /**
     * 设置类型
     *
     * @param type 0 estimator 1 transformer
     */
	public void setType(int type) {
		this.type = type;
	}

}
