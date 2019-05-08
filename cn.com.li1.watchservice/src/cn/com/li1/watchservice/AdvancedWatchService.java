package cn.com.li1.watchservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JDK7 WatchService封装
 * WatchService太灵敏了，flush一下都能够触发，采用队列将触发频率限制到1s
 * 
 * 注意：系统对WatchService能够监控的文件总数有限制。
 * 如果监听的文件超过限制，会报java.io.IOException: User limit of inotify watches reached异常
 * 在linux中，通过命令cat /proc/sys/fs/inotify/max_user_watches查看系统限制
 * 修改/etc/sysctl.conf文件中下面的这一行（没有就新增）
 * 	fs.inotify.max_user_watches=50000
 * 修改配置需重启服务器才生效
 * 建议改成50000，每个1会占用1k内存，50000全部用完的话，大概会多用50M内存，完全可以接受。
 */
public class AdvancedWatchService {
	private static final Log logger = LogFactory.getLog(AdvancedWatchService.class);
	
	private final static Object EMPTY = new String("EMPTY VALUE");

	private class WrappedListener {
		final IWatchServiceFilter filter;

		final IWatchServiceListener listener;

		final BlockingQueue<File> files = new LinkedBlockingQueue<File>();

		WrappedListener(IWatchServiceListener listener, IWatchServiceFilter filter) {
			this.listener = listener;
			this.filter = filter;
		}
		
		void update(File file) {
			boolean acceptable = false;
			String relativePath = toRelative(file.getAbsoluteFile().toPath());
			try {
				acceptable = filter.accept(file, allFileSet.contains(relativePath), allDirSet.contains(relativePath));
			} catch (Exception e) {
				logger.error("文件过滤失败：" + file + 
						"，监听器：" + listener +
						"，过滤器：" + filter + 
						"，原因:" + e.getMessage(), e);
			}
			if (acceptable && !files.contains(file)) {
				if (logger.isDebugEnabled()) {
					logger.debug("检测到文件更新：" + file + 
							"，监听器：" + listener);
				}
				files.add(file);
			}
			
			if (files.size() > 0) {
				addActiveListener(this);
			}
			// 清理
			if (allFileSet.contains(relativePath) && !file.exists()) {
				allFileSet.remove(relativePath);
			}
			if (allDirSet.contains(relativePath) && !file.exists()) {
				allDirSet.remove(relativePath);
			}
		}

