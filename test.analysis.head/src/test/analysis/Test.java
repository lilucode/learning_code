package test.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Test {
	
	private static List<Element> listChildElements(Element element, String nameFilter) {
		List<Element> result = new ArrayList<Element>();
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getParentNode().equals(element) && child instanceof Element) {
				if (nameFilter == null || nameFilter.equals(((Element) child).getTagName())) {
					result.add((Element) child);
				}
			}
		}
		return result;
	}

	public static Object parse(InputStream in) throws IOException {
		// 1. 准备builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IOException(e.getMessage(), e);
		}
		// 2. doc
		Document doc;
		try {
			doc = builder.parse(in);
		} catch (SAXException e) {
			throw new IOException(e.getMessage(), e);
		} finally {
			try {
				in.close();
			}catch (IOException e) {
			}
		}
		// 分析元素并创建
		Element root = doc.getDocumentElement();
		// 数据篮子 DataBasket

		for (Element componentElement : listChildElements(root, "Component")) {
			
			System.out.println(componentElement.getTagName());
//			for (Element adr : listChildElements(dataRuleElement, "Lfc")) {
//				// entry.setPath(adr.getAttribute("path"));
//
//				for (Element mapping : listChildElements(adr, "Mappings")) {
//					for (Element mappingElement : listChildElements(mapping, "Mapping")) {
//						String target = mappingElement.getAttribute("target");
//						String source = mappingElement.getAttribute("source");
//						// entry.addMapping(target, source);
//					}
//				}
//			}
		}
		// 解析Entry,Bean
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("./bcpt/OM.src");
		System.out.println(file.exists());
		parse(new FileInputStream(file));
	}
}
