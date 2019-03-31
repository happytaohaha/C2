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
	 * 将字符串转化成List
	 * @param str
	 * @return
	 */
	public ArrayList<String> getStringList(String str){
		ArrayList<String> result = new ArrayList<String>();
		String num = "";
		for (int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))){
				num = num + str.charAt(i);
			}else{
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
	
	/**
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
					stack.push(inOrderList.get(i));
					break;
				case ')':
					while (!stack.peek().equals("(")) {
						result.add(stack.pop());
					}
					stack.pop();
					break;
				default:
					while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
						result.add(stack.pop());
					}
					stack.push(inOrderList.get(i));
					break;
				}
			}
		}
		while(!stack.isEmpty()){
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
					}
				}
			}
		}
		return resultList;
	}
	/**
	 * 比较运算符等级
	 * @param peek
	 * @param cur
	 * @return
	 */
	public static boolean compare(String peek, String cur){
		if("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
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
