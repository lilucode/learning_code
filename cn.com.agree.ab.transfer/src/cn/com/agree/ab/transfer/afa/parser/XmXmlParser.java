package cn.com.agree.ab.transfer.afa.parser;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.afa.model.Constant;
import cn.com.agree.ab.transfer.runtime.lfc.Xm;

public class XmXmlParser extends AbstractParser<Xm> {

	@Override
	protected Xm parse(Document doc, String paramString) throws XmlParseException {
		Element xmElement = (Element) doc.getElementsByTagName("Xm").item(0);
		Xm xm=getXmModel(xmElement);
		return xm;
	}
	
	private Xm getXmModel(Element xmElement) {
		Xm xm = new Xm();
		xm.setCaption(xmElement.getAttribute("caption"));
		xm.setDescription(xmElement.getAttribute("description"));
		Element dataBasketElement = getDirectChildElement(xmElement, "DataBasket");
		List<Element> adeElement = getDirectChildElements(dataBasketElement, "Ade");
		for (Element element : adeElement) {
			String adeName = element.getAttribute("name");
			String str = "data://" + Constant.ProjectName + "/dataDictionary/" + adeName + ".ade";
			xm.setDataBasket(str);
		}
		Element xmrefsElement = getDirectChildElement(xmElement, "Xmrefs");
		List<Element> xmPathElement = getDirectChildElements(xmrefsElement, "Xm");
		for (Element element : xmPathElement) {
			String xmPath=element.getAttribute("path");
			String str = "data://" + Constant.ProjectName + "/dataModel" + xmPath;
			xm.setDataBasket(str);
		}
		return xm;
	}

}
