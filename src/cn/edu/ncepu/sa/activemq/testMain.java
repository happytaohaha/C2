package cn.edu.ncepu.sa.activemq;

import java.util.List;

/**
 * @author yt
 * @data 2019/3/30
 */
public class testMain {

    public static void main(String[] args) {

        WordsAnalyse wordsAnalyse =new WordsAnalyse("20+10+5*4");
        List<String> resultList = wordsAnalyse.midToleast();
        System.out.println(resultList);
        String result =wordsAnalyse.calculate(resultList);
        System.out.println(result);
    }

}
