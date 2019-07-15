package EciComm.EciComm;

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
import tc.bank.product.B_BatchGetAbsAdeValue;
import tc.bank.product.B_ConstantOperate;
import tc.bank.product.B_DetailDangban;
import tc.bank.product.B_ParamMemory;
import tc.platform.P_Dict;
import tc.platform.P_Jdbc;
import tc.platform.P_JudgmentStatement;
import tc.platform.P_Logger;
import tc.platform.P_Object;
import tc.platform.P_Time;

/**
 * 应用描述：直连核心通讯 <br/>
 *
 * 交易描述：核心通讯组件 <br/>
 *
 * 创建时间：2017-07-11 02:02 <br/>
 *
 * 修改时间：2018-12-07 09:59 <br/>
 *
 * @author  <br/>
 * @version 1.0 <br/>
 *
 */
public class TEciComm extends JScript {
    protected INode startStep;
    protected long startTime;
     
    public TEciComm(JavaDict __REQ__, JavaDict __RSP__) {
        super(__REQ__, __RSP__);
    }
    
    protected INode getNextStep(int id) {
        INode step = null;
		switch (id) {
			case 1	: step = new Step1(); break;
			case 3	: step = new Step3(); break;
			case 4	: step = new Step4(); break;
		}
		return step;
	}
	
