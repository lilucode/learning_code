package GateWay.AfaGateWay;

import cn.com.agree.afa.jcomponent.SdkHttp;
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
import tc.bank.product.B_GetServiceMethod;
import tc.bank.product.B_StringParser;
import tc.platform.P_HttpCodec;
import tc.platform.P_Logger;
import tc.platform.P_String;

/**
 * 应用描述：afa服务网关 <br/>
 *
 * 交易描述：afa接入网关 <br/>
 *
 * 创建时间：2018-12-01 02:38 <br/>
 *
 * 修改时间：2018-12-03 09:01 <br/>
 *
 * @author  <br/>
 * @version 1.0 <br/>
 *
 */
public class TAfaGateWay extends JScript {
    protected INode startStep;
    protected long startTime;
     
    public TAfaGateWay(JavaDict __REQ__, JavaDict __RSP__) {
        super(__REQ__, __RSP__);
    }
    
    protected INode getNextStep(int id) {
        INode step = null;
		switch (id) {
			case 1	: step = new Step1(); break;
		}
		return step;
	}
	
    @Override
    public synchronized INode execute() {
        String uuid = __REQ__.getStringItem("__UUID__", "");
        if (startStep == null) {
            startTime = System.currentTimeMillis();
            startStep = new Step1();
            __REQ__.setItem("__TRADENAME__", "afa接入网关");
            log(LogLevel.INFO, "log start:" + uuid);
            log(LogLevel.INFO, "开始交易  AfaGateWay:afa接入网关");
            printTrace();
            if (__REQ__.getStringItem("__SESSION_ID__") != null) {
            	log(LogLevel.INFO,
                "渠道请求ID:" + __REQ__.getStringItem("__SESSION_ID__") + ",线程名:"
                        + __REQ__.getStringItem("__THREAD_NAME__") + ",本机地址:"
                        + __REQ__.getStringItem("__LOCAL_IP__"));
            }
        } else {
            log(LogLevel.INFO, "唤醒交易");
        }

        INode step = startStep;
        while (canExecute(step)) {
            step = step.execute();
        }

        if (step == EndNode.SUSPEND_END) {
            log(LogLevel.INFO, "挂起交易");
        } else {
            log(LogLevel.INFO, "结束交易 AfaGateWay");

            long endTime = System.currentTimeMillis();
            long scriptExecutedTime = endTime - startTime;
            long appExecutedTime = endTime - getContext().getCreationTime();
            log(LogLevel.INFO, "脚本执行时间:" + scriptExecutedTime + "毫秒");
            log(LogLevel.INFO, "交易处理时间:" + appExecutedTime + "毫秒");
            __REQ__.setItem("__SCRIPT_EXECUTED_TIME__", scriptExecutedTime);
            __REQ__.setItem("__APP_EXECUTED_TIME__", appExecutedTime);
        }

        if (step instanceof EndNode) {
            return step;
        } else {
            return EndNode.EXCEPTION_END;
        }
    }
    protected class Step1 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step1 服务接入");
                startNode = new Node1();
                setExceptionHandler(null);
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            if (node == EndNode.SUSPEND_END) {
                startStep = this;
                return node;
            }
            gatherStat("Step1", "服务接入", 1, startTime);
            
            return node;
        }
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node4());
                log(LogLevel.INFO, "将默认异常委托到Node4节点");
                return new Node11();
            }    
        }
        
        private class Node3 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node3 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node4 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node4 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node5 截取JSON串");
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
                    	gatherStat("Step1_Node5", "截取JSON串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node5", "截取JSON串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node5", "截取JSON串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node5", "截取JSON串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node6 字节数组转换为字符串");
                startTime = System.currentTimeMillis();
                try {
                    byte[] _arg0_ = __REQ__.getItem("__RCVPCK__");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "UTF8";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToString(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node6", "字节数组转换为字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node6", "字节数组转换为字符串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __REQ__.setItem("JsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node6", "字节数组转换为字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node6", "字节数组转换为字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node7 HTTP响应报文拼包");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "200";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaDict _arg1_ = null;
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = __REQ__.getItem("_JsonStrRet_");
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    String _arg3_ = "UTF8";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = P_HttpCodec.packResponse(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node7", "HTTP响应报文拼包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node7", "HTTP响应报文拼包", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __RSP__.setItem("__SNDPCK__", outputParams.get(0));
                        logVar(LogLevel.valueOf(3), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node7", "HTTP响应报文拼包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node3();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node7", "HTTP响应报文拼包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node8 同步postJSON格式数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "GateWay_HTTP_SDK_OUT";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaDict _arg1_ = null;
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "http://"+__REQ__.getItem("service")+"!1.0.0!"+__REQ__.getItem("method")+"!1.0.0/"+__REQ__.getItem("service")+"/"+__REQ__.getItem("method");
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = __REQ__.getItem("JsonStr");
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    String _arg4_ = __REQ__.getItem("HTTP_REQUEST_CONTENT_CHARSET");
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    String _arg5_ = "[B";
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    long _arg6_ = 30000;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    TCResult result = SdkHttp.postJson(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node8", "同步postJSON格式数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node8", "同步postJSON格式数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __REQ__.setItem("_JsonDictRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node8", "同步postJSON格式数据", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node8", "同步postJSON格式数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node9 获取服务码与场景码");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("HTTP_URI");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = B_GetServiceMethod.B_GetServiceMethod(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node9", "获取服务码与场景码", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node9", "获取服务码与场景码", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __REQ__.setItem("service", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        __REQ__.setItem("method", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                    }
                	gatherStat("Step1_Node9", "获取服务码与场景码", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node9", "获取服务码与场景码", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node10 字节数组转换为字符串");
                startTime = System.currentTimeMillis();
                try {
                    byte[] _arg0_ = __REQ__.getItem("_JsonDictRet_");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "utf8";
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = B_StringParser.B_parseToString(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node10", "字节数组转换为字符串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
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
                        	gatherStat("Step1_Node10", "字节数组转换为字符串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node4());
                        }
                        __REQ__.setItem("_JsonStrRet_", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node10", "字节数组转换为字符串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node10", "字节数组转换为字符串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
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
                log(LogLevel.INFO, "Step1_Node11 调试日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    TCResult result = P_Logger.debug(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node11", "调试日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node4());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node11", "调试日志", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node4());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node11", "调试日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node4());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    

}   