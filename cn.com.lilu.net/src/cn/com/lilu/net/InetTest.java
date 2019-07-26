package cn.com.lilu.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetTest {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		try {
			System.out.println(InetAddress.getLocalHost().getHostName());
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Enumeration netInterfaces=null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		System.out.println("------开始------");
		while (netInterfaces.hasMoreElements())
        {
            NetworkInterface ni = (NetworkInterface) netInterfaces
                    .nextElement();
            Enumeration ips = ni.getInetAddresses();
            while (ips.hasMoreElements())
            {
                InetAddress ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(':') == -1)
                {
                    System.out.println(ip.getHostAddress());
                }
            }
        }
		System.out.println("------结束------");
	}

}
