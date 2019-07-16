package cn.com.agree.ab.transfer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import cn.com.agree.ab.transfer.afa.model.BCModel;
import cn.com.agree.ab.transfer.afa.model.BStepModel;
import cn.com.agree.ab.transfer.afa.model.FCModel;
import cn.com.agree.ab.transfer.afa.model.NStepModel;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.afa.model.StepModel;
import cn.com.agree.ab.transfer.main.FcToLfcMain;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentOut;
import cn.com.agree.ab.transfer.runtime.lfc.Geometry;
import cn.com.agree.ab.transfer.runtime.lfc.LfcComponentElement;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;


public class FcToLfcUtil extends LfcUtil{

	public String fileName = "";

	private List<String> allName = new ArrayList<String>();

	public FcToLfcUtil(String fileName) {
		this.fileName = fileName;
	}

	public LogicFlowControl parse(FCModel fcModel) {
		LogicFlowControl lfc = new LogicFlowControl();
		// 开始组件
		lfc.setGeometry(new Geometry(50, 50, 100, 50));
		// 结束组件
		lfc.getEndstep().setGeometry(new Geometry(200, 500, 100, 50));
		lfc.setEnd("1001");
		List<StepModel> stepModels = fcModel.getStepModels();
		if (stepModels.size() == 0) {
			return null;
		}
		lfc.setStart(Integer.parseInt(getStartId(stepModels)));

		for (StepModel stepModel : stepModels) {
			if (stepModel.getType() == 4) {
				// bcpt作为内嵌lfc
				LfcComponentElement lce = new LfcComponentElement();
				String refImpl = ((BStepModel) stepModel).getCptName();
				refImpl = refImpl.substring(refImpl.lastIndexOf(".") + 1);
				lce.setName(refImpl);
				// 内嵌bcpt路径
				String filePath = stepModel.getFilePath();
				filePath = filePath.substring(0, filePath.lastIndexOf("/"))
						.replaceAll("/functionModule/businessComponent", "") + "/";
				lce.setLfcPath("/" + FcToLfcMain.projectName + "/business" + filePath + refImpl + ".lfc");

				// 入参出参
				lce.addInArgs(getArgComponent(((BStepModel) stepModel).getInputArgs()));
				lce.addOutArgs(getArgComponent(((BStepModel) stepModel).getOutputArgs()));
				lce.setCaption(stepModel.getDesc());
				lfc.addLfc(setLfcCommon(lce, stepModel));
			} else if (stepModel.getType() == 5) {
				// 生成一个新的lfc
				LfcComponentElement lce = new LfcComponentElement();
				String refImpl = ((NStepModel) stepModel).getRefImpl();
				refImpl = checkName(refImpl);
				lce.setName(refImpl);
				lce.setCaption(refImpl);
				lce.setLfcPath("/" + FcToLfcMain.projectName + "/business/"
						+ fileName.substring(fileName.lastIndexOf("\\") + 1) + "/" + refImpl + ".lfc");
				lfc.addLfc(setLfcCommon(lce, stepModel));

				List<NodeModel> nodeList = ((NStepModel) stepModel).getNodeModels();
				BCModel bcModel = new BCModel();
				bcModel.setName(refImpl);
				bcModel.setNodeModels(nodeList);
				LogicFlowControl newLfc = new LogicFlowControl();
				BcptToLfcUtil btl = new BcptToLfcUtil();
				newLfc = btl.parserNode(bcModel, newLfc);
				// System.out.println(JSON.toJSONString(newLfc));
				File file = new File(fileName + "/" + refImpl + ".lfc");
				if (!file.exists()) {
					File uploadDir = file.getParentFile();
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try (FileOutputStream fos = new FileOutputStream(file);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
					osw.write(FormatUtil.formatJson(JSON.toJSONString(newLfc)));
					osw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lfc;
	}

	public String checkName(String name) {
		for (int i = 1; i < Integer.MAX_VALUE && allName.contains(name); i++) {
			name = name + '(' + i + ')';
		}
		allName.add(name);
		return name;
	}

	public LfcComponentElement setLfcCommon(LfcComponentElement lce, StepModel stepModel) {
		lce.setId(stepModel.getId());
		lce.setShowId(stepModel.getId());
		setGeometry(lce.getGeometry(), stepModel);
		// 出口
		ComponentOut out = new ComponentOut();
		out.setCaption("正常出口");
		out.setName("正常出口");
		if (!stepModel.getTrueId().equals("0")) {
			out.setNext(stepModel.getTrueId());
		} else if (!stepModel.getFalseId().equals("0")) {
			lce.getException().setNext(stepModel.getFalseId());
		} else {
			out.setNext("1001");
		}
		lce.addOut(out);
		return lce;
	}

	public void setGeometry(Geometry geometry, StepModel stepModel) {
		String location = stepModel.getLocation();
		String size = stepModel.getSize();
		geometry.setX(Math.abs(Integer.parseInt(location.substring(0, location.lastIndexOf(",")))));
		geometry.setY(Math.abs(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1))));
		geometry.setWidth(Math.abs(Integer.parseInt(size.substring(0, size.lastIndexOf(",")))));
		geometry.setHeight(Math.abs(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1))));
	}

	/**
	 * 获取开始id
	 * 
	 * @param stepModels
	 * @return
	 */
	public String getStartId(List<StepModel> stepModels) {
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		for (StepModel stepModel : stepModels) {
			set.add(stepModel.getId());
			set1.add(stepModel.getTrueId());
			set1.add(stepModel.getFalseId());
		}
		set1.remove("0");
		if (set.containsAll(set1)) {
			set.removeAll(set1);
		}
		String str = "";
		for (String string : set) {
			str = string;
			break;
		}
		return str;
	}

}
