package cn.com.li1.exception;

public class TryWithResource {

	public static void main(String[] args) throws Exception {

		try {
			throw new Exception("异常1");
		} catch (Exception e) {
			System.out.println("bbb");
			throw new Exception("异常2");
		} finally {
			// try {
			// throw new Exception("异常3");
			// } catch (Exception e) {
			// throw new Exception("异常4");
			// }
			// if(arg1.equals("111")) {
			// throw new Exception("异常3");
			// }
			// System.out.println("aaa");
		}
	}

}
