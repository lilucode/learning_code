package cn.com.agree.afa.compiler.parser.ide3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.a5.runtime.lfc.ArgComponent;
import cn.com.agree.ab.a5.runtime.lfc.ArgElement;
import cn.com.agree.ab.a5.runtime.lfc.ComponentElement;
import cn.com.agree.ab.a5.runtime.lfc.ComponentOut;
import cn.com.agree.ab.a5.runtime.lfc.Geometry;
import cn.com.agree.ab.a5.runtime.lfc.LfcComponentElement;
import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.ab.a5.runtime.lfc.LogicletComponentElement;
import cn.com.agree.afa.compiler.model.Arg;
import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.ComponentArg;
import cn.com.agree.afa.compiler.model.NodeModel;
import cn.com.agree.afa.compiler.model.TerminalsMode;
import cn.com.agree.afa.compiler.parser.XmlParseException;

public class BcptParser extends AbstractParser<BCModel> {

	private LogicFlowControl lfc = new LogicFlowControl();

	// Component/Implementation/Node/Constraint/Location
	private String location;
	// Component/Implementation/Node/Constraint/Size
	private String size;
	private String idString;
	private String desc;
	private String target;
	private List<String> end_id = new ArrayList<String>();
	private List<String> exception_id = new ArrayList<String>();
	private String default_id;
	private String default_next_id;
	private String default_error_id;
	private Map<String, String> change_id = new ConcurrentHashMap<String, String>();

	public String componentName;

	public LogicFlowControl getLfc() {
		return lfc;
	}

	@Override
	protected BCModel parse(Document doc, String paramString) throws XmlParseException {
		Element component = (Element) doc.getElementsByTagName("Component").item(0);
		BCModel bcModel = getBCModel(component);
		return bcModel;
	}

	private BCModel getBCModel(Element component) throws XmlParseException {
		BCModel bcModel = new BCModel();
		// fileDescription：Component/Auth，Component/RefImpl，Component/Desp
		String auth = getChildElementText(component, "Auth");
		componentName = getChildElementText(component, "RefImpl");
		String desp = getChildElementText(component, "Desp");
		bcModel.setAuth(auth);
		bcModel.setName(componentName);
		bcModel.setDesc(desp);
		lfc.getFileDescription().setAuthor(auth);
		lfc.getFileDescription().setFunction(componentName);
		lfc.getFileDescription().setRemark(desp);

		// lfc出入参： Component/InArgs，Component/OutArgs
		List<ComponentArg> cInArgs = getComponentArgs(component, "In");
		List<ComponentArg> cOutArgs = getComponentArgs(component, "Out");
		bcModel.setInputArgs(cInArgs);
		bcModel.setOutputArgs(cOutArgs);
		lfc.addInArgs(getArgElement(cInArgs));
		lfc.addOutArgs(getArgElement(cOutArgs));

		Element implementation = getDirectChildElement(component, "Implementation");
		bcModel.setNodeModels(getNodeModels(implementation));
		return bcModel;
	}

