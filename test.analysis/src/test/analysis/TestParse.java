package test.analysis;

import java.io.File;

import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.PkgModel;
import cn.com.agree.afa.compiler.model.TradeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;
import cn.com.agree.afa.compiler.parser.ide3.BcptParser;
import cn.com.agree.afa.compiler.parser.ide3.PkgParser;
import cn.com.agree.afa.compiler.parser.ide3.TradeParser;
import com.alibaba.fastjson.JSON;

public class TestParse {
	public static void main(String[] args) throws XmlParseException {
//		PkgParser pp = new PkgParser();
//		File file1 = new File("./bcpt/CheckService.src");
//		PkgModel pm = pp.parse(file1);
//		System.out.println(JSON.toJSONString(pm));
//		
//		TradeParser tp = new TradeParser();
//		File file2 = new File("./fc/IBOperaManage_CentAuthTask.src");
//		TradeModel tm = tp.parse(file2);
//		System.out.println(JSON.toJSONString(tm));
		
		BcptParser bp = new BcptParser();
		File file3 = new File("./bcpt/token校验.bcpt");
		BCModel bm = bp.parse(file3);
		System.out.println(JSON.toJSONString(bm));
	}
}