		void fire() {
			Set<File> fileSet = new HashSet<File>();
			files.drainTo(fileSet);
			try {
				if (logger.isInfoEnabled()) {
					logger.info("通知监听器：" + listener + 
							"，更新文件：" + fileSet);
				}
				listener.handle(fileSet);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		@Override
		public String toString() {
			return listener.toString();
		}

	}

	private class Node {
		final Path path;
		final Node parent;
		private List<WrappedListener> listeners;
		private Map<Path, Node> children = new HashMap<Path, Node>(0);
		/**
		 * 用来标记对应的节点是否已经使用WatchService进行监听
		 */
		boolean registered = false;
		
		public void setRegistered(boolean registered) {
			this.registered = registered;
		}
		
		public boolean isRegistered(){
			return registered;
		}
		
		Node(Node parent, Path path) {
			this.parent = parent;
			this.path = path;
		}

		synchronized Node getChild(Path name) {
			return children.get(name);
		}

		synchronized void addChild(Path name, Node child) {
			children.put(name, child);
		}

		synchronized void update(File file) {
			if (listeners != null && listeners.size() != 0) {
				for (WrappedListener listener : listeners) {
					listener.update(file);
				}
			}
			if (parent != null) parent.update(file);
		}

		synchronized void addListener(WrappedListener listener) {
			if (listeners == null) {
				listeners = new CopyOnWriteArrayList<WrappedListener>();
			}
			listeners.add(listener);
		}

		synchronized void removeListener(WrappedListener listener) {
			listeners.remove(listener);
		}
	}
	
	private class ExtensionsFileVisitor implements FileVisitor<Path> {
		
		private Set<String> extensions = new HashSet<String>();
		
		public ExtensionsFileVisitor(String ... extensions) {
			for (String extension : extensions) {
				this.extensions.add(extension);
			}
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (Files.isHidden(dir) || dir.getFileName().toString().startsWith("."))
            {
                return FileVisitResult.SKIP_SUBTREE;
            }
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			if (!Files.isHidden(file) && !file.getFileName().toString().startsWith("."))
            {
				if (extensions.isEmpty()) {
					allFileSet.add(toRelative(file));
				} else {
					if (extensions.contains(getExtension(file.toFile()))) {
						allFileSet.add(toRelative(file));
					}
				}
            }
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			logger.error("visit file[" + file.toString() + "]failed:"+ exc.getMessage(), exc);
			throw exc;
//			return null;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
		private String getExtension(File file) {
			if (!file.isFile()) return "";
			
			String s = file.getAbsolutePath();
			int dotIndex = s.lastIndexOf(".");
			if (dotIndex != -1) {
				return s.substring(dotIndex + 1);
			}
			
			return "";
		}
		
	}

	/**
	 * 默认5个线程:<br/>
	 * - 1个操作WatchService<br/>
	 * - 1个定时检查监听器队列是否有更新<br/>
	 * - 3个调用队列有更新的监听器（）
	 */
	private final static int CORE_POOL_SIZE = 5;

	private final Path rootPath;
	private final Node rootNode;
	private final WatchService watchService;
	private final ExecutorService service;
	private final Map<WrappedListener, Object> activeListeners = new ConcurrentHashMap<WrappedListener, Object>();
	private final Map<IWatchServiceListener, WrappedListener> listenerMap = new ConcurrentHashMap<IWatchServiceListener, WrappedListener>();
	private final Map<WatchKey, Node> keyNodeMap = new ConcurrentHashMap<WatchKey, Node>();
	
	private final Set<String> allFileSet = new CopyOnWriteArraySet<String>();
	private final Set<String> allDirSet = new CopyOnWriteArraySet<String>();

	private void pollEvent() throws InterruptedException {
		WatchKey key = watchService.take();
		Node node = keyNodeMap.get(key);
		Set<File> changeFileSet = new HashSet<File>();
		for (WatchEvent<?> event : key.pollEvents()) {
			WatchEvent.Kind<?> kind = event.kind();
			if (kind == StandardWatchEventKinds.OVERFLOW) {
				continue;
			}
			Path eventName = (Path) event.context();
			Path path = node.path.resolve(eventName);

			if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)) {
				if (Files.isDirectory(path)) {
					try {
						Path name = path.getFileName();
						Node child = node.getChild(name);
						if (child == null) {
							child = new Node(node, path);
							node.addChild(name, child);
							registerEvent(child);
						} else if(!child.isRegistered()){
							// 因为节点可能已经存在了，但是并没有使用WatchService进行监听
							registerEvent(child);
						}
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				} else if (Files.isRegularFile(path)) {
					allFileSet.add(toRelative(path));
				}
			} else if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)) {
				Path name = path.getFileName();
				Node child = node.getChild(name);
				if (child != null) {
					child.setRegistered(false);
				}
			}
			if (!changeFileSet.contains(path.toFile())) {
				changeFileSet.add(path.toFile());
			}
		}
		for (File file : changeFileSet) {
			node.update(file);
		}
		if (!key.reset()) {
			keyNodeMap.remove(key);
		}
	}
	
	private void addActiveListener(WrappedListener listener) {
		activeListeners.put(listener, EMPTY);
	}

	private void fireActiveListeners() throws InterruptedException {
		for (Iterator<WrappedListener> it = activeListeners.keySet().iterator(); it
				.hasNext();) {
			final WrappedListener wrappedListener = it.next();
			service.execute(new Runnable() {
				public void run() {
					wrappedListener.fire();
				}
			});
			it.remove();
		}
	}

	public AdvancedWatchService(Path rootPath) throws IOException {
		this.rootPath = rootPath.toRealPath();
		rootNode = new Node(null, rootPath);
		watchService = FileSystems.getDefault().newWatchService();
		
		ThreadFactory tf = new ThreadFactory() {
			private AtomicLong counter = new AtomicLong(0);
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				t.setName("WatchService-" + counter.getAndIncrement());
				return t;
			}
		};
		service = new ThreadPoolExecutor(CORE_POOL_SIZE, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                tf);

