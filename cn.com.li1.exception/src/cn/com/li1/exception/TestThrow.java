package cn.com.li1.exception;

public class TestThrow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if(true) {
			System.out.println("111");
			throw new NullPointerException();
		}
		System.out.println("222");
	}

}
