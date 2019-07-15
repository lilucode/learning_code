package DB.DBComm;

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
import tc.PUBLIC.DB.DBUtil.A_BatchHandler;
import tc.bank.product.B_DBUnityAltOper;
import tc.bank.product.B_DBUnityAltParaOpt;
import tc.bank.product.B_DBUnityRptOper;
import tc.bank.product.B_ParamMemory;
import tc.platform.P_Dict;
import tc.platform.P_Jdbc;
import tc.platform.P_JudgmentStatement;
import tc.platform.P_Object;
import tc.platform.P_String;

/**
 * 应用描述：数据库穿透性服务 <br/>
 *
 * 交易描述：数据库原子服务 <br/>
 *
 * 创建时间：2016-11-15 09:12 <br/>
 *
 * 修改时间：2019-01-08 04:47 <br/>
 *
 * @author  <br/>
 * @version 1.0 <br/>
 *
 */
public class TDBComm extends JScript {
    protected INode startStep;
    protected long startTime;
     
    public TDBComm(JavaDict __REQ__, JavaDict __RSP__) {
        super(__REQ__, __RSP__);
    }
    
    protected INode getNextStep(int id) {
        INode step = null;
		switch (id) {
			case 1	: step = new Step1(); break;
			case 2	: step = new Step2(); break;
			case 3	: step = new Step3(); break;
			case 4	: step = new Step4(); break;
			case 5	: step = new Step5(); break;
			case 6	: step = new Step6(); break;
			case 7	: step = new Step7(); break;
		}
		return step;
	}
	
