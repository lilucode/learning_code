package cn.com.agree.ab.transfer.afa.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.model.ComponentArg;


public class BcptParse extends AbstractParser<BCModel> {

	@Override
	protected BCModel parse(Document doc, String paramString) throws XmlParseException {
		Element component = (Element) doc.getElementsByTagName("Component").item(0);
		BCModel bcModel = getBCModel(component);
		return bcModel;
	}

	private BCModel getBCModel(Element component) throws XmlParseException {
		BCModel bcModel = new BCModel();
		bcModel.setAuth(getChildElementText(component, "Auth"));
		bcModel.setName(getChildElementText(component, "RefImpl"));
		bcModel.setDesc(getChildElementText(component, "Desp"));

		bcModel.setInputArgs(getComponentArgs(component, "In"));
		bcModel.setOutputArgs(getComponentArgs(component, "Out"));

		Element implementation = getDirectChildElement(component, "Implementation");
		bcModel.setNodeModels(getNodeModels(implementation));
		return bcModel;
	}


	/**
	 * bcpt的出入参
	 */
	protected List<ComponentArg> getComponentArgs(Element parent, String typePrefix) throws XmlParseException {
		List<ComponentArg> args = new ArrayList<>();
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");
		if (argsElement == null) {
			return args;
		}
		for (Element argElement : getDirectChildElements(argsElement, "Arg")) {
			ComponentArg componentArg = new ComponentArg();
			componentArg.setKey(getChildElementText(argElement, "Key"));
			componentArg.setDefValue(getChildElementText(argElement, "DefValue"));
			componentArg.setDesp(getChildElementText(argElement, "Desp"));
			args.add(componentArg);
		}
		return args;
	}
}
