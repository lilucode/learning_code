package cn.com.agree.ab.transfer.afa.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.afa.model.Constant;
import cn.com.agree.ab.transfer.runtime.lfc.ArgComponent;
import cn.com.agree.ab.transfer.runtime.lfc.ArgElement;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentElement;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentException;
import cn.com.agree.ab.transfer.runtime.lfc.ComponentOut;
import cn.com.agree.ab.transfer.runtime.lfc.Endstep;
import cn.com.agree.ab.transfer.runtime.lfc.Geometry;
import cn.com.agree.ab.transfer.runtime.lfc.LfcComponentElement;
import cn.com.agree.ab.transfer.runtime.lfc.LogicFlowControl;

public class LfcXmlParser extends AbstractParser<LogicFlowControl> {

	@Override
	protected LogicFlowControl parse(Document doc, String paramString) throws XmlParseException {
		Element lfcElement = (Element) doc.getElementsByTagName("LogicFlowControl").item(0);
		// xml文件第一个<LogicFlowControl>标签转换
		LogicFlowControl lfc = getLfCMode(lfcElement);
		lfc.setCaption(lfcElement.getAttribute("caption"));
		lfc.setStart(Integer.parseInt(lfcElement.getAttribute("start")));
		String location = lfcElement.getAttribute("xy_location");
		if (location != null && location != "") {
			Geometry geometry = new Geometry();
			geometry.setX(Integer.parseInt(location.substring(0, location.indexOf(','))));
			geometry.setY(Integer.parseInt(location.substring(location.indexOf(',') + 1)));
			// 没有width，height
			lfc.setGeometry(geometry);
		}
		return lfc;
	}

