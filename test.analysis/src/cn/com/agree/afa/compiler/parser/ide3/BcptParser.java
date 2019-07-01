package cn.com.agree.afa.compiler.parser.ide3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.a5.runtime.lfc.ArgElement;
import cn.com.agree.ab.a5.runtime.lfc.ComponentOut;
import cn.com.agree.ab.a5.runtime.lfc.LfcComponentElement;
import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;
import cn.com.agree.ab.a5.runtime.lfc.LogicletComponentElement;
import cn.com.agree.afa.compiler.model.Arg;
import cn.com.agree.afa.compiler.model.BCModel;
import cn.com.agree.afa.compiler.model.ComponentArg;
import cn.com.agree.afa.compiler.model.NodeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;

public class BcptParser extends AbstractParser<BCModel> {

	private LogicFlowControl lfc = new LogicFlowControl();

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
		// Component/Auth
		// Component/RefImpl
		// Component/Desp
		String auth = getChildElementText(component, "Auth");
		String componentName = getChildElementText(component, "RefImpl");
		String desp = getChildElementText(component, "Desp");
		bcModel.setAuth(auth);
		bcModel.setName(componentName);
		bcModel.setDesc(desp);
		lfc.getFileDescription().setAuthor(auth);
		lfc.getFileDescription().setFunction(componentName);
		lfc.getFileDescription().setRemark(desp);

		// Component/InArgs
		List<ComponentArg> cInArgs = getComponentArgs(component, "In");
		List<ArgElement> cInList=new ArrayList<ArgElement>();
		for (ComponentArg componentArg : cInArgs) {
			ArgElement ae = new ArgElement();
			ae.setName(componentArg.getKey());
			ae.setType(componentArg.getDefValue());
			ae.setCaption(componentArg.getDesp());
			cInList.add(ae);
		}
		lfc.addInArg(cInList);
		bcModel.setInputArgs(cInArgs);
		// Component/OutArgs
		List<ComponentArg> cOutArgs = getComponentArgs(component, "Out");
		List<ArgElement> cOutList=new ArrayList<ArgElement>();
		for (ComponentArg componentArg : cOutArgs) {
			ArgElement ae = new ArgElement();
			ae.setName(componentArg.getKey());
			ae.setType(componentArg.getDefValue());
			ae.setCaption(componentArg.getDesp());
			cOutList.add(ae);
		}
		lfc.addOutArg(cOutList);
		bcModel.setOutputArgs(cOutArgs);

		Element implementation = getDirectChildElement(component, "Implementation");
		bcModel.setNodeModels(getNodeModels(implementation));
		return bcModel;
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

