import java.io.IOException;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Serial {
	SerialPort serialPort;
	
	public Serial() {
		super();
	}

	void connect(String portName) throws Exception {
		serialPort=new SerialPort(portName);
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void send(String send) {
		try {
			serialPort.writeBytes(packetConverter(send));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void send(byte[] send) {
		try {
			serialPort.writeBytes(send);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	byte[] packetConverter(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
