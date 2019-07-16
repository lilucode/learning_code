package cn.com.agree.ab.transfer.util;

import java.util.ArrayList;
import java.util.List;

import cn.com.agree.ab.transfer.afa.model.Arg;
import cn.com.agree.ab.transfer.afa.model.NodeModel;
import cn.com.agree.ab.transfer.runtime.lfc.ArgComponent;
import cn.com.agree.ab.transfer.runtime.lfc.ArgElement;
import cn.com.agree.ab.transfer.runtime.lfc.Geometry;
import cn.com.agree.ab.transfer.value.jj.REQParser;

public class LfcUtil {

	/**
	 * bcpt入参，过滤自定义参数名
	 */
	protected List<String> inArgs = new ArrayList<String>();
	/**
	 * bcpt出参
	 */
	protected List<String> outArgs = new ArrayList<String>();

	/**
	 * 设置geometry
	 * 
	 * @param geometry
	 * @param node
	 */
	protected void setGeometry(Geometry geometry, NodeModel node) {
		String location = node.getLocation();
		String size = node.getSize();
		geometry.setX(Math.abs(Integer.parseInt(location.substring(0, location.lastIndexOf(",")))));
		geometry.setY(Math.abs(Integer.parseInt(location.substring(location.lastIndexOf(",") + 1))));
		geometry.setWidth(Math.abs(Integer.parseInt(size.substring(0, size.lastIndexOf(",")))));
		geometry.setHeight(Math.abs(Integer.parseInt(size.substring(size.lastIndexOf(",") + 1))));
	}

	/**
	 * 技术组件和内嵌lfc的出入参
	 * 
	 * @param cInArgs
	 * @return
	 */
	protected List<ArgElement> getArgComponent(List<Arg> Args) {
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
