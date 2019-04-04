package cn.edu.ncepu.sa.activemq;

import jdk.nashorn.internal.ir.Flags;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class MainClass {

	public static void main(String[] args) {

		Boolean toWrok = true;
		
		// �ӷ���
		Add add = new Add("localhost", 61616, "admin", "admin");
		add.StartWork();
		System.out.println("Add is working...");

		Sub sub = new Sub("localhost", 61616, "admin", "admin");
		sub.StartWork();
		System.out.println("Sub is working...");

		Mul mul = new Mul("localhost", 61616, "admin", "admin");
		mul.StartWork();
		System.out.println("Mul is working...");
		Div div = new Div("localhost", 61616, "admin", "admin");
		div.StartWork();
		System.out.println("Div is working...");

		Sin sin = new Sin("localhost", 61616, "admin", "admin");
		sin.StartWork();
		System.out.println("Sin is working...");

		Cos cos = new Cos("localhost", 61616, "admin", "admin");
		cos.StartWork();
		System.out.println("Cos is working...");
/*
		//����ǵ�һ�汾
		Calhappytaohaha calhappytaohaha =new Calhappytaohaha("localhost", 61616, "admin", "admin");
		calhappytaohaha.AddWorkItem("+,2,3");
		calhappytaohaha.AddWorkItem("-,1");
		calhappytaohaha.AddWorkItem("+,109");
		calhappytaohaha.AddWorkItem("*,2");
		calhappytaohaha.AddWorkItem("sin");
		calhappytaohaha.StartWork();

		System.out.println("Calhappytaohaha is working...");

*/


		while(toWrok){
			try {


				//�Ľ�֮���
				//����һ�������������һ������
				System.out.println("�������������:(����һ��)");
				Scanner scanner =new Scanner(System.in);
				String string = scanner.nextLine();
				CalA calA = new CalA("localhost", 61616, "admin", "admin",string);
				calA.StartWork();
				System.out.println("Calculator is working...");



				System.out.println("System is runing, press 'x' to exit.");
				try {
					//��ȡһ����
					int xx = System.in.read();
					//��ֹ������Ӱ����һ������
					String string1 = scanner.nextLine();
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
