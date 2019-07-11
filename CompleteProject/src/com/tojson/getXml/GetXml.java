package com.tojson.getXml;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.tojson.pojo.Ade;
import com.tojson.pojo.Arg;
import com.tojson.pojo.Component;
import com.tojson.pojo.Csd;
import com.tojson.pojo.DataBasket;
import com.tojson.pojo.End;
import com.tojson.pojo.Endstep;
import com.tojson.pojo.Exception;
import com.tojson.pojo.FileDescription;
import com.tojson.pojo.Geometry;
import com.tojson.pojo.In;
import com.tojson.pojo.InArgs;
import com.tojson.pojo.InternalVars;
import com.tojson.pojo.JsonRootBean;
import com.tojson.pojo.Lfc;
import com.tojson.pojo.Out;
import com.tojson.pojo.OutArgs;
import com.tojson.util.FormatUtil;

//��xml�л�ȡ���ݴ���javaBean
public class GetXml {
	public JsonRootBean getXml(String fileString) throws DocumentException {
		JsonRootBean jsonRootBean = new JsonRootBean();
		// ��ȡ����JsonRootBean�е�id����ֵ
		SAXReader saxread = new SAXReader();
		File xmlFile = new File(fileString);
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name");
				if (name.getTextTrim().equals("��ʼ")) {
					Element sourceConnections = (Element) node.selectSingleNode("SourceConnections/Connection");
					Iterator<Element> sourceConnectionsIterator = sourceConnections.elementIterator();
					while (sourceConnectionsIterator.hasNext()) {
						Element temporary = sourceConnectionsIterator.next();
						if (temporary.getName().equals("targetId")) {
							jsonRootBean.setStart(temporary.getTextTrim());
						}

					}
				}

			}
		}
		// ��ȡ����JsonRootBean�е�caption����ֵ
		jsonRootBean.setCaption("");
		// ��ȡ����JsonRootBean�е�geometry����ֵ
		Geometry geometry = new Geometry();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name"); // ���houseMonitor�ڵ��µ�idֵ
				if (name.getTextTrim().equals("��ʼ")) {
					Element Constraint = (Element) node.selectSingleNode("Constraint");
					Iterator<Element> ConstraintIterator = Constraint.elementIterator();
					while (ConstraintIterator.hasNext()) {
						Element temporary = ConstraintIterator.next();
						if (temporary.getName().equals("Location")) {
							String locationString = temporary.getTextTrim();
							String[] strings = locationString.split(",");
							geometry.setX(Integer.valueOf(strings[0]));
							geometry.setY(Integer.valueOf(strings[1]));
						} else if (temporary.getName().equals("Size")) {
							String sizeString = temporary.getTextTrim();
							String[] strings = sizeString.split(",");
							geometry.setWidth(Integer.valueOf(strings[0]));
							geometry.setHeight(Integer.valueOf(strings[1]));
						}
					}
				}
			}
		}
		jsonRootBean.setGeometry(geometry);
		// ��ȡ����JsonRootBean�е�fileDescription����ֵ
		FileDescription fileDescription = new FileDescription();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element author = (Element) node.selectSingleNode("Auth");
				Element function = (Element) node.selectSingleNode("RefImpl");
				Element remark = (Element) node.selectSingleNode("Desp");
				fileDescription.setAuthor(author.getTextTrim());
				fileDescription.setFunction(function.getTextTrim());
				fileDescription.setRemark(remark.getTextTrim());
			}
		}
		jsonRootBean.setFileDescription(fileDescription);
		// ��ȡ����JsonRootBean�е�dataBasket����ֵ
		DataBasket dataBasket = new DataBasket();
		List<Ade> ades = new ArrayList<Ade>();
		for (int i = 0; i < 1; i++) {
			Ade ade = new Ade();
			ades.add(ade);
		}
		dataBasket.setAde(ades);
		jsonRootBean.setDataBasket(dataBasket);
		// ��ȡ����JsonRootBean�е�inArgs����ֵ
		InArgs inArgs = new InArgs();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/InArgs/Arg");
			List<Arg> args = new ArrayList<Arg>();
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Arg arg = new Arg();
				Element name = (Element) node.selectSingleNode("Key");
				Element type = (Element) node.selectSingleNode("DefValue");
				Element description = (Element) node.selectSingleNode("Desp");
				arg.setName(name.getTextTrim());
				arg.setType(type.getTextTrim());
				arg.setDescription(description.getTextTrim());
				arg.setExample("");
				arg.setValue("");
				args.add(arg);
			}
			inArgs.setArg(args);
		}
		jsonRootBean.setInArgs(inArgs);
		// ��ȡ����JsonRootBean�е�ouArgs����ֵ
		OutArgs outArgs = new OutArgs();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/OutArgs/Arg");
			List<Arg> args = new ArrayList<Arg>();
			for (int i = 0; i < nodeElements.size(); i++) {
				if (nodeElements.size() == 0) {
					Arg arg = new Arg();
					args.add(arg);
				} else {
					Arg arg = new Arg();
					Element node = nodeElements.get(i);
					Element name = (Element) node.selectSingleNode("Key");
					Element type = (Element) node.selectSingleNode("DefValue");
					Element description = (Element) node.selectSingleNode("Desp");
					// ���Ŀ���в����ڸñ�ǩ��Ĭ������Ϊ��
					if (name == null) {
						arg.setName("");
					} else {
						arg.setName(name.getTextTrim());
					}
					if (type == null) {
						arg.setType("");
					} else {
						arg.setType(type.getTextTrim());
					}
					if (description == null) {
						arg.setDescription("");
					} else {
						arg.setDescription(description.getTextTrim());
					}
					arg.setExample("");
					arg.setValue("");
					args.add(arg);
				}
			}
			outArgs.setArg(args);
		}
		jsonRootBean.setOutArgs(outArgs);
		// ��ȡ����JsonRootBean�е�internalVars����ֵ
		InternalVars internalVars = new InternalVars();
		List<Arg> args = new ArrayList<Arg>();
		Arg arg = new Arg();
		args.add(arg);
		internalVars.setArg(args);
		jsonRootBean.setInternalVars(internalVars);
		// ��ȡ����JsonRootBean�е�endstep����ֵ
		Endstep endstep = new Endstep();
		Geometry geometryEndstep = new Geometry();
		// ��ȡgeometryEndstep
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name"); // ���houseMonitor�ڵ��µ�idֵ
				if (name.getTextTrim().equals("��������")) {
					Element Constraint = (Element) node.selectSingleNode("Constraint");
					Iterator<Element> ConstraintIterator = Constraint.elementIterator();
					while (ConstraintIterator.hasNext()) {
						Element temporary = ConstraintIterator.next();
						if (temporary.getName().equals("Location")) {
							String locationString = temporary.getTextTrim();
							String[] strings = locationString.split(",");
							geometryEndstep.setX(Integer.valueOf(strings[0]));
							geometryEndstep.setY(Integer.valueOf(strings[1]));
						} else if (temporary.getName().equals("Size")) {
							String sizeString = temporary.getTextTrim();
							String[] strings = sizeString.split(",");
							geometryEndstep.setWidth(Integer.valueOf(strings[0]));
							geometryEndstep.setHeight(Integer.valueOf(strings[1]));
						}
					}
				}
			}
		}
		// ��ȡIn
		List<In> ins = new ArrayList<In>();
		In in = new In();
		in.setId("1");
		in.setCaption("��������");
		in.setName("��������");
		ins.add(in);
		endstep.setId("1000");
		endstep.setGeometry(geometryEndstep);
		endstep.setIn(ins);
		jsonRootBean.setEndstep(endstep);
		// ��ȡ����JsonRootBean�е�end����ֵ
		List<End> ends = new ArrayList<End>();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			End end = new End();
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name");
				if (name.getTextTrim().equals("��������")) {
					Element id = (Element) node.selectSingleNode("Id");
					end.setId(id.getTextTrim());
					end.setCaption("��������");
					end.setName("��������");
				}
			}
			ends.add(end);
		}
		jsonRootBean.setEnd(ends);
		// ��ȡ����JsonRootBean�е�component����ֵ
		List<Component> components = new ArrayList<Component>();
		// ��ȡcomponent
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element id = (Element) node.selectSingleNode("Id");
				Element caption = (Element) node.selectSingleNode("Name");
				Element name = (Element) node.selectSingleNode("Target");
				if (!(caption.getTextTrim().equals("��ʼ")) && !caption.getTextTrim().equals("��������")) {
					Component component = new Component();
					component.setId(id.getTextTrim());
					component.setCaption(caption.getTextTrim());
					component.setName(name.getTextTrim());
					component.setShowId(id.getTextTrim());
					// ��ȡgeomery
					Element Constraint = (Element) node.selectSingleNode("Constraint");
					Iterator<Element> ConstraintIterator = Constraint.elementIterator();
					while (ConstraintIterator.hasNext()) {
						Geometry geometryCompoent = new Geometry();
						Element temporary = ConstraintIterator.next();
						if (temporary.getName().equals("Location")) {
							String locationString = temporary.getTextTrim();
							String[] strings = locationString.split(",");
							geometryCompoent.setX(Integer.valueOf(strings[0]));
							geometryCompoent.setY(Integer.valueOf(strings[1]));
						} else if (temporary.getName().equals("Size")) {
							String sizeString = temporary.getTextTrim();
							String[] strings = sizeString.split(",");
							geometryCompoent.setWidth(Integer.valueOf(strings[0]));
							geometryCompoent.setHeight(Integer.valueOf(strings[1]));
						}
						component.setGeometry(geometryCompoent);
						// ��ȡInArgs
						InArgs inArgsComponent = new InArgs();
						List<Element> inArgsrgElements = node.selectNodes("InArgs/Arg");
						List<Arg> inargsComponent = new ArrayList<Arg>();
						for (int j = 0; j < inArgsrgElements.size(); j++) {
							Arg argComponent = new Arg();
							Element inArgNode = inArgsrgElements.get(j);
							Element inArgName = (Element) inArgNode.selectSingleNode("Key");
							Element inArgCaption = (Element) inArgNode.selectSingleNode("Name");
							Element inArgDescription = (Element) inArgNode.selectSingleNode("Type");
							Element inArgValue = (Element) inArgNode.selectSingleNode("Arg");
							if (inArgName == null) {
								argComponent.setName("");
							} else {
								argComponent.setName(inArgName.getTextTrim());
							}
							if (inArgCaption == null) {
								argComponent.setCaption("");
							} else {
								argComponent.setCaption(inArgCaption.getTextTrim());
							}
							if (inArgDescription == null) {
								argComponent.setDescription("");
							} else {
								argComponent.setDescription(inArgDescription.getTextTrim());
							}
							if (inArgValue == null) {
								argComponent.setValue("");
							} else {
								argComponent.setValue(inArgValue.getTextTrim());
							}
							argComponent.setContains("");
							argComponent.setEditor("");
							argComponent.setExample("");
							argComponent.setRequired("");
							argComponent.setType("");
							inargsComponent.add(argComponent);
						}
						inArgsComponent.setArg(inargsComponent);
						component.setInArgs(inArgsComponent);

						// ��ȡOutArgs
						OutArgs outArgsComponent = new OutArgs();
						List<Element> outArgElements = node.selectNodes("OutArgs/Arg");
						List<Arg> ouArgsComponent = new ArrayList<Arg>();
						for (int j = 0; j < outArgElements.size(); j++) {
							Arg argComponent = new Arg();
							Element outArgNode = outArgElements.get(j);
							Element outArgName = (Element) outArgNode.selectSingleNode("Key");
							Element outArgCaption = (Element) outArgNode.selectSingleNode("Name");
							Element outArgDescription = (Element) outArgNode.selectSingleNode("Type");
							Element outArgValue = (Element) outArgNode.selectSingleNode("Arg");
							if (outArgName == null) {
								argComponent.setName("");
							} else {
								argComponent.setName(outArgName.getTextTrim());
							}
							if (outArgCaption == null) {
								argComponent.setCaption("");
							} else {
								argComponent.setCaption(outArgCaption.getTextTrim());
							}
							if (outArgDescription == null) {
								argComponent.setDescription("");
							} else {
								argComponent.setDescription(outArgDescription.getTextTrim());
							}
							if (outArgValue == null) {
								argComponent.setValue("");
							} else {
								argComponent.setValue(outArgValue.getTextTrim());
							}
							argComponent.setContains("");
							argComponent.setEditor("");
							argComponent.setExample("");
							argComponent.setRequired("");
							argComponent.setType("");
							ouArgsComponent.add(argComponent);
						}
						component.setOutArgs(outArgsComponent);

						// ��ȡOut
						List<Out> outList = new ArrayList<Out>();
						List<Element> outElements = node.selectNodes("Terminals/Terminal");
						for (int j = 0; j < outElements.size(); j++) {

							Element outNode = outElements.get(j);
							Element outCaption = (Element) outNode.selectSingleNode("Desp");
							Element outName = (Element) outNode.selectSingleNode("Name");
							if (!outCaption.getTextTrim().equals("ʧ��")) {
								Out out = new Out();
								// �ж�next�������
								String nextString = null;
								List<Element> Connections = node.selectNodes("SourceConnections/Connection");
								for (int x = 0; x < Connections.size(); x++) {
									Element ConnectionNode = Connections.get(x);
									Element SourceTerminal = (Element) ConnectionNode
											.selectSingleNode("SourceTerminal");
									String sourceTerminalString = SourceTerminal.getTextTrim();
									if (sourceTerminalString.equals(outName.getTextTrim())) {
										Element targetId = (Element) ConnectionNode.selectSingleNode("targetId");
										nextString = targetId.getTextTrim();
									}
								}
								out.setCaption(outCaption.getTextTrim());
								out.setName(outName.getTextTrim());
								out.setId(caption.getTextTrim());
								out.setNext(nextString);
								outList.add(out);
							}
						}
						component.setOut(outList);
						// ��ȡexception
						Exception exception = new Exception();
						List<Element> exceptionElements = node.selectNodes("Terminals/Terminal");
						for (int j = 0; j < exceptionElements.size(); j++) {
							Element exceptionNode = exceptionElements.get(j);
							Element exceptionCaption = (Element) exceptionNode.selectSingleNode("Desp");
							Element exceptionName = (Element) exceptionNode.selectSingleNode("Name");
							if (exceptionCaption.getTextTrim().equals("ʧ��")) {
								// �ж�next�������
								String nextString = null;
								List<Element> Connections = node.selectNodes("SourceConnections/Connection");
								for (int x = 0; x < Connections.size(); x++) {
									Element ConnectionNode = Connections.get(x);
									Element SourceTerminal = (Element) ConnectionNode
											.selectSingleNode("SourceTerminal");
									String sourceTerminalString = SourceTerminal.getTextTrim();
									if (sourceTerminalString.equals(exceptionName.getTextTrim())) {
										Element targetId = (Element) ConnectionNode.selectSingleNode("targetId");
										nextString = targetId.getTextTrim();
									}
								}
								exception.setName(exceptionName.getTextTrim());
								exception.setNext(nextString);
							}
							component.setException(exception);
						}
					}
					components.add(component);
				}
			}
		}
		jsonRootBean.setComponent(components);
		// ��ȡ����JsonRootBean�е�lfc����ֵ
		List<Lfc> lfcs = new ArrayList<Lfc>();
		// ��ȡLfc
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// ��ȡXML�ļ�
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element id = (Element) node.selectSingleNode("Id");
				Element caption = (Element) node.selectSingleNode("Name");
				Element name = (Element) node.selectSingleNode("Target");
				Element type = (Element) node.selectSingleNode("Type");
				if (!(caption.getTextTrim().equals("��ʼ")) && !caption.getTextTrim().equals("��������")) {
					if (type.getTextTrim().equals("7") || type.getTextTrim().equals("12")) {
						Lfc lfc = new Lfc();
						lfc.setId(id.getTextTrim());
						lfc.setCaption(caption.getTextTrim());
						lfc.setName(name.getTextTrim());
						lfc.setShowId(id.getTextTrim());
						// ��ȡgeomery
						Element Constraint = (Element) node.selectSingleNode("Constraint");
						Iterator<Element> ConstraintIterator = Constraint.elementIterator();
						while (ConstraintIterator.hasNext()) {
							Geometry geometryLfc = new Geometry();
							Element temporary = ConstraintIterator.next();
							if (temporary.getName().equals("Location")) {
								String locationString = temporary.getTextTrim();
								String[] strings = locationString.split(",");
								geometryLfc.setX(Integer.valueOf(strings[0]));
								geometryLfc.setY(Integer.valueOf(strings[1]));
							} else if (temporary.getName().equals("Size")) {
								String sizeString = temporary.getTextTrim();
								String[] strings = sizeString.split(",");
								geometryLfc.setWidth(Integer.valueOf(strings[0]));
								geometryLfc.setHeight(Integer.valueOf(strings[1]));
							}
							lfc.setGeometry(geometryLfc);
							// ��ȡInArgs
							InArgs inArgsLfc = new InArgs();
							List<Element> inArgsrgElements = node.selectNodes("InArgs/Arg");
							List<Arg> inargsLfc = new ArrayList<Arg>();
							for (int j = 0; j < inArgsrgElements.size(); j++) {
								Arg argLfc = new Arg();
								Element inArgNode = inArgsrgElements.get(j);
								Element inArgName = (Element) inArgNode.selectSingleNode("Key");
								Element inArgCaption = (Element) inArgNode.selectSingleNode("Name");
								Element inArgDescription = (Element) inArgNode.selectSingleNode("Type");
								Element inArgValue = (Element) inArgNode.selectSingleNode("Arg");
								if (inArgName == null) {
									argLfc.setName("");
								} else {
									argLfc.setName(inArgName.getTextTrim());
								}
								if (inArgDescription == null) {
									argLfc.setDescription("");
								} else {
									argLfc.setDescription(inArgDescription.getTextTrim());
								}
								if (inArgValue == null) {
									argLfc.setValue("");
								} else {
									argLfc.setValue(inArgValue.getTextTrim());
								}
								argLfc.setExample("");
								argLfc.setType("");
								inargsLfc.add(argLfc);
							}
							inArgsLfc.setArg(inargsLfc);
							lfc.setInArgs(inArgsLfc);

							// ��ȡOutArgs
							OutArgs outArgsLfc = new OutArgs();
							List<Element> outArgElements = node.selectNodes("OutArgs/Arg");
							List<Arg> ouArgsLfc = new ArrayList<Arg>();
							for (int j = 0; j < outArgElements.size(); j++) {
								Arg argLfc = new Arg();
								Element outArgNode = outArgElements.get(j);
								Element outArgName = (Element) outArgNode.selectSingleNode("Key");
								Element outArgCaption = (Element) outArgNode.selectSingleNode("Name");
								Element outArgDescription = (Element) outArgNode.selectSingleNode("Type");
								Element outArgValue = (Element) outArgNode.selectSingleNode("Arg");
								if (outArgName == null) {
									argLfc.setName("");
								} else {
									argLfc.setName(outArgName.getTextTrim());
								}
								if (outArgDescription == null) {
									argLfc.setDescription("");
								} else {
									argLfc.setDescription(outArgDescription.getTextTrim());
								}
								if (outArgValue == null) {
									argLfc.setValue("");
								} else {
									argLfc.setValue(outArgValue.getTextTrim());
								}
								argLfc.setExample("");
								argLfc.setType("");
								ouArgsLfc.add(argLfc);
							}
							lfc.setOutArgs(outArgsLfc);

							// ��ȡOut
							List<Out> outList = new ArrayList<Out>();
							List<Element> outElements = node.selectNodes("Terminals/Terminal");
							for (int j = 0; j < outElements.size(); j++) {
								Element outNode = outElements.get(j);
								Element outCaption = (Element) outNode.selectSingleNode("Desp");
								Element outName = (Element) outNode.selectSingleNode("Name");
								if (!outCaption.getTextTrim().equals("ʧ��")) {
									Out out = new Out();
									// �ж�next�������
									String nextString = null;
									List<Element> Connections = node.selectNodes("SourceConnections/Connection");
									for (int x = 0; x < Connections.size(); x++) {
										Element ConnectionNode = Connections.get(x);
										Element SourceTerminal = (Element) ConnectionNode
												.selectSingleNode("SourceTerminal");
										String sourceTerminalString = SourceTerminal.getTextTrim();
										if (sourceTerminalString.equals(outName.getTextTrim())) {
											Element targetId = (Element) ConnectionNode.selectSingleNode("targetId");
											nextString = targetId.getTextTrim();
										}
									}
									out.setCaption(outCaption.getTextTrim());
									out.setName(outName.getTextTrim());
									out.setId(caption.getTextTrim());
									out.setNext(nextString);
									outList.add(out);
								}
							}
							lfc.setOut(outList);
							// ��ȡexception
							Exception exception = new Exception();
							List<Element> exceptionElements = node.selectNodes("Terminals/Terminal");
							for (int j = 0; j < exceptionElements.size(); j++) {
								Element exceptionNode = exceptionElements.get(j);
								Element exceptionCaption = (Element) exceptionNode.selectSingleNode("Desp");
								Element exceptionName = (Element) exceptionNode.selectSingleNode("Name");
								if (exceptionCaption.getTextTrim().equals("ʧ��")) {
									// �ж�next�������
									String nextString = null;
									List<Element> Connections = node.selectNodes("SourceConnections/Connection");
									for (int x = 0; x < Connections.size(); x++) {
										Element ConnectionNode = Connections.get(x);
										Element SourceTerminal = (Element) ConnectionNode
												.selectSingleNode("SourceTerminal");
										String sourceTerminalString = SourceTerminal.getTextTrim();
										if (sourceTerminalString.equals(exceptionName.getTextTrim())) {
											Element targetId = (Element) ConnectionNode.selectSingleNode("targetId");
											nextString = targetId.getTextTrim();
										}
									}
									exception.setName(exceptionName.getTextTrim());
									exception.setNext(nextString);
								}
								lfc.setException(exception);
							}
							// ����lfcPath
							if (type.getTextTrim().equals("7")) {
								Element path = (Element) node.selectSingleNode("FilePath");
								lfc.setLfcPath(path.getTextTrim().replace(".tcpt", ".lfc"));
							} else if (type.getTextTrim().equals("12")) {

							}
							// ����fileDescription
							FileDescription fileDescriptionLfc = new FileDescription();
							fileDescriptionLfc.setAuthor("");
							fileDescriptionLfc.setFunction("");
							fileDescriptionLfc.setRemark("");
							lfc.setFileDescription(fileDescriptionLfc);
							// ����mappings
							List<String> mappings = new ArrayList<String>();
							lfc.setMappings(mappings);
							// ����mappingPath
							String mappingPath = null;
							lfc.setMappingPath(mappingPath);
							// ����ades
							List<Ade> adesLfc = new ArrayList<Ade>();
							Ade adeLfc = new Ade();
							adesLfc.add(adeLfc);
							lfc.setAdes(adesLfc);
						}
						lfcs.add(lfc);
					}
				}
			}
		}
		jsonRootBean.setLfc(lfcs);

		// ����csd
		List<Csd> csds = new ArrayList<Csd>();
		Csd csd = new Csd();
		csds.add(csd);
		jsonRootBean.setCsd(csds);
		return jsonRootBean;
	}
}
