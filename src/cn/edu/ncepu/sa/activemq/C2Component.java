package cn.edu.ncepu.sa.activemq;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.transport.stomp.StompFrame;

public abstract class C2Component extends Thread {

	/**
	 * 消息服务器主机名
	 */
	protected String host;
	
	/**
	 * 消息服务器端口
	 */
	protected int port;
	
	/**
	 * 用户名
	 */
	protected String user;
	
	/**
	 * 密码
	 */
	protected String pwd;


	/**
	 * 当前构件队列名称
	 */
	public  String queue = "Test";

	/**
	 * 是否继续工作
	 */
	Boolean toWork = false;

	/**
	 * 消息发送器
	 */
	Sender sender = null;
	
	/**
	 * 消息接收器
	 */
	Reciever reciever = null;

	/**
	 * 构造函数
	 * @param host 主机名/IP
	 * @param port 端口
	 * @param user 用户名
	 * @param pwd 密码
	 */
	public C2Component(String host, int port, String user, String pwd,String queue) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.queue = queue;
	}

	/**
	 * 开始工作
	 */
	public void StartWork() {
		
		// 启动一个发送器
		sender = new Sender(host, port, user, pwd);
		sender.startWork();
		
		// 启动一个接收器
		reciever = new Reciever(host, port, user, pwd, queue);
		reciever.startListern();

		// 开启工作线程
		toWork = true;
		this.start();
	}

	/**
	 * 停止工作，释放连接
	 */
	public void StopWork() {
		if (reciever != null) {
			reciever.stopListern();
			reciever = null;
		}
		
		if(sender != null){
			sender.StopWork();
			sender = null;
		}

		toWork = false;

	}

	/**
	 * 进行计算,并取结果
	 * @param msg 字符串消息
	 * @return 加后的值
	 */
	abstract String  getResult(String msg) ;

	@Override
	public void run() {
		while (toWork) {
			if (null != reciever) {
				
				// 接收器对了中是否消息系
				if (reciever.msgList.size() > 0) {
					
					// 取出一个消息
					TextMessage msg = reciever.msgList.poll();
					String result = null;
					try {
						// 计算结果
						result = getResult(msg.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// 将计算结果返回给发送者， （谁发来的数据，就返回给谁）
					if (result != null && sender != null) {
						try {
							// 获取发送者名称
							String disUrl = msg.getJMSReplyTo().toString();
							disUrl = disUrl.substring(8);
							
							// 发送消息
							sender.SendAMsg(disUrl, result,queue);
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

}
