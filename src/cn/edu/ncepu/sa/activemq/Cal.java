package cn.edu.ncepu.sa.activemq;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Cal extends C2Component {

	/**
	 * 工作任务列表
	 */
	java.util.Queue<String> workList = new LinkedList<String>();

	public Cal(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "Cal");
		// TODO Auto-generated constructor stub
	}

	public void AddWorkItem(String work) {

		workList.offer(work);
	}
	//在这增加计算东西
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

				// 接收器对了中是否消息系
				if (reciever.msgList.size() > 0) {

					// 取出一个消息
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
