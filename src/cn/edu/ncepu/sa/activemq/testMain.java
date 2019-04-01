package cn.edu.ncepu.sa.activemq;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author yt
 * @data 2019/3/30
 */
public class testMain {

    public static void main(String[] args) {

        Anaylsis anaylsis =new Anaylsis();
        ArrayList<String> result = anaylsis.getStringList("2+sin2+3");

        ArrayList<String> result2 = anaylsis.getPostOrder(result);

        System.out.println(result);
        System.out.println(result2);
    }

}