    @Override
    public synchronized INode execute() {
        String uuid = __REQ__.getStringItem("__UUID__", "");
        if (startStep == null) {
            startTime = System.currentTimeMillis();
            startStep = new Step3();
            __REQ__.setItem("__TRADENAME__", "核心通讯组件");
            log(LogLevel.INFO, "log start:" + uuid);
            log(LogLevel.INFO, "开始交易  EciComm:核心通讯组件");
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
            log(LogLevel.INFO, "结束交易 EciComm");

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
    protected class Step3 implements INode {
        protected BCScript bcScript;
        protected long startTime;
        @Override
        public INode execute() {
            JavaDict __BUILTIN__TEMP__;
            if (bcScript == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step3 拼包公共接入流程");
                __BUILTIN__TEMP__ = new JavaDict();
                bcScript = new bc.PublicPkg.Publicflow(__REQ__, __RSP__, __BUILTIN__TEMP__);
            } else {
                __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
            }
            
            INode node = bcScript.execute();
            if (node == EndNode.SUSPEND_END) {
                startStep = this;
                return node;
            }
            
              
            if (node instanceof EndNode) {
            	gatherStat("Step3", "拼包公共接入流程", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(4);
                case 1:
                    return getNextStep(1);
                }
            }
            return node;
        }
    }
    
    protected class Step4 implements INode {
        protected BCScript bcScript;
        protected long startTime;
        @Override
        public INode execute() {
            JavaDict __BUILTIN__TEMP__;
            if (bcScript == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step4 接出报文处理");
                __BUILTIN__TEMP__ = new JavaDict();
                bcScript = new bc.PublicPkg.PickUpMsgProc(__REQ__, __RSP__, __BUILTIN__TEMP__);
            } else {
                __BUILTIN__TEMP__ = bcScript.getBuiltinDict();
            }
            
            INode node = bcScript.execute();
            if (node == EndNode.SUSPEND_END) {
                startStep = this;
                return node;
            }
            
              
            gatherStat("Step4", "接出报文处理", 1, startTime);
            
            return node;
        }
    }
    
    protected class Step1 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step1 服务执行");
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
            if (node instanceof EndNode) {
            	gatherStat("Step1", "服务执行", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(4);
                case 1:
                    return getNextStep(4);
                }
            }
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
                setExceptionHandler(new Node24());
                log(LogLevel.INFO, "将默认异常委托到Node24节点");
                return new Node27();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node3 信息日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("p_servicecode")+__REQ__.getStringItem("G_SysId")+"|";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_Logger.info(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node3", "信息日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node3", "信息日志", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node3", "信息日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node4 系统码是否为空");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getStringItem("G_SysId")==null||"".equals(__REQ__.getStringItem("G_SysId"));
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node4", "系统码是否为空", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node4", "系统码是否为空", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node23();
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node4", "系统码是否为空", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node5 获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "te_info_comm_nosys";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("G_CommCode").toString()+__REQ__.getStringItem("G_SceneCode");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node5", "获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node5", "获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node5", "获取内存化数据", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node22();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node5", "获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node6 根据通讯码设置对应服务信息");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("serviceaccttype", ((List)((List)__REQ__.getItem("Result")).get(0)).get(1)), new JavaList("modulecode", ((List)((List)__REQ__.getItem("Result")).get(0)).get(2)), new JavaList("serverhost", ((List)((List)__REQ__.getItem("Result")).get(0)).get(3)), new JavaList("h_scenecode", ((List)((List)__REQ__.getItem("Result")).get(0)).get(4)), new JavaList("h_servicecode", __REQ__.getItem("G_CommCode")), new JavaList("h_msgtypeid", ((List)((List)__REQ__.getItem("Result")).get(0)).get(3)+"."+__REQ__.getItem("G_CommCode")+"."+((List)((List)__REQ__.getItem("Result")).get(0)).get(4)), new JavaList("h_timeout", ((List)((List)__REQ__.getItem("Result")).get(0)).get(5)));
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node6", "根据通讯码设置对应服务信息", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node6", "根据通讯码设置对应服务信息", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node6", "根据通讯码设置对应服务信息", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node7 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList("Result");
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node7", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node7", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node7", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node8 设置交易类型");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_excptype", __REQ__.getItem("serverhost")));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node8", "设置交易类型", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node8", "设置交易类型", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node8", "设置交易类型", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node9 获数据库类型");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "DatabaseType";
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = B_ConstantOperate.B_GetSysConstant(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node9", "获数据库类型", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node9", "获数据库类型", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("_DatabaseType_", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node9", "获数据库类型", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node9", "获数据库类型", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node10 判断交易路径是是否上送");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("G_tranpath")==null||"".equals(__REQ__.getItem("G_tranpath")+"");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node10", "判断交易路径是是否上送", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node10", "判断交易路径是是否上送", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node12();
                    case 1:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node10", "判断交易路径是是否上送", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node11 设置交易路径默认值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(2), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("G_tranpath", "mainorlogin"));
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node11", "设置交易路径默认值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node11", "设置交易路径默认值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node11", "设置交易路径默认值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node12 判断是否为原子服务");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("e_servicecode").equals("ATOMSVR.01");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node12", "判断是否为原子服务", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node12", "判断是否为原子服务", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node14();
                    case 1:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node12", "判断是否为原子服务", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node13 布尔判断框架");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = (new JavaList("CQMS_Q002.01", "CQMS_Q408.01", "CQMS_Q005.01", "CQMS_Q201.01", "CQMS_Q202.01", "CQMS_Q203.01", "CQMS_Q204.01", "CQMS_Q205.01", "CQMS_Q207.01", "PUB_M003.01", "CRMS_RQ007.01", "TELLER.TellerManage.01", "12003000006.02")).contains(((JavaDict)((JavaDict)__REQ__.getItem("_MsgDict_")).getItem("SYS_HEAD")).getItem("CommCode")+"."+((JavaDict)((JavaDict)__REQ__.getItem("_MsgDict_")).getItem("SYS_HEAD")).getItem("SceneCode"));
                    logVar(LogLevel.valueOf(2), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node13", "布尔判断框架", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node13", "布尔判断框架", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node16();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node13", "布尔判断框架", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node14 主机接口调用");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPPublicmethod.HostInterfaceInvoke(__REQ__, __RSP__, __BUILTIN__TEMP__);
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
                		gatherStat("Step1_Node14", "主机接口调用", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 0:
                            return new Node15();
                        case 1:
                            return new Node15();
                        }
                    }
                    return getExceptionHandler(new Node25());
                } catch (Throwable e) {
                	gatherStat("Step1_Node14", "主机接口调用", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node25());
                }
            }    
        }
        
        private class Node15 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node15 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node16 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node16 A_Out_Msg");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("h_servicecode").toString()+"."+__REQ__.getItem("h_scenecode").toString();
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("G_tranpath");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参3=__REQ__");
                    TCResult result = B_DetailDangban.B_B_DetailDangban(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node16", "A_Out_Msg", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node16", "A_Out_Msg", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("_Result_Map_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node16", "A_Out_Msg", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node28();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node16", "A_Out_Msg", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node17 是否主通讯");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("MainCmmSymb")!=null&&("Y".equals(__REQ__.getItem("MainCmmSymb")));
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node17", "是否主通讯", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node17", "是否主通讯", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node15();
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node17", "是否主通讯", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node18 获取响应时间");
                startTime = System.currentTimeMillis();
                try {
                    TCResult result = P_Time.getTime();
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node18", "获取响应时间", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node18", "获取响应时间", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("responsedate", outputParams.get(0));
                        logVar(LogLevel.valueOf(0), "出参0", outputParams.get(0));
                        __REQ__.setItem("responsetime", outputParams.get(1));
                        logVar(LogLevel.valueOf(0), "出参1", outputParams.get(1));
                    }
                	gatherStat("Step1_Node18", "获取响应时间", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node19();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node18", "获取响应时间", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node19 映射公有字典值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList("Ccy", "CashFlag", "AmtOfMny", "CstTp", "CstNum", "AcNum", "AcNm", "AcctBro", "AcctBroNm", "AcNum1", "AcNm1", "CtfTp", "CtfNum");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = B_BatchGetAbsAdeValue.B_getAdeValueByList(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node19", "映射公有字典值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node19", "映射公有字典值", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("_PubAde_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node19", "映射公有字典值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node20();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node19", "映射公有字典值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node20 是否查询类通讯");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = "03".equals(__REQ__.getItem("serviceaccttype"));
                    logVar(LogLevel.valueOf(2), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node20", "是否查询类通讯", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node20", "是否查询类通讯", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node21();
                    case 1:
                        return new Node15();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node20", "是否查询类通讯", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node21 流水日志插入");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = "IB_LOG_LOGMASTER_LOG";
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    JavaList _arg2_ = new JavaList(new JavaList("COMMSERIALNUM", __REQ__.getItem("G_channelserno")), new JavaList("AppNum", __REQ__.getItem("G_AppNum")), new JavaList("TranCode", __REQ__.getItem("G_trancode")), new JavaList("TRANNAME", __REQ__.getItem("G_trannm")), new JavaList("BranchNum", __REQ__.getItem("G_brahnum")), new JavaList("BranchName", __REQ__.getItem("G_brahnm")), new JavaList("TellerNum", __REQ__.getItem("G_tellerno")), new JavaList("USERNAME", __REQ__.getItem("G_tellername")), new JavaList("TRANDATE", __REQ__.getItem("responsedate")), new JavaList("TRANTIME", __REQ__.getItem("responsetime")), new JavaList("CoreDate", __REQ__.getItem("responsedate")), new JavaList("CoreTime", __REQ__.getItem("responsetime")), new JavaList("IP", __REQ__.getItem("G_channelip")), new JavaList("Oid", __REQ__.getItem("Oid")), new JavaList("TRANSTATE", ((JavaDict)((JavaDict)__REQ__.getItem("_Result_Map_")).getItem("SYS_HEAD")).getItem("ReturnStatus")), new JavaList("RETURNCODE", __REQ__.getItem("FaidNode")), new JavaList("RETURNDESC", ((JavaDict)__REQ__.getItem("_Result_Map_")).getItem("p_ret_code")), new JavaList("TranPath", __REQ__.getItem("TdgPiceDataStoePath")), new JavaList("BACKSERIALNUM", __REQ__.getItem("TaskNum")), new JavaList("TASKID", ((JavaDict)__REQ__.getItem("_Result_Map_")).getItem("TASK_ID")), new JavaList("IMAGEID", __REQ__.getItem("ImageID")), new JavaList("CURRENCY", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("Ccy")), new JavaList("CASHTRANSFERSIGN", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("CashFlag")), new JavaList("AmountMoney", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AmtOfMny")), new JavaList("CustType", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("CstTp")), new JavaList("CustNum", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("CstNum")), new JavaList("Account", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcNum")), new JavaList("AccountName", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcNm")), new JavaList("AccountBranch", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcctBro")), new JavaList("ACCOUNTBRANCHNAME", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcctBroNm")), new JavaList("RECIACCOUNTNUM", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcNum1")), new JavaList("RECIACCOUNTNAME", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("AcNm1")), new JavaList("CertType", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("CtfTp")), new JavaList("CertNum", ((JavaDict)__REQ__.getItem("_PubAde_")).getItem("CtfNum")), new JavaList("AGENTCERTTYPE", __REQ__.getItem("AgntCtfTp")), new JavaList("AGENTCERTNUM", __REQ__.getItem("AgntIdNum")), new JavaList("CellNum", ""), new JavaList("VeriResults", __REQ__.getItem("ChkState")), new JavaList("INITIATESERIALNUM", __REQ__.getItem("_BusiSerNo_")), new JavaList("PACKETSERIALNUM", __REQ__.getItem("G_BagSeqNo")), new JavaList("CUSTSERIALNUM", __REQ__.getItem("G_CustSerialNum")));
                    logVar(LogLevel.valueOf(2), "入参2", _arg2_);
                    boolean _arg3_ = true;
                    logVar(LogLevel.valueOf(0), "入参3", _arg3_);
                    TCResult result = P_Jdbc.preparedBdtInsert(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node21", "流水日志插入", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node21", "流水日志插入", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("_num_", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node21", "流水日志插入", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node15();
                    case 2:
                        return new Node15();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node21", "流水日志插入", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node22 设置返回码为TE0003");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(2), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "TES001"), new JavaList("p_ret_desc", "请检查通讯配置信息是否正确"), new JavaList("p_ret_status", "F"));
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node22", "设置返回码为TE0003", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node22", "设置返回码为TE0003", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node15();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node22", "设置返回码为TE0003", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node23 获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "te_info_comm";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("G_CommCode").toString()+__REQ__.getItem("G_SysId").toString()+__REQ__.getStringItem("G_SceneCode");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node23", "获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
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
                        	gatherStat("Step1_Node23", "获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node25());
                        }
                        __REQ__.setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(2), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step1_Node23", "获取内存化数据", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node22();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node23", "获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node24 取全局错误到容器");
                setExceptionHandler(null);
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "p_excptype";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "p_ret_code";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "p_ret_desc";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node24", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node24", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node25();
                    case 1:
                        return new Node25();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node24", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node25 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node25 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node26 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node26 设置默认返回码");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "000000"));
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node26", "设置默认返回码", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node26", "设置默认返回码", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node3();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node26", "设置默认返回码", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node27 创建拆包、拼包Dict");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("_Query_Map_", new JavaDict()), new JavaList("_Result_Map_", new JavaDict()));
                    logVar(LogLevel.valueOf(0), "入参1", _arg1_);
                    TCResult result = P_Object.createObject(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node27", "创建拆包、拼包Dict", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node27", "创建拆包、拼包Dict", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node26();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node27", "创建拆包、拼包Dict", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
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
                log(LogLevel.INFO, "Step1_Node28 设置返回数据");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("RspInfo", __REQ__.getItem("_Result_Map_"));
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node28", "设置返回数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node25());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node28", "设置返回数据", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node17();
                    default:
                        return getExceptionHandler(new Node25());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node28", "设置返回数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node25());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    

}   