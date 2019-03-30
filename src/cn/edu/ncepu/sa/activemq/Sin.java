package cn.edu.ncepu.sa.activemq;
import java.math.*;
/**
 * @author yt
 * @data 2019/3/28
 */

public class Sin extends C2Component {
     static final double  PAI = Math.acos(-1);
    public Sin(String host, int port, String user, String pwd) {
        super(host, port, user, pwd, "Sin");
        // TODO Auto-generated constructor stub
    }

    @Override
    String getResult(String msg) {
        // TODO Auto-generated method stub
        String[] tmp = msg.split(",");
        if (tmp.length >= 2) {
            if (tmp[0].equals("sin")) {

                double x = Double.parseDouble(tmp[1]);
                x= x * PAI / 180;
                return String.valueOf(Math.sin(x));
            }
        }
        return null;
    }



}
