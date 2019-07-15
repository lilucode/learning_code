package Cache.__Initializer__;

import cn.com.agree.afa.jcomponent.GlobalErrorHolder;
import cn.com.agree.afa.svc.javaengine.AppLogger.LogLevel;
import cn.com.agree.afa.svc.javaengine.BCScript;
import cn.com.agree.afa.svc.javaengine.EndNode;
import cn.com.agree.afa.svc.javaengine.INode;
import cn.com.agree.afa.svc.javaengine.JScript;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.annotation.ExecuteOnServiceStartup;
import cn.com.agree.afa.svc.javaengine.context.JavaContext;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import cn.com.agree.afa.util.ExceptionUtils;
import cn.com.agree.afa.util.future.IFuture;
import cn.com.agree.afa.util.future.IFutureListener;
import java.util.ArrayList;
import java.util.List;
import static cn.com.agree.afa.jcomponent.GlobalErrorHolder.setGlobalError;
import tc.bank.product.B_ParamMemory;

/**
 * 应用描述：内存初始化定时调度 <br/>
 *
 * 交易描述：默认服务初次化钩子场景 <br/>
 *
 * 创建时间：2018-11-27 11:28 <br/>
 *
 * 修改时间：2018-11-27 03:21 <br/>
 *
 * @author  <br/>
 * @version 1.0 <br/>
 *
 */
@ExecuteOnServiceStartup
public class T__Initializer__ extends JScript {
    protected INode startStep;
    protected long startTime;
     
    public T__Initializer__(JavaDict __REQ__, JavaDict __RSP__) {
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
            __REQ__.setItem("__TRADENAME__", "默认服务初次化钩子场景");
            log(LogLevel.INFO, "log start:" + uuid);
            log(LogLevel.INFO, "开始交易  __Initializer__:默认服务初次化钩子场景");
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
            log(LogLevel.INFO, "结束交易 __Initializer__");

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
                log(LogLevel.INFO, "Step1 init");
                startNode = new Node4();
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
            gatherStat("Step1", "init", 1, startTime);
            
            return node;
        }
        private class Node1 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node1 取全局错误到容器");
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
                    	gatherStat("Step1_Node1", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node1", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node3();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node1", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node2 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node2 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node3 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node3 异常结束");
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node4 implements INode {
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node4 开始");
                return new Node6();
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
            
            @Override
            public INode execute() {
                log(LogLevel.INFO, "Step1_Node5 参数内存化");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "1";
                    logVar(LogLevel.valueOf(2), "入参0", _arg0_);
                    TCResult result = B_ParamMemory.B_ParamterMemory(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("Step1_Node5", "参数内存化", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.INFO, "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                    
                	gatherStat("Step1_Node5", "参数内存化", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node2();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("Step1_Node5", "参数内存化", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
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
                log(LogLevel.INFO, "Step1_Node6 默认逻辑错误委托");
                setExceptionHandler(new Node1());
                log(LogLevel.INFO, "将默认异常委托到Node1节点");
                return new Node5();
            }    
        }
        
    
    }
    

}   