package cn.edu.ncepu.sa.activemq;

public class Mul extends C2Component {

	public Mul(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "Mul");
		// TODO Auto-generated constructor stub
	}

	@Override
	String getResult(String msg) {
		// TODO Auto-generated method stub
		String[] tmp = msg.split(",");
		if (tmp.length >= 3) {
			if (tmp[0].equals("*")) {
				int x = Integer.parseInt(tmp[1]);
				int y = Integer.parseInt(tmp[2]);
				return String.valueOf((x * y));
			}
		}
		return null;
	}
	
	

}
