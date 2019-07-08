package req.transfer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class AReqMain {
	public static void main(String[] args) throws IOException, ParseException {
		String in = "_REQ__\r\n" + 
				"[ [\"CallModuleCode\",__REQ__[\"MsgDeviceInfo\"][__BUILTIN__[\"j\"].toInt()][\"modulecode\"]], [\"CallTransCode\",__REQ__[\"MsgDeviceInfo\"][__BUILTIN__[\"j\"].toInt()][\"transcode\"]], [\"msgmodule\",__BUILTIN__[\"msgmodule\"]], [\"msgcode\",__BUILTIN__[\"msgcode\"]], [\"SndDeviceInfo\",__REQ__[\"MsgDeviceInfo\"][__BUILTIN__[\"j\"].toInt()]]]";
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
