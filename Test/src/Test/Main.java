package Test;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner sc=new Scanner(System.in);
		
		String msg="01000000";
		int num=Integer.parseInt("11111111111111111111111111000000", 2);
		System.out.println(num);
		System.out.println(Integer.toBinaryString((-2147483648+Integer.parseInt(msg, 2))));
	}
}