	protected List<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		List<Element> nodeList = getDirectChildElements(impl, "Node");
		return getNodeModels(nodeList);
	}

	// ide右侧逻辑组件实现node
	protected List<NodeModel> getNodeModels(Collection<Element> nodeList) throws XmlParseException {
		if (nodeList.size() < 1) {
			throw new XmlParseException("缺少Node节点");
		}
		List<NodeModel> nodeModels = new ArrayList<NodeModel>();
		changeEndId(nodeList);
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

	public void changeEndId(Collection<Element> nodeList) throws XmlParseException {
		for (Element node : nodeList) {
			int type = Integer.parseInt(getChildElementText(node, "Type"));
			if (type == 3) {
				String end = getChildElementText(node, "Id");
				end_id.add(end);
			} else if (type == 4) {
				String end = getChildElementText(node, "Id");
				exception_id.add(end);
			} else if (type == 6) {
				// 默认逻辑错误委托
				default_id = getChildElementText(node, "Id");
				default_next_id = getNextId(node, "成功");
				default_error_id = getNextId(node, "失败");
			} else if (type == 10) {
				// 中转节点
				change_id.put("id", getChildElementText(node, "Id"));
				change_id.put("next", getNextId(node, "锚点一"));
			}
		}
	}

	public String getNextId(Element node, String desp) throws XmlParseException {
		Element parallelTerminals = getDirectChildElement(node, "Terminals");
		if (parallelTerminals != null) {
			List<Element> terminals = getDirectChildElements(parallelTerminals, "Terminal");
			for (Element terminalVariable : terminals) {
				String terminalName = getChildElementText(terminalVariable, "Name");
				String terminalDesc = getChildElementText(terminalVariable, "Desp");
				Element sourceConnections = getDirectChildElement(
						(Element) terminalVariable.getParentNode().getParentNode(), "SourceConnections");
				if (sourceConnections == null) {
					throw new XmlParseException("sourceConnections is null");
				}
				List<Element> connections = getDirectChildElements(sourceConnections, "Connection");
				for (Element connection : connections) {
					if (terminalName.equals(getChildElementText(connection, "SourceTerminal"))) {
						String targetNodeId = getChildElementText(connection, "targetId");
						if (terminalDesc.equals(desp)) {
							return targetNodeId;
						}
					}
				}
			}
		}
		return null;
	}

	// 把ide右侧的node节点，转换成NodeModel
	private NodeModel getNodeModel(Element node) throws XmlParseException {
		NodeModel nodeModel = new NodeModel();
		// Component/Implementation/Node/Type
		int type = Integer.parseInt(getChildElementText(node, "Type"));
		// Component/Implementation/Node/Id
		idString = getChildElementText(node, "Id");
		// Component/Implementation/Node/Desp
		desc = getChildElementText(node, "Name");
		int atIndex = desc.indexOf("@");
		if (atIndex >= 0) {
			desc = desc.substring(atIndex + 1);
		}
		if (idString.equals(default_error_id)) {
			return nodeModel;
		}

		// Component/Implementation/Node/Target
		target = getChildElementText(node, "Target");
		nodeModel.setType(type);
		nodeModel.setIdString(idString);
		nodeModel.setDesc(desc);
		nodeModel.setCptName(target);

		Element constraint = getDirectChildElement(node, "Constraint");
		location = getChildElementText(constraint, "Location");
		size = getChildElementText(constraint, "Size");
		nodeModel.setLocation(location);
		nodeModel.setSize(size);

		if (type == 11) {
			LogicletComponentElement ce = new LogicletComponentElement();

			List<Arg> cInArgs = getArgs(node, "In");
			ce.addInArgs(getArgComponent(cInArgs));
			nodeModel.setInputArgs(cInArgs);
			// Component/OutArgs
			List<Arg> cOutArgs = getArgs(node, "Out");
			ce.addOutArgs(getArgComponent(cOutArgs));
			nodeModel.setOutputArgs(cOutArgs);

			setLfcAndComponent(ce, type, node, nodeModel);

			lfc.addComponent(ce);
		} else if (type == 7 || type == 12) {
			LfcComponentElement lce = new LfcComponentElement();
			if (type == 7) {
				String filePath = getChildElementText(node, "FilePath");
				nodeModel.setFilePath(filePath);
				try {
					
					filePath = filePath.substring(filePath.indexOf("bank"), filePath.lastIndexOf("/")).replaceAll("bank", "")
							+ "/";
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("内嵌lfc路径有问题："+componentName);
				}
				// Component/Implementation/Node/FilePath 内嵌lfc的路径
				lce.setLfcPath("/demo-s/business" + filePath + target.substring(target.lastIndexOf(".") + 1) + ".lfc");
			} else {
				lce.setLfcPath("/demo-s/business" + desc + ".lfc");
			}

			List<Arg> cInArgs = getArgs(node, "In");
			lce.setMappingPath("/" + componentName + ".lfc");
			lce.addInArgs(getArgLfc(cInArgs));
			nodeModel.setInputArgs(cInArgs);
			// Component/OutArgs
			List<Arg> cOutArgs = getArgs(node, "Out");
			lce.addOutArgs(getArgLfc(cOutArgs));
			nodeModel.setOutputArgs(cOutArgs);

			setLfcAndComponent(lce, type, node, nodeModel);
			lfc.addLfc(lce);
		}

		if (type == 2) {
			// Component/Implementation/Node/SourceConnections/Connection/targetId
			String targetId = getChildElementText(
					getDirectChildElement(getDirectChildElement(node, "SourceConnections"), "Connection"), "targetId");
			nodeModel.setTargetId(targetId);
			if (targetId.equals(default_id)) {
				targetId = default_next_id;
			} else if (targetId.equals(change_id.get("id"))) {
				targetId = change_id.get("next");
			}
			lfc.setStart(Integer.valueOf(targetId));
			setGeometry(lfc.getGeometry());
		} else if (type == 3) {
			// Component/Implementation/Node/Id
			if(lfc.getEnd().size()==0) {
				
				String endId = getChildElementText(node, "Id");
				nodeModel.setEndId(endId);
				lfc.setEnd("1001");
				setGeometry(lfc.getEndstep().getGeometry());
			}
		}

		return nodeModel;
	}

	// Node转换成内嵌lfc和技术组件
	public void setLfcAndComponent(ComponentElement ce, int type, Element node, NodeModel nodeModel)
			throws XmlParseException {
		ce.setId(idString);
		ce.setShowId(idString);
		ce.setCaption(desc);
		String target_name = target.substring(target.lastIndexOf(".") + 1) + "Logiclet";
		ce.setName(target_name.substring(0, 1).toUpperCase() + target_name.substring(1));
		setGeometry(ce.getGeometry());

		// Component/Implementation/Node/Terminals/
		Element parallelTerminals = getDirectChildElement(node, "Terminals");
		if (parallelTerminals != null) {

			// Component/Implementation/Node/Terminals/Terminal
			List<Element> terminals = getDirectChildElements(parallelTerminals, "Terminal");

			for (Element terminalVariable : terminals) {
				TerminalsMode terminalsMode=new TerminalsMode();
				// Component/Implementation/Node/Terminals/Terminal/Name
				String terminalName = getChildElementText(terminalVariable, "Name");
				terminalsMode.setTerminalName(terminalName);
				String sourceTerminal = terminalName;
				// Component/Implementation/Node/Terminals/Terminal/Desp
				String terminalDesc = getChildElementText(terminalVariable, "Desp");
				terminalsMode.setTerminalDesc(terminalDesc);
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
						terminalsMode.setTargetNodeId(targetNodeId);
						if (!terminalDesc.equals("失败")) {

							ComponentOut out = new ComponentOut();
							out.setCaption(terminalDesc);
							out.setName(terminalName);
							if (end_id != null && end_id.contains(targetNodeId)) {
								out.setNext("1001");
							} else if (exception_id != null && exception_id.contains(targetNodeId)) {
								out.setNext("1000");
							} else {
								out.setNext(targetNodeId);
							}
							ce.addOut(out);
						} else {
							if (end_id != null && end_id.contains(targetNodeId)) {
								ce.getException().setNext("1001");
							} else if (exception_id != null && exception_id.contains(targetNodeId)) {
								ce.getException().setNext("1000");
							} else {
								ce.getException().setNext(targetNodeId);
							}
						}
						break;
					}
				}
				nodeModel.addTerminals(terminalsMode);
			}
		}
	}

	// bcpt的出入参
	protected List<ComponentArg> getComponentArgs(Element parent, String typePrefix) throws XmlParseException {
		List<ComponentArg> args = new ArrayList<>();
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");
		if (argsElement == null) {
			return args;
		}
		for (Element argElement : getDirectChildElements(argsElement, "Arg")) {
			ComponentArg componentArg = new ComponentArg();
			// Component/InArgs/Arg/Key
			componentArg.setKey(getChildElementText(argElement, "Key"));
			// Component/InArgs/Arg/DefValue
			componentArg.setDefValue(getChildElementText(argElement, "DefValue"));
			// Component/InArgs/Arg/Desp
			componentArg.setDesp(getChildElementText(argElement, "Desp"));
			args.add(componentArg);
		}
		return args;
	}

	// 最外层出入参放到lfc
	public List<ArgElement> getArgElement(List<ComponentArg> Args) {
		List<ArgElement> list = new ArrayList<ArgElement>();
		for (ComponentArg componentArg : Args) {
			ArgElement ae = new ArgElement();
			ae.setName(componentArg.getKey());
			ae.setExample(componentArg.getDefValue().replaceAll("\"", "\\\\\"").replaceAll("\n", ""));
			ae.setDescription(componentArg.getDesp());
			list.add(ae);
		}
		return list;
	}

	// 技术组件入参到lfc
	public List<ArgElement> getArgComponent(List<Arg> cInArgs) {
		List<ArgElement> cInList = new ArrayList<ArgElement>();
		for (Arg componentArg : cInArgs) {
			ArgComponent ae = new ArgComponent();
			ae.setName(componentArg.getKey());
			ae.setCaption(componentArg.getName());
			ae.setEditor(componentArg.getType());
			ae.setValue(componentArg.getArg().replaceAll("\"", "\\\\\"").replaceAll("\n", ""));
			// if (arg_Value.indexOf("\"") != -1) {
			// ae.setValue(arg_Value.substring(arg_Value.indexOf("\"") + 1,
			// arg_Value.lastIndexOf("\"")));
			// } else {
			// ae.setValue(arg_Value);
			// }
			cInList.add(ae);
		}
		return cInList;
	}

	// 内嵌lfc出入参到lfc
	public List<ArgElement> getArgLfc(List<Arg> cInArgs) {
		List<ArgElement> cInList = new ArrayList<ArgElement>();
		for (Arg componentArg : cInArgs) {
			ArgElement ae = new ArgElement();
			ae.setName(componentArg.getKey());
			ae.setDescription(componentArg.getName());
			ae.setValue(componentArg.getArg().replaceAll("\"", "\\\\\"").replaceAll("\n", ""));
			// String arg_Value = componentArg.getArg();
			// if (arg_Value.indexOf("\"") != -1) {
			// ae.setValue(arg_Value.substring(arg_Value.indexOf("\"") + 1,
			// arg_Value.lastIndexOf("\"")));
			// } else {
			// ae.setValue(arg_Value);
			// }
			cInList.add(ae);
		}
		return cInList;
	}

	// Node的出入参
	protected List<Arg> getArgs(Element parent, String typePrefix) throws XmlParseException {
		// Component/Implementation/Node/InArgs
		Element argsElement = getDirectChildElement(parent, typePrefix + "Args");

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
			arg.setType(getChildElementText(argElement, "Type"));
			// Component/Implementation/Node/InArgs/Arg/Arg
			arg.setArg(getChildElementText(argElement, "Arg"));
			args.add(arg);
		}
		return args;
	}

	// 设置geometry
	public void setGeometry(Geometry geometry) {
		geometry.setX(Integer.parseInt(location.substring(0, location.lastIndexOf(","))));
		geometry.setY(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1)));
		geometry.setWidth(Integer.parseInt(size.substring(0, size.lastIndexOf(","))));
		geometry.setHeight(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1)));
	}

	@Override
	protected String[] getCptName(String paramString) {
		return null;
	}

}
