package cn.com.agree.ab.transfer.afa.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.afa.model.Arg;
import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.model.ComponentArg;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.afa.model.TerminalsMode;


public class BcptBeanParse extends AbstractParser<BCModel> {

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

	/**
	 * 每个node的值
	 * 
	 * @param node
	 * @return
	 * @throws XmlParseException
	 */
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

	@Override
	protected String[] getCptName(String paramString) {
		return null;
	}

}
