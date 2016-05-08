package org.hhu.spago;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.hhu.spago.controller.dto.DTOParameter;
import org.hhu.spago.controller.dto.DTOPipelineComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	SQLContext sqlContext;
	JavaSparkContext jsc;

	@Before
	public void before() {
		SparkConf conf = new SparkConf().setAppName("JavaTfIdfExample").setMaster("local");
		jsc = new JavaSparkContext(conf);
		sqlContext = new SQLContext(jsc);
	}

	@After
	public void after() {
		jsc.stop();
	}

	@Test
	public void DTOtest() throws Exception {
		// $example on$
		JavaRDD<Row> jrdd = jsc.parallelize(Arrays.asList(RowFactory.create(0, "Hi I heard about Spark"),
				RowFactory.create(0, "I wish Java could use case classes"),
				RowFactory.create(1, "Logistic regression models are neat")));
		StructType schema = new StructType(
				new StructField[] { new StructField("label", DataTypes.IntegerType, false, Metadata.empty()),
						new StructField("sentence", DataTypes.StringType, false, Metadata.empty()) });
		DataFrame sentenceData = sqlContext.createDataFrame(jrdd, schema);

		DTOPipelineComponent dpc = new DTOPipelineComponent();
		dpc.setName("Tokenizer");
		dpc.setType(1);

		List<DTOParameter> params = new ArrayList<DTOParameter>();
		DTOParameter dp1 = new DTOParameter();
		dp1.setName("InputCol");
		dp1.setValue("sentence");
		DTOParameter dp2 = new DTOParameter();
		dp2.setName("OutputCol");
		dp2.setValue("words");
		params.add(dp1);
		params.add(dp2);

		dpc.setParams(params);

		Pipeline p = new Pipeline();
		p.setStages(new PipelineStage[] { dpc.convertToFilledTransformer().convert() });
		PipelineModel model = p.fit(sentenceData);
		DataFrame df = model.transform(sentenceData);

		for (Row r : df.select("words", "sentence").takeAsList(3)) {
			List<String> words = r.getList(0);
			String sen = r.getString(1);
			System.out.println(sen);
			for (String word : words) {
				System.out.print(word + " ");
			}
			System.out.println();
		}
	}

}