		service.execute(new Runnable() {
			public void run() {
				while (true) {
					try {
						pollEvent();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		});

		service.execute(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(WatchServiceFactory.getCheckPeriod());
						fireActiveListeners();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		});
	}


	private synchronized Node search(Path relativePath, boolean newIfAbsent) {
		Path path = rootPath;

		Node parent = rootNode;
		Node child = null;

		for (Iterator<Path> it = relativePath.iterator(); it.hasNext();) {
			Path name = it.next();
			path = path.resolve(name);

			child = parent.getChild(name);
			if (child == null) {
				if (newIfAbsent) {
					child = new Node(parent, path);
					parent.addChild(name, child);
				} else {
					return null;
				}
			}
			parent = child;
		}
		return child;
	}

	private synchronized void register(Node node) throws IOException {
		WatchKey key = node.path.register(watchService,
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		node.setRegistered(true);
		keyNodeMap.put(key, node);
		if (!allDirSet.contains(toRelative(node.path))) {
			allDirSet.add(toRelative(node.path));
		}

		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(node.path);
			for (Path path : stream) {
				if (Files.isDirectory(path) 
						&& !Files.isHidden(path)
						&& !path.getFileName().toString().startsWith(".")) {
					Path name = path.getFileName();
					Node child = node.getChild(name);
					if (child == null) {
						child = new Node(node, path);
						node.addChild(name, child);
						register(child);
					} else if(!child.isRegistered()){
						// 因为节点可能已经存在了，但是并没有使用WatchService进行监听
						register(child);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	private synchronized void registerEvent(Node node) throws IOException {
		WatchKey key = node.path.register(watchService,
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		node.setRegistered(true);
		keyNodeMap.put(key, node);
		if (!allDirSet.contains(toRelative(node.path))) {
			allDirSet.add(toRelative(node.path));
		}

		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(node.path);
			for (Path path : stream) {
				if (!Files.isHidden(path)
						&& !path.getFileName().toString().startsWith(".")) {
					if (Files.isDirectory(path)) {
						Path name = path.getFileName();
						Node child = node.getChild(name);
						if (child == null) {
							child = new Node(node, path);
							node.addChild(name, child);
							registerEvent(child);
						} else if(!child.isRegistered()){
							// 因为节点可能已经存在了，但是并没有使用WatchService进行监听
							registerEvent(child);
						}
					} else if (Files.isRegularFile(path)) {
        				path.toFile().setLastModified(System.currentTimeMillis());
        				allFileSet.add(toRelative(path));
        			}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public String toRelative(Path absolutePath) {
		String path = "/";
		if (absolutePath.startsWith(rootPath) && !absolutePath.equals(rootPath)) {
			path = "/" + absolutePath.subpath(rootPath.getNameCount(), 
					absolutePath.getNameCount()).toString();
		}
		path = path.replace("\\", "/");
		return path;
	}
	
	public synchronized void walkFileTree(File file, String ... extensions) {
		try {
			Files.walkFileTree(file.toPath(), new ExtensionsFileVisitor(extensions));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public synchronized void unregister(File file, IWatchServiceListener listener)
			throws IOException {
		Path path = Paths.get(file.getAbsolutePath()).toRealPath(LinkOption.NOFOLLOW_LINKS);
		
		Path directoryPath = path;
		if (Files.isRegularFile(path)) {
			directoryPath = path.getParent();
		}
		Path relativePath = rootPath.relativize(directoryPath);
		Node node = search(relativePath, false);
		if (node == null) {
			throw new IOException("监听器没有被添加到文件");
		}
		WrappedListener wrappedListener = listenerMap.remove(listener);
		if (wrappedListener == null) {
			throw new IOException("监听器没有被添加到文件");
		}
		node.removeListener(wrappedListener);
	}

	public synchronized void register(File file, IWatchServiceListener listener,
			IWatchServiceFilter filter) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("添加文件监听：" + file + 
					"，监听器：" + listener + 
					"，过滤器：" + filter);
		}
		Path path = Paths.get(file.getAbsolutePath()).toRealPath(LinkOption.NOFOLLOW_LINKS);

		Path directoryPath = path;
		Path relativePath = rootPath.relativize(directoryPath);

		Node node = search(relativePath, true);

		WrappedListener wrappedListener = new WrappedListener(listener, filter);
		listenerMap.put(listener, wrappedListener);

		node.addListener(wrappedListener);

		register(node);
	}
}
