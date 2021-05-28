package ac.kr.kongju.MotorController;

import java.text.SimpleDateFormat;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

public class SerialUtil {

	static SerialPort serialPort;
	static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");

	/**
	 * COM PORT 를 통해서 보드와의 Connection을 Open
	 */
	public static boolean open(String comport) {
		serialPort = new SerialPort(comport);
		try {
			serialPort.openPort();
			serialPort.setParams(
					SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1, 
					SerialPort.PARITY_NONE);
			Starter.setMSG(comport + " : 연결되었습니다.");
			Starter.enableButton(true);
			return true;
		} catch (SerialPortException e) {
			Starter.setMSG(comport + " : 연결되지 않았습니다");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 보드와의 Connection을 Close
	 */
	public static void close() {
		try {
			serialPort.closePort();
			Starter.setMSG("Com Port 연결해주세요~");
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 현재 보드와의 Connection 상태를 반환
	 */
	public static boolean isConnected() {
		if(serialPort == null)	// null 이라면 연결이 안된 것으로..
			return false;
		return serialPort.isOpened();
	}
	
	/**
	 * byte[] 형태의 Packet을 인자로 전달받아서 보드로 송신
	 */
	public static void write(byte[] buffer) {
		try {
			serialPort.writeBytes(buffer);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 수신할 Packet의 byte size를 인자로 전달받아서
	 * size만큼 Packet을 수신함
	 */
	public static int[] read(int size) {
		int[] buffer = new int[589];
		try {
			buffer = serialPort.readIntArray(size, 2000);
			// 구현 안함
			
		} catch (SerialPortException e) {
			e.printStackTrace();
		} catch (SerialPortTimeoutException e) {	// timeout catch
			e.printStackTrace();
		}
		return buffer;
	}
	
}

