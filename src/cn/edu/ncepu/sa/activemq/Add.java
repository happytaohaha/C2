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
				int x = Integer.parseInt(tmp[1]);
				int y = Integer.parseInt(tmp[2]);
				return String.valueOf((x + y));
			}
		}
		
		
		
		return null;
	}

}
