package org.hhu.spago.controller.dto;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.VectorUDT;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class DataFrameConverter {

	public static DataFrame convert(JavaSparkContext context, DTOFileFormat fileFormat) throws CantConverException {
		String filePath = fileFormat.getFilePath();
		SQLContext sqlContext = new SQLContext(context);
		
		if(fileFormat.getFileType().equals(SupportFileType.LIBSVM)) {
			return sqlContext.read().format("libsvm").load(filePath);
		}
		
		StructType schema = fileFormat.getStructType();
		JavaRDD<Row> points = context.textFile(filePath).map(new LineParse(fileFormat));
		return sqlContext.createDataFrame(points, schema);
	}

}

class LineParse implements Function<String, Row> {

	private static final long serialVersionUID = -1481954080127428634L;
	
	private DTOFileFormat fileFormat;
	
	public LineParse(DTOFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	@Override
	public Row call(String line) throws Exception {	
		return fileFormat.call(line);
	}
}


