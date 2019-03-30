package cn.edu.ncepu.sa.activemq;

import java.util.LinkedList;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.StompConnection;

public class Sender extends Thread{
	/**
	 * ������
	 */
	private QueueConnection connection = null;
	
	/**
	 * ��Ϣ������������
	 */
	private String host;
	
	/**
	 * ��Ϣ�������˿�
	 */
	private int port;
	
	/**
	 * �û���
	 */
	private String user;
	
	/**
	 * ����
	 */
	private String pwd;

	private boolean toWork = false;
	
	/**
	 * �����͵���Ϣ�б�
	 */
	java.util.Queue<TextMessage> msgList = new LinkedList<TextMessage>();

	/**
	 * ���캯��
	 * @param host ����������IP
	 * @param port �˿ں�
	 * @param user �û���
	 * @param pwd ����
	 */
	public Sender(String host, int port, String user, String pwd) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;

		//OpenConn();

	}

	/**
	 * ������
	 * @return
	 */
	public Boolean OpenConn() {
		try {

			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user,pwd,
					"tcp://"+host+":"+String.valueOf(port));
			
			connection = factory.createQueueConnection();
			
			// connection.close();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection = null;
			return false;
		}
	}

	/**
	 * ����Ϣ����������һ����Ϣ
	 * @param desQueueName Ŀ�Ķ���
	 * @param msg Ҫ���͵��ı���Ϣ
	 * @param ReplyTo ����Ϣ���ظ�˭
	 * @return �Ƿ��ͳɹ�
	 */
	public Boolean SendAMsg(String desQueueName, String msg, String ReplyTo) {

		
		// ��������ж�������
		if (null == connection) {
			if (!OpenConn()) {
				return false;
			}
		}

		// ������Ϣ����
		try {

			QueueSession session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(desQueueName);
			QueueSender sender = session.createSender(queue);
			TextMessage message = session.createTextMessage();
			
			// ������Ϣ����
			message.setText(msg);
			
			// ���÷���Ŀ���ַ
			Destination destination = session.createQueue(ReplyTo);
			message.setJMSReplyTo(destination);
			
			// ������Ϣ
			sender.send(message);
			
			// �ر�����			
			sender.close();
			session.close();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * ��������
	 */
	public void startWork() {
		msgList.clear();
		toWork  = true;
		

		try {
			
			if(null == connection){
				OpenConn();
			}
			
			// �����Ự
		
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.start();
	}
	
	public void StopWork(){
		if(null != connection)
		{
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		toWork = false;
	}
	
	@Override
	public void run()
	{
		while(toWork){
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
