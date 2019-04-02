package cn.edu.ncepu.sa.activemq;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Cal extends C2Component {
	private String calString;
	/**
	 * 工作任务列表
	 */
	java.util.Queue<String> workList = new LinkedList<String>();

	public Cal(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "Cal");
		// TODO Auto-generated constructor stub
	}

	public Cal(String host, int port, String user, String pwd,String calAString) {
		super(host, port, user, pwd, "Cal");
		this.calString = calString;
		// TODO Auto-generated constructor stub
	}

	public void AddWorkItem(String work) {

		workList.offer(work);
	}
	//在这增加计算东西
	//发送子任务时需要增加新的队列
	protected void sendASubTask(String msg) {
		String tmp[] = msg.split(",");
		switch (tmp[0]) {
			case "+":
				sender.SendAMsg("Add", msg, queue);
				break;
			case "-":
				sender.SendAMsg("Sub", msg, queue);
				break;
			case "*":
				sender.SendAMsg("Mul", msg, queue);
				break;
			case "/":
				sender.SendAMsg("Div", msg, queue);
				break;
			case "sin":
				sender.SendAMsg("Sin", msg, queue);
				break;
			case "cos":
				sender.SendAMsg("Cos", msg, queue);
				break;
		default:
			break;
		}

	}

	/**
	 * 开始工作
	 */
	@Override
	public void StartWork() {
		super.StartWork();

		if (workList.size() > 0) {
			String first = workList.poll();
			sendASubTask(first);
		}
		
	}
	@Override
	public void run() {

		while (toWork) {
			if (null != reciever) {

				String result = "";

				if (workList.size() > 0) {
					// 判断工作队列还有没工作项
					String next = workList.poll();
					String[] unit = next.split(",");
					if (unit.length >= 3) {
						// 工作项有三项，说明将产生两个回填，则不从receiver的消息列表中取result
						sendASubTask(next);
					} else {
						// 工作项只有一项或两项，比如(+,1,result),(+,result1,result2)

						if (reciever.msgstack.size() > 0) {
							// 从存储返回值的栈中取出栈顶result
							TextMessage msg = reciever.msgstack.pop();

							try {
								result = msg.getText();
							} catch (JMSException e1) {
								e1.printStackTrace();
								result = null;
							}
						}

						if (null != result) {
							if (next.charAt(0) == '1') {
								// 第一个标志位为1，表示第1个操作数为result
								int index = 1;
								while (index < next.length()
										&& next.charAt(index) != ',') {
									index++;
								}
								// 取操作符
								String next1 = next.substring(1, ++index);
								// 取第二个操作数
								String next2 = next.substring(index,
										next.length());
								// 将result插入中间
								next = next1 + result + "," + next2;
							} else if (next.charAt(0) == '2') {
								// 第二个操作数为result,将result加到后面
								next = next.substring(1, next.length());
								next += "," + result;
							} else if (next.charAt(0) == '3') {

								// 只有操作符，说明两个操作数都是result，再从receiver队列中取出一个消息
								// 回填两次
								String result1 = "";
								if (reciever.msgstack.size() > 0) {
									TextMessage msg1 = reciever.msgstack.pop();

									try {
										result1 = msg1.getText();
									} catch (JMSException e1) {
										e1.printStackTrace();
										result1 = null;
									}
								}
								next = next.substring(1, next.length());
								next = next + "," + result1 + "," + result;
							}// else 标志位为空，直接提交

							sendASubTask(next);
						}

					}
				} else {
					if (reciever.msgstack.size() > 0) {
						try {
							result = reciever.msgstack.pop().getText();
							toWork = false;
							System.out.println("Work over:" + result);
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
					// 判断接收队列中有无消息
					else {
						System.out.println("Work over:" + "error");
					}
				}
				// 休眠0.1秒
				try {
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	String getResult(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
