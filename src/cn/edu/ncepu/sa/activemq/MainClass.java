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
		
		// 加法器
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
/*
		//这个是第一版本
		Calhappytaohaha calhappytaohaha =new Calhappytaohaha("118.230.232.226", 61616, "admin", "admin");
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


				//改进之后的
				System.out.println("请输入计算任务:");
				Scanner scanner =new Scanner(System.in);
				String string = scanner.nextLine();
				CalA calA = new CalA("118.230.232.226", 61616, "admin", "admin",string);
				calA.StartWork();
				System.out.println("Calculator is working...");



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
				// 休眠2秒
				//Thread.sleep(2000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
