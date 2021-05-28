import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {
	OutputStream out;

	public SerialWriter(OutputStream out) {
		this.out = out;
	}

	@Override
	public void run() {
		int c = 0;

		try {
			while ((c = System.in.read()) > -1) {
				System.out.println("before : " + c);
				if (c < 58) {
					c -= 48;
				} else {
					c -= 55;
				}
				System.out.println("after : " + c);
				if (c >= 0) {
					out.write(c);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
