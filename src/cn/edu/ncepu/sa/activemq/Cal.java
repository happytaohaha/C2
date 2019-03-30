package cn.edu.ncepu.sa.activemq;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Cal extends C2Component {

	/**
	 * ���������б�
	 */
	java.util.Queue<String> workList = new LinkedList<String>();

	public Cal(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "Cal");
		// TODO Auto-generated constructor stub
	}

	public void AddWorkItem(String work) {

		workList.offer(work);
	}
	//�������Ӽ��㶫��
	private void sendASubTask(String msg) {
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
		case "sin":
			sender.SendAMsg("Sin", msg, queue);
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

				// �������������Ƿ���Ϣϵ
				if (reciever.msgList.size() > 0) {

					// ȡ��һ����Ϣ
					TextMessage msg = reciever.msgList.poll();
					String result;
					try {
						result = msg.getText();
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						result = null;
					}

					if (workList.size() > 0) {
						if (null != result) {
							String next = workList.poll();
							next += "," + result;
							sendASubTask(next);
						}

					} else {
						System.out.println("Work over:" + result);
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
