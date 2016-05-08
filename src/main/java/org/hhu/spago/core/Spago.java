package org.hhu.spago.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.DataFrame;
import org.hhu.spago.controller.dto.DTOPipeline;
import org.hhu.spago.controller.dto.DataFrameConverter;

public class Spago {
	//需要使用回调函数 处理结果，还需要做率定 、testing部分
	public void submit(DTOPipeline dtoPipeline) throws Exception {
		SparkConf conf = new SparkConf().setAppName("spago").setMaster("local");
	    JavaSparkContext context = new JavaSparkContext(conf);
	    
		Pipeline pipeline = dtoPipeline.convertToFilledTransformer();
		DataFrame training = DataFrameConverter.convert(context, dtoPipeline.getTraining());
				
		// Fit the pipeline to training documents.
	    PipelineModel model = pipeline.fit(training);


	    // Make predictions on test documents.
	    DataFrame r = model.transform(training);
	    r.show(3);

	    context.stop();
	}
	
}
