package cn.com.agree.ab.transfer.afa.parser;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.afa.model.Constant;
import cn.com.agree.ab.transfer.csd.CSDRequest;
import cn.com.agree.ab.transfer.csd.CSDResponse;
import cn.com.agree.ab.transfer.csd.CloudServiceDefinition;
import cn.com.agree.ab.transfer.csd.ServiceMetadata;
import cn.com.agree.ab.transfer.runtime.lfc.Xm;

public class CsdXmlParser extends AbstractParser<CloudServiceDefinition> {

	@Override
	protected CloudServiceDefinition parse(Document doc, String paramString) throws XmlParseException {
		Element csdElement = (Element) doc.getElementsByTagName("CloudServiceDefinition").item(0);
		CloudServiceDefinition csd=getCsdMode(csdElement);
		csd.setCaption(csdElement.getAttribute("caption"));
		csd.setDescripsion(csdElement.getAttribute("description"));
		return csd;
	}
	
	private CloudServiceDefinition getCsdMode(Element csdElement) throws XmlParseException {
		CloudServiceDefinition csd=new CloudServiceDefinition();
		//<ServiceMetadata>
		Element serviceMetadataElement = getDirectChildElement(csdElement, "ServiceMetadata");
		ServiceMetadata serviceMetadata = new ServiceMetadata();
		serviceMetadata.setMethod(getChildElementText(serviceMetadataElement,"Method"));
		serviceMetadata.setImplement("/"+Constant.ProjectName+"/servers"+getChildElementText(serviceMetadataElement, "Implement"));
		csd.setServiceMetadata(serviceMetadata);
		
		//<Request
		Element requestElement = getDirectChildElement(csdElement, "Request");
		CSDRequest request = new CSDRequest();
		request.setContentType(requestElement.getAttribute("contentType"));
		Element inArgsElement = getDirectChildElement(requestElement, "InArgs");
		List<Element> argInElement = getDirectChildElements(inArgsElement, "Arg");
		for (Element element : argInElement) {
			String adeName = element.getAttribute("name");
			String str = "data://" + Constant.ProjectName + "/dataDictionary/" + adeName + ".ade";
			request.setBody(str);
			Constant.ArgAdeList.add(adeName);
		}
		Element bodyElement = getDirectChildElement(requestElement, "Body");
		String body = bodyElement.getAttribute("model");
		XmXmlParser xmXmlParser = new XmXmlParser();
		File reqFile = new File(Constant.TransferPath, body);
		if (reqFile.exists() && reqFile.isFile()) {
			Xm reqXm = xmXmlParser.parse(reqFile);
			List<String> reqDataBasket = reqXm.getDataBasket();
			for (String string : reqDataBasket) {
				request.setBody(string);
			}
			Constant.XmList.add(body);
		}
		csd.setRequest(request);

		//<Response
		Element responseElement = getDirectChildElement(csdElement, "Response");
		CSDResponse response = new CSDResponse();
		response.setContentType(responseElement.getAttribute("contentType"));
		Element outArgsElement = getDirectChildElement(responseElement, "OutArgs");
		List<Element> argOutElement = getDirectChildElements(outArgsElement, "Arg");
		for (Element element : argOutElement) {
			String adeName = element.getAttribute("name");
			String str = "data://" + Constant.ProjectName + "/dataDictionary/" + adeName + ".ade";
			response.setBody(str);
			Constant.ArgAdeList.add(adeName);
		}
		Element resultElement = getDirectChildElement(responseElement, "Result");
		String result = resultElement.getAttribute("model");
		File resFile = new File(Constant.TransferPath, result);
		if (resFile.exists() && resFile.isFile()) {
			Xm resXm = xmXmlParser.parse(resFile);
			List<String> resDataBasket = resXm.getDataBasket();
			for (String string : resDataBasket) {
				response.setBody(string);
			}
			Constant.XmList.add(result);
		}
		
		// 查重，Args参数和xm中参数是否有重复，重复的保留lfc路径，不进行转换
		long reqCount = request.getBody().stream().distinct().count();
		long resCount = response.getBody().stream().distinct().count();
		String implement = getChildElementText(serviceMetadataElement, "Implement");
		if (!implement.isEmpty()) {
			if (reqCount == request.getBody().size() || resCount == response.getBody().size()) {
				Constant.LfcList.add(implement);
			}else {
				Constant.RepeatLfcList.add(implement);
			}
		}
		csd.setResponse(response);
		return csd;
	}
}
