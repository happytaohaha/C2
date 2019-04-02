package cn.edu.ncepu.sa.activemq;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Cal extends C2Component {
	private String calString;
	/**
	 * ���������б�
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
	//�������Ӽ��㶫��
	//����������ʱ��Ҫ�����µĶ���
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
	 * ��ʼ����
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
					// �жϹ������л���û������
					String next = workList.poll();
					String[] unit = next.split(",");
					if (unit.length >= 3) {
						// �����������˵����������������򲻴�receiver����Ϣ�б���ȡresult
						sendASubTask(next);
					} else {
						// ������ֻ��һ����������(+,1,result),(+,result1,result2)

						if (reciever.msgstack.size() > 0) {
							// �Ӵ洢����ֵ��ջ��ȡ��ջ��result
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
								// ��һ����־λΪ1����ʾ��1��������Ϊresult
								int index = 1;
								while (index < next.length()
										&& next.charAt(index) != ',') {
									index++;
								}
								// ȡ������
								String next1 = next.substring(1, ++index);
								// ȡ�ڶ���������
								String next2 = next.substring(index,
										next.length());
								// ��result�����м�
								next = next1 + result + "," + next2;
							} else if (next.charAt(0) == '2') {
								// �ڶ���������Ϊresult,��result�ӵ�����
								next = next.substring(1, next.length());
								next += "," + result;
							} else if (next.charAt(0) == '3') {

								// ֻ�в�������˵����������������result���ٴ�receiver������ȡ��һ����Ϣ
								// ��������
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
							}// else ��־λΪ�գ�ֱ���ύ

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
					// �жϽ��ն�����������Ϣ
					else {
						System.out.println("Work over:" + "error");
					}
				}
				// ����0.1��
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