	private LogicFlowControl getLfCMode(Element lfcElement) throws XmlParseException {
		LogicFlowControl lfc = new LogicFlowControl();
		//lfc本身出入参
		lfc.addInArgs(getOutsideArg(lfcElement,"InArgs"));
		lfc.addOutArgs(getOutsideArg(lfcElement, "OutArgs"));
		// <EndStep
		Element endStepElement = getDirectChildElement(lfcElement, "EndStep");
		Endstep endstep = new Endstep();
		endstep.setId(endStepElement.getAttribute("id"));
		endstep.setGeometry(getGeometry(endStepElement));
		List<Element> inElement = getDirectChildElements(endStepElement, "In");
		endstep.setIn(new ArrayList<Map<String, String>>());
		for (Element element : inElement) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", element.getAttribute("id"));
			map.put("caption", element.getAttribute("caption"));
			map.put("name", element.getAttribute("name"));
			endstep.getIn().add(map);
		}
		lfc.setEndstep(endstep);
		// <End 结束组件的出口可能有多个
		List<Element> endElement = getDirectChildElements(lfcElement, "End");
		for (Element element : endElement) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", element.getAttribute("id"));
			map.put("caption", element.getAttribute("caption"));
			map.put("name", element.getAttribute("name"));
			lfc.setEnd(map);
		}
		// <Component
		List<Element> componentElement = getDirectChildElements(lfcElement, "Component");
		for (Element componentXml : componentElement) {
			ComponentElement componentJson = new ComponentElement();
			// 转换技术组件 <Entry
			componentJson.setCaption(componentXml.getAttribute("caption"));
			componentJson.setId(componentXml.getAttribute("id"));
			componentJson.setName(componentXml.getAttribute("name"));
			componentJson.setShowId(componentXml.getAttribute("showId"));
			// 获取位置大小
			componentJson.setGeometry(getGeometry(componentXml));
			// 获取入参 <InArg
			componentJson.addInArgs(getInsideArg(componentXml, "InArg"));
			// 获取出参 <OutArg
			componentJson.addOutArgs(getInsideArg(componentXml, "OutArg"));
			// <Out List
			getInsideOut(componentJson,componentXml, "Out");
			// <Exception
			getInsideException(componentJson,componentXml, "Exception");

			lfc.addComponent(componentJson);
		}
		
		//<LFC 内嵌lfc
		List<Element> lfcXmlList = getDirectChildElements(lfcElement, "LFC");
		for (Element element : lfcXmlList) {
			LfcComponentElement lfcJson = new LfcComponentElement();
			lfcJson.setCaption(element.getAttribute("caption"));
			lfcJson.setId(element.getAttribute("id"));
			String path=element.getAttribute("path");
			if (path.startsWith("/bank")) {
				path = "/" + Constant.ProjectName + path;
			} else {
				path = "/" + Constant.ProjectName + "/servers" + path;
			}
			lfcJson.setLfcPath(path);
			lfcJson.setShowId(element.getAttribute("showId"));
			lfcJson.setGeometry(getGeometry(element));
			
			lfcJson.addInArgs(getInsideArg(element, "InArg"));
			lfcJson.addOutArgs(getInsideArg(element, "OutArg"));
			getInsideOut(lfcJson,element, "Out");
			getInsideException(lfcJson,element, "Exception");
			
			lfc.addLfc(lfcJson);
		}

		return lfc;

	}
	
	private Geometry getGeometry(Element componentXml) {
		List<Element> entryList = getDirectChildElements(componentXml, "Entry");
		Geometry geometry = new Geometry();
		for (Element entryElement : entryList) {
			if ("xy_location".equals(entryElement.getAttribute("id"))) {
				String value = entryElement.getAttribute("value");
				geometry.setX(Integer.parseInt(value.substring(0, value.indexOf(','))));
				geometry.setY(Integer.parseInt(value.substring(value.indexOf(',') + 1)));
			} else if ("xy_size".equals(entryElement.getAttribute("id"))) {
				String value = entryElement.getAttribute("value");
				geometry.setWidth(Integer.parseInt(value.substring(0, value.indexOf(','))));
				geometry.setHeight(Integer.parseInt(value.substring(value.indexOf(',') + 1)));
			}
		}
		return geometry;
	}

	private List<ArgElement> getOutsideArg(Element e,String str) {
		Element argsElement = getDirectChildElement(e, str);
		List<Element> argElementList = getDirectChildElements(argsElement, "Arg");
		List<ArgElement> ae=new ArrayList<ArgElement>();
		for (Element element : argElementList) {
			ArgElement argElement=new ArgElement();
			argElement.setDescription(element.getAttribute("description"));
			argElement.setExample(element.getAttribute("example"));
			argElement.setName(element.getAttribute("name"));
			argElement.setType(element.getAttribute("type"));
			ae.add(argElement);
		}
		return ae;
	}
	
	private List<ArgElement> getInsideArg(Element element,String str){
		List<Element> argList = getDirectChildElements(element, str);
		List<ArgElement> argJsonList = new ArrayList<ArgElement>();
		for (Element inArgElement : argList) {
			ArgComponent argComponent = new ArgComponent();
			argComponent.setCaption(inArgElement.getAttribute("caption"));
			argComponent.setName(inArgElement.getAttribute("name"));
			argComponent.setValue(inArgElement.getTextContent());
			argJsonList.add(argComponent);
		}
		return argJsonList;
	}
	
	private void getInsideOut(ComponentElement component,Element element,String str) {
		List<Element> outList = getDirectChildElements(element, "Out");
		for (Element outElement : outList) {
			ComponentOut componentOut = new ComponentOut();
			componentOut.setCaption(outElement.getAttribute("caption"));
			componentOut.setId(outElement.getAttribute("id"));
			componentOut.setName(outElement.getAttribute("name"));
			componentOut.setNext(outElement.getAttribute("next"));
			component.addOut(componentOut);
		}
	}
	
	private void getInsideException(ComponentElement component,Element element,String str) {
		Element exceptionElement = getDirectChildElement(element, str);
		ComponentException exception = new ComponentException();
		exception.setName(exceptionElement.getAttribute("name"));
		exception.setNext(exceptionElement.getAttribute("next"));
		component.setException(exception);
	}
}
