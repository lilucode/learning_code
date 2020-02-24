package cn.com.agree.ab.transfer.afa.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.agree.ab.transfer.csd.Ade;

public class AdeXmlParser extends AbstractParser<Ade> {

	@Override
	protected Ade parse(Document doc, String paramString) throws XmlParseException {
		Element adeElement = (Element) doc.getElementsByTagName("Ade").item(0);
		Ade ade=new Ade();
		ade.setCaption(adeElement.getAttribute("caption"));
		ade.setDescription(adeElement.getAttribute("description"));
		ade.setExtend(adeElement.getAttribute("extends"));
		return ade;
	}

}
