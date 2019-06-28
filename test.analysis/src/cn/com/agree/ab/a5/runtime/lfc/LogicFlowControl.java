/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年3月11日 下午4:24:19
 * 
 */
public class LogicFlowControl implements IDncContainer
{
    private Map<String, ArgElement> inArgMap = new ConcurrentHashMap<String, ArgElement>();

    private Map<String, ArgElement> outArgMap = new ConcurrentHashMap<String, ArgElement>();
    
    private Map<String, ComponentElement> componentElementMap = new ConcurrentHashMap<String, ComponentElement>();

    private Map<String, String> endValueMap = new ConcurrentHashMap<String, String>();

    private List<Object> nodesList;

    private String path;

    private String startNodeId;
    
    private DataNameCollection dnc;
    
    private List<String> conditionList = Collections
            .synchronizedList(new ArrayList<String>());
    
    private boolean runOnServer;
    
    public Boolean getRunOnServer()
    {
        return runOnServer;
    }

    public void setRunOnServer(Boolean runOnServer)
    {
        this.runOnServer = runOnServer;
    }

    public void addCondition(String ade)
    {
        conditionList.add(ade);
    }

    public List<String> getConditionList()
    {
        return conditionList;
    }
    
    private List<Lane> lanes = new ArrayList<Lane>();
    
    public List<Lane> getLanes()
    {
        return lanes;
    }
    
    public void addLane(Lane lane)
    {
        lanes.add(lane);
    }

    public LogicFlowControl()
    {
    }
    
    private VarMap varMap;
    
    public VarMap getVarMap()
    {
        return varMap;
    }

    public void addVarMap(String expression,Object value)
    {
        if (varMap==null)
        {
            varMap = new VarMap();
        }
        varMap.put(expression, value);
    }
    /**
     * 
     */
    public void addComponentElement(ComponentElement ce)
    {
        componentElementMap.put(ce.getId(), ce);
    }

    /**
     * 
     */
    public void addEndValue(String id, String value)
    {
        endValueMap.put(id, value);
    }
    
    public String getEndValue(String id)
    {
        return endValueMap.get(id);
    }

    /**
     * 
     */
    public Object getNode(String nodeId)
    {
        if (componentElementMap.containsKey(nodeId))
        {
            return componentElementMap.get(nodeId);
        }
        return null;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * 
     */
    public String getStartNodeId()
    {
        return startNodeId;
    }

    /**
     * 
     */
    public List<Object> listNodes()
    {
        if (nodesList == null)
        {
            List<Object> candidate = new ArrayList<Object>();
            // x.
            candidate.addAll(componentElementMap.values());
            // x. 以后可能还会有其他的元素加入
            //
            // $.
            nodesList = candidate;
        }
        return nodesList;
    }

    /**
     * @param path
     *        the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * 
     */
    public void setStartNodeId(String startNodeId)
    {
        this.startNodeId = startNodeId;
    }
    
    public void setDnc(DataNameCollection dnc)
    {
        this.dnc = dnc;
    }
    
    @Override
    public DataNameCollection getDnc()
    {
        return dnc;
    }

    @Override
    public DataNameCollection getDnc(String path)
    {
        return dnc.getDnc(path);
    }

    @Override
    public boolean supportDnc(String path)
    {
        return dnc.supportDnc(path);
    }
    
    public void addInArg(ArgElement ae)
    {
        inArgMap.put(ae.getName(), ae);
    }
    
    /**
     * @return the inArgMap
     */
    public Map<String, ArgElement> getInArgMap()
    {
        return inArgMap;
    }
    
    public void addOutArg(ArgElement ae)
    {
        outArgMap.put(ae.getName(), ae);
    }
    
    /**
     * @return the outArgMap
     */
    public Map<String, ArgElement> getOutArgMap()
    {
        return outArgMap;
    }

}
