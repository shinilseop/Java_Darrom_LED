package Serial;

import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	OutputStream out;
	CommPortIdentifier comm=null;
	CommPort commPort = null;
	SerialPort serialPort = null;

	String bin[] = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };
	String start = "02C207";
	// black, blue, green, orange, purple, red, sky, yellow
	String color[] = { "000000", "000012", "001200", "120600", "060012", "120000", "000912", "120900", "404040"};
	String gameover = "808080";
	String not = "000000";
	String end = "03";

	public Serial(String port) {
		try {
			comm=CommPortIdentifier.getPortIdentifier(port);
			
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
			out = serialPort.getOutputStream();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void ledON_Color(String index, int colorIdx) {
		try {
			String packet = start + index + color[colorIdx];
			packet = packet + getEachXOR(packet) + end;
			out.write(packetConverter(packet));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ledOFF(String index) {
		try {
			String packet = start + index + color[0];
			packet = packet + getEachXOR(packet) + end;
			out.write(packetConverter(packet));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GameOver(String index) {
		try {
			String packet = start + index + gameover;
			String xor=getEachXOR(packet);
			if(!xor.equals("not")) {
				packet = packet + getEachXOR(packet) + end;
				out.write(packetConverter(packet));
			}
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

	public String getEachXOR(String number) {
		String strNum1 = "1100";
		String strNum2 = "0010";

		for (int i = 4; i < number.length(); i += 2) {
			int tmp1 = number.charAt(i);
			int tmp2 = number.charAt(i + 1);
			if (tmp1 >= 65) {
				tmp1 -= 55;
			} else {
				tmp1 -= 48;
			}
			if (tmp2 >= 65) {
				tmp2 -= 55;
			} else {
				tmp2 -= 48;
			}
			if (0 <= tmp1 && tmp1 <= 16 && 0 <= tmp2 && tmp2 <= 16) {
				String strTmp1 = bin[tmp1];
				String strTmp2 = bin[tmp2];

				String result1 = "";
				String result2 = "";
				for (int j = 0; j < strNum1.length(); j++) {
					if (strNum1.charAt(j) != strTmp1.charAt(j)) {
						result1 += "1";
					} else {
						result1 += "0";
					}

					if (strNum2.charAt(j) != strTmp2.charAt(j)) {
						result2 += "1";
					} else {
						result2 += "0";
					}
				}
				strNum1 = result1;
				strNum2 = result2;
			} else {
				return "not";
			}
		}

		String last1 = Integer.toHexString((strNum1.charAt(3) - 48) + (strNum1.charAt(2) - 48) * 2
				+ (strNum1.charAt(1) - 48) * 4 + (strNum1.charAt(0) - 48) * 8).toUpperCase();
		String last2 = Integer.toHexString((strNum2.charAt(3) - 48) * 1 + (strNum2.charAt(2) - 48) * 2
				+ (strNum2.charAt(1) - 48) * 4 + (strNum2.charAt(0) - 48) * 8).toUpperCase();
		return last1 + last2;
	}
}
