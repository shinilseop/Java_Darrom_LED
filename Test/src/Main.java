import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		Serial serial = null;
		try {
			serial=new Serial();
			serial.connect("COM5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
 