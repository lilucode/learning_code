package Cache.__Finalizer__;

import cn.com.agree.afa.svc.javaengine.AppLogger.LogLevel;
import cn.com.agree.afa.svc.javaengine.BCScript;
import cn.com.agree.afa.svc.javaengine.EndNode;
import cn.com.agree.afa.svc.javaengine.INode;
import cn.com.agree.afa.svc.javaengine.JScript;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.annotation.ExecuteBeforeServiceDestroy;
import cn.com.agree.afa.svc.javaengine.context.JavaContext;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import cn.com.agree.afa.util.ExceptionUtils;
import cn.com.agree.afa.util.future.IFuture;
import cn.com.agree.afa.util.future.IFutureListener;
import java.util.ArrayList;
import java.util.List;
import static cn.com.agree.afa.jcomponent.GlobalErrorHolder.setGlobalError;

/**
 * 应用描述：内存初始化定时调度 <br/>
 *
 * 交易描述：默认服务销毁钩子场景 <br/>
 *
 * 创建时间：2019-05-23 05:29 <br/>
 *
 * 修改时间：2019-05-23 05:29 <br/>
 *
 * @author  <br/>
 * @version 1.0 <br/>
 *
 */
@ExecuteBeforeServiceDestroy
public class T__Finalizer__ extends JScript {
    protected INode startStep;
    protected long startTime;
     
    public T__Finalizer__(JavaDict __REQ__, JavaDict __RSP__) {
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
            __REQ__.setItem("__TRADENAME__", "默认服务销毁钩子场景");
            log(LogLevel.INFO, "log start:" + uuid);
            log(LogLevel.INFO, "开始交易  __Finalizer__:默认服务销毁钩子场景");
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
            log(LogLevel.INFO, "结束交易 __Finalizer__");

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
                log(LogLevel.INFO, "Step1 finalizer");
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
            gatherStat("Step1", "finalizer", 1, startTime);
            
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
                log(LogLevel.INFO, "Step1_Node2 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
    
    }
    

}   