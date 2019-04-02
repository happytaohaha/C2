package cn.edu.ncepu.sa.activemq;

import java.util.ArrayList;

/**
 * @author yt
 * @data 2019/3/30
 */
public class testMain {

    public static void main(String[] args) {

        Analysis anaylsis =new Analysis();
        ArrayList<String> result = anaylsis.getStringList("1*2+3*4");

        ArrayList<String> result2 = anaylsis.getPostOrder(result);
        ArrayList<String> target2 = anaylsis.PostToThreeUint(result2);
        System.out.println(result);
        System.out.println(result2);
        System.out.println(target2);
        Add add = new Add("localhost", 61616, "admin", "admin");
        add.StartWork();

        Sub sub = new Sub("localhost", 61616, "admin", "admin");
        sub.StartWork();

        Mul mul = new Mul("localhost", 61616, "admin", "admin");
        mul.StartWork();

        Div div = new Div("localhost", 61616, "admin", "admin");
        div.StartWork();

        Sin sin = new Sin("localhost", 61616, "admin", "admin");
        sin.StartWork();

        Cos cos = new Cos("localhost", 61616, "admin", "admin");
        cos.StartWork();

        Cal cal = new Cal("localhost", 61616, "admin", "admin");



        // 将工作项添加进队列
        for (String s : target2) {

            cal.AddWorkItem(s);
        }

        cal.StartWork();
    }

}
