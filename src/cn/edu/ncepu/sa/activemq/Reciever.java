package cn.edu.ncepu.sa.activemq;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

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
	 * 连接工厂
	 */
	private ActiveMQConnectionFactory factory;
	
	/**
	 * 队列连接器
	 */
	private QueueConnection connection = null;
	/**
	 * 消息服务器主机名
	 */
	private String host;
	
	/**
	 * 消息服务器端口
	 */
	private int port;
	
	/**
	 * 用户名
	 */
	private String user;
	
	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * 要侦听的队列名字
	 */
	private String queueName;
	
	/**
	 * 是否侦听，用于控制线程运行
	 */
	Boolean toWork = false;

	/**
	 * 当前会话
	 */
	QueueSession session;

	/**
	 * 基于事件的接收器
	 */
	QueueReceiver receiver;
	
	/**
	 * 消息队列
	 */
	javax.jms.Queue queue;

	/**
	 * 收到的消息列表
	 */
	java.util.Queue<TextMessage> msgList = new LinkedList<TextMessage>();

	/**
	 * 构造函数
	 * @param host 主机名/IP
	 * @param port 端口
	 * @param user 用户名
	 * @param pwd 密码
	 * @param queue 要侦听的队列名称
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
	 * 打开连接
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
	 * 启动侦听消息
	 */
	public void startListern() {
		toWork = true;
		

		try {
			
			if(null == connection){
				OpenConn();
			}
			
			// 创建会话
			session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
			receiver = session.createReceiver(queue);
			
			
			// 侦听事件
			receiver.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;

						// 消息加入队列
						msgList.offer(textMessage);

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
	 * 停止侦听
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

		// 释放连接
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
