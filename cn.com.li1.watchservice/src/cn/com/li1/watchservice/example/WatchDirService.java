package cn.com.li1.watchservice.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WatchDirService {

	private WatchService watchService;
    private boolean notDone = true;
 
    public WatchDirService(String dirPath){
        init(dirPath);
    }
 
    private void init(String dirPath) {
        Path path = Paths.get(dirPath);
        try {
            watchService = FileSystems.getDefault().newWatchService();  //创建watchService
            path.register(watchService, 
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE); //注册需要监控的事件,ENTRY_CREATE 文件创建,ENTRY_MODIFY 文件修改,ENTRY_MODIFY 文件删除
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void start(){
        System.out.print("watch...");
        while (notDone){
            try {
                WatchKey watchKey = watchService.poll(Config.POLL_TIME_OUT, TimeUnit.SECONDS); 
                if(watchKey != null){
                    List<WatchEvent<?>> events = watchKey.pollEvents();  //获取所有得事件
                    for (WatchEvent event : events){
                        WatchEvent.Kind<?> kind = event.kind(); 
                        if (kind == StandardWatchEventKinds.OVERFLOW){
                            //当前磁盘不可用
                            continue;
                        }
                        WatchEvent<Path> ev = event;
                        Path path = ev.context();
                        if(kind == StandardWatchEventKinds.ENTRY_CREATE){
                            System.out.println("create " + path.getFileName());
                        }else if(kind == StandardWatchEventKinds.ENTRY_MODIFY){
                            System.out.println("modify " + path.getFileName());
                        }else if(kind == StandardWatchEventKinds.ENTRY_DELETE){
                            System.out.println("delete " + path.getFileName());
                        }
                    }
                    if(!watchKey.reset()){ 
                        //已经关闭了进程
                        System.out.println("exit watch server");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}
