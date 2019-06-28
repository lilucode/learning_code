package cn.com.agree.afa.compiler.parser.ide3;

import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.NodeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;

public class BcptParser extends AbstractParser<BCModel> {

	private LogicFlowControl lfc = new LogicFlowControl();
	
	public LogicFlowControl getLfc() {
		return lfc;
	}

	public void setLfc(LogicFlowControl lfc) {
		this.lfc = lfc;
	}

	@Override
	protected BCModel parse(Document doc, String paramString) throws XmlParseException {
		Element component = (Element) doc.getElementsByTagName("Component").item(0);
		BCModel bcModel = getBCModel(component);
		return bcModel;
	}

	private BCModel getBCModel(Element component) throws XmlParseException {
		BCModel bcModel = new BCModel();
		// Component/Desp
		String desp = getChildElementText(component, "Desp");
		bcModel.setDesc(desp);
		//Component/RefImpl
		String componentName = getChildElementText(component, "RefImpl");
		bcModel.setName(componentName);
		//Component/Auth
		String auth = getChildElementText(component, "Auth");
		bcModel.setAuth(auth);
		//Component/InArgs
		bcModel.setInputArgs(getComponentArgs(component, "In"));
		//Component/OutArgs
		bcModel.setOutputArgs(getComponentArgs(component, "Out"));
		Element implementation = getDirectChildElement(component, "Implementation");
		bcModel.setNodeModels(getNodeModels(implementation));
		return bcModel;
	}

	protected Set<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		List<Element> nodeList = getDirectChildElements(impl, "Node");
		return getNodeModels(nodeList);
	}

	@Override
	protected String[] getCptName(String paramString) {
		return null;
	}

}
