package org.hhu.spago;

import java.util.ArrayList;
import java.util.List;

import org.hhu.spago.controller.dto.DTOFileFormat;
import org.hhu.spago.controller.dto.DTOParameter;
import org.hhu.spago.controller.dto.DTOPipeline;
import org.hhu.spago.controller.dto.DTOPipelineComponent;
import org.hhu.spago.controller.dto.DTOStructField;
import org.hhu.spago.controller.dto.SupportDataType;
import org.hhu.spago.core.Spago;
import org.junit.Test;

public class SpagoTest {
	@Test
	public  void test() throws Exception {
		DTOPipeline dtoPipeline = new DTOPipeline();
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
		List<DTOPipelineComponent> ls = new ArrayList<DTOPipelineComponent>();
		ls.add(dpc);
		dtoPipeline.setComponents(ls);
		
		DTOFileFormat ff = new DTOFileFormat();
		ff.setDelim("");
		ff.setFileType(SupportDataType.DOUBLE_TYPE);
		ff.setFilePath("F:/spago.txt");
		List<DTOStructField> structFields = new ArrayList<DTOStructField>();
		DTOStructField structField  = new DTOStructField();
		structField.setDataType(SupportDataType.STRING_TYPE);
		structField.setName("sentence");
		structField.setNullable(false);
		structField.setEnd(1);
		structField.setStart(1);
		structFields.add(structField);
		ff.setStructFields(structFields);
		
		dtoPipeline.setTrainning(ff);
		
		new Spago().submit(dtoPipeline);
	}

}
