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
	 * 连接器
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

	private boolean toWork = false;
	
	/**
	 * 待发送的消息列表
	 */
	java.util.Queue<TextMessage> msgList = new LinkedList<TextMessage>();

	/**
	 * 构造函数
	 * @param host 主机名或者IP
	 * @param port 端口号
	 * @param user 用户名
	 * @param pwd 密码
	 */
	public Sender(String host, int port, String user, String pwd) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;

		//OpenConn();

	}

	/**
	 * 打开连接
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
	 * 向消息服务器发送一个消息
	 * @param desQueueName 目的队列
	 * @param msg 要发送的文本消息
	 * @param ReplyTo 该消息返回给谁
	 * @return 是否发送成功
	 */
	public Boolean SendAMsg(String desQueueName, String msg, String ReplyTo) {

		
		// 如果连接中断则重连
		if (null == connection) {
			if (!OpenConn()) {
				return false;
			}
		}

		// 发送消息过程
		try {

			QueueSession session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(desQueueName);
			QueueSender sender = session.createSender(queue);
			TextMessage message = session.createTextMessage();
			
			// 设置消息内容
			message.setText(msg);
			
			// 设置返回目标地址
			Destination destination = session.createQueue(ReplyTo);
			message.setJMSReplyTo(destination);
			
			// 发送消息
			sender.send(message);
			
			// 关闭连接			
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
	 * 开启连接
	 */
	public void startWork() {
		msgList.clear();
		toWork  = true;
		

		try {
			
			if(null == connection){
				OpenConn();
			}
			
			// 创建会话
		
			
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
