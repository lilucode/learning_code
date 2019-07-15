package bc;

import cn.com.agree.afa.jcomponent.GlobalErrorHolder;
import cn.com.agree.afa.svc.javaengine.AppLogger.LogLevel;
import cn.com.agree.afa.svc.javaengine.BCScript;
import cn.com.agree.afa.svc.javaengine.EndNode;
import cn.com.agree.afa.svc.javaengine.INode;
import cn.com.agree.afa.svc.javaengine.JScript;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaContext;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import cn.com.agree.afa.util.ExceptionUtils;
import cn.com.agree.afa.util.future.IFuture;
import cn.com.agree.afa.util.future.IFutureListener;
import java.util.ArrayList;
import java.util.List;
import static cn.com.agree.afa.jcomponent.GlobalErrorHolder.setGlobalError;
import tc.bank.product.B_AppInterfaceMng;
import tc.bank.product.B_ConstantOperate;
import tc.bank.product.B_DBUnityAltOper;
import tc.bank.product.B_DBUnityRptOper;
import tc.bank.product.B_ParamMemory;
import tc.bank.product.B_RespCodeAdm;
import tc.bank.product.B_StringParser;
import tc.platform.P_Dict;
import tc.platform.P_Jdbc;
import tc.platform.P_Json;
import tc.platform.P_JudgmentStatement;
import tc.platform.P_Logger;
import tc.platform.P_Object;
import tc.platform.P_String;
import tc.platform.P_Time;

/**
 * 业务组件包名称：PublicPkg <br/>
 *
 * 业务组件包描述：PublicPkg <br/>
 *
 * @author AFA Compiler <br/>
 * @version 1.0 <br/>
 *
 */
public class PublicPkg {

