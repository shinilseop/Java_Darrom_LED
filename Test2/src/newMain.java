
public class newMain {
	public static void main(String args[]) {
		Serial serial=new Serial("COM5");
		byte[] off= {0x02, (byte) 0xC0, 0x05, 0x00, 0x00, 0x00, (byte) 0xC5, 0x03};//LED OFF
		byte[] red= {0x02, (byte) 0xC0, 0x05, 0x40, 0x00, 0x00, (byte) 0x85, 0x03};//LED RED
		byte[] green= {0x02, (byte) 0xC0, 0x05, 0x00, 0x40, 0x00, (byte) 0x85, 0x03};//LED GREEN
		byte[] blue= {0x02, (byte) 0xC0, 0x05, 0x00, 0x00, 0x40, (byte) 0x85, 0x03};//LED BLUE
		
		serial.send(red);
	}
}
