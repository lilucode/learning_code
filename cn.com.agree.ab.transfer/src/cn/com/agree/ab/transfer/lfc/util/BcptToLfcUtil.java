package cn.com.agree.ab.transfer.lfc.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.agree.ab.transfer.afa.model.Arg;
import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.model.ComponentArg;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.afa.model.TerminalsMode;
import cn.com.agree.ab.transfer.main.BcptToLfcMain;
import cn.com.agree.ab.transfer.runtime.lfc.ArgComponent;
import cn.com.agree.ab.transfer.runtime.lfc.ArgElement;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentElement;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentOut;
import cn.com.agree.ab.transfer.runtime.lfc.Geometry;
import cn.com.agree.ab.transfer.runtime.lfc.LfcComponentElement;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;
import cn.com.agree.ab.transfer.runtime.lfc.LogicletComponentElement;
import cn.com.agree.ab.transfer.value.jj.REQParser;


public class BcptToLfcUtil {

	/**
	 * bcpt中正常结束Node，可能存在多个
	 */
	private List<String> end_id = new ArrayList<String>();

	/**
	 * bcpt中异常结束Node，可能存在多个
	 */
	private List<String> exception_id = new ArrayList<String>();

	/**
	 * bcpt中中转节点，可能存在多个
	 */
	private Map<String, String> change_id = new ConcurrentHashMap<String, String>();
	
	/**
	 * id对应node节点
	 */
	private Map<String, NodeModel> NodeMap = new ConcurrentHashMap<String, NodeModel>();
	
	/**
	 * 默认逻辑错误委托需要删除的组件id
	 */
	private Set<String> default_error = new HashSet<String>();

	private String default_id;
	private String default_next_id;
	private String bcptName;

	/**
	 * bcpt入参
	 */
	private List<String> inArgs = new ArrayList<String>();
	/**
	 * bcpt出参
	 */
	private List<String> outArgs = new ArrayList<String>();

	public BcptToLfcUtil() {
		
	}
	
	public BcptToLfcUtil(String bcptName) {
		this.bcptName = bcptName;
	}

	// 从传入的BCModel获取参数传入lfc
	public LogicFlowControl parse(BCModel bcModel) {
		LogicFlowControl lfc = new LogicFlowControl();
		lfc.getFileDescription().setAuthor(bcModel.getAuth());
		lfc.getFileDescription().setFunction(bcModel.getName());
		lfc.getFileDescription().setRemark(bcModel.getDesc());

		lfc.addInArgs(getArgElement(bcModel.getInputArgs()));
		lfc.addOutArgs(getArgElement(bcModel.getOutputArgs()));

		for (ComponentArg arg : bcModel.getInputArgs()) {
			inArgs.add(arg.getKey());
		}
		for (ComponentArg arg : bcModel.getOutputArgs()) {
			outArgs.add(arg.getKey());
		}

		return parserNode(bcModel, lfc);

	}

