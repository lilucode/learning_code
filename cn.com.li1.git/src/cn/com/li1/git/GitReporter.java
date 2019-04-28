package cn.com.li1.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GitReporter
{

	/**
	 * 是否显示历史提交信息
	 */
	private static boolean showHistoryCommit = true;

	/**
	 * 是否每个插件的详细更改信息
	 */
	private static boolean showPluginDetailInfo = true;

	private static List<DiffEntry> listDiff(Git git, ObjectId oldCommit,
			ObjectId newCommit) throws Exception
	{
		Repository repository = git.getRepository();
		List<DiffEntry> diffs = git.diff()
				.setOldTree(prepareTreeParser(repository, oldCommit))
				.setNewTree(prepareTreeParser(repository, newCommit)).call();

		return diffs;
	}

	private static AbstractTreeIterator prepareTreeParser(Repository repository,
			ObjectId objectId) throws IOException
	{
		RevWalk walk = new RevWalk(repository);
		RevCommit commit = walk.parseCommit(objectId);
		RevTree tree = walk.parseTree(commit.getTree().getId());

		CanonicalTreeParser treeParser = new CanonicalTreeParser();
		try (ObjectReader reader = repository.newObjectReader())
		{
			treeParser.reset(reader, tree.getId());
		}
		walk.dispose();

		walk.close();
		return treeParser;
	}

	private static String getProjectName(File projectFile) throws Exception
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(projectFile));
			String line;
			while ((line = br.readLine()) != null)
			{
				line = line.trim();
				if (line.startsWith("<name>"))
				{
					return line.replaceAll("<name>", "").replaceAll("</name>",
							"");
				}
			}
		} finally
		{
			if (br != null)
				try
				{
					br.close();
				} catch (IOException e)
				{
				}
		}
		return "<" + projectFile.getAbsolutePath() + ">";
	}

	private static String getProject(String path) throws Exception
	{
		File file = new File(path);
		if (file.exists())
		{
			File parent;
			if (file.isDirectory())
			{
				parent = file;
			} else
			{
				parent = file.getParentFile();
			}
			while (parent != null)
			{
				File projectFile = new File(parent, ".project");
				if (projectFile.exists())
				{
					return getProjectName(projectFile);
				}
				parent = parent.getParentFile();
			}
		} else
		{
			// 文件不存在，就找其父亲
			int lastIndex = path.lastIndexOf('/');
			if (lastIndex != -1)
			{
				return getProject(path.substring(0, lastIndex));
			}
		}
		return "<" + path + ">";
	}

	private static String getProject(String repositoryDir, String path)
			throws Exception
	{
		return getProject(repositoryDir + "/" + path);
	}

	private static List<RevCommit> getCommits(Git git, ObjectId start,
			ObjectId end) throws Exception
	{
		List<RevCommit> commits = new LinkedList<>();
		Iterator<RevCommit> it = git.log().addRange(start, end).call()
				.iterator();
		while (it.hasNext())
		{
			commits.add(0, it.next());
		}
		return commits;
	}

	private static Set<String> getProject(String repositoryDir,
			List<DiffEntry> diffs) throws Exception
	{
		Set<String> projects = new HashSet<>();

		// /dev/null
		for (DiffEntry diff : diffs)
		{
			String oldPath = diff.getOldPath();
			String newPath = diff.getNewPath();
			ChangeType ct = diff.getChangeType();

			if (ChangeType.ADD.equals(ct) || ChangeType.MODIFY.equals(ct))
			{
				projects.add(getProject(repositoryDir, newPath));
			} else if (ChangeType.DELETE.equals(ct))
			{
				projects.add(getProject(repositoryDir, oldPath));
			} else if (ChangeType.COPY.equals(ct)
					|| ChangeType.RENAME.equals(ct))
			{
				projects.add(getProject(repositoryDir, oldPath));
				projects.add(getProject(repositoryDir, newPath));
			}
		}

		return projects;
	}

	private static Map<String, List<RevCommit>> getProjectCommitsMap(Git git,
			String repositoryDir, List<RevCommit> commits) throws Exception
	{
		Map<String, List<RevCommit>> projectCommitsMap = new HashMap<>();

		Iterator<RevCommit> it = commits.iterator();
		if (it.hasNext())
		{
			RevCommit lastCommit = it.next();

			while (it.hasNext())
			{
				RevCommit curCommit = it.next();

				List<DiffEntry> diffs = listDiff(git, lastCommit, curCommit);
				Set<String> projects = getProject(repositoryDir, diffs);

				for (String project : projects)
				{
					List<RevCommit> projectCommits = projectCommitsMap
							.get(project);
					if (projectCommits == null)
					{
						projectCommits = new LinkedList<>();
						projectCommitsMap.put(project, projectCommits);
					}
					projectCommits.add(curCommit);
				}

				lastCommit = curCommit;
			}
		}

		return projectCommitsMap;
	}

	private static void dump(String repositoryDir, String start, String end)
			throws Exception
	{
		String gitDir = repositoryDir + "/.git";

		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repository = repositoryBuilder.setGitDir(new File(gitDir))
				.readEnvironment().findGitDir().setMustExist(true).build();

		repository.getDirectory();

		Git git = new Git(repository);

		ObjectId startCommitId = repository.resolve(start);
		ObjectId endCommitId = repository.resolve(end);

		List<RevCommit> commits = getCommits(git, startCommitId, endCommitId);
		Map<String, List<RevCommit>> projectCommitsMap = getProjectCommitsMap(
				git, repositoryDir, commits);

		if (showHistoryCommit)
		{
			for (RevCommit commit : commits)
			{
				System.out.println(commit.getId().abbreviate(8).name() + "  "
						+ commit.getShortMessage());
			}
		}

		System.out.println();

		List<String> projects = new ArrayList<>();
		projects.addAll(projectCommitsMap.keySet());
		Collections.sort(projects);

		for (String project : projects)
		{
			if (showPluginDetailInfo)
			{
				System.out.println();
			}
			System.out.println(project);

			if (showPluginDetailInfo)
			{
				List<RevCommit> projectCommits = projectCommitsMap.get(project);
				for (RevCommit commit : projectCommits)
				{
					System.out
							.println("\t" + commit.getId().abbreviate(8).name()
									+ "  " + commit.getShortMessage() + "	提交时间：" + getCommitTime(commit));
				}
			}
		}
	}
	
	private static String getCommitTime(RevCommit commit)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(
				new Date(Long.parseLong(commit.getCommitTime() + "") * 1000));
	}

	public static void main(String[] args) throws Exception
	{
		showHistoryCommit = true;
		showPluginDetailInfo = true;
		
//		run张家口4_0();
		
		runMaster4_1();
		
//		run湖北4_1();
	}
	
	public static void runMaster4_1() throws Exception
	{
		// master
		String abRepositoryDir = "E:\\git_me\\learning_code";
//		String adoreRepositoryDir = "D:\\workspace\\ab4.0_bak\\adore";
		
		//第一个不打印
		String abStart = "d1e6bf0f3a9038045137be88b130b2c3895abf75";
		String abEnd = "c407bb86e2a57004dd2f99fa13ee8c948f04ea51";

		/**
		 * 往前面两个，第一个不打印，第二个插件不打印
		 * 2019年03月04日
		 * String abStart = "b4e438723c3830e5ac5fbfc9ef86cace7ba5614e";
		 * String abEnd = "09e9e589aa5d36c7d344183c2d95ba29a6c448ad";
		 * 
		 * 2019年04月01日
		 * String abStart = "6093a7737a8bf23381fa45a5944c643343e8b07e";
		 * String abEnd = "65d82ca705d459d6db65a1295f0fe4bcdc25b5b1";
		 */
		
		System.out.println("=== AB ===");
		dump(abRepositoryDir, abStart, abEnd);

//		String adoreStart = "6093a7737a8bf23381fa45a5944c643343e8b07e";
//		String adoreEnd = "0974c2e08772b89b1a84769e68fbadc280b2d9b6";
//
//		System.out.println();
//		System.out.println("=== Adore ===");
//
//		dump(adoreRepositoryDir, adoreStart, adoreEnd);
	}
	
	public static void run张家口4_0() throws Exception
	{
		// 张家口
		String abRepositoryDir = "D:\\workspace\\Ameba4.0.0\\ab";
		String adoreRepositoryDir = "D:\\workspace\\Ameba4.0.0\\adore";
		
		String abStart = "3d5d30b";
		String abEnd = "2802b3a2a1a8ac4294c1b543b74cdeb3e27c5ddf";

		System.out.println("=== AB ===");
		dump(abRepositoryDir, abStart, abEnd);

		String adoreStart = "d63db55";
		String adoreEnd = "135f3a57b3ce46a2d21ca0a52a7bb98eb3d34859";

		System.out.println();
		System.out.println("=== Adore ===");

		dump(adoreRepositoryDir, adoreStart, adoreEnd);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public static void run湖北4_1() throws Exception
	{
		/**
		 * 2018年08月13日: 
		 * 		ab:		6a94cc36b37e7747ab1eed7fd6833c42c1a344d7
		 * 		adore:	866512539ab03fde18fd99efc52ad94a5863aedf
		 * 
		 * 2018年10月19日：
		 * 		ab：		ca0f8db96befb743b6358a6b159f84faeef0a2b6
		 * 		adore：	d051688b42f9f87dc5613d321420b54899187e59
		 * 
		 * 2018年11月1日
		 * 		ab:		a9647889424c1f0d9b0a0ffe538132df8e278dcb
		 * 		adore：	238c2d20ec009866744280b365f102afc4b977ed
		 * 
		 * 2018年11月9日
		 * 		ab:		84b871cf2440270add6233750e363ac71b5f6f08
		 * 		adore:	674347dc73fb07223c8c1e0d402e5a060383ff9b
		 * 
		 * 2018年11月16日
		 * 		ab:		41a20d2c2341459bd8d643cf1b15988ea1655c91
		 * 		adore:	7c49b9cbbadac8c6bddece0b71e57a19f4dbcc83
		 * 
		 */
		// 湖北
		showHistoryCommit = false;
		showPluginDetailInfo = true;
		String abRepositoryDir = "D:\\workspace\\ab4.0_bak\\ab";
		String adoreRepositoryDir = "D:\\workspace\\ab4.0_bak\\adore";
		
		String abStart = "84b871cf2440270add6233750e363ac71b5f6f08";
		String abEnd = "41a20d2c2341459bd8d643cf1b15988ea1655c91";

		System.out.println("=== AB ===");
		dump(abRepositoryDir, abStart, abEnd);

		String adoreStart = "674347dc73fb07223c8c1e0d402e5a060383ff9b";
		String adoreEnd = "7c49b9cbbadac8c6bddece0b71e57a19f4dbcc83";

		System.out.println();
		System.out.println("=== Adore ===");

		dump(adoreRepositoryDir, adoreStart, adoreEnd);
	}
}
