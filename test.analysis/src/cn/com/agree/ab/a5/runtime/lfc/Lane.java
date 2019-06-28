package cn.com.agree.ab.a5.runtime.lfc; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @author wangyang  E-mail: wang.yang@cfischina.com
 * 
 * @version 创建时间：2015年4月13日  下午4:09:00 
 */
public class Lane
{
    private String name ;
    
    private String width;
    
    private List<String> containsNodesId = new ArrayList<String>();
    
    private Map<String,Object> data = new HashMap<String,Object>();

    public Map<String, Object> getData() {
        return data;
    }
    
    public void addData(String key,Object value){
        data.put(key, value);
    }
    
    public void clearData(){
        data.clear();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public List<String> getContainsNodesId()
    {
        return containsNodesId;
    }
    
    public void addNodeId(String nodeId)
    {
        containsNodesId.add(nodeId);
    }
    
    
    
    
}
