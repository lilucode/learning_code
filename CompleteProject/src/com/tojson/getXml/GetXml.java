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
import com.tojson.pojo.InArgs2;
import com.tojson.pojo.InOutArg;
import com.tojson.pojo.InternalVars;
import com.tojson.pojo.JsonRootBean;
import com.tojson.pojo.Lfc;
import com.tojson.pojo.Out;
import com.tojson.pojo.OutArgs;
import com.tojson.pojo.OutArgs2;
import com.tojson.util.FormatUtil;
import com.tojson.util.UpperCase;

//从xml中获取数据存入javaBean
public class GetXml {
	public JsonRootBean getXml(String fileString) throws DocumentException {
		JsonRootBean jsonRootBean = new JsonRootBean();
		// 获取设置JsonRootBean中的id属性值
		SAXReader saxread = new SAXReader();
		File xmlFile = new File(fileString);
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name");
				if (name.getTextTrim().equals("开始")) {
					Element sourceConnections = (Element) node.selectSingleNode("SourceConnections/Connection");
					Iterator<Element> sourceConnectionsIterator = sourceConnections.elementIterator();
					while (sourceConnectionsIterator.hasNext()) {
						Element temporary = sourceConnectionsIterator.next();
						if (temporary.getName().equals("targetId")) {
							jsonRootBean.setStart(Integer.valueOf(temporary.getTextTrim()));
						}

					}
				}

			}
		}
		// 获取设置JsonRootBean中的caption属性值
		jsonRootBean.setCaption("");
		// 获取设置JsonRootBean中的geometry属性值
		Geometry geometry = new Geometry();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name"); // 获得houseMonitor节点下的id值
				if (name.getTextTrim().equals("开始")) {
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
						//System.out.println(geometry);
					}
				}
			}
		}
		jsonRootBean.setGeometry(geometry);
		// 获取设置JsonRootBean中的fileDescription属性值
		FileDescription fileDescription = new FileDescription();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
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
		// 获取设置JsonRootBean中的dataBasket属性值
		DataBasket dataBasket = new DataBasket();
		List<Ade> ades = new ArrayList<Ade>();
		
		dataBasket.setAde(ades);
		jsonRootBean.setDataBasket(dataBasket);
		// 获取设置JsonRootBean中的inArgs属性值
		InArgs2 inArgs = new InArgs2();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/InArgs/Arg");
			List<InOutArg> args = new ArrayList<InOutArg>();
			for (int i = 0; i < nodeElements.size(); i++) {
				if (nodeElements.size() == 0) {
					InOutArg arg = new InOutArg();
					args.add(arg);
				} else {
					Element node = nodeElements.get(i);
					InOutArg arg = new InOutArg();
					Element name = (Element) node.selectSingleNode("Key");
					Element example = (Element) node.selectSingleNode("DefValue");
					Element description = (Element) node.selectSingleNode("Desp");
					arg.setName(name.getTextTrim());
					arg.setType("");
					arg.setDescription(description.getTextTrim());
					arg.setExample(example.getTextTrim());
					arg.setValue("");
					args.add(arg);
				}
			}
			inArgs.setInOutArgs(args);
		}
		jsonRootBean.setInArgs(inArgs);
		// 获取设置JsonRootBean中的ouArgs属性值
		OutArgs2 outArgs = new OutArgs2();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/OutArgs/Arg");
			List<InOutArg> args = new ArrayList<InOutArg>();
			for (int i = 0; i < nodeElements.size(); i++) {
				if (nodeElements.size() == 0) {
					InOutArg arg = new InOutArg();
					args.add(arg);
				} else {
					InOutArg arg = new InOutArg();
					Element node = nodeElements.get(i);
					Element name = (Element) node.selectSingleNode("Key");
					Element type = (Element) node.selectSingleNode("DefValue");
					Element description = (Element) node.selectSingleNode("Desp");
					// 如果目标中不存在该标签，默认设置为空
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
			outArgs.setInOutArgs(args);
		}
		jsonRootBean.setOutArgs(outArgs);
		// 获取设置JsonRootBean中的internalVars属性值
		InternalVars internalVars = new InternalVars();
		List<InOutArg> args = new ArrayList<InOutArg>();
		internalVars.setArg(args);
		jsonRootBean.setInternalVars(internalVars);
		// 获取设置JsonRootBean中的endstep属性值
		Endstep endstep = new Endstep();
		Geometry geometryEndstep = new Geometry();
		// 获取geometryEndstep
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name"); // 获得houseMonitor节点下的id值
				if (name.getTextTrim().equals("正常结束")) {
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
		// 获取In
		List<In> ins = new ArrayList<In>();
		In in = new In();
		in.setId("1");
		in.setCaption("正常出口");
		in.setName("正常出口");
		ins.add(in);
		endstep.setId("1000");
		endstep.setGeometry(geometryEndstep);
		endstep.setIn(ins);
		jsonRootBean.setEndstep(endstep);
		// 获取设置JsonRootBean中的end属性值
		List<End> ends = new ArrayList<End>();
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			End end = new End();
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element name = (Element) node.selectSingleNode("Name");
				if (name.getTextTrim().equals("正常结束")) {
					Element id = (Element) node.selectSingleNode("Id");
					end.setId(id.getTextTrim());
					end.setCaption("正常出口");
					end.setName("正常出口");
				}
			}
			ends.add(end);
		}
		jsonRootBean.setEnd(ends);
		// 获取设置JsonRootBean中的component属性值
		List<Component> components = new ArrayList<Component>();
		// 获取component
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element type = (Element) node.selectSingleNode("Type");
				if (type.getTextTrim().equals("11")) {
					Element id = (Element) node.selectSingleNode("Id");
					Element caption = (Element) node.selectSingleNode("Name");
					Element name = (Element) node.selectSingleNode("Target");
					String stringName = name.getTextTrim();
					//System.out.println(stringName);
					String[] string = stringName.split("\\.");
					int length = string.length;
					String NameString = string[length-1];
					String realNameString = UpperCase.upperCase(NameString)+"Logiclet";
					//System.out.println("-----------");
					if (!(caption.getTextTrim().equals("开始")) && !caption.getTextTrim().equals("正常结束")) {
						Component component = new Component();
						component.setId(id.getTextTrim());
						component.setCaption(caption.getTextTrim());
						component.setName(realNameString);
						component.setShowId(id.getTextTrim());
						// 获取geomery
						Element Constraint = (Element) node.selectSingleNode("Constraint");
						Iterator<Element> ConstraintIterator = Constraint.elementIterator();
						Geometry geometryc = new Geometry();
						while (ConstraintIterator.hasNext()) {

							Element temporary = ConstraintIterator.next();
							if (temporary.getName().equals("Location")) {
								String locationString = temporary.getTextTrim();
								String[] strings = locationString.split(",");
								geometryc.setX((Integer.valueOf(strings[0])));
								// System.out.println(Integer.valueOf(string[0]));
								geometryc.setY(Integer.valueOf(strings[1]));
							} else if (temporary.getName().equals("Size")) {
								String sizeString = temporary.getTextTrim();
								String[] strings = sizeString.split(",");
								geometryc.setWidth(Integer.valueOf(strings[0]));
								geometryc.setHeight(Integer.valueOf(strings[1]));
							}

						}
						component.setGeometry(geometryc);
						// 获取InArgs
						InArgs inArgsComponent = new InArgs();
						List<Element> inArgsrgElements = node.selectNodes("InArgs/Arg");
						List<Arg> inargsComponent = new ArrayList<Arg>();
						for (int j = 0; j < inArgsrgElements.size(); j++) {
							Arg argComponent = new Arg();
							Element inArgNode = inArgsrgElements.get(j);
							Element inArgName = (Element) inArgNode.selectSingleNode("Key");
							Element inArgCaption = (Element) inArgNode.selectSingleNode("Name");
							Element inArgEditor = (Element) inArgNode.selectSingleNode("Type");
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
							if (inArgEditor == null) {
								argComponent.setDescription("");
							} else {
								argComponent.setDescription(inArgEditor.getTextTrim());
							}
							if (inArgValue == null) {
								argComponent.setValue("");
							} else {
								argComponent.setValue(inArgValue.getTextTrim());
							}
							argComponent.setContains("");
							argComponent.setDescription("");
							argComponent.setExample("");
							argComponent.setRequired("");
							argComponent.setType("");
							inargsComponent.add(argComponent);
						}
						inArgsComponent.setArgs(inargsComponent);
						component.setInArgs(inArgsComponent);

						// 获取OutArgs
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
						outArgsComponent.setArgs(ouArgsComponent);
						component.setOutArgs(outArgsComponent);

						// 获取Out
						List<Out> outList = new ArrayList<Out>();
						List<Element> outElements = node.selectNodes("Terminals/Terminal");
						for (int j = 0; j < outElements.size(); j++) {

							Element outNode = outElements.get(j);
							Element outCaption = (Element) outNode.selectSingleNode("Desp");
							Element outName = (Element) outNode.selectSingleNode("Name");
							if (!outCaption.getTextTrim().equals("失败")) {
								Out out = new Out();
								// 判断next所需代码
								
								List<Element> Connections = node.selectNodes("SourceConnections/Connection");
								for (int x = 0; x < Connections.size(); x++) {
									Element ConnectionNode = Connections.get(x);
									Element SourceTerminal = (Element) ConnectionNode
											.selectSingleNode("SourceTerminal");
									String sourceTerminalString = SourceTerminal.getTextTrim();
									if (sourceTerminalString.equals(outName.getTextTrim())) {
										Element targetId = (Element) ConnectionNode.selectSingleNode("targetId");
										String nextString = targetId.getTextTrim();
										if (nextString.equals("2")) {
											out.setNext("1001");
										}else {
											out.setNext(nextString);
										}
									}
								}
								out.setCaption(outCaption.getTextTrim());
								out.setName(outName.getTextTrim());
								out.setId("");
								
								
								outList.add(out);
							}
						}
						component.setOut(outList);
						// 获取exception
						Exception exception = new Exception();
						List<Element> exceptionElements = node.selectNodes("Terminals/Terminal");
						for (int j = 0; j < exceptionElements.size(); j++) {
							Element exceptionNode = exceptionElements.get(j);
							Element exceptionCaption = (Element) exceptionNode.selectSingleNode("Desp");
							Element exceptionName = (Element) exceptionNode.selectSingleNode("Name");
							if (exceptionCaption.getTextTrim().equals("失败")) {
								// 判断next所需代码
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
								exception.setName("");
								if (nextString==null) {
									exception.setNext("");
								}else {
									exception.setNext(nextString);
								}
								
							}
							component.setException(exception);
						}

						components.add(component);
					}
				}

			}
		}
		jsonRootBean.setComponent(components);
		// 获取设置JsonRootBean中的lfc属性值
		List<Lfc> lfcs = new ArrayList<Lfc>();
		// 获取Lfc
		if (xmlFile.exists()) {
			Document document = saxread.read(xmlFile);// 读取XML文件
			List<Element> nodeElements = document.selectNodes("//Component/Implementation/Node");
			for (int i = 0; i < nodeElements.size(); i++) {
				Element node = nodeElements.get(i);
				Element id = (Element) node.selectSingleNode("Id");
				Element caption = (Element) node.selectSingleNode("Desp");
				Element name = (Element) node.selectSingleNode("Target");
				Element type = (Element) node.selectSingleNode("Type");
				if (!(caption.getTextTrim().equals("开始")) && !caption.getTextTrim().equals("正常结束")) {
					if (type.getTextTrim().equals("7") || type.getTextTrim().equals("12")) {
						Lfc lfc = new Lfc();
						lfc.setId(id.getTextTrim());
						lfc.setCaption(caption.getTextTrim());
						lfc.setName(name.getTextTrim());
						lfc.setShowId(id.getTextTrim());
						// 获取geomery
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
							// 获取InArgs
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
							inArgsLfc.setArgs(inargsLfc);
							lfc.setInArgs(inArgsLfc);

							// 获取OutArgs
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

							// 获取Out
							List<Out> outList = new ArrayList<Out>();
							List<Element> outElements = node.selectNodes("Terminals/Terminal");
							for (int j = 0; j < outElements.size(); j++) {
								Element outNode = outElements.get(j);
								Element outCaption = (Element) outNode.selectSingleNode("Desp");
								Element outName = (Element) outNode.selectSingleNode("Name");
								if (!outCaption.getTextTrim().equals("失败")) {
									Out out = new Out();
									// 判断next所需代码
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
							// 获取exception
							Exception exception = new Exception();
							List<Element> exceptionElements = node.selectNodes("Terminals/Terminal");
							for (int j = 0; j < exceptionElements.size(); j++) {
								Element exceptionNode = exceptionElements.get(j);
								Element exceptionCaption = (Element) exceptionNode.selectSingleNode("Desp");
								Element exceptionName = (Element) exceptionNode.selectSingleNode("Name");
								if (exceptionCaption.getTextTrim().equals("失败")) {
									// 判断next所需代码
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
							// 设置lfcPath
							if (type.getTextTrim().equals("7")) {
								Element path = (Element) node.selectSingleNode("FilePath");
								if (path==null) {
									lfc.setLfcPath("");
								}else {
									lfc.setLfcPath(path.getTextTrim().replace(".tcpt", ".lfc"));
								}
								
							} else if (type.getTextTrim().equals("12")) {

							}
							// 设置fileDescription
							FileDescription fileDescriptionLfc = new FileDescription();
							fileDescriptionLfc.setAuthor("");
							fileDescriptionLfc.setFunction("");
							fileDescriptionLfc.setRemark("");
							lfc.setFileDescription(fileDescriptionLfc);
							// 设置mappings
							List<String> mappings = new ArrayList<String>();
							lfc.setMappings(mappings);
							// 设置mappingPath
							String mappingPath = null;
							lfc.setMappingPath(mappingPath);
							// 设置ades
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

		// 设置csd
		List<Csd> csds = new ArrayList<Csd>();
		Csd csd = new Csd();
		jsonRootBean.setCsd(csds);
		return jsonRootBean;
	}
}