    @Override
    public synchronized INode execute() {
        String uuid = __REQ__.getStringItem("__UUID__", "");
        if (startStep == null) {
            startTime = System.currentTimeMillis();
            startStep = new Step1();
            __REQ__.setItem("__TRADENAME__", "数据库原子服务");
            log(LogLevel.INFO, "log start:" + uuid);
            log(LogLevel.INFO, "开始交易  DBComm:数据库原子服务");
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
            log(LogLevel.INFO, "结束交易 DBComm");

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
                log(LogLevel.INFO, "Step1 公共接入");
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
            	gatherStat("Step1", "公共接入", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(7);
                case 1:
                    return getNextStep(2);
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
                setExceptionHandler(new Node10());
                log(LogLevel.INFO, "将默认异常委托到Node10节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node3 接入报文处理");
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
                		gatherStat("Step1_Node3", "接入报文处理", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node4();
                        }
                    }
                    return getExceptionHandler(new Node10());
                } catch (Throwable e) {
                	gatherStat("Step1_Node3", "接入报文处理", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node10());
                }
            }    
        }
        
        private class Node4 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node4 登记通讯事件");
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
                		gatherStat("Step1_Node4", "登记通讯事件", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node5();
                        }
                    }
                    return getExceptionHandler(new Node10());
                } catch (Throwable e) {
                	gatherStat("Step1_Node4", "登记通讯事件", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node10());
                }
            }    
        }
        
        private class Node5 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node5 渠道检查");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPServiceManage.CheckChannel(__REQ__, __RSP__, __BUILTIN__TEMP__);
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
                		gatherStat("Step1_Node5", "渠道检查", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node6();
                        }
                    }
                    return getExceptionHandler(new Node10());
                } catch (Throwable e) {
                	gatherStat("Step1_Node5", "渠道检查", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node10());
                }
            }    
        }
        
        private class Node6 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node6 服务路由映射");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPServiceManage.ServiceRtMap(__REQ__, __RSP__, __BUILTIN__TEMP__);
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
                		gatherStat("Step1_Node6", "服务路由映射", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node7();
                        }
                    }
                    return getExceptionHandler(new Node10());
                } catch (Throwable e) {
                	gatherStat("Step1_Node6", "服务路由映射", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node10());
                }
            }    
        }
        
        private class Node7 implements INode {
            private BCScript bcScript;
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node7 服务开放渠道校验");
                try {
                    JavaDict __BUILTIN__TEMP__;
                    if (bcScript == null) {
                    	startTime = System.currentTimeMillis();
                        __BUILTIN__TEMP__ = new JavaDict();
                        bcScript = new bc.CIPServiceManage.ServiceChlCheck(__REQ__, __RSP__, __BUILTIN__TEMP__);
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
                		gatherStat("Step1_Node7", "服务开放渠道校验", ((EndNode) node).getType(), startTime);
                        switch (((EndNode) node).getType()) {
                        case 1:
                            return new Node8();
                        }
                    }
                    return getExceptionHandler(new Node10());
                } catch (Throwable e) {
                	gatherStat("Step1_Node7", "服务开放渠道校验", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);    
                    return getExceptionHandler(new Node10());
                }
            }    
        }
        
        private class Node8 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node8 容器间变量参数化拷贝");
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
                    	gatherStat("Step1_Node8", "容器间变量参数化拷贝", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node10());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node8", "容器间变量参数化拷贝", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    default:
                        return getExceptionHandler(new Node10());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node8", "容器间变量参数化拷贝", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node10());
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
                log(LogLevel.INFO, "Step1_Node9 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node10 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node10 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    
    protected class Step5 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step5 对内流程");
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
            	gatherStat("Step5", "对内流程", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(6);
                case 1:
                    return getNextStep(6);
                }
            }
            return node;
        }
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node20());
                log(LogLevel.INFO, "将默认异常委托到Node20节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node3 是否批量操作");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_opType_")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node3", "是否批量操作", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node3", "是否批量操作", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node5();
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node3", "是否批量操作", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node4 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node5 判断_busidatakey_");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_busidatakey_")!=null&&!"".equals(__REQ__.getItem("_busidatakey_"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node5", "判断_busidatakey_", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node5", "判断_busidatakey_", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node19();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node5", "判断_busidatakey_", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node6 获取内存化数据");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "t_arsm_datasqltype";
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("_busidatakey_");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = B_ParamMemory.B_GetCacheData(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node6", "获取内存化数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
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
                        	gatherStat("Step5_Node6", "获取内存化数据", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node11());
                        }
                        __REQ__.setItem("Oper", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step5_Node6", "获取内存化数据", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node17();
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node6", "获取内存化数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node7 字符串SWITCH匹配");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = (String)((List)((List)__REQ__.getItem("Oper")).get(0)).get(0);
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "D";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = "I";
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "U";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    String _arg4_ = "DA";
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    String _arg5_ = "DL";
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    String _arg6_ = "BI";
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    String _arg7_ = (String)((List)((List)__REQ__.getItem("Oper")).get(0)).get(0);
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = P_JudgmentStatement.switchCaseFrame(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node7", "字符串SWITCH匹配", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node7", "字符串SWITCH匹配", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    case 2:
                        return new Node8();
                    case 3:
                        return new Node8();
                    case 4:
                        return new Node8();
                    case 5:
                        return new Node8();
                    case 6:
                        return new Node12();
                    case 7:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node7", "字符串SWITCH匹配", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node8 数据库增删改");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("_busidatakey_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    boolean _arg3_ = true;
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    JavaDict _arg4_ = new JavaDict("modulecode", __REQ__.getItem("modulecode"), "transcode", __REQ__.getItem("transcode"));
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    TCResult result = B_DBUnityAltOper.B_DBUnityAltOpr(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node8", "数据库增删改", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
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
                        	gatherStat("Step5_Node8", "数据库增删改", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node11());
                        }
                    }
                	gatherStat("Step5_Node8", "数据库增删改", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    case 2:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node8", "数据库增删改", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node9 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node10 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node10 取全局错误到容器");
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
                    	gatherStat("Step5_Node10", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node10", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node11();
                    case 1:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node10", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node11 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node12 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node12 参数增删改统一接口(记录操作日志)");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(0), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("_busidatakey_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    boolean _arg2_ = true;
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", __REQ__.getItem("modulecode"), "transcode", __REQ__.getItem("transcode"));
                    logVar(LogLevel.valueOf(0), "入参3", _arg3_);
                    TCResult result = B_DBUnityAltParaOpt.B_DBUnityAltParaOpte(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node12", "参数增删改统一接口(记录操作日志)", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
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
                        	gatherStat("Step5_Node12", "参数增删改统一接口(记录操作日志)", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node11());
                        }
                    }
                	gatherStat("Step5_Node12", "参数增删改统一接口(记录操作日志)", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    case 2:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node12", "参数增删改统一接口(记录操作日志)", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node13 字符串SWITCH匹配");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = (String)((List)((List)__REQ__.getItem("Oper")).get(0)).get(0);
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "LI";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = (String)((List)((List)__REQ__.getItem("Oper")).get(0)).get(0);
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = null;
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    String _arg4_ = null;
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    String _arg5_ = null;
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    String _arg6_ = null;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    String _arg7_ = null;
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = P_JudgmentStatement.switchCaseFrame(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node13", "字符串SWITCH匹配", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node13", "字符串SWITCH匹配", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    case 2:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node13", "字符串SWITCH匹配", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node14 是否分页");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("CurtPage")!=null&&!"".equals(__REQ__.getItem("CurtPage"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node14", "是否分页", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node14", "是否分页", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node16();
                    case 1:
                        return new Node15();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node14", "是否分页", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node15 数据库查询");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("_busidatakey_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    JavaDict _arg2_ = new JavaDict("pagelist", new JavaList(4, toInt(__REQ__.getItem("CurtPage")), toInt(__REQ__.getItem("PageSize"))));
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", __REQ__.getItem("modulecode"), "transcode", __REQ__.getItem("transcode"));
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityRptOper.B_DBUnityRptOpr(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node15", "数据库查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
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
                        	gatherStat("Step5_Node15", "数据库查询", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node11());
                        }
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("RecordNum", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("totalNum", outputParams.get(2));
                        logVar(LogLevel.valueOf(4), "出参2", outputParams.get(2));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotNumOfPags", outputParams.get(3));
                        logVar(LogLevel.valueOf(4), "出参3", outputParams.get(3));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("PageNum", outputParams.get(4));
                        logVar(LogLevel.valueOf(4), "出参4", outputParams.get(4));
                    }
                	gatherStat("Step5_Node15", "数据库查询", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    case 2:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node15", "数据库查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node16 数据库查询");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("_busidatakey_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", __REQ__.getItem("modulecode"), "transcode", __REQ__.getItem("transcode"));
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityRptOper.B_DBUnityRptOpr(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node16", "数据库查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
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
                        	gatherStat("Step5_Node16", "数据库查询", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node11());
                        }
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("RecordNum", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("totalNum", outputParams.get(2));
                        logVar(LogLevel.valueOf(4), "出参2", outputParams.get(2));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotNumOfPags", outputParams.get(3));
                        logVar(LogLevel.valueOf(4), "出参3", outputParams.get(3));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("PageNum", outputParams.get(4));
                        logVar(LogLevel.valueOf(4), "出参4", outputParams.get(4));
                    }
                	gatherStat("Step5_Node16", "数据库查询", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    case 2:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node16", "数据库查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
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
                log(LogLevel.INFO, "Step5_Node17 数据库没有此服务码");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "TEDCommCode"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step5_Node17", "数据库没有此服务码", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node17", "数据库没有此服务码", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node17", "数据库没有此服务码", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node18 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node18 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node19 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node19 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node20 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node20 取全局错误到容器");
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
                    	gatherStat("Step5_Node20", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node11());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step5_Node20", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node21();
                    case 1:
                        return new Node21();
                    default:
                        return getExceptionHandler(new Node11());
                    }
                } catch (Throwable e) {
                	gatherStat("Step5_Node20", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node11());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node21 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step5_Node21 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    
    protected class Step4 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step4 虚接口流程");
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
            	gatherStat("Step4", "虚接口流程", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(5);
                case 1:
                    return getNextStep(5);
                }
            }
            return node;
        }
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node17());
                log(LogLevel.INFO, "将默认异常委托到Node17节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node3 是否批量操作");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_opType_")!=null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node3", "是否批量操作", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node3", "是否批量操作", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node5();
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node3", "是否批量操作", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node4 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node5 判断_busidatakey_");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_busidatakey_")!=null&&!"".equals(__REQ__.getItem("_busidatakey_"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node5", "判断_busidatakey_", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node5", "判断_busidatakey_", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node6();
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node5", "判断_busidatakey_", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node6 服务码分割");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("e_servicecode");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "_";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_String.split(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node6", "服务码分割", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step4_Node6", "服务码分割", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                        __REQ__.setItem("moduleTran", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step4_Node6", "服务码分割", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node6", "服务码分割", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node7 截取BusiOper");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("BusiOper");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    int _arg1_ = 0;
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    int _arg2_ = 1;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    TCResult result = P_String.subStr(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node7", "截取BusiOper", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step4_Node7", "截取BusiOper", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                        __REQ__.setItem("BusiOperNew", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                    }
                	gatherStat("Step4_Node7", "截取BusiOper", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node16();
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node7", "截取BusiOper", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node8 判断BusiOper");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("BusiOperNew");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = "D";
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    String _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    String _arg3_ = "A";
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    String _arg4_ = null;
                    logVar(LogLevel.valueOf(4), "入参4", _arg4_);
                    String _arg5_ = "M";
                    logVar(LogLevel.valueOf(4), "入参5", _arg5_);
                    String _arg6_ = null;
                    logVar(LogLevel.valueOf(4), "入参6", _arg6_);
                    String _arg7_ = "Q";
                    logVar(LogLevel.valueOf(4), "入参7", _arg7_);
                    TCResult result = P_JudgmentStatement.switchCaseFrame(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node8", "判断BusiOper", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node8", "判断BusiOper", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node16();
                    case 1:
                        return new Node9();
                    case 3:
                        return new Node9();
                    case 5:
                        return new Node9();
                    case 7:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node8", "判断BusiOper", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node9 参数增删改统一接口(记录操作日志)");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("BusiOper");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    boolean _arg2_ = true;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", ((List)__REQ__.getItem("moduleTran")).get(0), "transcode", ((List)__REQ__.getItem("moduleTran")).get(1));
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityAltParaOpt.B_DBUnityAltParaOpte(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node9", "参数增删改统一接口(记录操作日志)", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step4_Node9", "参数增删改统一接口(记录操作日志)", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                    }
                	gatherStat("Step4_Node9", "参数增删改统一接口(记录操作日志)", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    case 2:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node9", "参数增删改统一接口(记录操作日志)", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node10 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node11 取全局错误到容器");
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
                    	gatherStat("Step4_Node11", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node11", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node12();
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node11", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node12 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node13 是否分页");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("CurtPage")!=null&&!"".equals(__REQ__.getItem("CurtPage"));
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node13", "是否分页", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node13", "是否分页", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node15();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node13", "是否分页", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node14 数据分页查询");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("BusiOper");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = new JavaDict("pagelist", new JavaList(4, toInt(__REQ__.getItem("CurtPage")), toInt(__REQ__.getItem("PageSize"))));
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", ((List)__REQ__.getItem("moduleTran")).get(0), "transcode", ((List)__REQ__.getItem("moduleTran")).get(1));
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityRptOper.B_DBUnityRptOpr(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node14", "数据分页查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step4_Node14", "数据分页查询", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("RecordNum", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotalRcrdNum", outputParams.get(2));
                        logVar(LogLevel.valueOf(4), "出参2", outputParams.get(2));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotalPage", outputParams.get(3));
                        logVar(LogLevel.valueOf(4), "出参3", outputParams.get(3));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("CurtPage", outputParams.get(4));
                        logVar(LogLevel.valueOf(4), "出参4", outputParams.get(4));
                    }
                	gatherStat("Step4_Node14", "数据分页查询", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    case 2:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node14", "数据分页查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node15 数据查询");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    String _arg1_ = __REQ__.getItem("BusiOper");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaDict _arg2_ = null;
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    JavaDict _arg3_ = new JavaDict("modulecode", ((List)__REQ__.getItem("moduleTran")).get(0), "transcode", ((List)__REQ__.getItem("moduleTran")).get(1));
                    logVar(LogLevel.valueOf(4), "入参3", _arg3_);
                    TCResult result = B_DBUnityRptOper.B_DBUnityRptOpr(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node15", "数据查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step4_Node15", "数据查询", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("Result", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0", outputParams.get(0));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("RecordNum", outputParams.get(1));
                        logVar(LogLevel.valueOf(4), "出参1", outputParams.get(1));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotalRcrdNum", outputParams.get(2));
                        logVar(LogLevel.valueOf(4), "出参2", outputParams.get(2));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("TotalPage", outputParams.get(3));
                        logVar(LogLevel.valueOf(4), "出参3", outputParams.get(3));
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("CurtPage", outputParams.get(4));
                        logVar(LogLevel.valueOf(4), "出参4", outputParams.get(4));
                    }
                	gatherStat("Step4_Node15", "数据查询", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    case 2:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node15", "数据查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node16 操作标志无效");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "PUB016"), new JavaList("p_ret_desc", "操作标志无效"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step4_Node16", "操作标志无效", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node16", "操作标志无效", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node16", "操作标志无效", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step4_Node17 取全局错误到容器");
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
                    	gatherStat("Step4_Node17", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step4_Node17", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node18();
                    case 1:
                        return new Node18();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step4_Node17", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node18 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step4_Node18 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    
    protected class Step3 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step3 特殊操作");
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
            	gatherStat("Step3", "特殊操作", ((EndNode) node).getType(), startTime);
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
                log(LogLevel.INFO, "Step3_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node13());
                log(LogLevel.INFO, "将默认异常委托到Node13节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node3 是否批量操作");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_opType_")!=null&&__REQ__.getItem("_opType_").toString().equals("BA");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node3", "是否批量操作", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node3", "是否批量操作", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node9();
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node3", "是否批量操作", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node4 批量数据库操作");
                startTime = System.currentTimeMillis();
                try {
                    JavaList _arg0_ = __REQ__.getItem("_dataList_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("_sql_");
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    JavaList _arg2_ = __REQ__.getItem("_colist_");
                    logVar(LogLevel.valueOf(4), "入参2", _arg2_);
                    TCResult result = A_BatchHandler.A_BatchInsert(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node4", "批量数据库操作", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node4", "批量数据库操作", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node7();
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node4", "批量数据库操作", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node5 数据库提交");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_Jdbc.commit(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node5", "数据库提交", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node5", "数据库提交", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node5", "数据库提交", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node6 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node7 数据库回滚");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    TCResult result = P_Jdbc.rollBack(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node7", "数据库回滚", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node7", "数据库回滚", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node7", "数据库回滚", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node8 数据库操作失败");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "TED005"), new JavaList("p_ret_desc", "数据操作失败"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node8", "数据库操作失败", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node8", "数据库操作失败", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node8", "数据库操作失败", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node9 是否直接执行sql查询");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("_opType_")!=null&&__REQ__.getItem("_opType_").toString().equals("RA");
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    TCResult result = P_JudgmentStatement.boolFrame(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node9", "是否直接执行sql查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node9", "是否直接执行sql查询", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node6();
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node9", "是否直接执行sql查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node10 数据查询");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = null;
                    logVar(LogLevel.valueOf(0), "入参0", _arg0_);
                    String _arg1_ = __REQ__.getItem("_sqlcmd_");
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    int _arg2_ = 0;
                    logVar(LogLevel.valueOf(0), "入参2", _arg2_);
                    TCResult result = P_Jdbc.dmlSelect(_arg0_, _arg1_, _arg2_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step3_Node10", "数据查询", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
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
                        	gatherStat("Step3_Node10", "数据查询", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node12());
                        }
                        ((JavaDict)__REQ__.getItem("_Result_Map_")).setItem("Result", outputParams.get(1));
                        logVar(LogLevel.valueOf(2), "出参1", outputParams.get(1));
                    }
                	gatherStat("Step3_Node10", "数据查询", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node11();
                    case 1:
                        return new Node6();
                    case 2:
                        return new Node11();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node10", "数据查询", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node11 取全局错误到容器");
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
                    	gatherStat("Step3_Node11", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node11", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node12();
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node11", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
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
                log(LogLevel.INFO, "Step3_Node12 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node13 取全局错误到容器");
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
                    	gatherStat("Step3_Node13", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step3_Node13", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node14();
                    case 1:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("Step3_Node13", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step3_Node14 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    
    protected class Step7 implements INode {
        protected BCScript bcScript;
        protected long startTime;
        @Override
        public INode execute() {
            JavaDict __BUILTIN__TEMP__;
            if (bcScript == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step7 接出报文处理");
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
            
              
            gatherStat("Step7", "接出报文处理", 1, startTime);
            
            return node;
        }
    }
    
    protected class Step6 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step6 服务返回信息设置");
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
            	gatherStat("Step6", "服务返回信息设置", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(7);
                case 1:
                    return getNextStep(7);
                }
            }
            return node;
        }
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node5());
                log(LogLevel.INFO, "将默认异常委托到Node5节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node3 设置返回数据");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0=__RSP__");
                    JavaList _arg1_ = new JavaList("RspInfo", __REQ__.getItem("_Result_Map_"));
                    logVar(LogLevel.valueOf(2), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step6_Node3", "设置返回数据", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node6());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step6_Node3", "设置返回数据", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node6());
                    }
                } catch (Throwable e) {
                	gatherStat("Step6_Node3", "设置返回数据", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node6());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node4 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node4 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node5 取全局错误到容器");
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
                    	gatherStat("Step6_Node5", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node6());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step6_Node5", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node6();
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node6());
                    }
                } catch (Throwable e) {
                	gatherStat("Step6_Node5", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node6());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step6_Node6 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    
    protected class Step2 implements INode {
        protected INode startNode;
        protected long startTime;
        @Override
        public INode execute() {
            if (startNode == null) {
            	startTime = System.currentTimeMillis();
                log(LogLevel.INFO, "Step2 服务初始信息准备");
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
            	gatherStat("Step2", "服务初始信息准备", ((EndNode) node).getType(), startTime);
            }
            
            if (node instanceof EndNode) {
                switch (((EndNode) node).getType()) {
                case 0:
                    return getNextStep(6);
                case 1:
                    return getNextStep(3);
                }
            }
            return node;
        }
        private class Node1 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node6());
                log(LogLevel.INFO, "将默认异常委托到Node6节点");
                return new Node3();
            }    
        }
        
        private class Node3 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node3 创建拆包、拼包Dict");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("_Query_Map_", new JavaDict()), new JavaList("_Result_Map_", new JavaDict()));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Object.createObject(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step2_Node3", "创建拆包、拼包Dict", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node7());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step2_Node3", "创建拆包、拼包Dict", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node7());
                    }
                } catch (Throwable e) {
                	gatherStat("Step2_Node3", "创建拆包、拼包Dict", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node7());
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
                log(LogLevel.INFO, "Step2_Node4 设置默认返回码");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("_Result_Map_");
                    logVar(LogLevel.valueOf(4), "入参0", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("p_ret_code", "000000"));
                    logVar(LogLevel.valueOf(4), "入参1", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step2_Node4", "设置默认返回码", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node7());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step2_Node4", "设置默认返回码", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node7());
                    }
                } catch (Throwable e) {
                	gatherStat("Step2_Node4", "设置默认返回码", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node7());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node5 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node6 取全局错误到容器");
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
                    	gatherStat("Step2_Node6", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node7());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step2_Node6", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 0:
                        return new Node7();
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node7());
                    }
                } catch (Throwable e) {
                	gatherStat("Step2_Node6", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node7());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step2_Node7 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    

}   