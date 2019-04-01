package cn.edu.ncepu.sa.activemq;

public class Add extends C2Component {

	
	public Add(String host, int port, String user, String pwd) {
		super(host, port, user, pwd,"Add");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	String getResult(String msg) {
//		System.out.println("调用结果。。。。。。result");
		String[] tmp = msg.split(",");
		if (tmp.length >= 3) {
			if (tmp[0].equals("+")) {
				Double x = Double.parseDouble(tmp[1]);
				Double y = Double.parseDouble(tmp[2]);
				return String.valueOf((x + y));
			}
		}
		
		
		
		return null;
	}

}