	public LogicFlowControl parserNode(BCModel bcModel,LogicFlowControl lfc) {
		NodeMap = getAllNodeId(bcModel);
		getSpecialNodeId(bcModel);
		for (NodeModel nodeModel : bcModel.getNodeModels()) {

			if (default_error.contains(nodeModel.getIdString())) {
				continue;
			}
			int type = nodeModel.getType();
			if (type == 11) {

				LogicletComponentElement ce = new LogicletComponentElement();
				lfc.addComponent(setLfcAndComponent(ce, nodeModel));
			} else if (type == 7 || type == 12) {

				LfcComponentElement lce = new LfcComponentElement();
				lce.setMappingPath("/" + bcModel.getName() + ".lfc");
				String target = nodeModel.getCptName().substring(nodeModel.getCptName().lastIndexOf(".") + 1);
				// 内嵌的地址
				if (type == 7) {
					try {
						String filePath = nodeModel.getFilePath();
						filePath = filePath.substring(0, filePath.lastIndexOf("/"))
								.replaceAll("/functionModule/businessComponent", "") + "/";
						// lce.setLfcPath("/demo-s/business" + filePath + target + ".lfc");
						lce.setLfcPath("/" + BcptToLfcMain.projectName + "/business" + filePath + target + ".lfc");
					} catch (Exception e) {

						System.out.println(bcptName + ":" + bcModel.getName() + "包含的业务组件源文件已被删除");
						lce.setLfcPath("业务组件源文件已被删除");
					}
				} else {
					String dir = "";
					for (Arg arg : nodeModel.getInputArgs()) {
						if (arg.getName().equals("服务代码")) {
							dir = arg.getArg().replaceAll("\"", "");
						}
					}
					lce.setLfcPath(
							"/" + BcptToLfcMain.projectName + "/business/" + dir + "/" + nodeModel.getName() + ".lfc");
				}
				lfc.addLfc(setLfcAndComponent(lce, nodeModel));
			} else if (type == 2) {

				// 开始组件
				for (TerminalsMode tm : nodeModel.getTerminals()) {
					String targetId = "";
					targetId = tm.getTargetNodeId();
					if (targetId.equals(default_id)) {
						targetId = default_next_id;
					} else if (!change_id.isEmpty() && change_id.keySet().contains(targetId)) {
						targetId = change_id.get(targetId);
					}
					// 得到开始id
					lfc.setStart(Integer.valueOf(targetId));
				}
				setGeometry(lfc.getGeometry(), nodeModel);
			} else if (type == 3) {

				// 结束组件
				if (lfc.getEnd().size() == 0) {
					lfc.setEnd("1001");
					setGeometry(lfc.getEndstep().getGeometry(), nodeModel);
				}
			}
		}
		return lfc;
	}
	
	/**
	 * 存储特殊id
	 * 
	 * @param bcModel
	 */
	public void getSpecialNodeId(BCModel bcModel) {
		for (NodeModel nodeModel : bcModel.getNodeModels()) {
			int type = nodeModel.getType();
			if (type == 3) {
				// 正常结束
				end_id.add(nodeModel.getIdString());
			} else if (type == 4) {
				// 异常结束
				exception_id.add(nodeModel.getIdString());
			} else if (type == 10) {
				// 中转节点
				change_id.put(nodeModel.getIdString(), getNextId(nodeModel, "锚点一"));
			} else if (type == 6) {
				// 默认逻辑错误委托
				default_id = nodeModel.getIdString();
				default_next_id = getNextId(nodeModel, "成功");
				default_error = getNextSetId(getNextId(nodeModel, "失败"));
			}
		}
	}

	/**
	 * 存储所有node对应id节点
	 */
	public Map<String, NodeModel> getAllNodeId(BCModel bcModel) {
		Map<String, NodeModel> nodeMap = new ConcurrentHashMap<String, NodeModel>();
		for (NodeModel nodeModel : bcModel.getNodeModels()) {
			nodeMap.put(nodeModel.getIdString(), nodeModel);
		}
		return nodeMap;
	}

	/**
	 * 获取该节点已经后续所有节点id
	 */
	public Set<String> getNextSetId(String id) {
		Set<String> idSet = new HashSet<String>();
		idSet.add(id);
		NodeModel nodeModel = NodeMap.get(id);
		List<TerminalsMode> terminals = nodeModel.getTerminals();
		if (terminals.size() != 0) {
			for (TerminalsMode terminalsMode : terminals) {
				if (terminalsMode.getTargetNodeId() != null) {
					idSet.addAll(getNextSetId(terminalsMode.getTargetNodeId()));
				}
			}
		}
		return idSet;
	}
	
	/**
	 * 获取指定Node的具体出口
	 * 
	 * @param nodeModel
	 * @param terminalDesc
	 * @return
	 */
	public String getNextId(NodeModel nodeModel, String terminalDesc) {
		List<TerminalsMode> terminals = nodeModel.getTerminals();
		if (terminals.size() != 0) {
			for (TerminalsMode terminalsMode : terminals) {
				if (terminalDesc.equals(terminalsMode.getTerminalDesc())) {
					return terminalsMode.getTargetNodeId();
				}
			}
		}
		return null;
	}

