package cn.edu.ncepu.sa.activemq;

/**
 * @author yt
 * @data 2019/4/1
 */
public class Cos extends C2Component {
    static final double  PAI = Math.acos(-1);
    public Cos(String host, int port, String user, String pwd) {
        super(host, port, user, pwd, "Cos");
        // TODO Auto-generated constructor stub
    }

    @Override
    String getResult(String msg) {
        // TODO Auto-generated method stub
        String[] tmp = msg.split(",");
        if (tmp.length >= 2) {
            if (tmp[0].equals("cos")) {
                Double x = Double.parseDouble(tmp[1]);
                return String.format("%.2f",Math.cos(x * Math.PI / 180));
            }
        }
        return null;
    }



}
