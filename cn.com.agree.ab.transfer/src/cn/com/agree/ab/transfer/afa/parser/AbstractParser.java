package cn.com.agree.ab.transfer.afa.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


abstract class AbstractParser<T> implements IParser<T> {
	public T parse(File srcFile) throws XmlParseException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			Document doc = factory.newDocumentBuilder().parse(srcFile);
			return parse(doc, srcFile.getAbsoluteFile().getParent());
		} catch (Exception e) {
			if ((e instanceof XmlParseException))
				throw ((XmlParseException) e);
		}
		throw new XmlParseException("");
	}

	protected abstract T parse(Document paramDocument, String paramString) throws XmlParseException;

	// 根据tagName返回第一个孩子
	protected Element getDirectChildElement(Element element, String childElementName) {
		List<Element> childElements = getDirectChildElements(element, childElementName);
		return childElements.size() > 0 ? (Element) childElements.get(0) : null;
	}

	// 根据tagName，寻找该tag的孩子
	protected List<Element> getDirectChildElements(Element element, String childElementName) {
		List<Element> childElements = new ArrayList<>();
		NodeList nodeList = element.getElementsByTagName(childElementName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element childElement = (Element) nodeList.item(i);
			if (childElement.getParentNode() == element) {
				childElements.add(childElement);
			}
		}
		return childElements;
	}

	// 根据tagName返回第一个孩子的text
	protected String getChildElementText(Element element, String childElementName) throws XmlParseException {
		Element childElement = getDirectChildElement(element, childElementName);
		if (childElement == null) {
//			System.out.println(element.getTagName() + "节点没有" + childElementName + "子节点");
			return "";
		}
		return childElement.getTextContent();
	}

}
