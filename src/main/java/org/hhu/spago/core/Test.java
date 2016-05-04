package org.hhu.spago.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.hhu.spago.filledmodel.FilledParameter;
import org.hhu.spago.filledmodel.FilledTransformer;
import org.hhu.spago.sparkmodel.AllPipelineComponents;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		SparkConf conf = new SparkConf().setAppName("JavaTfIdfExample").setMaster("local");
	    JavaSparkContext jsc = new JavaSparkContext(conf);
	    SQLContext sqlContext = new SQLContext(jsc);

	    // $example on$
	    JavaRDD<Row> jrdd = jsc.parallelize(Arrays.asList(
	      RowFactory.create(0, "Hi I heard about Spark"),
	      RowFactory.create(0, "I wish Java could use case classes"),
	      RowFactory.create(1, "Logistic regression models are neat")
	    ));
	    StructType schema = new StructType(new StructField[]{
	      new StructField("label", DataTypes.IntegerType, false, Metadata.empty()),
	      new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
	    });
	    DataFrame sentenceData = sqlContext.createDataFrame(jrdd, schema);
	    
	    FilledTransformer tokFT = new FilledTransformer(AllPipelineComponents.getTransformers().get("Tokenizer"));
	    List<FilledParameter<?>> fps = new ArrayList<FilledParameter<?>>();
	    FilledParameter<String> fp1 = new FilledParameter<String>(tokFT.getDesc().getParameters().get("InputCol"));
	    fp1.setValue("sentence");
	    FilledParameter<String> fp2 = new FilledParameter<String>(tokFT.getDesc().getParameters().get("OutputCol"));
	    fp2.setValue("words");
	    fps.add(fp1);
	    fps.add(fp2);
	    tokFT.setFilledParameters(fps);
	    
	   
		
		Pipeline p = new Pipeline();
		p.setStages(new PipelineStage[] {tokFT.convert()});
		PipelineModel model = p.fit(sentenceData);
		DataFrame df = model.transform(sentenceData);
		
		 for (Row r : df.select("words", "sentence").takeAsList(3)) {
		      List<String> words = r.getList(0);
		      String sen = r.getString(1);
		      System.out.println(sen);
		      for(String word : words) {
		    	  System.out.print(word+" ");
		      }
		      System.out.println();	      
		 }
	    // $example off$

	    jsc.stop();	
		
	}

}
