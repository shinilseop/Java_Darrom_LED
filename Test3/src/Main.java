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
		
		byte[] send= {0x02, (byte) 0xC0, 0x05, 0x40, 0x00, 0x00, (byte) 0x85, 0x03};
		serial.send(send);
	}
}
 