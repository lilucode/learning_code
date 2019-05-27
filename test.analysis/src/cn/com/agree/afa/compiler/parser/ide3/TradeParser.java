package cn.com.agree.afa.compiler.parser.ide3;

import cn.com.agree.afa.compiler.model.BStepModel;
import cn.com.agree.afa.compiler.model.NStepModel;
import cn.com.agree.afa.compiler.model.NoopStepModel;
import cn.com.agree.afa.compiler.model.StepModel;
import cn.com.agree.afa.compiler.model.TradeModel;
import cn.com.agree.afa.compiler.parser.XmlParseException;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//解析fc转换后的src文件
public class TradeParser extends AbstractParser<TradeModel> {
	protected TradeModel parse(Document doc, String outputPath) throws XmlParseException {
		TradeModel tradeModel = new TradeModel();
		Context.getContext().setOutsideTradeModel(tradeModel);
		NodeList segmentList = doc.getElementsByTagName("Segment");
		if (segmentList.getLength() < 1) {
			throw new XmlParseException("Xml结构非法，必须存在一个Segement节点");
		}
		tradeModel.setOutputPath(outputPath);
		Context.getContext().setOutputPath(outputPath);
		Element segment = (Element) segmentList.item(0);
		String appCode = getChildElementText(segment, "Name");
		tradeModel.setAppCode(appCode);
		Context.getContext().setAppCode(appCode);
		tradeModel.setAppDesc(getChildElementText(segment, "Desp"));
		Element tfds = getDirectChildElement(segment, "TFDS");
		Element trade = getDirectChildElement(tfds, "Trade");
		String tradeCode = getChildElementText(trade, "Name");
		tradeModel.setTradeCode(tradeCode);
		Context.getContext().setTradeCode(tradeCode);
		tradeModel.setTradeDesc(getChildElementText(trade, "Desp"));
		tradeModel.setAuthor(getChildElementText(trade, "Author"));
		tradeModel.setCreationDate(getChildElementText(trade, "CreateDate"));
		tradeModel.setModificationDate(getChildElementText(trade, "ModifyDate"));
		Element aspectUsed = getDirectChildElement(trade, "AspectUsed");
		if (aspectUsed != null) {
			tradeModel.setAspectUsed(aspectUsed.getTextContent());
		}
		Element executeOnWGStartup = getDirectChildElement(trade, "ExecuteOnWGStartup");
		if (executeOnWGStartup != null) {
			tradeModel.setExecuteOnWGStartup("1".equals(executeOnWGStartup.getTextContent()));
		}
		Element executeOnServiceStartup = getDirectChildElement(trade, "ExecuteOnServiceStartup");
		if (executeOnServiceStartup != null) {
			tradeModel.setExecuteOnServiceStartup("1".equals(executeOnServiceStartup.getTextContent()));
		}
		Element executeBeforeServiceDestroy = getDirectChildElement(trade, "ExecuteBeforeServiceDestroy");
		if (executeBeforeServiceDestroy != null) {
			tradeModel.setExecuteBeforeServiceDestroy("1".equals(executeBeforeServiceDestroy.getTextContent()));
		}

		Element parent = getDirectChildElement(trade, "ParentName");
		String parentName = null;
		if (parent != null)
			parentName = parent.getTextContent();
		else {
			parentName = "";
		}
		tradeModel.setParentName(parentName);
		Element debugMode = getDirectChildElement(trade, "DebugMode");
		if (debugMode != null) {
			tradeModel.setDebugMode("1".equals(debugMode.getTextContent()));
			Context.getContext().setDebugCompile("1".equals(debugMode.getTextContent()));
		}

		Element regulation = getDirectChildElement(trade, "Regulation");
		Element steps = getDirectChildElement(regulation, "Steps");
		List<Element> stepList = getDirectChildElements(steps, "Step");
		if (stepList.size() < 1) {
			throw new XmlParseException("缺少Step节点");
		}
		for (Element step : stepList) {
			int id = Integer.parseInt(getChildElementText(step, "Id"));
			try {
				StepModel stepModel = null;
				if (parentName.isEmpty())
					stepModel = getStepModel(step, false);
				else {
					stepModel = getStepModel(step, true);
				}
				stepModel.setId(id);
				stepModel.setParentTradeName(parentName);
				tradeModel.addStepModel(stepModel);
				if (stepModel.getInfoLogMode() != 910817) {
					tradeModel.setInfoLogMode(2);
					tradeModel.setInfoLogLevel(6);
				}
			} catch (Throwable e) {
				String newMessage = "StepId=" + id + ", " + e.getMessage();
				if ((e instanceof XmlParseException)) {
					XmlParseException ce = (XmlParseException) e;
					ce.setMessage(newMessage);
					throw ce;
				}
				throw new XmlParseException(newMessage, e);
			}
		}

		return tradeModel;
	}

