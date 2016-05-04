package org.hhu.spago.sparkmodel;

import java.util.HashMap;
import java.util.Map;

public class AllPipelineComponents {
	private static Map<String,SpagoTransformer> transformers;
	private static Map<String,SpagoEstimator> estimators;

	public static Map<String, SpagoTransformer> getTransformers() {
		if(transformers == null) {
			build();
		}
		return transformers;
	}

	public static Map<String, SpagoEstimator> getEstimators() {
		if(estimators == null) {
			build();
		}
		return estimators;
	}

	//将componts写成配置文件
	private void toConf() {
		
	}
	
	//将所有的compont配置初始化
	private static void build() {
		transformers = new HashMap<String,SpagoTransformer>();
		estimators = new HashMap<String,SpagoEstimator>();
		//transformers
		tokenizer();
		hashingTF();
		
		//estimators
		IDF();
	}
	
	private static void IDF() {
		SpagoEstimator esti = new SpagoEstimator("IDF");
		esti.setClassName("org.apache.spark.ml.feature.IDF");
		esti.setShowName("IDF");
	
		
		Map<String,Parameter<?>> ps = new HashMap<String,Parameter<?>>();
		Parameter<String> p1 = new Parameter<String>("InputCol");
		p1.setShowName("输入列名");
		p1.setMustExists(true);
		p1.setType(ParameterType.STRING_TYPE);
		ps.put(p1.getName(), p1);
		
		Parameter<String> p2 = new Parameter<String>("OutputCol");
		p2.setShowName("输出列名");
		p2.setMustExists(true);
		p2.setType(ParameterType.STRING_TYPE);
		ps.put(p2.getName(), p2);
		
		Parameter<String> p3 = new Parameter<String>("MinDocFreq");
		p3.setShowName("最小文件频率");
		p3.setMustExists(false);
		p3.setType(ParameterType.INT_TYPE);
		ps.put(p3.getName(), p3);	
		
		esti.setParameters(ps);
		estimators.put(esti.getName(), esti);
	}
	
	private static void hashingTF() {
		SpagoTransformer trans = new SpagoTransformer("HashingTF");
		trans.setClassName("org.apache.spark.ml.feature.HashingTF");
		trans.setShowName("TF");
		trans.setType(TransformerType.FEATURE_TRANSFORMER);
		trans.setFeatureTransformerType(FeatureTransformerType.EXTRACTORS);	
		
		Map<String,Parameter<?>> ps = new HashMap<String,Parameter<?>>();
		Parameter<String> p1 = new Parameter<String>("InputCol");
		p1.setShowName("输入列名");
		p1.setMustExists(true);
		p1.setType(ParameterType.STRING_TYPE);		
		ps.put(p1.getName(), p1);
		
		Parameter<String> p2 = new Parameter<String>("OutputCol");
		p2.setShowName("输出列名");
		p2.setMustExists(true);
		p2.setType(ParameterType.STRING_TYPE);
		ps.put(p2.getName(), p2);
		
		
		trans.setParameters(ps);		
		transformers.put(trans.getName(), trans);
	}
	
	private static void tokenizer() {
		SpagoTransformer trans = new SpagoTransformer("Tokenizer");
		trans.setClassName("org.apache.spark.ml.feature.Tokenizer");
		trans.setShowName("分词");
		trans.setType(TransformerType.FEATURE_TRANSFORMER);
		trans.setFeatureTransformerType(FeatureTransformerType.EXTRACTORS);	
		
		Map<String,Parameter<?>> ps = new HashMap<String,Parameter<?>>();
		Parameter<String> p1 = new Parameter<String>("InputCol");
		p1.setShowName("输入列名");
		p1.setMustExists(true);
		p1.setType(ParameterType.STRING_TYPE);
		ps.put(p1.getName(), p1);
		
		Parameter<String> p2 = new Parameter<String>("OutputCol");
		p2.setShowName("输出列名");
		p2.setMustExists(true);
		p2.setType(ParameterType.STRING_TYPE);
		ps.put(p2.getName(), p2);
		
		trans.setParameters(ps);		
		transformers.put(trans.getName(), trans);
	}
	
	
}
