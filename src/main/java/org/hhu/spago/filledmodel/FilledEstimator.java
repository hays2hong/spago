package org.hhu.spago.filledmodel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.spark.ml.PipelineStage;
import org.hhu.spago.sparkmodel.SpagoEstimator;

public class FilledEstimator {
	private SpagoEstimator desc;
	private List<FilledParameter<?>> filledParameters;

	public FilledEstimator(SpagoEstimator desc) {
		this.desc = desc;
	}

	// 转换成PipelineStage，还有一些异常处理要做
	public PipelineStage convert() throws Exception {
		Class clazz = Class.forName(this.getDesc().getClassName());
		Object object = clazz.newInstance();
		for (FilledParameter<?> fp : this.getFilledParameters()) {
			Method m = clazz.getMethod("set" + fp.getDesc().getName(), fp.getDesc().getTypeClass());
			m.invoke(object, fp.getValue());
		}
		return (PipelineStage) object;
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
