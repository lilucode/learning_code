package cn.com.agree.ab.transfer.afa.parser;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.com.agree.ab.transfer.afa.model.BStepModel;
import cn.com.agree.ab.transfer.afa.model.FCModel;
import cn.com.agree.ab.transfer.afa.model.NStepModel;
import cn.com.agree.ab.transfer.afa.model.StepModel;


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
	
}
