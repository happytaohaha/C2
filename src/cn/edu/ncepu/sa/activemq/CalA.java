package cn.edu.ncepu.sa.activemq;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class CalA extends Cal {

	private String calAString;
	public CalA(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "CalA");
		// TODO Auto-generated constructor stub
	}
	//ʵ����
	public CalA(String host, int port, String user, String pwd,String calAString) {
		super(host, port, user, pwd, "CalA");
		this.calAString = calAString;
		// TODO Auto-generated constructor stub
	}
	



	/**
	 * ��ʼ����
	 */
	@Override
	public void StartWork() {
		super.StartWork();

	}

	/**
	 * ���з���
	 */
	@Override
	public void run() {
		String returnResult = "100";
		Analysis calculate = new Analysis();
//		System.out.println("�������������:");
//		Scanner scanner =new Scanner(System.in);
//		String string = scanner.nextLine();
	    // String string="1*2+3*4";
		ArrayList<String> result = calculate.getStringList(calAString);//Stringת��ΪList

//		System.out.println(result);
		if(null == result||0 == result.size()){
			System.out.println("δ�������δ�漰�������㣬��Ǹ����������!!!");
			StopWork();
		}
		ArrayList<String> postresult = calculate.getPostOrder(result);
		if((null == postresult ||0 == postresult.size())&&result!=null&&result.size()!=0){
			System.out.println("��������룬��������");
			StopWork();
		}
//		System.out.println(postresult);


		Stack stack = new Stack();
		int i = 0;
		boolean flag = true;
		while (toWork) {
			try {
				if(flag) {
					for ( ;i < postresult.size(); i++) {
						if(Character.isDigit(postresult.get(i).charAt(0))){
							stack.push(Double.parseDouble(postresult.get(i)));
						}else{
							flag = false;
							Double back;
							Double front;
							String next="";
							switch (postresult.get(i).charAt(0)) {
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
			}catch (EmptyStackException e){
				System.out.println("���벻���������������¼���");
				StopWork();
			}
			if (null != reciever) {
				// �������������Ƿ���Ϣϵ
//				System.out.println(reciever.msgList.size());
				if (reciever.msgList.size() > 0) {
					if(i >= postresult.size()&&reciever.msgList.size()==1) {
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
					// ȡ��һ����Ϣ
					TextMessage msg = reciever.msgList.poll();
					try {
						returnResult = msg.getText();
						stack.push(Double.parseDouble(returnResult));
						for ( ;i < result.size(); i++) {
							if(Character.isDigit(postresult.get(i).charAt(0))){
								stack.push(Double.parseDouble(postresult.get(i)));
							}else{
								flag = false;
								Double back;
								Double front;
								String next="";
								switch (postresult.get(i).charAt(0)) {
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
					}catch (EmptyStackException e){
						System.out.println("���벻���������������¼���");
						StopWork();
					}
				}
				}
			}




			// ����0.1��
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
