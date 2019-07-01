package cn.com.agree.afa.compiler.parser.ide3;

import org.w3c.dom.Document;

import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.afa.compiler.parser.XmlParseException;

public class LfcParser extends AbstractParser<LogicFlowControl> {

	@Override
	protected LogicFlowControl parse(Document paramDocument, String paramString) throws XmlParseException {
		LogicFlowControl lfc = new LogicFlowControl();
		return lfc;
	}

	@Override
	protected String[] getCptName(String paramString) {
		return null;
	}

}
