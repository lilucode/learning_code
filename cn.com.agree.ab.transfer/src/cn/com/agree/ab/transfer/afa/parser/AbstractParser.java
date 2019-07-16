package cn.com.agree.ab.transfer.afa.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.com.agree.ab.transfer.afa.model.Arg;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.afa.model.TerminalsMode;


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
	
	protected List<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		List<Element> nodeList = getDirectChildElements(impl, "Node");
		return getNodeModels(nodeList);
	}

	/**
	 * 获取所有node
	 */
	protected List<NodeModel> getNodeModels(Collection<Element> nodeList) throws XmlParseException {
		if (nodeList.size() < 1) {
			throw new XmlParseException("缺少Node节点");
		}
		List<NodeModel> nodeModels = new ArrayList<NodeModel>();
		for (Element node : nodeList) {
			NodeModel nodeModel = null;
			try {
				nodeModel = getNodeModel(node);
				nodeModels.add(nodeModel);
			} catch (Throwable e) {
				String newMessage = "NodeId=" + Context.getContext().getNodeId() + ", " + e.getMessage();
				if ((e instanceof XmlParseException)) {
					XmlParseException ce = (XmlParseException) e;
					ce.setMessage(newMessage);
					throw ce;
				}
				throw new XmlParseException(newMessage, e);
			}
		}

		return nodeModels;
	}
	
	private NodeModel getNodeModel(Element node) throws XmlParseException {
		NodeModel nodeModel = new NodeModel();
		String name = getChildElementText(node, "Name");
		String desc = getChildElementText(node, "Desp");
		int atIndex = desc.indexOf("@");
		if (atIndex >= 0) {
			desc = desc.substring(atIndex + 1);
		}
		nodeModel.setType(Integer.parseInt(getChildElementText(node, "Type")));
		nodeModel.setIdString(getChildElementText(node, "Id"));
		nodeModel.setName(name);
		nodeModel.setDesc(desc);
		nodeModel.setCptName(getChildElementText(node, "Target"));

		Element constraint = getDirectChildElement(node, "Constraint");
		nodeModel.setLocation(getChildElementText(constraint, "Location"));
		nodeModel.setSize(getChildElementText(constraint, "Size"));

		nodeModel.setInputArgs(getArgs(node, "In"));
		nodeModel.setOutputArgs(getArgs(node, "Out"));

		String filePath = getChildElementText(node, "FilePath");
		nodeModel.setFilePath(filePath);

		Element parallelTerminals = getDirectChildElement(node, "Terminals");
		if (parallelTerminals != null) {

			List<Element> terminals = getDirectChildElements(parallelTerminals, "Terminal");

			for (Element terminalVariable : terminals) {
				TerminalsMode terminalsMode = new TerminalsMode();
				String terminalName = getChildElementText(terminalVariable, "Name");
				terminalsMode.setTerminalName(terminalName);
				terminalsMode.setTerminalDesc(getChildElementText(terminalVariable, "Desp"));
				String sourceTerminal = terminalName;
				Element sourceConnections = getDirectChildElement(
						(Element) terminalVariable.getParentNode().getParentNode(), "SourceConnections");
				if (sourceConnections == null) {
					throw new XmlParseException("sourceConnections is null");
				}
				List<Element> connections = getDirectChildElements(sourceConnections, "Connection");
				for (Element connection : connections) {

					if (sourceTerminal.equals(getChildElementText(connection, "SourceTerminal"))) {
						terminalsMode.setTargetNodeId(getChildElementText(connection, "targetId"));
						break;
					}
				}
				nodeModel.addTerminals(terminalsMode);
			}
		}

		return nodeModel;
	}

	/**
	 * Node的出入参
	 */
	protected List<Arg> getArgs(Element parent, String typePrefix) throws XmlParseException {
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");

		List<Arg> args = new ArrayList<>();
		if (argsElement == null) {
			return args;
		}

		int id = 0;
		for (Element argElement : getDirectChildElements(argsElement, "Arg")) {
			Arg arg = new Arg();
			arg.setValue(argElement.getTextContent());
			arg.setId(id++);
			arg.setKey(getChildElementText(argElement, "Key"));
			arg.setName(getChildElementText(argElement, "Name"));
			arg.setType(getChildElementText(argElement, "Type"));
			arg.setArg(getChildElementText(argElement, "Arg"));
			args.add(arg);
		}
		return args;
	}

}
