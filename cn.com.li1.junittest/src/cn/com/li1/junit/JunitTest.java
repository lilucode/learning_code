package cn.com.li1.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JunitTest {

	/**
	 * @BeforeClass:这个注解表示这个方法会在所有测试方法执行之前执行 因为是static修饰的静态方法，所有只会执行一次。
	 * 通常用来进行一些资源的加载，如通过JUnit测试Spring相关的东西时，可以在这个方法中加载Spring的配置文件
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("@BeforeClass");
	}

	/**
	 * @AfterClass:这个注解表示这个方法会在所有方法执行完毕之后执行，通常用来释放资源
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("@AfterClass");
	}

	/**
	 * @Before:这个注解表示这个方法会在每个测试方法执行之前执行一次
	 * 有多少个测试方法就会执行多少次
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("@Before");
	}

	/**
	 * @After:这个注解表示这个方法会在每个测试方法执行之后执行一次
	 * 有多少个测试方法就会执行多少次
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("@After");
	}

	/**
	 * junit的测试方法必须使用@Test注解
	 * 测试方法必须以public void修饰，并且不包含参数
	 */
	@Test
	public void test1() {
		System.out.println("测试1");
	}
	
	@Test
	public void test2() {
		System.out.println("测试2");
	}
	
	@Test
	public void test3() {
		System.out.println("测试3");
	}

}
