package cn.edu.ncepu.sa.activemq;


import java.util.*;

/**
 * 
 * @author xhx
 * @modified happytaohaha
 */
public class Analysis {
	/**
	 * ����ֻ������ջ�Ż��ڴ�
	 */
	//�������ȼ�
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
	// ������ջ
	private static Stack<String> stack = new Stack<>();
	// ������ջ
	private static Stack<String> operationStack = new Stack<>();
	/**
	 * �㷨�Ż�
	 * 1.���õ�Ԫ��ʼλ��
	 * 2.��ǰλ��
	 * 3.��Ԫ����λ��
	 * ���� �� �ַ�  sin�����ж�
	 *
	 * ���ַ���ת����List
	 * @param exp
	 * @return
	 */
	public ArrayList<String> getStringList(String exp){
		int beginIndex = 0;// ��Ԫ��ʼλ��
		int endIndex = 0;// ��Ԫ����λ��
		int index = 0;// ��ǰλ��
		int length = exp.length();
		ArrayList<String> result = new ArrayList<String>();
		while (index < length) {
			char temp = exp.charAt(index);
			beginIndex = index;
			if (Character.isDigit(temp)) {
				// ���������
				index++;
				//�жϵ���һ��
				while (index < length && Character.isDigit(exp.charAt(index))
						&& (exp.charAt(index) != '\t')
						&& (exp.charAt(index) != '\r')
						&& (exp.charAt(index) != '\n')) {
					index++;
				}
				endIndex = index;
				//�жϵ���һ��,���Ҫ����
				index--;// ����һ���ַ�
				//exclusive���������һ���ַ�
				result.add(exp.substring(beginIndex, endIndex));
			} else if (temp == '+' || temp == '-' || temp == '*' || temp == '/'
					|| temp == '(' || temp == ')') {// �������
				endIndex = index;
				//���ǵ�ǰ�ַ�
				result.add(exp.substring(beginIndex, endIndex + 1));
			} else if (temp == 's' || temp == 'c') {
				// ��sin����cos
				index += 2;// ������λ

				endIndex = index + 1;
				result.add(exp.substring(beginIndex, endIndex));
			}
			index++;// ����һ���ַ�
		}

		return result;
	}
	
	/** �㷨����������ֱ�ӷŵ����
	 *�� �� ���� ���� ���ִ���ʽ   �õ����������
	 * ��׺���׺ʽ��˼��
	 *
	 * ����׺���ʽת��Ϊ��׺���ʽ
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
					//��(�Ž�ȥ
					stack.push(inOrderList.get(i));
					break;
				case ')':
					while (!stack.peek().equals("(") ) {
						result.add(stack.pop());
					}
					//����(
					stack.pop();
					break;
				default:
					//�Ƚϵ�ǰ���ź�ջ�ڵķ��Ž��бȽ�   ��ǰ���ȼ�С���� true   ��������ĺ���ȵĶ��ӽ�ȥ
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
	 * �Ƚ�������ȼ�
	 * ǰһ���͵�ǰ�Ľ��бȽ�  ǰһ���͵�ǰ�����ȼ���Ȼ���
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
	 * �����׺���ʽ
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

	static ArrayList<String> target = new ArrayList<String>();// ��Ԫ��
	/**
	 *
	 * @param post
	 * @return ���ع�����
	 */
	public static ArrayList<String> PostToThreeUint(ArrayList<String> post) {

		ArrayList<String> workItem = new ArrayList<String>();// ����Ҫ���صĹ�����
		for (String s : post) {
			if (operspriority.get(s) == null) {// ���ǲ�����,��������ջ
				operationStack.push(s);
			} else {
				// ������

				if (s.equals("+") || s.equals("-") || s.equals("*")
						|| s.equals("/")) {
					// ��Ԫ��������������ջ��������������

					String operation2 = operationStack.pop();// �ȵ������ǵڶ�������
					String operation1 = operationStack.pop();// �󵯳����ǵ�һ������
					String newTarget = s + "," + operation1 + "," + operation2;// ����Ԫ��
					target.add(newTarget);// ������Ԫ���б�

					if (operation1.equals("result")
							&& operation2.equals("result")) {

						// ������������Ϊresult,Ҫ��������
						// ���ñ�־λ
						workItem.add(3 + s);
					} else if (operation1.equals("result")) {
						// ��һ��������Ϊ�м���,������λΪ��־λ1
						workItem.add(1 + s + "," + operation2);
					} else if (operation2.equals("result")) {
						// �ڶ�����Ϊ�м���,������λΪ��־λ2
						workItem.add(2 + s + "," + operation1);
					} else {// ���������������result
						workItem.add(newTarget);
					}
					operationStack.push("result");
				} else if (s.equals("sin") || s.equals("cos")) {
					// һԪ������������������ջ��һ��ջ��������

					String operation = operationStack.pop();
					String newTarget = s + "," + operation;
					target.add(newTarget);

					if (operation.equals("result")) {// ��������result
						workItem.add(2 + s);
					} else {
						workItem.add(s + "," + operation);
					}
					//����һ��������
					operationStack.push("result");
				}
			}
		}

		// �����Ԫ��
		for (String str : target) {
			System.out.println(str);
		}
		return workItem;
	}

	
}