	private StepModel getStepModel(Element step, boolean inheriting) throws XmlParseException {
		StepModel stepModel = null;
		if (inheriting) {
			Element hasOverride = getDirectChildElement(step, "HasOverride");
			if (hasOverride == null) {
				throw new XmlParseException("重写交易的Step节点要有HasOverride");
			}
			Element implementation = getDirectChildElement(step, "Implementation");
			if ((hasOverride.getTextContent().equals("false")) || (implementation == null)
					|| (getDirectChildElement(implementation, "Nodes") == null))
				stepModel = getNoopStepModel(step);
			else
				stepModel = getBOrNStep(step);
		} else {
			Element abstractFlag = getDirectChildElement(step, "IsAbstract");
			if ((abstractFlag != null) && (abstractFlag.getTextContent().equals("true")))
				stepModel = getNoopStepModel(step);
			else {
				stepModel = getBOrNStep(step);
			}
		}
		stepModel.setDesc(getChildElementText(step, "Desp"));
		stepModel.addNextStepId(1, Integer.parseInt(getChildElementText(step, "True")));
		stepModel.addNextStepId(0, Integer.parseInt(getChildElementText(step, "False")));

		Element customOuts = getDirectChildElement(step, "CustomOuts");
		if (customOuts != null) {
			List<Element> customOutList = getDirectChildElements(customOuts, "CustomOut");
			for (Element customOut : customOutList) {
				String[] customOutValue = customOut.getTextContent().split("_");
				if ((customOutValue != null) && (customOutValue.length >= 2)) {
					stepModel.addNextStepId(Integer.parseInt(customOutValue[0]), Integer.parseInt(customOutValue[1]));
				}
			}
		}

		Element skip = getDirectChildElement(step, "Skip");
		if (skip != null) {
			stepModel.setSkip("1".equals(getChildElementText(skip, "Enabled")));
			String branch = getChildElementText(skip, "Branch");
			if ((branch == null) || (branch.isEmpty()))
				stepModel.setDefaultStatus(1);
			else {
				stepModel.setDefaultStatus(Integer.parseInt(branch));
			}
		}
		return stepModel;
	}

	private StepModel getNStepModel(Element step) throws XmlParseException {
		NStepModel stepModel = new NStepModel();
		Element implementation = getDirectChildElement(step, "Implementation");
		stepModel.setNodeModels(getNodeModels(implementation));
		stepModel.setNoop(false);
		setPointInfoLogContext(stepModel, step);
		return stepModel;
	}

	private StepModel getBStepModel(Element step) throws XmlParseException {
		BStepModel stepModel = new BStepModel();
		stepModel.setCptName(getChildElementText(step, "RefImpl"));
		stepModel.setInputArgs(getArgs(step, "In"));
		stepModel.setOutputArgs(getArgs(step, "Out"));
		stepModel.setNoop(false);
		setPointInfoLogContext(stepModel, step);
		return stepModel;
	}

	private StepModel getNoopStepModel(Element step) throws XmlParseException {
		NoopStepModel stepModel = new NoopStepModel();
		stepModel.setNoop(true);
		setPointInfoLogContext(stepModel, step);
		return stepModel;
	}

	private void setPointInfoLogContext(StepModel step, Element stepElement) {
		Element infoLogLevelElement = getDirectChildElement(stepElement, "PointInfoLogLevel");
		if (infoLogLevelElement == null)
			step.setInfoLogLevel(2);
		else {
			step.setInfoLogLevel(Integer.valueOf(infoLogLevelElement.getTextContent()).intValue());
		}

		Element infoLogModeElemnt = getDirectChildElement(stepElement, "PointInfoLogMode");
		if (infoLogModeElemnt == null)
			step.setInfoLogMode(910817);
		else
			step.setInfoLogMode(Integer.valueOf(infoLogModeElemnt.getTextContent()).intValue());
	}

	private StepModel getBOrNStep(Element step) throws XmlParseException {
		int quote = Integer.parseInt(getChildElementText(step, "Quote"));
		if (quote == 0) {
			return getNStepModel(step);
		}
		return getBStepModel(step);
	}

	protected String[] getCptName(String name) {
		String appCode = Context.getContext().getAppCode();
		String tradeCode = Context.getContext().getTradeCode() + "_" + name;
		String[] result = { appCode, tradeCode };
		return result;
	}
}
