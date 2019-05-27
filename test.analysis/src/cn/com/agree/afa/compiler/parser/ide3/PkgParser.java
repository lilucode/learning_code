package cn.com.agree.afa.compiler.parser.ide3;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.PkgModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;

//解析bcpt转换后的src文件
public class PkgParser extends AbstractParser<PkgModel> {
	protected PkgModel parse(Document doc, String outputPath) throws XmlParseException {
		PkgModel pkgModel = new PkgModel();
		Context.getContext().setOutsidePkgModel(pkgModel);
		pkgModel.setOutputPath(outputPath);
		Context.getContext().setOutputPath(outputPath);
		Element pkg = (Element) doc.getElementsByTagName("Package").item(0);
		String packageName = getChildElementText(pkg, "Name");
		pkgModel.setName(packageName);
		pkgModel.setDesc(getChildElementText(pkg, "Desp"));
		Context.getContext().setPackageName(packageName);
		List<Element> componentList = getDirectChildElements(pkg, "Component");
		for (Element component : componentList) {
			String desc = getChildElementText(component, "Desp");
			try {
				BCModel bcModel = getBCModel(component);
				bcModel.setDesc(desc);
				pkgModel.addBCModel(bcModel);
			} catch (Throwable e) {
				String newMessage = "ComponentName=" + desc + ", " + e.getMessage();
				if ((e instanceof XmlParseException)) {
					XmlParseException ce = (XmlParseException) e;
					ce.setMessage(newMessage);
					throw ce;
				}
				throw new XmlParseException(newMessage, e);
			}
		}

		return pkgModel;
	}

	//Component
	private BCModel getBCModel(Element component) throws XmlParseException {
		BCModel bcModel = new BCModel();
		String componentName = getChildElementText(component, "RefImpl");
		bcModel.setName(componentName);
		Context.getContext().setComponentName(componentName);
		Element aspectUsed = getDirectChildElement(component, "AspectUsed");
		if (aspectUsed != null) {
			bcModel.setAspectUsed(aspectUsed.getTextContent());
		}
		setPointInfoLogContext(bcModel, component);
		Element implementation = getDirectChildElement(component, "Implementation");
		try {
			String debugComplie = getChildElementText(component, "DebugMode");
			bcModel.setDebugComplie("1".equals(debugComplie));
			Context.getContext().setDebugCompile("1".equals(debugComplie));
		} catch (Exception e) {
			bcModel.setDebugComplie(false);
			Context.getContext().setDebugCompile(false);
		}

		bcModel.setNodeModels(getNodeModels(implementation));

		return bcModel;
	}

	private void setPointInfoLogContext(BCModel bc, Element component) {
		Element infoLogLevelElement = getDirectChildElement(component, "PointInfoLogLevel");
		if (infoLogLevelElement == null)
			bc.setInfoLogLevel(2);
		else {
			bc.setInfoLogLevel(Integer.valueOf(infoLogLevelElement.getTextContent()).intValue());
		}

		Element infoLogModeElemnt = getDirectChildElement(component, "PointInfoLogMode");
		if (infoLogModeElemnt == null)
			bc.setInfoLogMode(910817);
		else
			bc.setInfoLogMode(Integer.valueOf(infoLogModeElemnt.getTextContent()).intValue());
	}

	protected String[] getCptName(String name) {
		String appCode = Context.getContext().getPackageName();
		String tradeCode = Context.getContext().getComponentName() + "_" + name;
		String[] result = { appCode, tradeCode };
		return result;
	}
}
