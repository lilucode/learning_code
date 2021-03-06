package cn.com.agree.ab.transfer.runtime.lfc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DNC含有成员adeNameList，includedDncList，path 
 * 提供方法getMergedAdeNameList()，getPath()，supportDnc(String dncName)
 */
public class DataNameCollection //implements IDncContainer
{
//    private String path;

    private List<String> ade = new ArrayList<String>();

//    private List<DataNameCollection> includedDncList = new ArrayList<DataNameCollection>();

//    public String getPath()
//    {
//        return path;
//    }
//
//    public void setPath(String path)
//    {
//        this.path = path;
//    }

    public List<String> getAde()
    {
        return ade;
    }

    public void setAde(List<String> ade)
    {
        this.ade = ade;
    }

//    public List<DataNameCollection> getIncludedDncList()
//    {
//        return includedDncList;
//    }
//
//    public void setIncludedDncList(List<DataNameCollection> includedDncList)
//    {
//        this.includedDncList = includedDncList;
//    }
//
//    public List<String> getMergedAdeNameList()
//    {
//        Set<String> adeSet = new HashSet<String>(ade);
//        for (DataNameCollection dnc : this.getIncludedDncList())
//        {
//            adeSet.addAll(dnc.getMergedAdeNameList());
//        }
//        return new ArrayList<String>(adeSet);
//    }
//
//    @Override
//    public DataNameCollection getDnc(String path)
//    {
//        if (this.path.equals(path))
//        {
//            return this;
//        }
//        for (DataNameCollection dnc : this.getIncludedDncList())
//        {
//            if (dnc.getPath().equals(path))
//            {
//                return dnc;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public DataNameCollection getDnc()
//    {
//        return this;
//    }
//
//    @Override
//    public boolean supportDnc(String path)
//    {
//        if (this.path.equals(path))
//        {
//            return true;
//        }
//        for (DataNameCollection dnc : this.getIncludedDncList())
//        {
//            if (dnc.supportDnc(path))
//            {
//                return true;
//            }
//        }
//        return false;
//    }

}