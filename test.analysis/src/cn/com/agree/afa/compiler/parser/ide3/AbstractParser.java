package cn.com.agree.afa.compiler.parser.ide3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.com.agree.afa.compiler.model.Arg;
import cn.com.agree.afa.compiler.model.ComponentArg;
import cn.com.agree.afa.compiler.model.DebugInfo;
import cn.com.agree.afa.compiler.model.ExitType;
import cn.com.agree.afa.compiler.model.NStepModel;
import cn.com.agree.afa.compiler.model.NodeModel;
import cn.com.agree.afa.compiler.model.ParallelNodeModel;
import cn.com.agree.afa.compiler.model.ParallelTerminal;
import cn.com.agree.afa.compiler.model.TradeModel;
import cn.com.agree.afa.compiler.parser.IParser;
import cn.com.agree.afa.compiler.parser.XmlParseException;

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
			return null;
		}
		return childElement.getTextContent();
	}

	// ide右侧逻辑组件实现node
	protected Set<NodeModel> getNodeModels(Collection<Element> nodeList) throws XmlParseException {
		if (nodeList.size() < 1) {
			throw new XmlParseException("缺少Node节点");
		}
		Set<NodeModel> nodeModels = new TreeSet<>();
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

	// ide右侧逻辑组件实现node
	protected Set<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		Element nodes = getDirectChildElement(impl, "Nodes");
		List<Element> nodeList = getDirectChildElements(nodes, "Node");
		return getNodeModels(nodeList);
	}

	private void setPointInfoLogContext(NodeModel node, Element nodeElement) {
		Element infoLogLevelElement = getDirectChildElement(nodeElement, "PointInfoLogLevel");
		if (infoLogLevelElement == null)
			node.setInfoLogLevel(2);
		else {
			node.setInfoLogLevel(Integer.valueOf(infoLogLevelElement.getTextContent()).intValue());
		}

		Element infoLogModeElemnt = getDirectChildElement(nodeElement, "PointInfoLogMode");
		if (infoLogModeElemnt == null)
			node.setInfoLogMode(910817);
		else
			node.setInfoLogMode(Integer.valueOf(infoLogModeElemnt.getTextContent()).intValue());
	}

	protected abstract String[] getCptName(String paramString);

	// 把ide右侧的node节点，转换成NodeModel
	private NodeModel getNodeModel(Element node) throws XmlParseException {
		NodeModel nodeModel = new NodeModel();

		// Component/Implementation/Node/Type
		int type = Integer.parseInt(getChildElementText(node, "Type"));
		nodeModel.setType(type);
		// Component/Implementation/Node/Id
		String idString = getChildElementText(node, "Id");
		nodeModel.setIdString(idString);
		int id = Integer.parseInt(getChildElementText(node, "Id"));
		nodeModel.setId(id);
		// Component/Implementation/Node/Desp
		String desc = getChildElementText(node, "Desp");
		int atIndex = desc.indexOf("@");
		if (atIndex >= 0) {
			desc = desc.substring(atIndex + 1);
		}
		nodeModel.setDesc(desc);

		Element constraint = getDirectChildElement(node, "Constraint");
		// Component/Implementation/Node/Constraint/Location
		String location = getChildElementText(constraint, "Location");
		nodeModel.setLocation(location);
		// Component/Implementation/Node/Constraint/Size
		String size = getChildElementText(constraint, "Size");
		nodeModel.setSize(size);

		if (desc.equals("开始")) {
			// Component/Implementation/Node/SourceConnections/Connection/targetId
			String targetId = getChildElementText(
					getDirectChildElement(getDirectChildElement(node, "SourceConnections"), "Connection"), "targetId");
			nodeModel.setTargetId(targetId);
		} else if (desc.equals("正常结束")) {
			// Component/Implementation/Node/Id
			String endId = getChildElementText(node, "Id");
			nodeModel.setEndId(endId);
		}

		// Component/Implementation/Node/Target
		nodeModel.setCptName(getChildElementText(node, "Target"));

		// Component/Implementation/Node/Terminals/
		Element parallelTerminals = getDirectChildElement(node, "Terminals");
		if (parallelTerminals != null) {
			// Component/Implementation/Node/Terminals/Terminal
			List<Element> terminals = getDirectChildElements(parallelTerminals, "Terminal");
			for (Element terminalVariable : terminals) {
				// Component/Implementation/Node/Terminals/Terminal/Name
				String terminalName = getChildElementText(terminalVariable, "Name");
				nodeModel.setTerminalName(terminalName);
				String sourceTerminal = terminalName;
				// Component/Implementation/Node/Terminals/Terminal/Desp
				String terminalDesc = getChildElementText(terminalVariable, "Desp");
				nodeModel.setTerminalDesc(terminalDesc);
				Element sourceConnections = getDirectChildElement(
						(Element) terminalVariable.getParentNode().getParentNode(), "SourceConnections");
				if (sourceConnections == null) {
					throw new XmlParseException("sourceConnections is null");
				}
				List<Element> connections = getDirectChildElements(sourceConnections, "Connection");
				for (Element connection : connections) {
					// Component/Implementation/Node/SourceConnections/Connection/SourceTermina
					if (sourceTerminal.equals(getChildElementText(connection, "SourceTerminal"))) {
						// Component/Implementation/Node/SourceConnections/Connection/targetId
						String targetNodeId = getChildElementText(connection, "targetId");
						nodeModel.setTargetNodeId(targetNodeId);
						break;
					}
				}
			}
		}

		return nodeModel;
	}

	private Element getTerminalNode(Element node, Element terminal, Map<Integer, Element> cache, String sourceTerminal)
			throws XmlParseException {
		Element sourceConnections = getDirectChildElement((Element) terminal.getParentNode().getParentNode(),
				"SourceConnections");
		if (sourceConnections == null) {
			throw new XmlParseException("sourceConnections is null");
		}
		List<Element> connections = getDirectChildElements(sourceConnections, "Connection");
		int targetNodeId = 0;
		for (Element connection : connections) {
			if (sourceTerminal.equals(getChildElementText(connection, "SourceTerminal"))) {
				targetNodeId = Integer.parseInt(getChildElementText(connection, "TargetId"));
				break;
			}
		}
		if (targetNodeId <= 0) {
			throw new XmlParseException("targetNodeId is not more than 0,it is likely that sourceTerminal "
					+ sourceTerminal + " has no connections");
		}
		Element nodeReturned = (Element) cache.get(Integer.valueOf(targetNodeId));
		if (nodeReturned != null) {
			return nodeReturned;
		}
		Element nodesEle = (Element) node.getParentNode();
		List<Element> nodes = getDirectChildElements(nodesEle, "Node");
		for (Element nodeVariable : nodes) {
			int id = Integer.parseInt(getChildElementText(nodeVariable, "Id"));
			cache.put(Integer.valueOf(id), nodeVariable);
			if (id == targetNodeId) {
				return nodeVariable;
			}
		}
		throw new XmlParseException("找不到对应的terminalNode");
	}

	private void getInnerTradeNodes(Element subRoot, Map<Integer, Element> cache, Set<NodeModel> tradeNodes)
			throws XmlParseException {
		if (subRoot == null) {
			return;
		}
		NodeModel subRootModel = getNodeModel(subRoot);
		tradeNodes.add(subRootModel);
		if (!cache.containsKey(Integer.valueOf(subRootModel.getId()))) {
			cache.put(Integer.valueOf(subRootModel.getId()), subRoot);
		}
		Map<String, Integer> nodeRelations = subRootModel.getNodeRelations();
		for (Integer childId : nodeRelations.values()) {
			Element childElement = (Element) cache.get(childId);
			if (childElement != null) {
				getInnerTradeNodes(childElement, cache, tradeNodes);
			} else {
				Collection<Element> elements = Context.getContext().getNodeList();
				if (elements != null)
					for (Element element : elements) {
						int nodeId = Integer.parseInt(getChildElementText(element, "Id"));
						cache.put(Integer.valueOf(nodeId), element);
						if (nodeId == childId.intValue()) {
							getInnerTradeNodes(element, cache, tradeNodes);
							break;
						}
					}
			}
		}
	}

	

	// 出入参
	protected List<Arg> getArgs(Element parent, String typePrefix) throws XmlParseException {
		// Component/Implementation/Node/InArgs
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");
		Element levelsElement = getDirectChildElement(parent, typePrefix + "ArgLevels");
		Element keysElement = getDirectChildElement(parent, typePrefix + "ArgKeys");

		List<Arg> args = new ArrayList<>();
		if (argsElement == null) {
			return args;
		}

		int id = 0;
		// Component/Implementation/Node/InArgs/Arg
		for (Element argElement : getDirectChildElements(argsElement, "Arg")) {
			Arg arg = new Arg();
			arg.setValue(argElement.getTextContent());
			arg.setId(id++);
			// Component/Implementation/Node/InArgs/Arg/Key
			arg.setKey(getChildElementText(argElement, "Key"));
			// Component/Implementation/Node/InArgs/Arg/Name
			arg.setName(getChildElementText(argElement, "Name"));
			// Component/Implementation/Node/InArgs/Arg/Type
			// arg.setType(getChildElementText(argElement, "Type"));
			// Component/Implementation/Node/InArgs/Arg/Arg
			arg.setArg(getChildElementText(argElement, "Arg"));
			args.add(arg);
		}
		if (levelsElement != null) {
			List<Element> levelElements = getDirectChildElements(levelsElement, "Level");
			if (args.size() != levelElements.size()) {
				throw new XmlParseException("Level节点个数与Arg节点个数不一致");
			}
			for (int i = 0; i < levelElements.size(); i++) {
				((Arg) args.get(i)).setLogLevel(Integer.parseInt(((Element) levelElements.get(i)).getTextContent()));
			}
		}
		if (keysElement != null) {
			List<Element> keyElements = getDirectChildElements(keysElement, "Key");
			if (args.size() != keyElements.size()) {
				throw new XmlParseException("Key节点个数与Arg节点个数不一致");
			}
			for (int i = 0; i < keyElements.size(); i++) {
				((Arg) args.get(i)).setKey(((Element) keyElements.get(i)).getTextContent());
			}
		}
		return args;
	}
}
