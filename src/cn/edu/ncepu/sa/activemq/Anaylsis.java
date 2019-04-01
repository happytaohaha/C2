package cn.edu.ncepu.sa.activemq;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
 
/**
 * 
 * @author xhx
 *
 */
public class Anaylsis {
	
	/**
	 * 修改增加字母
	 * 将字符串转化成List
	 * @param str
	 * @return
	 */
	public ArrayList<String> getStringList(String str){
		ArrayList<String> result = new ArrayList<String>();
		String num = "";
		String suanfu = "";
		for (int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))){
				num = num + str.charAt(i);
			}else if(Character.isAlphabetic(str.charAt(i))){//增加字母
				suanfu = suanfu + str.charAt(i);
			}else{
				//先处理算符 再处理 数字
				if(suanfu != ""){
					result.add(suanfu);
					suanfu = "";
				}
				if(num != ""){
					result.add(num);
				}
				result.add(str.charAt(i) + "");
				num = "";
			}
		}
		if(num != ""){
			result.add(num);
		}
		return result;
	}
	
	/** 增加sin
	 * 将中缀表达式转化为后缀表达式
	 * @param inOrderList
	 * @return
	 */
	public ArrayList<String> getPostOrder(ArrayList<String> inOrderList){
		
		ArrayList<String> result = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < inOrderList.size(); i++) {
			if(Character.isDigit(inOrderList.get(i).charAt(0))){
				result.add(inOrderList.get(i));
			}else{
				switch (inOrderList.get(i).charAt(0)) {
				case '(':
					//把(放进去
					stack.push(inOrderList.get(i));
					break;
				case ')':
					//增加sin
					while (!stack.peek().equals("(") && !stack.peek().equals("sin") &&!stack.peek().equals("sin") ) {
						result.add(stack.pop());
					}
					//弹出(
					stack.pop();
					break;
				default:
					//比较当前符号和栈内的符号进行比较   当前优先级小返回 true   将比他大的都扔进去
					while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
						//直接弹出增加
						result.add(stack.pop());
					}
					//增加这个
					stack.push(inOrderList.get(i));
					break;
				}
			}
		}
		while(!stack.isEmpty()){
			//将所有的扔出来
			result.add(stack.pop());
		}
		return result;
	}
	
	/**
	 * 计算后缀表达式
	 * @param postOrder
	 * @return
	 */
	public Integer calculate(ArrayList<String> postOrder){
		Stack stack = new Stack();
		for (int i = 0; i < postOrder.size(); i++) {
			if(Character.isDigit(postOrder.get(i).charAt(0))){
				stack.push(Integer.parseInt(postOrder.get(i)));
			}else{
				Integer back = (Integer)stack.pop();
				Integer front = (Integer)stack.pop();
				Integer res = 0;
				switch (postOrder.get(i).charAt(0)) {
				case '+':
					res = front + back;
					break;
				case '-':
					res = front - back;
					break;
				case '*':
					res = front * back;
					break;
				case '/':
					res = front / back;
					break;
				}
				stack.push(res);
			}
		}
		return (Integer)stack.pop();
	}
	/**
	 * 
	 * TODO
	 * @author XHX
	 * @date 2019年3月29日
	 * @param postOrder
	 * @return
	 */
	public Queue<String> calculateA(ArrayList<String> postOrder){
		Queue<String> resultList = new LinkedList<>();
		int flag = 0;
		Stack stack = new Stack();
		for (int i = 0; i < postOrder.size(); i++) {
			if(Character.isDigit(postOrder.get(i).charAt(0))){
				stack.push(Integer.parseInt(postOrder.get(i)));
			}else{
				//todo 如果只有一个计算
				if(flag==0) {
				Integer back = (Integer)stack.pop();
				Integer front = (Integer)stack.pop();
				switch (postOrder.get(i).charAt(0)) {
					case '+':
						resultList.add("+,"+front+","+back);
						break;
					case '-':
						resultList.add("-,"+front+","+back);
						break;
					case '*':
						resultList.add("*,"+front+","+back);
						break;
					case '/':
						resultList.add("/,"+front+","+back);
						break;
					case 's':
						resultList.add("sin,"+front);
						break;
					case 'c':
						resultList.add("cos,"+front);
						break;
				}
				flag++;
				}else {
					Integer front = (Integer)stack.pop();
					
					switch (postOrder.get(i).charAt(0)) {
					case '+':
						resultList.add("+,"+front);
						break;
					case '-':
						resultList.add("-,"+front);
						break;
					case '*':
						resultList.add("*,"+front);
						break;
					case '/':
						resultList.add("/,"+front);
						break;
					case 's':
						resultList.add("sin,"+front);
						break;
					case 'c':
						resultList.add("cos,"+front);
						break;
					}
				}
			}
		}
		return resultList;
	}
	/**
	 * 比较运算符等级
	 * 前一个和当前的进行比较  前一个和当前的优先级相等或者
	 * @param peek
	 * @param cur
	 * @return
	 */
	public static boolean compare(String peek, String cur){

		if("sin".equals(peek) && ("cos".equals(cur) ||"sin".equals(cur) ||"/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals	(cur))){
			return true;
		}else if("cos".equals(peek) && ("cos".equals(cur) ||"sin".equals(cur) ||"/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals	(cur))){
			return true;
		}
		else if("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
			return true;
		}else if("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
			return true;
		}else if("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
			return true;
		}else if("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
			return true;
		}
		return false;
	}
	
	
}
