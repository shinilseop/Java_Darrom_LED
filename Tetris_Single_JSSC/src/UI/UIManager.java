package UI;

import Serial.Serial;

public class UIManager {
	UI ui;
	String id;
	UIChanger uic;

	Serial led;

	public UIManager() {
		led=new Serial("COM5");
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				int y = 20 - i - 1;
				int x = j;
				String index = "";
				if (y >= 16) {
					index += "1" + (y - 16);
				} else if (y >= 10) {
					char ch = (char) (y + 55);
					index += "0" + ch;
				} else {
					index += "0" + y;
				}

				if (x >= 16) {
					index += "1" + (x - 16);
				} else if (x >= 10) {
					char ch = (char) (x + 55);
					index += "0" + ch;
				} else {
					index += "0" + x;
				}
				
				if(j<=4) {
					led.ledON_Color(index, 1);
				} else if(j>=15) {
					led.ledON_Color(index, 1);
				} else {
					led.ledOFF(index);
				}
			}
		}
		uic = new UIChanger() {
			public void goMenu(int menu) {
				ui.closeThis();
				ui = new MenuUI(uic, menu);
			}
			public void goHow() {
				ui.closeThis();
				ui = new HowUI(uic);
			}
			public void goGame() {
				ui.closeThis();
				ui = new GameUI(uic, led);
			}
			public void goScore() {
				ui.closeThis();
				ui = new ScoreUI(uic);
			}     
			public void goRegister() {
				ui.closeThis();
				ui = new RegisterUI(uic);
			}
			public void goLogin() {
				ui.closeThis();
				ui = new LoginUI(uic);
			}
			public String getId() {
				return id;
			}
			public void setId(String idTmp) {
				id=idTmp;
			}
		};
	}

	public void start() {
		ui = new TitleUI(uic);
	}
}
