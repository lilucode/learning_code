package test.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.PkgModel;
import cn.com.agree.afa.compiler.model.TradeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;
import cn.com.agree.afa.compiler.parser.ide3.BcptParser;
import cn.com.agree.afa.compiler.parser.ide3.PkgParser;
import cn.com.agree.afa.compiler.parser.ide3.TradeParser;

import com.alibaba.fastjson.JSON;

public class TestParse {
	public static void main(String[] args) throws XmlParseException, Exception {
		// PkgParser pp = new PkgParser();
		// File file1 = new File("./bcpt/CheckService.src");
		// PkgModel pm = pp.parse(file1);
		// System.out.println(JSON.toJSONString(pm));
		//
		// TradeParser tp = new TradeParser();
		// File file2 = new File("./fc/IBOperaManage_CentAuthTask.src");
		// TradeModel tm = tp.parse(file2);
		// System.out.println(JSON.toJSONString(tm));

		File file=new File("./bcpt/");
		File[] files = file.listFiles();
		for (File file2 : files) {
			if(file2.isFile()){
				BcptParser bp = new BcptParser();
				BCModel bm = bp.parse(file2);
//				System.out.println(JSON.toJSONString(bm));
				String content = JSON.toJSONString(bp.getLfc());
				System.out.println(content);
				content = FormatUtil.formatJson(content);

//				File file3 = new File("./lfc/"+file2.getName().substring(0, file2.getName().lastIndexOf("."))+".lfc");
				File file3 = new File("./lfc/"+bp.componentName+".lfc");
				FileOutputStream fop = new FileOutputStream(file3);
				if (!file3.exists()) {
					file3.createNewFile();
				}
				fop.write(content.getBytes());
				fop.flush();
				fop.close();
			}
		}
//		BcptParser bp = new BcptParser();
//		File file3 = new File("./bcpt/token校验.bcpt");
//		BCModel bm = bp.parse(file3);
//		System.out.println(JSON.toJSONString(bm));
//		String content = JSON.toJSONString(bp.getLfc());
//		System.out.println(content);

		
	}
}