	/**
	 * 内嵌lfc与技术组件信息
	 * 
	 * @param ce
	 * @param node
	 */
	public ComponentElement setLfcAndComponent(ComponentElement ce, NodeModel nodeModel) {
		ce.addInArgs(getArgComponent(nodeModel.getInputArgs()));
		ce.addOutArgs(getArgComponent(nodeModel.getOutputArgs()));

		ce.setId(nodeModel.getIdString());
		ce.setShowId(nodeModel.getIdString());
		// 技术组件显示文字
		ce.setCaption(nodeModel.getDesc());
		// 设置技术组件name
		String target = nodeModel.getCptName();
		String target_name = target.substring(target.lastIndexOf(".") + 1) + "Logiclet";
		ce.setName(target_name.substring(0, 1).toUpperCase() + target_name.substring(1));
		setGeometry(ce.getGeometry(), nodeModel);

		for (TerminalsMode terminalsMode : nodeModel.getTerminals()) {

			if (terminalsMode.getTerminalDesc().equals("失败")) {

				String targetId = terminalsMode.getTargetNodeId();
				if (targetId != null) {

					if (end_id != null && end_id.contains(targetId)) {
						ce.getException().setNext("1001");
					} else if (exception_id != null && exception_id.contains(targetId)) {
						ce.getException().setNext("1000");
					} else if (!change_id.isEmpty() && change_id.keySet().contains(targetId)) {
						ce.getException().setNext(change_id.get(targetId));
					} else {
						ce.getException().setNext(targetId);
					}
				}
			} else {

				ComponentOut out = new ComponentOut();
				out.setCaption(terminalsMode.getTerminalDesc());
				out.setName(terminalsMode.getTerminalName());
				String targetId = terminalsMode.getTargetNodeId();
				if (targetId != null) {

					if (end_id != null && end_id.contains(targetId)) {
						out.setNext("1001");
					} else if (exception_id != null && exception_id.contains(targetId)) {
						out.setNext("1000");
					} else if (!change_id.isEmpty() && change_id.keySet().contains(targetId)) {
						out.setNext(change_id.get(targetId));
					} else {
						out.setNext(targetId);
					}
				}
				ce.addOut(out);
			}
		}
		return ce;
	}

	/**
	 * 设置geometry
	 * 
	 * @param geometry
	 * @param node
	 */
	public void setGeometry(Geometry geometry, NodeModel node) {
		String location = node.getLocation();
		String size = node.getSize();
		geometry.setX(Math.abs(Integer.parseInt(location.substring(0, location.lastIndexOf(",")))));
		geometry.setY(Math.abs(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1))));
		geometry.setWidth(Math.abs(Integer.parseInt(size.substring(0, size.lastIndexOf(",")))));
		geometry.setHeight(Math.abs(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1))));
	}

	/**
	 * lfc出入参
	 * 
	 * @param Args
	 * @return
	 */
	public List<ArgElement> getArgElement(List<ComponentArg> Args) {
		List<ArgElement> list = new ArrayList<ArgElement>();
		for (ComponentArg componentArg : Args) {
			ArgElement ae = new ArgElement();
			ae.setName(componentArg.getKey());
			ae.setExample(componentArg.getDefValue().replaceAll("\n", ""));
			ae.setDescription(componentArg.getDesp());
			list.add(ae);
		}
		return list;
	}

	/**
	 * 技术组件和内嵌lfc的出入参
	 * 
	 * @param cInArgs
	 * @return
	 */
	public List<ArgElement> getArgComponent(List<Arg> Args) {
		List<ArgElement> list = new ArrayList<ArgElement>();
		for (Arg componentArg : Args) {
			ArgComponent ae = new ArgComponent();
			ae.setName(componentArg.getKey());
			ae.setCaption(componentArg.getName());
			ae.setEditor(componentArg.getType());
			ae.setValue(componentArg.getArg().replaceAll("\n", ""));
			REQParser parser = new REQParser(ae.getValue());
			parser.addAllInArg(inArgs);
			parser.addAllOutArg(outArgs);
			try {
				String result = (String) parser.parse();
				ae.setValue(result);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("报错：" + ae.getValue());
			}
			list.add(ae);
		}
		return list;
	}
}
