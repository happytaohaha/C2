package cn.edu.ncepu.sa.activemq;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.transport.stomp.StompFrame;

public abstract class C2Component extends Thread {

	/**
	 * ��Ϣ������������
	 */
	protected String host;
	
	/**
	 * ��Ϣ�������˿�
	 */
	protected int port;
	
	/**
	 * �û���
	 */
	protected String user;
	
	/**
	 * ����
	 */
	protected String pwd;


	/**
	 * ��ǰ������������
	 */
	public  String queue = "Test";

	/**
	 * �Ƿ��������
	 */
	Boolean toWork = false;

	/**
	 * ��Ϣ������
	 */
	Sender sender = null;
	
	/**
	 * ��Ϣ������
	 */
	Reciever reciever = null;

	/**
	 * ���캯��
	 * @param host ������/IP
	 * @param port �˿�
	 * @param user �û���
	 * @param pwd ����
	 */
	public C2Component(String host, int port, String user, String pwd,String queue) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.queue = queue;
	}

	/**
	 * ��ʼ����
	 */
	public void StartWork() {
		
		// ����һ��������
		sender = new Sender(host, port, user, pwd);
		sender.startWork();
		
		// ����һ��������
		reciever = new Reciever(host, port, user, pwd, queue);
		reciever.startListern();

		// ���������߳�
		toWork = true;
		this.start();
	}

	/**
	 * ֹͣ�������ͷ�����
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
	 * ���м���,��ȡ���
	 * @param msg �ַ�����Ϣ
	 * @return �Ӻ��ֵ
	 */
	abstract String  getResult(String msg) ;

	@Override
	public void run() {
		while (toWork) {
			if (null != reciever) {
				
				// �������������Ƿ���Ϣϵ
				if (reciever.msgList.size() > 0) {
					
					// ȡ��һ����Ϣ
					TextMessage msg = reciever.msgList.poll();
					String result = null;
					try {
						// ������
						result = getResult(msg.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// �����������ظ������ߣ� ��˭���������ݣ��ͷ��ظ�˭��
					if (result != null && sender != null) {
						try {
							// ��ȡ����������
							String disUrl = msg.getJMSReplyTo().toString();
							disUrl = disUrl.substring(8);
							
							// ������Ϣ
							sender.SendAMsg(disUrl, result,queue);
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

}