	protected Set<NodeModel> getNodeModels(Element impl) throws XmlParseException {
		List<Element> nodeList = getDirectChildElements(impl, "Node");
		return getNodeModels(nodeList);
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

	// 把ide右侧的node节点，转换成NodeModel
	private NodeModel getNodeModel(Element node) throws XmlParseException {
		NodeModel nodeModel = new NodeModel();
		// Component/Implementation/Node/Type
		int type = Integer.parseInt(getChildElementText(node, "Type"));
		// Component/Implementation/Node/Id
		String idString = getChildElementText(node, "Id");
		// Component/Implementation/Node/Desp
		String desc = getChildElementText(node, "Desp");
		int atIndex = desc.indexOf("@");
		if (atIndex >= 0) {
			desc = desc.substring(atIndex + 1);
		}
		// Component/Implementation/Node/Target
		String target = getChildElementText(node, "Target");
		nodeModel.setType(type);
		nodeModel.setIdString(idString);
		nodeModel.setDesc(desc);
		nodeModel.setCptName(target);

		Element constraint = getDirectChildElement(node, "Constraint");
		// Component/Implementation/Node/Constraint/Location
		String location = getChildElementText(constraint, "Location");
		// Component/Implementation/Node/Constraint/Size
		String size = getChildElementText(constraint, "Size");
		nodeModel.setLocation(location);
		nodeModel.setSize(size);

		
		if (type == 11) {
			LogicletComponentElement ce = new LogicletComponentElement();
			ce.setId(idString);
			ce.setShowId(idString);
			ce.setCaption(desc);
			ce.setName(target.substring(target.lastIndexOf(".") + 1));
			ce.getGeometry().setX(Integer.parseInt(location.substring(0, location.lastIndexOf(","))));
			ce.getGeometry().setY(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1)));
			ce.getGeometry().setWidth(Integer.parseInt(size.substring(0, size.lastIndexOf(","))));
			ce.getGeometry().setHeight(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1)));

			List<Arg> cInArgs = getArgs(node, "In");
			List<ArgElement> cInList=new ArrayList<ArgElement>();
			for (Arg componentArg : cInArgs) {
				ArgElement ae = new ArgElement();
				ae.setName(componentArg.getKey());
				ae.setCaption(componentArg.getName());
				ae.setType(componentArg.getType());
				ae.setValue(componentArg.getArg());
				cInList.add(ae);
			}
			ce.addInArg(cInList);
			nodeModel.setInputArgs(cInArgs);
			// Component/OutArgs
			List<Arg> cOutArgs = getArgs(node, "Out");
			List<ArgElement> cOutList=new ArrayList<ArgElement>();
			for (Arg componentArg : cOutArgs) {
				ArgElement ae = new ArgElement();
				ae.setName(componentArg.getKey());
				ae.setCaption(componentArg.getName());
				ae.setType(componentArg.getType());
				ae.setValue(componentArg.getArg());
				cOutList.add(ae);
			}
			ce.addOutArg(cOutList);
			nodeModel.setOutputArgs(cOutArgs);
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
							if(!terminalDesc.equals("失败")) {
								
								ComponentOut out=new ComponentOut();
								out.setCaption(terminalDesc);
								out.setName(terminalName);
								out.setNext(targetNodeId);
								ce.addOut(out);
							}else {
								ce.getException().put("name", "");
								ce.getException().put("next", targetNodeId);
							}
							break;
						}
					}
				}
			}
			
			lfc.addComponent(ce);
		} else if (type == 7 || type == 12) {
			LfcComponentElement lce = new LfcComponentElement();
			lce.setId(idString);
			lce.setShowId(idString);
			lce.setCaption(desc);
			lce.setName(target.substring(target.lastIndexOf(".") + 1));
			
			lce.getGeometry().setX(Integer.parseInt(location.substring(0, location.lastIndexOf(","))));
			lce.getGeometry().setY(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1)));
			lce.getGeometry().setWidth(Integer.parseInt(size.substring(0, size.lastIndexOf(","))));
			lce.getGeometry().setHeight(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1)));
			
			List<Arg> cInArgs = getArgs(node, "In");
			List<ArgElement> cInList=new ArrayList<ArgElement>();
			for (Arg componentArg : cInArgs) {
				ArgElement ae = new ArgElement();
				ae.setName(componentArg.getKey());
				ae.setCaption(componentArg.getName());
				ae.setType(componentArg.getType());
				ae.setValue(componentArg.getArg());
				cInList.add(ae);
			}
			lce.addInArg(cInList);
			nodeModel.setInputArgs(cInArgs);
			// Component/OutArgs
			List<Arg> cOutArgs = getArgs(node, "Out");
			List<ArgElement> cOutList=new ArrayList<ArgElement>();
			for (Arg componentArg : cOutArgs) {
				ArgElement ae = new ArgElement();
				ae.setName(componentArg.getKey());
				ae.setCaption(componentArg.getName());
				ae.setType(componentArg.getType());
				ae.setValue(componentArg.getArg());
				cOutList.add(ae);
			}
			lce.addOutArg(cOutList);
			nodeModel.setOutputArgs(cOutArgs);
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
							if(!terminalDesc.equals("失败")) {
								
								ComponentOut out=new ComponentOut();
								out.setCaption(terminalDesc);
								out.setName(terminalName);
								out.setNext(targetNodeId);
								lce.addOut(out);
							}else {
								lce.getException().put("name", "");
								lce.getException().put("next", targetNodeId);
							}
							break;
						}
					}
				}
			}
			
			lfc.addLfc(lce);
		}

		if (desc.equals("开始")) {
			// Component/Implementation/Node/SourceConnections/Connection/targetId
			String targetId = getChildElementText(
					getDirectChildElement(getDirectChildElement(node, "SourceConnections"), "Connection"), "targetId");
			nodeModel.setTargetId(targetId);
			lfc.setStart(targetId);
			lfc.getGeometry().setX(Integer.parseInt(location.substring(0, location.lastIndexOf(","))));
			lfc.getGeometry().setY(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1)));
			lfc.getGeometry().setWidth(Integer.parseInt(size.substring(0, size.lastIndexOf(","))));
			lfc.getGeometry().setHeight(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1)));
		} else if (desc.equals("正常结束")) {
			// Component/Implementation/Node/Id
			String endId = getChildElementText(node, "Id");
			nodeModel.setEndId(endId);
			lfc.setEnd(endId);
			lfc.getEndstep().getGeometry().setX(Integer.parseInt(location.substring(0, location.lastIndexOf(","))));
			lfc.getEndstep().getGeometry().setY(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1)));
			lfc.getEndstep().getGeometry().setWidth(Integer.parseInt(size.substring(0, size.lastIndexOf(","))));
			lfc.getEndstep().getGeometry().setHeight(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1)));
		}

		return nodeModel;
	}

	// 出入参
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
//			arg.setType(getChildElementText(argElement, "Type"));
			// Component/Implementation/Node/InArgs/Arg/Arg
			// arg.setArg(getChildElementText(argElement, "Arg"));
			args.add(arg);
		}
		return args;
	}

	@Override
	protected String[] getCptName(String paramString) {
		return null;
	}

}
