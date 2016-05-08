package org.hhu.spago.controller.dto;

import java.util.List;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;

//还需要提供tuning等功能
public class DTOPipeline {
	List<DTOPipelineComponent> components;
	private DTOFileFormat training;
	private DTOFileFormat testing;
	
	public Pipeline convertToFilledTransformer() throws Exception{
		Pipeline p = new Pipeline();
		PipelineStage[] pipelineStages = new PipelineStage[components.size()];
		
		for(int i=0;i<components.size();i++) {
			DTOPipelineComponent dpc = components.get(i);
			if(dpc.getType() == 0) {
				pipelineStages[i] = dpc.convertToFilledEstimator().convert();
			} else {
				pipelineStages[i] = dpc.convertToFilledTransformer().convert();
			}		
		}
		
		p.setStages(pipelineStages);
		return p;
	}

	
	public List<DTOPipelineComponent> getComponents() {
		return components;
	}


	public void setComponents(List<DTOPipelineComponent> components) {
		this.components = components;
	}


	public DTOFileFormat getTesting() {
		return testing;
	}


	public void setTesting(DTOFileFormat testing) {
		this.testing = testing;
	}


	public DTOFileFormat getTraining() {
		return training;
	}


	public void setTrainning(DTOFileFormat training) {
		this.training = training;
	}

}
