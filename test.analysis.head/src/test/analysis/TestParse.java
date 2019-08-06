package test.analysis;

import java.io.File;

import com.alibaba.fastjson.JSON;

import cn.com.agree.afa.compiler.model.PkgModel;
import cn.com.agree.afa.compiler.model.TradeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;
import cn.com.agree.afa.compiler.parser.ide3.PkgParser;
import cn.com.agree.afa.compiler.parser.ide3.TradeParser;

public class TestParse {
	public static void main(String[] args) throws XmlParseException {
		PkgParser pp = new PkgParser();
		File file1 = new File("./bcpt/OM.src");
		PkgModel pm = pp.parse(file1);
		System.out.println(JSON.toJSONString(pm));
		
		TradeParser tp = new TradeParser();
		File file2 = new File("./fc/IBAgentMonitor_GetAgentMessage.src");
		TradeModel tm = tp.parse(file2);
		System.out.println(JSON.toJSONString(tm));
	}
}