    public static class PubCtlManage extends BCScript {
        private INode startNode;
        public PubCtlManage(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  公共控制管理");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
        
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node11());
                log(LogLevel.INFO, "将默认异常委托到Node11节点");
                return new Node13();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node3 设置查询项数量");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("_ChlChkItemCount_", ((JavaList)__REQ__.getItem("_ChlChkItemInfo_")).size()));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node3", "设置查询项数量", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node3", "设置查询项数量", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node3", "设置查询项数量", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node4 循环初始赋值(i=0)");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("i", 0));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node4", "循环初始赋值(i=0)", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node4", "循环初始赋值(i=0)", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node4", "循环初始赋值(i=0)", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node5 检查项目数是否大于i");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = toInt(__REQ__.getItem("i"))<toInt(__REQ__.getItem("_ChlChkItemCount_"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node5", "检查项目数是否大于i", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node5", "检查项目数是否大于i", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node15();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node5", "检查项目数是否大于i", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node6 渠道检查类型是否为1");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = ((List)((List)__REQ__.getItem("_ChlChkItemInfo_")).get(toInt(__REQ__.getItem("i")))).get(0).equals("1");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node6", "渠道检查类型是否为1", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node6", "渠道检查类型是否为1", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node8();
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node6", "渠道检查类型是否为1", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node7 调用项目检查组件");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        __BUILTIN__TEMP__.setItem("pkgName", ((List)((List)__REQ__.getItem("_ChlChkItemInfo_")).get(toInt(__REQ__.getItem("i")))).get(1));
                        logVar(LogLevel.valueOf(4), "入参0", __BUILTIN__TEMP__.getItem("pkgName"));
                        __BUILTIN__TEMP__.setItem("bcName", ((List)((List)__REQ__.getItem("_ChlChkItemInfo_")).get(toInt(__REQ__.getItem("i")))).get(2));
                        logVar(LogLevel.valueOf(4), "入参1", __BUILTIN__TEMP__.getItem("bcName"));
                        bcScript = new cn.com.agree.afa.jcomponent.bc.BComponentInvoker(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("PubCtlManage_Node7", "调用项目检查组件", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 0:
                            return new Node9();
                        case 1:
                            return new Node8();
                        }
                    }
                    return getExceptionHandler(new Node9());
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node7", "调用项目检查组件", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node9());
                }
            }    
        }
        
        private class Node8 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node8 循环+1");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("i", toInt(__REQ__.getItem("i"))+1));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node8", "循环+1", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node8", "循环+1", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node8", "循环+1", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node9 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node9 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node10 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node10 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node11 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node11", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node11", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node11", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node12 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node12 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node13 获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "tp_cip_checkiteminfo";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "channel"+__REQ__.getItem("G_channelcode");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node13", "获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PubCtlManage_Node13", "获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node9());
                        }
                        __REQ__.setItem("_AppChlChkItemInfo_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PubCtlManage_Node13", "获取内存化数据", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node3();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node13", "获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node14 List合并");
                startTime = System.currentTimeMillis();
                try {
                    JavaList _arg0_ = new JavaList(__REQ__.getItem("_ChlChkItemInfo_"), __REQ__.getItem("_AppChlChkItemInfo_"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_Object.P_mergeList(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node14", "List合并", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PubCtlManage_Node14", "List合并", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node9());
                        }
                        __REQ__.setItem("_ChlChkItemInfo_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PubCtlManage_Node14", "List合并", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node3();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node14", "List合并", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node15 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PubCtlManage_Node15 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList("_AppChlChkItemInfo_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PubCtlManage_Node15", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node9());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PubCtlManage_Node15", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node9());
                    }
                } catch (Throwable e) {
                	gatherStat("PubCtlManage_Node15", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node9());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    public static class Publicflow extends BCScript {
        private INode startNode;
        public Publicflow(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  拼包公共接入流程");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
        
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node7());
                log(LogLevel.INFO, "将默认异常委托到Node7节点");
                return new Node19();
            }    
        }
        
        private class Node3 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node3 接入报文处理");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.PublicPkg.AccessMsgProc(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("Publicflow_Node3", "接入报文处理", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node4();
                        }
                    }
                    return getExceptionHandler(new Node8());
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node3", "接入报文处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node8());
                }
            }    
        }
        
        private class Node4 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node4 登记通讯事件");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.PublicPkg.RgstCommEvents(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("Publicflow_Node4", "登记通讯事件", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node5();
                        }
                    }
                    return getExceptionHandler(new Node8());
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node4", "登记通讯事件", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node8());
                }
            }    
        }
        
        private class Node5 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node5 公共控制管理");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.PublicPkg.PubCtlManage(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("Publicflow_Node5", "公共控制管理", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node12();
                        }
                    }
                    return getExceptionHandler(new Node8());
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node5", "公共控制管理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node8());
                }
            }    
        }
        
        private class Node6 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node6 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node7 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node7", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node7", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node7", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node8 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node8 失败结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node12 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node12 是否特殊渠道交易");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = ("TE".equals(__REQ__.getItem("G_channelscode"))&&(new JavaList("110061", "110062", "110064", "110065")).contains(__REQ__.getItem("G_clienttype")))||"MG".equals(__REQ__.getItem("G_channelscode"))||(new JavaList("Comm", "PubTrades", "EsbComm")).contains(__REQ__.getItem("service"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node12", "是否特殊渠道交易", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node12", "是否特殊渠道交易", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node13();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node12", "是否特殊渠道交易", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node13 处理接口请求信息");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_MsgDict_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "COMMON";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList(__REQ__.getStringItem("svrinputcode",__REQ__.getStringItem("p_servicecode","")+".req"));
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "1";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参4=__REQ__");
                    boolean _arg5_ = true;
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    boolean _arg6_ = true;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    boolean _arg7_ = true;
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = B_AppInterfaceMng.B_ProcInMsg(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node13", "处理接口请求信息", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node13", "处理接口请求信息", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node13", "处理接口请求信息", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node14 容器间变量参数化拷贝");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = ((JavaDict)__REQ__.getItem("_MsgDict_")).getItem("BODY");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    JavaDict _arg1_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参1=__REQ__");
                    Object _arg2_ = null;
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    JavaList _arg3_ = new JavaList();
                    logVar(LogLevel.valueOf(0), "入参3", _arg3_);
                    TCResult result = P_Dict.copy(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node14", "容器间变量参数化拷贝", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node14", "容器间变量参数化拷贝", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node14", "容器间变量参数化拷贝", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node15 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node15 删除json字符串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList("JsonStr");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node15", "删除json字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node15", "删除json字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node15", "删除json字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node16 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node16 截取JSON串");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("JsonStr");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    int _arg1_ = __REQ__.getItem("JsonStr").toString().indexOf("{");
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    int _arg2_ = __REQ__.getItem("JsonStr").toString().lastIndexOf("}")+1;
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    TCResult result = P_String.subStr(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node16", "截取JSON串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("Publicflow_Node16", "截取JSON串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node8());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("Publicflow_Node16", "截取JSON串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node17();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node16", "截取JSON串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node17 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node17 json串转换javaDict");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("JsonStr");
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = P_Json.strToDict(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node17", "json串转换javaDict", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node17", "json串转换javaDict", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node15();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node17", "json串转换javaDict", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node18 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node18 字节数组转换为字符串");
                startTime = System.currentTimeMillis();
                try {
                    byte[] _arg0_ = __REQ__.getItem("__RCVPCK__");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "UTF8";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToString(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node18", "字节数组转换为字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("Publicflow_Node18", "字节数组转换为字符串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node8());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Publicflow_Node18", "字节数组转换为字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node16();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node18", "字节数组转换为字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node19 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Publicflow_Node19 服务间调用判断");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_AppToAppFlg_")!=null&&"true".equals(__REQ__.getItem("_AppToAppFlg_"));
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Publicflow_Node19", "服务间调用判断", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node8());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Publicflow_Node19", "服务间调用判断", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node3();
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node8());
                    }
                } catch (Throwable e) {
                	gatherStat("Publicflow_Node19", "服务间调用判断", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node8());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    public static class AccessMsgProc extends BCScript {
        private INode startNode;
        public AccessMsgProc(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  接入报文处理");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
        
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node25());
                log(LogLevel.INFO, "将默认异常委托到Node25节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node3 创建Json拆包容器");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("_MsgDict_", new JavaDict()));
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = P_Object.createObject(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node3", "创建Json拆包容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node3", "创建Json拆包容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node3", "创建Json拆包容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node4 参数内存化");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "0";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ParamMemory.B_ParamterMemory(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node4", "参数内存化", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node4", "参数内存化", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node4", "参数内存化", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node5 字节数组转换为字符串");
                startTime = System.currentTimeMillis();
                try {
                    byte[] _arg0_ = __REQ__.getItem("__RCVPCK__");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "UTF8";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToString(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node5", "字节数组转换为字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node5", "字节数组转换为字符串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("AccessMsgProc_Node5", "字节数组转换为字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node5", "字节数组转换为字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node6 截取JSON串");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("JsonStr");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    int _arg1_ = __REQ__.getItem("JsonStr").toString().indexOf("{");
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    int _arg2_ = __REQ__.getItem("JsonStr").toString().lastIndexOf("}")+1;
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    TCResult result = P_String.subStr(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node6", "截取JSON串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node6", "截取JSON串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("AccessMsgProc_Node6", "截取JSON串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node6", "截取JSON串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node7 json串转换javaDict");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_MsgDict_");
                    logVar(LogLevel.valueOf(3), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("JsonStr");
                    logVar(LogLevel.valueOf(3), "入参1", _arg1_);
                    TCResult result = P_Json.strToDict(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node7", "json串转换javaDict", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node7", "json串转换javaDict", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node7", "json串转换javaDict", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node8 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node8 删除json字符串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList("JsonStr");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node8", "删除json字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node8", "删除json字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node8", "删除json字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node9 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node9 信息日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    TCResult result = P_Logger.info(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node9", "信息日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node9", "信息日志", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node9", "信息日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node10 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node10 请求报文头接口处理");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_MsgDict_");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "COMMON";
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList("CommonReqSysHdr", "CommonReqAppHdr");
                    logVar(LogLevel.valueOf(2), "入参2", _arg2_);
                    String _arg3_ = "1";
                    logVar(LogLevel.valueOf(2), "入参3", _arg3_);
                    JavaDict _arg4_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参4=__REQ__");
                    boolean _arg5_ = true;
                    logVar(LogLevel.valueOf(2), "入参5", _arg5_);
                    boolean _arg6_ = true;
                    logVar(LogLevel.valueOf(2), "入参6", _arg6_);
                    boolean _arg7_ = true;
                    logVar(LogLevel.valueOf(2), "入参7", _arg7_);
                    TCResult result = B_AppInterfaceMng.B_ProcInMsg(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node10", "请求报文头接口处理", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node10", "请求报文头接口处理", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node23();
                    case 1:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node10", "请求报文头接口处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node11 登记通讯流水标记赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("_RegChlExchInfoFlag_", "1"), new JavaList("channelcode", __REQ__.getItem("G_channelcode")), new JavaList("channeldate", __REQ__.getItem("G_channeldate")), new JavaList("channeltime", __REQ__.getItem("G_channeltime")), new JavaList("channelserno", __REQ__.getItem("G_channelserno")), new JavaList("channelip", __REQ__.getItem("G_channelip")), new JavaList("modulecode", __REQ__.getItem("__MC__")), new JavaList("transcode", __REQ__.getItem("__TC__")));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node11", "登记通讯流水标记赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node11", "登记通讯流水标记赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node27();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node11", "登记通讯流水标记赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node14 取工作日期时间");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPPublicmethod.GetWorkDateTime(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("AccessMsgProc_Node14", "取工作日期时间", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node18();
                        }
                    }
                    return getExceptionHandler(new Node23());
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node14", "取工作日期时间", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node23());
                }
            }    
        }
        
        private class Node18 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node18 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node19 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node19 查询渠道权限检查项目");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "GetChlChkItem";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", "PUBLIC", "transcode", "JSONPKG");
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityRptOper.B_DBUnityRptOpr(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node19", "查询渠道权限检查项目", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 5) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node19", "查询渠道权限检查项目", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                        __REQ__.setItem("_ChlChkItemInfo_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        __REQ__.setItem("_ChlChkItemCount_", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                    }
                	gatherStat("AccessMsgProc_Node19", "查询渠道权限检查项目", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node20();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node19", "查询渠道权限检查项目", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node20 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node20 更新数据库版本");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "update TP_CIP_CACHE set versionnum=versionnum+1 where 1=1";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    boolean _arg2_ = true;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    TCResult result = P_Jdbc.executeSQL(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node20", "更新数据库版本", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node20", "更新数据库版本", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                    }
                	gatherStat("AccessMsgProc_Node20", "更新数据库版本", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node21();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node20", "更新数据库版本", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node21 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node21 参数内存化");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "0";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ParamMemory.B_ParamterMemory(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node21", "参数内存化", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node21", "参数内存化", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node22();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node21", "参数内存化", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node22 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node22 重新获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "tp_cip_checkiteminfo";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "channel*";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node22", "重新获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node22", "重新获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                        __REQ__.setItem("_ChlChkItemInfo_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("AccessMsgProc_Node22", "重新获取内存化数据", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node22", "重新获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node23 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node23 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node25 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node25 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node25", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("AccessMsgProc_Node25", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node26();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node25", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node26 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node26 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node27 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "AccessMsgProc_Node27 获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "tp_cip_checkiteminfo";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "channel*";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("AccessMsgProc_Node27", "获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node23());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("AccessMsgProc_Node27", "获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node23());
                        }
                        __REQ__.setItem("_ChlChkItemInfo_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("AccessMsgProc_Node27", "获取内存化数据", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node19();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node23());
                    }
                } catch (Throwable e) {
                	gatherStat("AccessMsgProc_Node27", "获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node23());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    public static class PickUpMsgProc extends BCScript {
        private INode startNode;
        public PickUpMsgProc(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  接出报文处理");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
        
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node47());
                log(LogLevel.INFO, "将默认异常委托到Node47节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node3 业务是否处理");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__.getItem("RspInfo")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node3", "业务是否处理", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node3", "业务是否处理", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node43();
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node3", "业务是否处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node4 场景内部值赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__.getItem("RspInfo");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaDict _arg1_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参1=__REQ__");
                    Object _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaList _arg3_ = new JavaList();
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = P_Dict.copy(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node4", "场景内部值赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node4", "场景内部值赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node4", "场景内部值赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node5 服务间调用判断");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_AppToAppFlg_")!=null&&"true".equals(__REQ__.getItem("_AppToAppFlg_"));
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node5", "服务间调用判断", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node5", "服务间调用判断", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node15();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node5", "服务间调用判断", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node6 业务是否处理");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__.getItem("RspInfo")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node6", "业务是否处理", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node6", "业务是否处理", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node11();
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node6", "业务是否处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node7 javaDict转换json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__.getItem("RspInfo");
                    logVar(LogLevel.valueOf(2), "入参0", _arg0_);
                    TCResult result = P_Json.dictToStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node7", "javaDict转换json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node7", "javaDict转换json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonDictRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node7", "javaDict转换json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node7", "javaDict转换json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node8 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node8 字符串转换成字节数组");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("_JsonDictRet_");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "utf8";
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToByteArray(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node8", "字符串转换成字节数组", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node8", "字符串转换成字节数组", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonArrRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node8", "字符串转换成字节数组", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node8", "字符串转换成字节数组", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node9 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node9 返回报文赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("__SNDPCK__", __REQ__.getItem("_JsonArrRet_"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node9", "返回报文赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node9", "返回报文赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node9", "返回报文赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node10 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node10 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node11 是否内部处理异常");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("p_ret_code")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node11", "是否内部处理异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node11", "是否内部处理异常", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node14();
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node11", "是否内部处理异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node12 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node12 失败状态赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_status", "F"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node12", "失败状态赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node12", "失败状态赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node12", "失败状态赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node13 javaDict转换json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    TCResult result = P_Json.dictToStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node13", "javaDict转换json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node13", "javaDict转换json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonDictRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node13", "javaDict转换json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node13", "javaDict转换json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node14 系统异常");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "SystemException";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetRespCode(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node14", "系统异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node14", "系统异常", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_ret_code", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node14", "系统异常", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node14", "系统异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node15 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node15 业务是否处理");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__.getItem("RspInfo")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node15", "业务是否处理", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node15", "业务是否处理", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node41();
                    case 1:
                        return new Node16();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node15", "业务是否处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node16 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node16 业务处理返回码是否返回");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = asDict(__RSP__.getItem("RspInfo")).hasKey("p_ret_code")&&((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_code")!=null&&!(((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_code").equals(""));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node16", "业务处理返回码是否返回", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node16", "业务处理返回码是否返回", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node39();
                    case 1:
                        return new Node17();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node16", "业务处理返回码是否返回", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node17 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node17 是否第三方返回");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.hasKey("p_excptype")&&__REQ__.getItem("p_excptype")!=null&&(!(__REQ__.getItem("p_excptype").equals("F")));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node17", "是否第三方返回", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node17", "是否第三方返回", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node38();
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node17", "是否第三方返回", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node18 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node18 是否返回状态");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = ((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_status")==null||("".equals(((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_status")));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node18", "是否返回状态", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node18", "是否返回状态", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node37();
                    case 1:
                        return new Node19();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node18", "是否返回状态", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node19 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node19 返回码是否成功");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "Success";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = ((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_code");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_ConstantOperate.B_RespCodeCompare(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node19", "返回码是否成功", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node19", "返回码是否成功", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node36();
                    case 1:
                        return new Node20();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node19", "返回码是否成功", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node20 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node20 成功状态赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_status", "S"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node20", "成功状态赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node20", "成功状态赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node21();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node20", "成功状态赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node21 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node21 是否特殊渠道交易");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = ("TE".equals(__REQ__.getItem("G_channelscode"))&&(new JavaList("110061", "110062", "110064", "110065")).contains(__REQ__.getItem("G_clienttype")))||"MG".equals(__REQ__.getItem("G_channelscode"))||(new JavaList("Comm", "PubTrades", "EsbComm")).contains(__REQ__.getItem("service"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node21", "是否特殊渠道交易", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node21", "是否特殊渠道交易", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node30();
                    case 1:
                        return new Node22();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node21", "是否特殊渠道交易", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node22 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node22 获取响应时间");
                startTime = System.currentTimeMillis();
                try {
                    TCResult result = P_Time.getTime();
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node22", "获取响应时间", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 2) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node22", "获取响应时间", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_workdate", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        __REQ__.setItem("p_worktime", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                    }
                	gatherStat("PickUpMsgProc_Node22", "获取响应时间", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node23();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node22", "获取响应时间", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node23 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node23 响应接口请求（无报文体）");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    String _arg1_ = "COMMON";
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList("CommonRspSysHdr");
                    logVar(LogLevel.valueOf(2), "入参2", _arg2_);
                    String _arg3_ = "2";
                    logVar(LogLevel.valueOf(2), "入参3", _arg3_);
                    JavaDict _arg4_ = __RSP__;
                    log(LogLevel.valueOf(2), "入参4=__RSP__");
                    boolean _arg5_ = false;
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    boolean _arg6_ = true;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    boolean _arg7_ = true;
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = B_AppInterfaceMng.B_ProcInMsg(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node23", "响应接口请求（无报文体）", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node23", "响应接口请求（无报文体）", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node24();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node23", "响应接口请求（无报文体）", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node24 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node24 业务是否处理");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__.getItem("RspInfo")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node24", "业务是否处理", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node24", "业务是否处理", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node54();
                    case 1:
                        return new Node25();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node24", "业务是否处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node25 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node25 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__.getItem("RspInfo");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList("trandate", "respcode", "p_ret_desc", "respmsg", "p_ret_code", "p_excptype");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node25", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node25", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node54();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node25", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node26 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node26 javaDict转换json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(2), "入参0=__RSP__");
                    TCResult result = P_Json.dictToStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node26", "javaDict转换json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node26", "javaDict转换json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonDictRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node26", "javaDict转换json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node27();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node26", "javaDict转换json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node27 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node27 字符串转换成字节数组");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("_JsonDictRet_");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "utf8";
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToByteArray(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node27", "字符串转换成字节数组", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node27", "字符串转换成字节数组", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonArrRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node27", "字符串转换成字节数组", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node28();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node27", "字符串转换成字节数组", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node28 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node28 返回报文赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("__SNDPCK__", __REQ__.getItem("_JsonArrRet_"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node28", "返回报文赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node28", "返回报文赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node29();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node28", "返回报文赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node29 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node29 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node30 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node30 取工作日期时间");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPPublicmethod.GetWorkDateTime(__REQ__, __RSP__, __BUILTIN__TEMP__);
                    } else {
                        __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
                    }
                    
                    INode node = bcScript.execute();
                    
                    
                    if (node == EndNode.SUSPEND_END) {
                        startNode = this;
                        return node;
                    }
                    
                    log(LogLevel.INFO, "逻辑返回值=" + node);
                    
                    if (node instanceof EndNode) {
                		gatherStat("PickUpMsgProc_Node30", "取工作日期时间", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node34();
                        }
                    }
                    return getExceptionHandler(new Node53());
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node30", "取工作日期时间", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node53());
                }
            }    
        }
        
        private class Node32 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node32 获取响应时间戳");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "YYYYMMddHHmmssSSS";
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_Time.getFormatTime(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node32", "获取响应时间戳", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node32", "获取响应时间戳", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_rsptimestamp_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node32", "获取响应时间戳", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node56();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node32", "获取响应时间戳", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node34 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node34 响应接口请求（含报文体）");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "COMMON";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList("CommonRspSysHdr", __REQ__.getStringItem("svroutputcode",__REQ__.getStringItem("p_servicecode","")+".rsp"));
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "2";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参4=__RSP__");
                    boolean _arg5_ = false;
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    boolean _arg6_ = true;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    boolean _arg7_ = true;
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = B_AppInterfaceMng.B_ProcInMsg(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node34", "响应接口请求（含报文体）", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node34", "响应接口请求（含报文体）", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node35();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node34", "响应接口请求（含报文体）", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node35 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node35 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(0), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("RspInfo");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node35", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node35", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node54();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node35", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node36 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node36 失败状态赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_status", "F"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node36", "失败状态赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node36", "失败状态赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node21();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node36", "失败状态赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node37 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node37 状态赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_status", ((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_status")));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node37", "状态赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node37", "状态赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node21();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node37", "状态赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node38 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node38 返回码映射");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = ((JavaDict)__RSP__.getItem("RspInfo")).getItem("p_ret_code");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "plat";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = __REQ__.getItem("channelcode");
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_RespCodeAdm.B_RespCodemap(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node38", "返回码映射", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 2) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node38", "返回码映射", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_ret_code", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        __REQ__.setItem("p_ret_desc", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                    }
                	gatherStat("PickUpMsgProc_Node38", "返回码映射", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node18();
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node38", "返回码映射", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node39 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node39 是否内部处理异常");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("p_ret_code")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node39", "是否内部处理异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node39", "是否内部处理异常", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node40();
                    case 1:
                        return new Node17();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node39", "是否内部处理异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node40 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node40 系统异常");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "SystemException";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetRespCode(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node40", "系统异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node40", "系统异常", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_ret_code", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node40", "系统异常", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node17();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node40", "系统异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node41 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node41 是否内部处理异常");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("p_ret_code")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node41", "是否内部处理异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node41", "是否内部处理异常", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node42();
                    case 1:
                        return new Node36();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node41", "是否内部处理异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node42 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node42 系统异常");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "SystemException";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetRespCode(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node42", "系统异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node42", "系统异常", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_ret_code", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node42", "系统异常", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node36();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node42", "系统异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node43 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node43 声明返回容器");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList(new JavaList("RspInfo", new JavaDict()));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node43", "声明返回容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node43", "声明返回容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node44();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node43", "声明返回容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node44 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node44 是否老服务，先兼容，遇到了自己修改");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__.getItem("__SNDPCK__")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node44", "是否老服务，先兼容，遇到了自己修改", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node44", "是否老服务，先兼容，遇到了自己修改", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node5();
                    case 1:
                        return new Node45();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node44", "是否老服务，先兼容，遇到了自己修改", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node45 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node45 兼容老交易参数拷贝");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__.getItem("__SNDPCK__");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaDict _arg1_ = __RSP__.getItem("RspInfo");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    Object _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaList _arg3_ = new JavaList();
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = P_Dict.copy(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node45", "兼容老交易参数拷贝", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node45", "兼容老交易参数拷贝", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node46();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node45", "兼容老交易参数拷贝", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node46 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node46 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(0), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("__SNDPCK__");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node46", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node46", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node46", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node47 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node47 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node47", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node47", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node48();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node47", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node48 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node48 系统异常");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "SystemException";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetRespCode(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node48", "系统异常", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node48", "系统异常", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_ret_code", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node48", "系统异常", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node49();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node48", "系统异常", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node49 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node49 系统异常赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_status", "F"), new JavaList("p_ret_desc", "系统异常"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node49", "系统异常赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node49", "系统异常赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node50();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node49", "系统异常赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node50 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node50 获取响应时间");
                startTime = System.currentTimeMillis();
                try {
                    TCResult result = P_Time.getTime();
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node50", "获取响应时间", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 2) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node50", "获取响应时间", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("p_workdate", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        __REQ__.setItem("p_worktime", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                    }
                	gatherStat("PickUpMsgProc_Node50", "获取响应时间", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node51();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node50", "获取响应时间", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node51 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node51 响应接口请求（含报文体）");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "COMMON";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList("CommonRspSysHdr");
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "2";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参4=__RSP__");
                    boolean _arg5_ = false;
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    boolean _arg6_ = true;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    boolean _arg7_ = true;
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = B_AppInterfaceMng.B_ProcInMsg(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node51", "响应接口请求（含报文体）", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node51", "响应接口请求（含报文体）", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node52();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node51", "响应接口请求（含报文体）", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node52 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node52 javaDict转换json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    TCResult result = P_Json.dictToStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node52", "javaDict转换json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node52", "javaDict转换json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                        __REQ__.setItem("_JsonDictRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("PickUpMsgProc_Node52", "javaDict转换json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node53();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node52", "javaDict转换json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node53 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node53 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node54 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node54 是否登记通讯日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = "1".equals(__REQ__.getItem("_RegChlExchInfoFlag_"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node54", "是否登记通讯日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("PickUpMsgProc_Node54", "是否登记通讯日志", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node26();
                    case 1:
                        return new Node32();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node54", "是否登记通讯日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node56 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "PickUpMsgProc_Node56 更新渠道通讯信息");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = new JavaDict("PFRM_RSPND_STUS", ((JavaDict)__RSP__.getItem("SYS_HEAD")).getItem("ReturnStatus"), "PFRM_RSPND_CD", ((JavaDict)__RSP__.getItem("SYS_HEAD")).getItem("ReturnCode"), "PFRM_RSPND_INFO", ((JavaDict)__RSP__.getItem("SYS_HEAD")).getItem("ReturnMessage"), "RSPND_TS", __REQ__.getItem("_rsptimestamp_"), "MSG_DT", __REQ__.getItem("_workdate_"), "MSG_SN", __REQ__.getItem("mesgserno"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "UpdChlExchInfo";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    boolean _arg3_ = true;
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = new JavaDict("modulecode", "PUB", "transcode", "PRDTUPCHNATP");
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    TCResult result = B_DBUnityAltOper.B_DBUnityAltOpr(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("PickUpMsgProc_Node56", "更新渠道通讯信息", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node53());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("PickUpMsgProc_Node56", "更新渠道通讯信息", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node53());
                        }
                    }
                	gatherStat("PickUpMsgProc_Node56", "更新渠道通讯信息", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node26();
                    default:
                        return getExceptionHandler(new Node53());
                    }
                } catch (Throwable e) {
                	gatherStat("PickUpMsgProc_Node56", "更新渠道通讯信息", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node53());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    public static class RgstCommEvents extends BCScript {
        private INode startNode;
        public RgstCommEvents(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  登记通讯事件");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
        
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node12());
                log(LogLevel.INFO, "将默认异常委托到Node12节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node3 是否登记通讯日志 0-否；1-是");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = "1".equals(__REQ__.getItem("_RegChlExchInfoFlag_"));
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("RgstCommEvents_Node3", "是否登记通讯日志 0-否；1-是", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node13());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("RgstCommEvents_Node3", "是否登记通讯日志 0-否；1-是", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node11();
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node13());
                    }
                } catch (Throwable e) {
                	gatherStat("RgstCommEvents_Node3", "是否登记通讯日志 0-否；1-是", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node13());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node6 序列号操作");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "SEQ_MESGSERNO";
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    String _arg2_ = __REQ__.getItem("_DatabaseType_");
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    int _arg3_ = 10;
                    logVar(LogLevel.valueOf(0), "入参3", _arg3_);
                    TCResult result = P_Jdbc.getSequence(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("RgstCommEvents_Node6", "序列号操作", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node13());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("RgstCommEvents_Node6", "序列号操作", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node13());
                        }
                        __REQ__.setItem("mesgserno", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("RgstCommEvents_Node6", "序列号操作", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node13());
                    }
                } catch (Throwable e) {
                	gatherStat("RgstCommEvents_Node6", "序列号操作", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node13());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node7 获取数据库类型");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "DatabaseType";
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetSysConstant(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("RgstCommEvents_Node7", "获取数据库类型", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node13());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("RgstCommEvents_Node7", "获取数据库类型", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node13());
                        }
                        __REQ__.setItem("_DatabaseType_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("RgstCommEvents_Node7", "获取数据库类型", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node13());
                    }
                } catch (Throwable e) {
                	gatherStat("RgstCommEvents_Node7", "获取数据库类型", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node13());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node9 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node9 登记通讯事件");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = new JavaDict("MSG_DT", __REQ__.getItem("_workdate_"), "MSG_SN", __REQ__.getItem("mesgserno"), "ORG_NO", __REQ__.getItem("G_brahnum"), "CHNL_CD", __REQ__.getItem("G_channelcode"), "CHNL_DT", __REQ__.getItem("G_channeldate"), "CHNL_TM", __REQ__.getItem("G_channeltime"), "CHNL_SN", __REQ__.getItem("G_channelserno"), "CHNL_IP", __REQ__.getItem("G_channelip"), "PLATFM_SERVCD", "", "MODULE_CD", __REQ__.getItem("__MC__"), "TRAN_CD", __REQ__.getItem("__TC__"), "LOG_FILE_NM", __REQ__.getItem("__LOG__"), "PLATFM_WORK_DT", __REQ__.getItem("_workdate_"), "PFRM_BIZ_SEQ_NO", __REQ__.getItem("G_busiserno"), "PFRM_RSPND_STUS", "", "PFRM_RSPND_CD", "", "PFRM_RSPND_INFO", "", "REQ_TS", __REQ__.getItem("_timestamp_"), "RSPND_TS", "", "TX_MODE", "", "TX_PFRM_IP", "", "TX_PFRM_PORT", "", "TX_INSID_SEQ", "");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "RegChlExchInfo";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    boolean _arg3_ = true;
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = new JavaDict("modulecode", "PUB", "transcode", "PRDTUPCHNATP");
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    TCResult result = B_DBUnityAltOper.B_DBUnityAltOpr(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("RgstCommEvents_Node9", "登记通讯事件", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node13());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("RgstCommEvents_Node9", "登记通讯事件", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node13());
                        }
                    }
                	gatherStat("RgstCommEvents_Node9", "登记通讯事件", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node13());
                    }
                } catch (Throwable e) {
                	gatherStat("RgstCommEvents_Node9", "登记通讯事件", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node13());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node11 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node11 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node12 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node12 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("RgstCommEvents_Node12", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node13());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("RgstCommEvents_Node12", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node13());
                    }
                } catch (Throwable e) {
                	gatherStat("RgstCommEvents_Node12", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node13());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node13 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "RgstCommEvents_Node13 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
      
}     

