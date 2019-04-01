package cn.edu.ncepu.sa.activemq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class CalA extends Cal {

	private String calAString;
	public CalA(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "CalA");
		// TODO Auto-generated constructor stub
	}
	//实例化
	public CalA(String host, int port, String user, String pwd,String calAString) {
		super(host, port, user, pwd, "CalA");
		this.calAString = calAString;
		// TODO Auto-generated constructor stub
	}
	



	/**
	 * 开始工作
	 */
	@Override
	public void StartWork() {
		super.StartWork();

	}

	/**
	 * //todo 如果只有一个计算
	 * 进行分析
	 */
	@Override
	public void run() {
		String returnResult = "100";
		Anaylsis calculate = new Anaylsis();
//		System.out.println("请输入计算任务:");
//		Scanner scanner =new Scanner(System.in);
//		String string = scanner.nextLine();
	    // String string="1*2+3*4"; 
		ArrayList<String> result = calculate.getStringList(calAString);//String转换为List
		result = calculate.getPostOrder(result);   //中缀变后缀
		System.out.println(result);
		Stack stack = new Stack();
		int i = 0;
		boolean flag = true;
		while (toWork) {
			if(flag) {
				for ( ;i < result.size(); i++) {
					if(Character.isDigit(result.get(i).charAt(0))){
						stack.push(Double.parseDouble(result.get(i)));
					}else{			
						flag = false;
						Double back;
						Double front;
						String next="";
						switch (result.get(i).charAt(0)) {
							case '+':
								back = (Double)stack.pop();
								front = (Double)stack.pop();
								next = "+,"+front+","+back;

								break;
							case '-':
								back = (Double)stack.pop();
								front = (Double)stack.pop();
								next = "-,"+front+","+back;					
								break;
							case '*':
								back = (Double)stack.pop();
								front = (Double)stack.pop();
								next = "*,"+front+","+back;
								break;
							case '/':
								back = (Double)stack.pop();
								front = (Double)stack.pop();
								next = "/,"+front+","+back;
								break;
							case 's':
								front = (Double)stack.pop();
								next = "sin,"+front;
								break;
							case 'c':
								front = (Double)stack.pop();
								next = "cos,"+front;
								break;
						}
						System.out.println(next);
						sendASubTask(next);
						i++;
						break;
					}
				}
			}
			if (null != reciever) {
				// 接收器对了中是否消息系
//				System.out.println(reciever.msgList.size());
				if (reciever.msgList.size() > 0) {
					if(i >= result.size()&&reciever.msgList.size()==1) {
						TextMessage msg1 = reciever.msgList.peek();
						try {
							returnResult = msg1.getText();
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Work over:" + returnResult);
						StopWork();
						
					}else {
					// 取出一个消息
					TextMessage msg = reciever.msgList.poll();
					try {
						returnResult = msg.getText();
						stack.push(Double.parseDouble(returnResult));
						for ( ;i < result.size(); i++) {
							if(Character.isDigit(result.get(i).charAt(0))){
								stack.push(Double.parseDouble(result.get(i)));
							}else{			
								flag = false;
								Double back;
								Double front;
								String next="";
								switch (result.get(i).charAt(0)) {
									case '+':
										back = (Double)stack.pop();
										front = (Double)stack.pop();
										next = "+,"+front+","+back;
										System.out.println(next);
										break;
									case '-':
										back = (Double)stack.pop();
										front = (Double)stack.pop();
										next = "-,"+front+","+back;
										break;
									case '*':
										back = (Double)stack.pop();
										front = (Double)stack.pop();
										next = "*,"+front+","+back;
										break;
									case '/':
										back = (Double)stack.pop();
										front = (Double)stack.pop();
										next = "/,"+front+","+back;
										break;
									case 's':
										back = (Double)stack.pop();
										next = "sin,"+back;
										break;
									case 'c':
										back = (Double)stack.pop();
										next = "cos,"+back;
										break;
								}
								sendASubTask(next);
								
								i++;
								break;
							}
						}

					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						returnResult = null;
					}
				}
				}
			}
			
			
			

			// 休眠0.1秒
			try {
				sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Override
	String getResult(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
