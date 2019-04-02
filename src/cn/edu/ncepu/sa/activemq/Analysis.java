package cn.edu.ncepu.sa.activemq;


import java.util.*;

/**
 * 
 * @author xhx
 * @modified happytaohaha
 */
public class Analysis {
	/**
	 * 这样只用两个栈优化内存
	 */
	//设置优先级
	private static Map<String, Integer> operspriority = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
		{
			put("(", 0);
			put(")", 0);
			put("+", 1);
			put("-", 1);
			put("*", 2);
			put("/", 2);
			put("sin", 3);
			put("cos", 3);
		}
	};
	// 操作符栈
	private static Stack<String> stack = new Stack<>();
	// 操作数栈
	private static Stack<String> operationStack = new Stack<>();
	/**
	 * 算法优化
	 * 1.设置单元开始位置
	 * 2.当前位置
	 * 3.单元结束位置
	 * 数字 和 字符  sin单独判断
	 *
	 * 将字符串转化成List
	 * @param exp
	 * @return
	 */
	public ArrayList<String> getStringList(String exp){
		int beginIndex = 0;// 单元开始位置
		int endIndex = 0;// 单元结束位置
		int index = 0;// 当前位置
		int length = exp.length();
		ArrayList<String> result = new ArrayList<String>();
		while (index < length) {
			char temp = exp.charAt(index);
			beginIndex = index;
			if (Character.isDigit(temp)) {
				// 如果是数字
				index++;
				//判断的下一个
				while (index < length && Character.isDigit(exp.charAt(index))
						&& (exp.charAt(index) != '\t')
						&& (exp.charAt(index) != '\r')
						&& (exp.charAt(index) != '\n')) {
					index++;
				}
				endIndex = index;
				//判断的下一个,因此要回退
				index--;// 回退一个字符
				//exclusive不包含最后一个字符
				result.add(exp.substring(beginIndex, endIndex));
			} else if (temp == '+' || temp == '-' || temp == '*' || temp == '/'
					|| temp == '(' || temp == ')') {// 简单运算符
				endIndex = index;
				//就是当前字符
				result.add(exp.substring(beginIndex, endIndex + 1));
			} else if (temp == 's' || temp == 'c') {
				// 是sin或者cos
				index += 2;// 后移两位

				endIndex = index + 1;
				result.add(exp.substring(beginIndex, endIndex));
			}
			index++;// 后移一个字符
		}

		return result;
	}
	
	/** 算法：遇到数字直接放到结果
	 *对 （ ）和 其他 三种处理方式   用到了算符优先
	 * 中缀变后缀式的思想
	 *
	 * 将中缀表达式转化为后缀表达式
	 * @param inOrderList
	 * @return
	 */
	public ArrayList<String> getPostOrder(ArrayList<String> inOrderList){
		
		ArrayList<String> result = new ArrayList<String>();

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
					while (!stack.peek().equals("(") ) {
						result.add(stack.pop());
					}
					//弹出(
					stack.pop();
					break;
				default:
					//比较当前符号和栈内的符号进行比较   当前优先级小返回 true   将比他大的和相等的都扔进去
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
	 * 比较运算符等级
	 * 前一个和当前的进行比较  前一个和当前的优先级相等或者
	 * @param peek
	 * @param cur
	 * @return
	 */
	public static boolean compare(String peek, String cur){
		if(operspriority.get(peek) >= operspriority.get(cur)){
			return true;
		}
		return false;
	}

	/**
	 * 计算后缀表达式
	 * @param postOrder
	 * @return
	 */
	public Double calculate(ArrayList<String> postOrder){
		Stack stack = new Stack();
		for (int i = 0; i < postOrder.size(); i++) {
			if(Character.isDigit(postOrder.get(i).charAt(0))){
				stack.push(Double.parseDouble(postOrder.get(i)));
			}else{
				Double back;
				Double front;
				Double res = 0.0;
				switch (postOrder.get(i).charAt(0)) {
				case '+':
					back = (Double)stack.pop();
					front = (Double)stack.pop();
					res = front + back;
					break;
				case '-':
					back = (Double)stack.pop();
					front = (Double)stack.pop();
					res = front - back;
					break;
				case '*':
					back = (Double)stack.pop();
					front = (Double)stack.pop();
					res = front * back;
					break;
				case '/':
					back = (Double)stack.pop();
					front = (Double)stack.pop();
					res = front / back;
					break;
				case 's'://sin
					front = (Double)stack.pop();
					res = Math.sin(Math.toDegrees(front));
					break;
				case 'c'://cos
					front = (Double)stack.pop();
					res = Math.cos(Math.toDegrees(front));
					break;
				}
				stack.push(res);
			}
		}
		return (Double)stack.pop();
	}

	static ArrayList<String> target = new ArrayList<String>();// 三元组
	/**
	 *
	 * @param post
	 * @return 返回工作项
	 */
	public static ArrayList<String> PostToThreeUint(ArrayList<String> post) {

		ArrayList<String> workItem = new ArrayList<String>();// 真正要返回的工作项
		for (String s : post) {
			if (operspriority.get(s) == null) {// 不是操作符,操作数进栈
				operationStack.push(s);
			} else {
				// 操作符

				if (s.equals("+") || s.equals("-") || s.equals("*")
						|| s.equals("/")) {
					// 二元操作符，操作数栈弹出两个操作数

					String operation2 = operationStack.pop();// 先弹出的是第二操作数
					String operation1 = operationStack.pop();// 后弹出的是第一操作数
					String newTarget = s + "," + operation1 + "," + operation2;// 新三元组
					target.add(newTarget);// 假如三元组列表

					if (operation1.equals("result")
							&& operation2.equals("result")) {

						// 两个操作数都为result,要回填两次
						// 设置标志位
						workItem.add(3 + s);
					} else if (operation1.equals("result")) {
						// 第一个操作数为中间数,设置首位为标志位1
						workItem.add(1 + s + "," + operation2);
					} else if (operation2.equals("result")) {
						// 第二个数为中间结果,设置首位为标志位2
						workItem.add(2 + s + "," + operation1);
					} else {// 如果操作数不包含result
						workItem.add(newTarget);
					}
					operationStack.push("result");
				} else if (s.equals("sin") || s.equals("cos")) {
					// 一元操作数，弹出操作数栈的一个栈顶操作数

					String operation = operationStack.pop();
					String newTarget = s + "," + operation;
					target.add(newTarget);

					if (operation.equals("result")) {// 操作数是result
						workItem.add(2 + s);
					} else {
						workItem.add(s + "," + operation);
					}
					//增加一个操作数
					operationStack.push("result");
				}
			}
		}

		// 输出三元组
		for (String str : target) {
			System.out.println(str);
		}
		return workItem;
	}

	
}
