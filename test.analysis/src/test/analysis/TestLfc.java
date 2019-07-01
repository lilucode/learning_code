package test.analysis;

import java.io.File;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.afa.compiler.parser.XmlParseException;
import cn.com.agree.afa.compiler.parser.ide3.LfcParser;

public class TestLfc {

	public static void main(String[] args) throws Exception {

		LfcParser lp = new LfcParser();
		File file = new File("./bcpt/token校验.bcpt");
		LogicFlowControl lfc = lp.parse(file);
		System.out.println(JSON.toJSONString(lfc));
	}

}
