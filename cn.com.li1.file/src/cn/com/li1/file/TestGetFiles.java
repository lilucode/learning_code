package cn.com.li1.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TestGetFiles {

	/**
     * 获取一个文件夹下的所有文件全路径
     * @param path
     * @param listFileName
     */
    public static void getAllFileName(String path,ArrayList<String> listFileName){
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null){
            String [] completNames = new String[names.length];
            for(int i=0;i<names.length;i++){
                completNames[i]=path+names[i];
            }
            listFileName.addAll(Arrays.asList(completNames));
        }
        for(File a:files){
            if(a.isDirectory()){//如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
                getAllFileName(a.getAbsolutePath()+"\\",listFileName);
            }
        }
    }

    public static void main(String[] args){
        ArrayList<String> listFileName = new ArrayList<String>(); 
        getAllFileName("E:\\testdownload\\",listFileName);
        for(String name:listFileName){
        	System.out.println(name);
//            if(name.contains(".txt")||name.contains(".properties")){
//            }
        }
    }
}
