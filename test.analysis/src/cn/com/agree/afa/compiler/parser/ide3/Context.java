package cn.com.agree.afa.compiler.parser.ide3;

import cn.com.agree.afa.compiler.model.PkgModel;
import cn.com.agree.afa.compiler.model.TradeModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

public class Context {
	private static final ThreadLocal<Context> TL_CONTEXT = new ThreadLocal<Context>() {
		protected Context initialValue() {
			return new Context();
		}
	};
	private boolean debugMode;
	private boolean isDebugCompile;
	private TypeProvider typeProvider;
	private Map<String, TradeModel> innerTrades;
	private TradeModel outsideTradeModel;
	private PkgModel outsidePkgModel;
	private int nodeId;
	private String appCode;
	private String tradeCode;
	private String outputPath;
	private Collection<Element> nodeList;
	private String packageName;
	private String componentName;

	public PkgModel getOutsidePkgModel() {
		return this.outsidePkgModel;
	}

	public void setOutsidePkgModel(PkgModel outsidePkgModel) {
		this.outsidePkgModel = outsidePkgModel;
	}

	public TradeModel getOutsideTradeModel() {
		return this.outsideTradeModel;
	}

	public void setOutsideTradeModel(TradeModel outsideTradeModel) {
		this.outsideTradeModel = outsideTradeModel;
	}

	public static Context getContext() {
		return (Context) TL_CONTEXT.get();
	}

	public static void setContext(Context value) {
		TL_CONTEXT.set(value);
	}

	public static void clear() {
		TL_CONTEXT.remove();
	}

	public boolean isDebugMode() {
		return this.debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public TypeProvider getTypeProvider() {
		return this.typeProvider;
	}

	public void setTypeProvider(TypeProvider typeProvider) {
		this.typeProvider = typeProvider;
	}

	public boolean isDebugCompile() {
		return this.isDebugCompile;
	}

	public void setDebugCompile(boolean isDebugCompile) {
		this.isDebugCompile = isDebugCompile;
	}

	public Map<String, TradeModel> getInnerTrades() {
		return this.innerTrades;
	}

	public void addInnerTrades(String id, TradeModel model) {
		if (this.innerTrades == null) {
			this.innerTrades = new HashMap<>();
		}
		if (this.outsideTradeModel == null) {
			if (this.outsidePkgModel == null) {
				throw new IllegalStateException("CUModel is null");
			}
			this.outsidePkgModel.addInnerTrades(id, model);
		} else {
			this.outsideTradeModel.addInnerTrades(id, model);
		}
		this.innerTrades.put(id, model);
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getOutputPath() {
		return this.outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public Collection<Element> getNodeList() {
		return this.nodeList;
	}

	public void setNodeList(Collection<Element> nodeList) {
		this.nodeList = nodeList;
	}
}
