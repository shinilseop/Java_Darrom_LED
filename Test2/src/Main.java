import java.util.Scanner;

public class Main {
	static String bin[] = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };
	public static void main(String args[]) {

		//Serial serial = new Serial("COM5");
		Scanner sc = new Scanner(System.in);

		String start = "02C207", color = "000040", end="03";
		/*
		 * for (int i = 0; i < 20; i++) { for (int j = 0; j < 20; j++) { String tmp =
		 * start; if (i >= 16) { tmp += "1" + (i - 16); } else if (i >= 10) { char ch =
		 * (char) (i + 55); tmp += "0" + ch; } else { tmp += "0" + i; }
		 * 
		 * if (j >= 16) { tmp += "1" + (j - 16); } else if (j >= 10) { char ch = (char)
		 * (j + 55); tmp += "0" + ch; } else { tmp += "0" + j; }
		 * 
		 * tmp += color; tmp += getXOR(tmp); tmp += end;
		 * 
		 * serial.send(hexStringToByteArray(tmp)); } }
		 * 
		 * serial.send(hexStringToByteArray("02C005000000C503"));
		 */
		System.out.println(getEachXOR("02C2070102FFFF00"));
	}

	public static String getXOR(String number) {
		int num1 = number.charAt(2);
		int num2 = number.charAt(3);

		String strNum1 = "";
		String strNum2 = "";

		if (num1 >= 65) {
			strNum1 = Integer.toBinaryString(num1 - 55);
		} else {
			strNum1 = Integer.toBinaryString(num1 - 48);
		}
		if (num2 >= 65) {
			strNum2 = Integer.toBinaryString(num2 - 55);
		} else {
			strNum2 = Integer.toBinaryString(num2 - 48);
		}
		for (; strNum2.length() < 4;) {
			strNum2 = "0" + strNum2;
		}

		// System.out.println("str 1=" + strNum1 + " " + num1);
		// System.out.println("str 2=" + strNum2 + " " + num2);
		for (int i = 4; i < number.length(); i += 2) {
			int tmp1 = number.charAt(i);
			int tmp2 = number.charAt(i + 1);
			String strTmp1 = "";
			String strTmp2 = "";
			if (tmp1 >= 65) {
				strTmp1 = Integer.toBinaryString(tmp1 - 55);
			} else {
				strTmp1 = Integer.toBinaryString(tmp1 - 48);
			}
			for (; strTmp1.length() < 4;) {
				strTmp1 = "0" + strTmp1;
			}
			if (tmp2 >= 65) {
				strTmp2 = Integer.toBinaryString(tmp2 - 55);
			} else {
				strTmp2 = Integer.toBinaryString(tmp2 - 48);
			}
			for (; strTmp2.length() < 4;) {
				strTmp2 = "0" + strTmp2;
			}

			// System.out.println("1=" + strTmp1);
			// System.out.println("2=" + strTmp2);

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
		}

		// System.out.println("Result "+strNum1+" "+strNum2);

		String last1 = Integer.toHexString((strNum1.charAt(3) - 48) * 1 + (strNum1.charAt(2) - 48) * 2
				+ (strNum1.charAt(1) - 48) * 4 + (strNum1.charAt(0) - 48) * 8).toUpperCase();
		String last2 = Integer.toHexString((strNum2.charAt(3) - 48) * 1 + (strNum2.charAt(2) - 48) * 2
				+ (strNum2.charAt(1) - 48) * 4 + (strNum2.charAt(0) - 48) * 8).toUpperCase();
		// System.out.println(last1+" "+last2);
		return last1 + last2;
	}
	
	public static String getEachXOR(String number) {
		String strNum1 = "1100";
		String strNum2 = "0010";

		for (int i = 4; i < number.length(); i += 2) {
			int tmp1 = number.charAt(i);
			int tmp2 = number.charAt(i + 1);
			if (tmp1 >= 65) {
				tmp1-=55;
			} else {
				tmp1-=48;
			}
			if (tmp2 >= 65) {
				tmp2 -= 55;
			} else {
				tmp2 -= 48;
			}
			
			String strTmp1=bin[tmp1];
			String strTmp2=bin[tmp2];
			System.out.println(strNum1+" "+strNum2);
			System.out.println(strTmp1+" "+strTmp2);
			
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
			System.out.println("result="+strNum1+" "+strNum2);
		}

		String last1 = Integer.toHexString((strNum1.charAt(3) - 48) + (strNum1.charAt(2) - 48) * 2
				+ (strNum1.charAt(1) - 48) * 4 + (strNum1.charAt(0) - 48) * 8).toUpperCase();
		String last2 = Integer.toHexString((strNum2.charAt(3) - 48) * 1 + (strNum2.charAt(2) - 48) * 2
				+ (strNum2.charAt(1) - 48) * 4 + (strNum2.charAt(0) - 48) * 8).toUpperCase();
		return last1 + last2;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
