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
	 * �޸�������ĸ
	 * ���ַ���ת����List
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
			}else if(Character.isAlphabetic(str.charAt(i))){//������ĸ
				suanfu = suanfu + str.charAt(i);
			}else{
				//�ȴ������ �ٴ��� ����
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
	
	/** ����sin
	 * ����׺���ʽת��Ϊ��׺���ʽ
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
					//��(�Ž�ȥ
					stack.push(inOrderList.get(i));
					break;
				case ')':
					//����sin
					while (!stack.peek().equals("(") && !stack.peek().equals("sin") &&!stack.peek().equals("sin") ) {
						result.add(stack.pop());
					}
					//����(
					stack.pop();
					break;
				default:
					//�Ƚϵ�ǰ���ź�ջ�ڵķ��Ž��бȽ�   ��ǰ���ȼ�С���� true   ��������Ķ��ӽ�ȥ
					while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
						//ֱ�ӵ�������
						result.add(stack.pop());
					}
					//�������
					stack.push(inOrderList.get(i));
					break;
				}
			}
		}
		while(!stack.isEmpty()){
			//�����е��ӳ���
			result.add(stack.pop());
		}
		return result;
	}
	
	/**
	 * �����׺���ʽ
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
	 * @date 2019��3��29��
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
				//todo ���ֻ��һ������
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
	 * �Ƚ�������ȼ�
	 * ǰһ���͵�ǰ�Ľ��бȽ�  ǰһ���͵�ǰ�����ȼ���Ȼ���
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
