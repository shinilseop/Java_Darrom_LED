import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	InputStream in;
	OutputStream out;
	CommPortIdentifier comm=null;
	CommPort commPort = null;
	SerialPort serialPort = null;
	
	Serial(String port){
		try {
			CommPortIdentifier comm=CommPortIdentifier.getPortIdentifier(port);
			
			if(comm.isCurrentlyOwned()) {
				System.out.println("Error : "+port+" 가 이미 사용중 입니다.");
			} else {
				commPort=comm.open(this.getClass().getName(), 2000);
				if(commPort instanceof SerialPort) {
					serialPort=(SerialPort)commPort;
					serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				}
				System.out.println("comport 성공");
			}

			in = serialPort.getInputStream();
			out = serialPort.getOutputStream();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void send(byte[] send) {
		try {
			out.write(send);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
