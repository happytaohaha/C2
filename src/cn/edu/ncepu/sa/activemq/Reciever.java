package cn.edu.ncepu.sa.activemq;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Reciever extends Thread {

	/**
	 * ���ӹ���
	 */
	private ActiveMQConnectionFactory factory;
	
	/**
	 * ����������
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

	/**
	 * Ҫ�����Ķ�������
	 */
	private String queueName;
	
	/**
	 * �Ƿ����������ڿ����߳�����
	 */
	Boolean toWork = false;

	/**
	 * ��ǰ�Ự
	 */
	QueueSession session;

	/**
	 * �����¼��Ľ�����
	 */
	QueueReceiver receiver;
	
	/**
	 * ��Ϣ����
	 */
	javax.jms.Queue queue;

	/**
	 * �յ�����Ϣ�б�
	 */
	java.util.Queue<TextMessage> msgList = new LinkedList<TextMessage>();


	/**
	 * �յ�����Ϣջ
	 */
	Stack<TextMessage> msgstack=new Stack<TextMessage>();
	/**
	 * ���캯��
	 * @param host ������/IP
	 * @param port �˿�
	 * @param user �û���
	 * @param pwd ����
	 * @param queue Ҫ�����Ķ�������
	 */
	public Reciever(String host, int port, String user, String pwd, String queue) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.queueName = queue;

		//OpenConn();
	}

	/**
	 * ������
	 * @return
	 */
	public Boolean OpenConn() {
		try {
			factory = new ActiveMQConnectionFactory(user,pwd,
					"tcp://"+host+":"+String.valueOf(port));
			connection = factory.createQueueConnection();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection = null;
			return false;
		}
	}

	/**
	 * ����������Ϣ
	 */
	public void startListern() {
		toWork = true;
		

		try {
			
			if(null == connection){
				OpenConn();
			}
			
			// �����Ự
			session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
			receiver = session.createReceiver(queue);
			
			
			// �����¼�
			receiver.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;

						// ��Ϣ�������
						msgList.offer(textMessage);

						//��Ϣ����ջ
						msgstack.push(textMessage);

					}
				}
			});
			connection.start();
			
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msgList.clear();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.start();
	}

	/**
	 * ֹͣ����
	 */
	public void stopListern() {
		toWork = false;
		// this.destroy();

	}

	@Override
	public void run() {
		while (toWork) {
			try {
				sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// �ͷ�����
		if (connection != null) {
			try {
				connection.stop();
				// connection.disconnect();
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			connection = null;
		}
	}
}
