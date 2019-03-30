package cn.edu.ncepu.sa.activemq;

/**
 * @author yt
 * @data 2019/3/28
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 5+6*7+9-78+sin30
 * 如果数字  往里压栈
 */
public class WordsAnalyse {

    List<String> stringList;

    //字符串转字符数组
    //多位数字
    WordsAnalyse(String words){
        stringList =new ArrayList<>();
        String a ="";
        for(int i = 0; i < words.length(); i++){
            if(Character.isDigit(words.charAt(i))){
                a = a +words.charAt(i);
                if(i==words.length()-1){
                    stringList.add(a);
                }
                continue;
            }
            if(a!=""){
                stringList.add(a);
                a="";
            }
            stringList.add(words.charAt(i)+"");
        }
        System.out.println(stringList);
    }

    //中缀表达式转 后缀表达式
    public List<String> midToleast(){
        List<String> leastList = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < stringList.size(); i++){
            //对数字的处理
            if(Character.isDigit(stringList.get(i).charAt(0))){
                leastList.add(stringList.get(i));
            }else{

                //  + - * /
                boolean bool = false;
                //获得当前和栈顶项
                while (!bool) {
                    char a = stringList.get(i).charAt(0);
                    if('('== a){
                        stack.push(a + "");
                        bool = true;
                        break;
                    }
                    if(')'== a){
                        char b = stack.get(stack.size()-1).charAt(0);
                        while(b!='('){
                            leastList.add(stack.pop());
                            b =stack.get(stack.size()-1).charAt(0);
                        }
                        stack.pop();
                        bool = true;
                        break;
                    }
                    if(0 == stack.size()){
                        stack.push(a + "");
                        bool = true;
                        break;
                    }
                    //前一个
                    char b = stack.get(stack.size()-1).charAt(0);

                    bool = comparepriority(a, b);
                    if (bool) {

                        stack.push(a + "");
                        bool = true;
                        break;
                    }
                    leastList.add(stack.pop());
                }
            }
        }
        while (stack.size()!= 0){
            leastList.add(stack.pop());
        }
        return leastList;
    }

    //处理后缀式给workItem用
    public List<String> getWorkItemList(List<String> list) {
        for(int i = 0; i <list.size(); i++) {
            list.get(i);
        }

    return null;
    }
    //计算
    public String calculate(List<String> list){
        Stack<String> stack = new Stack<>();
        String front, second;
        for(int i = 0; i <list.size(); i++){
            if(Character.isDigit(list.get(i).charAt(0))){
                stack.push(list.get(i));
            }else{
                char a = list.get(i).charAt(0);
                switch (a){
                    case '+':
                        second = stack.pop();
                        front = stack.pop();
                        front = (Integer.parseInt(front) + Integer.parseInt(second))+"";
                        stack.push(front);
                        break;
                    case '-':
                        second = stack.pop();
                        front = stack.pop();
                        front = (Integer.parseInt(front) - Integer.parseInt(second))+"";
                        stack.push(front);
                        break;
                    case '*':
                        second = stack.pop();
                        front = stack.pop();
                        front = (Integer.parseInt(front) * Integer.parseInt(second))+"";
                        stack.push(front);
                        break;
                    case '/':
                        second = stack.pop();
                        front = stack.pop();
                        front = (Integer.parseInt(front) / Integer.parseInt(second))+"";
                        stack.push(front);
                        break;
                    default:
                        break;
                }
            }
        }
        if(stack.size()!=1){
            return "-1";
        }
        return stack.pop();
    }

    //优先级的比较
    boolean comparepriority(char a,char b){
        //大的往里放
        if(getPriority(a)>getPriority(b)){
            return true;
        }
        //小的或者等于向外弹
        return false;
    }

    //设置优先性
    int getPriority(char a){
        int x =0;
        switch (a){
            case '+':
                x = 1;
                break;
            case '-':
                x = 1;
                break;
            case '*':
                x = 2;
                break;
            case '/':
                x = 2;
                break;
            default:
                break;
        }
        return x;
    }
}
