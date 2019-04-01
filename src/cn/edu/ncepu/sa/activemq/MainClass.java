package cn.edu.ncepu.sa.activemq;

import jdk.nashorn.internal.ir.Flags;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Boolean toWrok = true;
		
		// �ӷ���
		Add add = new Add("118.230.232.226", 61616, "admin", "admin");
		add.StartWork();
		System.out.println("Add is working...");

		Sub sub = new Sub("118.230.232.226", 61616, "admin", "admin");
		sub.StartWork();
		System.out.println("Sub is working...");

		Mul mul = new Mul("118.230.232.226", 61616, "admin", "admin");
		mul.StartWork();
		System.out.println("Mul is working...");
		Div div = new Div("118.230.232.226", 61616, "admin", "admin");
		div.StartWork();
		System.out.println("div is working...");

		Sin sin = new Sin("118.230.232.226", 61616, "admin", "admin");
		sin.StartWork();
		System.out.println("sin is working...");

		Cos cos = new Cos("118.230.232.226", 61616, "admin", "admin");
		cos.StartWork();
		System.out.println("cos is working...");

//		Add add = new Add("localhost", 61616, "admin", "admin");
//		add.StartWork();
//		System.out.println("Add is working...");
//
//		Sub sub = new Sub("localhost", 61616, "admin", "admin");
//		sub.StartWork();
//		System.out.println("Sub is working...");
//
//		Mul mul = new Mul("localhost", 61616, "admin", "admin");
//		mul.StartWork();
//		System.out.println("Mul is working...");
//		Sin sin = new Sin("localhost", 61616, "admin", "admin");
//		sin.StartWork();
//		System.out.println("Sin is working...");
		//�ն�
//		Cal cal  = new Cal("localhost", 61616, "admin", "admin");
//		cal.AddWorkItem("+,2,3");
//		cal.AddWorkItem("-,1");
//		cal.AddWorkItem("+,109");
//		cal.AddWorkItem("*,2");
//		cal.AddWorkItem("sin");
//		cal.StartWork();

//		Calhappytaohaha calhappytaohaha =new Calhappytaohaha("118.230.232.226", 61616, "admin", "admin");
//		calhappytaohaha.AddWorkItem("+,2,3");
//		calhappytaohaha.AddWorkItem("-,1");
//		calhappytaohaha.AddWorkItem("+,109");
//		calhappytaohaha.AddWorkItem("*,2");
//		calhappytaohaha.AddWorkItem("sin");
//		calhappytaohaha.StartWork();
//
//		System.out.println("Calhappytaohaha is working...");



//		 ������
		//Sender sender= new Sender("118.230.232.226", 61616, "admin", "admin");
		
//		 ���Դ���

//		 �����������
		//Random ran = new Random();



		while(toWrok){
			try {
//				String s1;
//				int flag = 0;
//				System.out.println("������һ����������");
//				Scanner scanner = new Scanner(System.in);
//				s1 = scanner.nextLine();
//				StringBuffer s2 =new StringBuffer();

				//�� s1 ����
//				int len =s1.length();
//				for(int i = 0; i < len; i++){
//					char a =s1.charAt(i);
//					switch (a){
//						case '+':
//							break;
//						case '*':
//							break;
//						default:
//
//							break;
//					}
//				}

				//�Ľ�֮���
				System.out.println("�������������:");
				Scanner scanner =new Scanner(System.in);
				String string = scanner.nextLine();
				CalA calA = new CalA("118.230.232.226", 61616, "admin", "admin",string);
				calA.StartWork();
				System.out.println("Calculator is working...");




//				// ��ȡ���������
//				int x = ran.nextInt(100);
//				int y = ran.nextInt(100);
//
//
//				// ���������Ϣ������ +,2,3
//				String msg = "+,"+String.valueOf(x)+","+String.valueOf(y);
//
//				// ������Ϣ
//				if(sender != null && add != null){
//					sender.SendAMsg(add.queue, msg, "Cal");
//					System.out.println("I have send:"+msg+",you can see answer "+String.valueOf(x+y)+" in Queue Cal");
//				}
				System.out.println("System is runing, press 'x' to exit.");
				try {
					int xx = System.in.read();
					if(xx == 'x'){
//						calhappytaohaha.StopWork();
						sub.StopWork();
						add.StopWork();
						calA.StopWork();
						System.out.println("Bye...");
						toWrok = false;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ����2��
				//Thread.sleep(2000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
