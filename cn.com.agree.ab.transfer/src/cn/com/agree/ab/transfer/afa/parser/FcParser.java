package cn.com.agree.ab.transfer.afa.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.com.agree.ab.transfer.afa.model.Arg;
import cn.com.agree.ab.transfer.afa.model.BStepModel;
import cn.com.agree.ab.transfer.afa.model.FCModel;
import cn.com.agree.ab.transfer.afa.model.NStepModel;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.afa.model.StepModel;
import cn.com.agree.ab.transfer.afa.model.TerminalsMode;


public class FcParser extends AbstractParser<FCModel> {

	@Override
	protected FCModel parse(Document doc, String paramString) throws XmlParseException {

		FCModel fcModel = new FCModel();
		NodeList rootList = doc.getElementsByTagName("Root");
		if (rootList.getLength() < 1) {
			throw new XmlParseException("Xml结构非法，必须存在一个Root节点");
		}
		Element root = (Element) rootList.item(0);
		fcModel.setUuid(getChildElementText(root, "UUID"));
		Element dateInfo = getDirectChildElement(root, "DateInfo");
		fcModel.setCreateDate(getChildElementText(dateInfo, "CreateDate"));
		fcModel.setModifyDate(getChildElementText(dateInfo, "ModifyDate"));
		
		Element regulation = getDirectChildElement(root, "Regulation");
		List<Element> stepList = getDirectChildElements(regulation, "Step");
		if (stepList.size() < 1) {
			throw new XmlParseException("缺少Step节点");
		}
		for (Element step : stepList) {
			if(getStepModel(step)!=null) {
				fcModel.addStepModel(getStepModel(step));
			}
		}
		return fcModel;
	}
	
	public StepModel getStepModel(Element step) throws XmlParseException {
		StepModel stepModel = null;
		int type = Integer.parseInt(getChildElementText(step, "Type"));
		if (type == 4) {
			// bcpt
			stepModel = getBStepModel(step);
		} else if (type == 5) {
			// 通用组件
			stepModel = getNStepModel(step);
		} else if (type == 3) {
			// 场景调用
		}
		return stepModel;
	}
	
	private StepModel getBStepModel(Element step) throws XmlParseException {
		BStepModel stepModel = new BStepModel();
		setStepModel(step, stepModel);
		stepModel.setCptName(getChildElementText(step, "RefImpl"));
		stepModel.setFilePath(getChildElementText(step, "FilePath"));
		stepModel.setInputArgs(getArgs(step, "In"));
		stepModel.setOutputArgs(getArgs(step, "Out"));
		return stepModel;
	}

	public void setStepModel(Element step,StepModel stepModel) throws XmlParseException {
		stepModel.setType(Integer.parseInt(getChildElementText(step, "Type")));
		stepModel.setId(getChildElementText(step, "Id"));
		stepModel.setDesc(getChildElementText(step, "Desp"));
		stepModel.setLocation(getChildElementText(getDirectChildElement(step, "Constraint"), "Location"));
		stepModel.setSize(getChildElementText(getDirectChildElement(step, "Constraint"), "Size"));
		stepModel.setTrueId(getChildElementText(step, "True"));
		stepModel.setFalseId(getChildElementText(step, "False"));
	}
	
	public StepModel getNStepModel(Element step) throws XmlParseException {
		NStepModel stepModel = new NStepModel();
		setStepModel(step, stepModel);
		Element implementation = getDirectChildElement(step, "Implementation");
		//路径
		stepModel.setRefImpl(getChildElementText(step, "RefImpl"));
		stepModel.setNodeModels(getNodeModels(implementation));
		return stepModel;
	}
	
	public List<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		List<Element> nodeList = getDirectChildElements(impl, "Node");
		return getNodeModels(nodeList);
	}
	
	/**
	 * 获取所有node
	 */
	public List<NodeModel> getNodeModels(Collection<Element> nodeList) throws XmlParseException {
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
