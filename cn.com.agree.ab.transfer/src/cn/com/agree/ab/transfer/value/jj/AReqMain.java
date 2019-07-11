package cn.com.agree.ab.transfer.value.jj;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AReqMain {
	public static void main(String[] args) throws IOException, ParseException {
		String in = "__REQ__[\"_Result_Map_\"][\"Result\"+__REQ__[\"i\"]]";
		REQParser parser = new REQParser(in);
		parser.addInArg("j");
		parser.addOutArg("msgcode");
		Object result = parser.parse();
		System.err.println(result);
		
		
//		int lineNum = 1;
//		try (BufferedReader br = new BufferedReader(
//				new InputStreamReader(new FileInputStream("D:/desktop/参数.txt")))) {
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				REQParser parser = new REQParser(line);
//				Object result = parser.parse();
//
//				if(lineNum == 163) {
//					System.err.println();
//				}
//				System.err.println(lineNum + "    " + result);
//				lineNum++;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
