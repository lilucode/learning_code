package cn.com.agree.ab.a5.runtime.lfc;

/**
 * DNC容器接口
 */
public interface IDncContainer
{
    public DataNameCollection getDnc();
    
    public DataNameCollection getDnc(String path);
    
    public boolean supportDnc(String path);

}
