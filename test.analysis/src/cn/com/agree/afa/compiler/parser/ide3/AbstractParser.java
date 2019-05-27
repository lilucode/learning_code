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

	//根据tagName返回第一个孩子
	protected Element getDirectChildElement(Element element, String childElementName) {
		List<Element> childElements = getDirectChildElements(element, childElementName);
		return childElements.size() > 0 ? (Element) childElements.get(0) : null;
	}

	//根据tagName，寻找该tag的孩子
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

	//根据tagName返回第一个孩子的text
	protected String getChildElementText(Element element, String childElementName) throws XmlParseException {
		Element childElement = getDirectChildElement(element, childElementName);
		if (childElement == null) {
			throw new XmlParseException(element.getTagName() + "节点没有" + childElementName + "子节点");
		}
		return childElement.getTextContent();
	}

	//ide右侧逻辑组件实现node
	private Set<NodeModel> getNodeModels(Collection<Element> nodeList) throws XmlParseException {
		if (nodeList.size() < 1) {
			throw new XmlParseException("缺少Node节点");
		}
		Context.getContext().setNodeList(nodeList);
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

	//ide右侧逻辑组件实现node
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

	//把ide右侧的node节点，转换成NodeModel
	private NodeModel getNodeModel(Element node) throws XmlParseException {
		int type = Integer.parseInt(getChildElementText(node, "Type"));
		NodeModel nodeModel = null;
		if (type == 17)
			nodeModel = new ParallelNodeModel();
		else {
			nodeModel = new NodeModel();
		}
		int id = Integer.parseInt(getChildElementText(node, "Id"));
		Context.getContext().setNodeId(id);
		nodeModel.setId(id);
		String desc = getChildElementText(node, "Desp");
		int atIndex = desc.indexOf("@");
		if (atIndex >= 0) {
			desc = desc.substring(atIndex + 1);
		}
		nodeModel.setDesc(desc);
		nodeModel.setType(type);
		nodeModel.setCptName(getChildElementText(node, "Target"));
		Element aspectUsed = getDirectChildElement(node, "AspectUsed");
		if (aspectUsed != null) {
			nodeModel.setAspectUsed(aspectUsed.getTextContent());
		}
		Element async = getDirectChildElement(node, "Async");
		if (async != null) {
			nodeModel.setAsync("1".equals(async.getTextContent()));
		}

		Element value = getDirectChildElement(node, "Value");
		if (value != null) {
			nodeModel.setValue(Integer.parseInt(value.getTextContent()));
		}

		Element skip = getDirectChildElement(node, "Skip");
		if (skip != null) {
			nodeModel.setSkip("1".equals(getChildElementText(skip, "Enabled")));
			String branch = getChildElementText(skip, "Branch");
			if ((branch == null) || (branch.isEmpty()))
				nodeModel.setDefaultStatus(1);
			else {
				nodeModel.setDefaultStatus(Integer.parseInt(branch));
			}
		}

		Element checkTradeExist = getDirectChildElement(node, "CheckTradeExist");
		if (checkTradeExist != null) {
			nodeModel.setCheckTradeExist(Integer.parseInt(checkTradeExist.getTextContent()));
		}

		DebugInfo debugInfo = new DebugInfo();
		nodeModel.setDebugInfo(debugInfo);
		Element isDebug = getDirectChildElement(node, "IsDebug");
		if (isDebug != null) {
			debugInfo.setDebug("1".equals(isDebug.getTextContent()));

			Element debug = getDirectChildElement(node, "Debug");
			if (debug != null) {
				String resultStatus = getChildElementText(debug, "Result");
				if ((resultStatus == null) || (resultStatus.isEmpty()))
					debugInfo.setStatus(1);
				else {
					debugInfo.setStatus(Integer.parseInt(resultStatus));
				}
				debugInfo.setOutputArgs(getChildElementText(debug, "Return"));

				Element codes = getDirectChildElement(debug, "Codes");
				if (codes != null) {
					for (Element code : getDirectChildElements(codes, "Code")) {
						debugInfo.addCode(code.getTextContent());
					}
				}
			}
		}

		setPointInfoLogContext(nodeModel, node);
		nodeModel.setInputArgs(getArgs(node, "In"));
		nodeModel.setOutputArgs(getArgs(node, "Out"));

		Element logic = getDirectChildElement(node, "Logic");
		NodeList rets = logic.getChildNodes();
		for (int i = 0; i < rets.getLength(); i++) {
			if (!(rets.item(i) instanceof Element)) {
				continue;
			}
			Element ret = (Element) rets.item(i);
			String tagName = ret.getTagName();
			if (!tagName.startsWith("RET")) {
				continue;
			}
			int status = Integer.parseInt(tagName.substring(3));
			int nextNodeId = Integer.parseInt(ret.getTextContent());
			nodeModel.addNextNodeId(status, nextNodeId);
		}

		if ((nodeModel instanceof ParallelNodeModel)) {
			ParallelNodeModel parallelNodeModel = (ParallelNodeModel) nodeModel;
			String isWaitForResult = getChildElementText(node, "IsWaitForResult");
			parallelNodeModel.setSync(Boolean.valueOf(isWaitForResult).booleanValue());
			Element parallelTerminals = getDirectChildElement(node, "Terminals");
			List<Element> terminals = getDirectChildElements(parallelTerminals, "Terminal");
			Map<String, ParallelTerminal> terminalMap = new HashMap<>();
			Map<Integer, Element> cache = new HashMap<>();

			if ((terminals != null) && (!terminals.isEmpty())) {
				for (Element terminalVariable : terminals) {
					Element hasResponseEle = getDirectChildElement(terminalVariable, "HasResponse");
					String terminalName = getChildElementText(terminalVariable, "Name");
					String sourceTerminal = terminalName;
					terminalName = id + "_" + terminalName;
					if (sourceTerminal.equals("-1")) {
						parallelNodeModel.setOutStatus(-1);
					} else {
						ParallelTerminal parallelTerminal = new ParallelTerminal();
						parallelTerminal.setName(terminalName);
						String terminalDesc = getChildElementText(terminalVariable, "Desp");
						parallelTerminal.setDesc(terminalDesc);
						String resultId = getChildElementText(terminalVariable, "ResultId");
						parallelTerminal.setResultId(resultId);
						String timeOutInMill = getChildElementText(terminalVariable, "Timeout");
						parallelTerminal.setTimeoutInMill(timeOutInMill);
						Element times = getDirectChildElement(terminalVariable, "Times");
						String timesText;
						if (times == null)
							timesText = "1";
						else {
							timesText = times.getTextContent();
						}
						parallelTerminal.setTimes(timesText);
						String hasResponse = hasResponseEle.getTextContent();
						parallelTerminal.setHasResponse(Boolean.parseBoolean(hasResponse));
						Element terminalNode = getTerminalNode(node, terminalVariable, cache, sourceTerminal);
						Set<NodeModel> tradeNodes = new HashSet<>();
						getInnerTradeNodes(terminalNode, cache, tradeNodes);
						List<Arg> terminalInArgs = getArgs(terminalVariable, "In");
						parallelTerminal.setTerminalInArgs(terminalInArgs);
						Element sharedEle = getDirectChildElement(terminalVariable, "ThreadShared");
						parallelTerminal.setThreadShared(sharedEle == null ? "" : sharedEle.getTextContent());

						Element threadOptimized = getDirectChildElement(terminalVariable, "ThreadOptimized");
						if (threadOptimized != null) {
							String optimized = threadOptimized.getTextContent();
							if ("true".equals(optimized)) {
								if (tradeNodes.size() == 1) {
									Iterator<NodeModel> localIterator3 = tradeNodes.iterator();
									if (localIterator3.hasNext()) {
										NodeModel nodeModelTmp = (NodeModel) localIterator3.next();
										if (!"场景异步调用".equals(nodeModelTmp.getDesc())) {
											if (!"cn.com.agree.afa.jcomponent.SdkBroker.asyncInvoke"
													.equals(nodeModelTmp.getCptName()))
												throw new XmlParseException("端口: " + parallelTerminal.getName()
														+ ",选择优化线程数，只能连场景异步调用组件，内部场景有且只有一个场景异步调用组件节点");
										}
										parallelTerminal.setAsyncScenarioNode(nodeModelTmp);
										terminalMap.put(terminalName, parallelTerminal);

										continue;
									}
								} else {
									throw new XmlParseException("端口: " + parallelTerminal.getName()
											+ ",选择优化线程数，只能连场景异步调用组件，内部交易有且只有一个场景异步调用组件节点");
								}
							}

						}

						TradeModel innerTradeModel = new TradeModel();
						innerTradeModel.setOutputPath(Context.getContext().getOutputPath());
						String[] cptName = getCptName(terminalName);
						innerTradeModel.setParentName("");
						innerTradeModel.setAppCode(cptName[0]);
						parallelTerminal.setAppCode(cptName[0]);
						innerTradeModel.setTradeCode(cptName[1]);
						parallelTerminal.setTradeCode(cptName[1]);
						innerTradeModel.setAppDesc("");
						innerTradeModel.setTradeDesc(terminalDesc);
						innerTradeModel.setAuthor("");
						innerTradeModel.setCreationDate("");
						innerTradeModel.setModificationDate("");
						innerTradeModel.setExecuteOnWGStartup(false);
						innerTradeModel.setDebugMode(false);

						NStepModel stepModel = new NStepModel();
						stepModel.setId(1);
						stepModel.setDesc("default step");
						stepModel.setSkip(false);
						stepModel.setParentTradeName("");
						stepModel.setNoop(false);
						NodeModel startNode = new NodeModel();
						startNode.setId(0);
						startNode.setType(2);
						startNode.setDesc("开始");
						startNode.setCptName("Begin");
						startNode.setAsync(false);
						startNode.setSkip(false);
						startNode.setCheckTradeExist(ExitType.DEFAULT_ERROR);
						startNode.setDebugMode(false);
						startNode.setInputArgs(Collections.emptyList());
						startNode.setOutputArgs(Collections.emptyList());

						int terminalNodeId = Integer.parseInt(getChildElementText(terminalNode, "Id"));
						NextIdGenerator idGenerator = new NextIdGenerator(tradeNodes);
						for (NodeModel nodeModelTmp : tradeNodes) {
							int nodeType = nodeModelTmp.getType();
							boolean notAdd = false;
							if ((nodeType == 3) || (nodeType == 4)) {
								notAdd = true;
							}
							Map<Integer, Integer> relations = nodeModelTmp.getNodeRelations();
							if (!relations.isEmpty()) {
								notAdd = true;
							}

							if (!notAdd) {
								NodeModel normalEnd = new NodeModel();
								normalEnd.setId(idGenerator.nextId());
								normalEnd.setType(3);
								normalEnd.setDesc("正常结束");
								normalEnd.setCptName("End");
								normalEnd.setAsync(false);
								normalEnd.setSkip(false);
								normalEnd.setCheckTradeExist(ExitType.DEFAULT_ERROR);
								normalEnd.setDebugMode(false);
								normalEnd.setInputArgs(Collections.emptyList());
								normalEnd.setOutputArgs(Collections.emptyList());

								NodeModel expEnd = new NodeModel();
								expEnd.setId(idGenerator.nextId());
								expEnd.setType(4);
								expEnd.setDesc("异常结束");
								expEnd.setCptName("End");
								expEnd.setAsync(false);
								expEnd.setSkip(false);
								expEnd.setCheckTradeExist(ExitType.DEFAULT_ERROR);
								expEnd.setDebugMode(false);
								expEnd.setInputArgs(Collections.emptyList());
								expEnd.setOutputArgs(Collections.emptyList());

								nodeModelTmp.addNextNodeId(1, normalEnd.getId());
								nodeModelTmp.addNextNodeId(0, expEnd.getId());
								tradeNodes.add(normalEnd);
								tradeNodes.add(expEnd);
							}

							String aspectUsedCondition = nodeModelTmp.getAspectUsed();
							if ((aspectUsedCondition != null) && (!aspectUsedCondition.isEmpty())) {
								innerTradeModel.setAspectUsed(nodeModelTmp.getAspectUsed());
							}

						}

						startNode.addNextNodeId(1, terminalNodeId);
						tradeNodes.add(startNode);

						stepModel.setNodeModels(tradeNodes);
						innerTradeModel.addStepModel(stepModel);
						Context.getContext().addInnerTrades(
								innerTradeModel.getAppCode() + "." + innerTradeModel.getTradeCode(), innerTradeModel);
						parallelTerminal.setTradeModel(innerTradeModel);
						terminalMap.put(terminalName, parallelTerminal);
					}
				}
				parallelNodeModel.setTerminals(terminalMap);
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
		Map<Integer, Integer> nodeRelations = subRootModel.getNodeRelations();
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

	protected List<Arg> getArgs(Element parent, String typePrefix) throws XmlParseException {
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");
		Element levelsElement = getDirectChildElement(parent, typePrefix + "ArgLevels");
		Element keysElement = getDirectChildElement(parent, typePrefix + "ArgKeys");

		List<Arg> args = new ArrayList<>();
		if (argsElement == null) {
			return args;
		}

		int id = 0;
		for (Element argElement : getDirectChildElements(argsElement, "Arg")) {
			Arg arg = new Arg();
			arg.setValue(argElement.getTextContent());
			arg.setId(id++);
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
