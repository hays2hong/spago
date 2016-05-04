package org.hhu.spago.filledmodel;

import java.util.List;
import java.util.Map;

import org.hhu.spago.sparkmodel.SpagoEstimator;

public class FilledEstimator {
	private SpagoEstimator desc;
	private List<FilledParameter<?>> filledParameters;
	
	public FilledEstimator(SpagoEstimator desc) {
		this.desc = desc;
	}
	


	public SpagoEstimator getDesc() {
		return desc;
	}

	public void setDesc(SpagoEstimator desc) {
		this.desc = desc;
	}



	public List<FilledParameter<?>> getFilledParameters() {
		return filledParameters;
	}



	public void setFilledParameters(List<FilledParameter<?>> filledParameters) {
		this.filledParameters = filledParameters;
	}
}
