package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DBMS.DBMS;

public class RegisterUI extends UI implements ActionListener {
	Font font;

	JPanel jpContent, jpText, jpHint;
	String strContent[] = { "아이디 : ", "패스워드 : ", "패스워드 확인 : ", "이름 : ", "이메일 : " };
	String strHint[] = { "4~20자 이내로 입력해주세요.", "영문+숫자로 조합하고 8~20자 이내로 입력해주세요.", "비밀번호를 한번 더 입력해주세요,", "이름을 입력해주세요.",
			"이메일 형식에 맞춰 입력해주세요. ex) abc123@abc.com" };
	JLabel jlContent[], jlHint[];
	JTextField jtfId, jtfName, jtfEmail;
	JPasswordField jpfPw, jpfPwcheck;
	JButton jbRegist, jbBack;

	boolean hintRun;

	RegisterUI(UIChanger uic) {
		super(uic);

		font = new Font("굴림", Font.BOLD, 12);

		jpContent = new JPanel(new GridLayout(5, 1));
		jpText = new JPanel(new GridLayout(5, 1));
		jpHint = new JPanel(new GridLayout(5, 1));

		jlContent = new JLabel[strContent.length];
		for (int i = 0; i < jlContent.length; i++) {
			jlContent[i] = new JLabel(strContent[i]);
			jlContent[i].setFont(font);
			jlContent[i].setHorizontalAlignment(JLabel.RIGHT);
		}

		jlHint = new JLabel[strHint.length];
		for (int i = 0; i < jlHint.length; i++) {
			jlHint[i] = new JLabel(strHint[i]);
			jlHint[i].setFont(font);
			jlHint[i].setHorizontalAlignment(JLabel.LEFT);
			jlHint[i].setForeground(Color.gray);
		}

		jtfId = new JTextField();
		jtfName = new JTextField();
		jtfEmail = new JTextField();
		jpfPw = new JPasswordField();
		jpfPwcheck = new JPasswordField();

		jbRegist = new JButton("회원가입");
		jbRegist.addActionListener(this);
		jbBack = new JButton("←");
		jbBack.addActionListener(this);

		jpContent.setBounds(20, 20, 150, 150);
		jpText.setBounds(170, 20, 200, 150);
		jpHint.setBounds(375, 20, 400, 150);
		jbRegist.setBounds(170, 180, 200, 30);
		jbBack.setBounds(20, 180, 50, 30);

		Container ct = getContentPane();
		ct.setLayout(null);
		ct.add(jpContent);
		ct.add(jpText);
		ct.add(jpHint);
		ct.add(jbRegist);
		ct.add(jbBack);

		for (int i = 0; i < jlContent.length; i++) {
			jpContent.add(jlContent[i]);
		}
		jpText.add(jtfId);
		jpText.add(jpfPw);
		jpText.add(jpfPwcheck);
		jpText.add(jtfName);
		jpText.add(jtfEmail);
		for (int i = 0; i < jlHint.length; i++) {
			jpHint.add(jlHint[i]);
		}

		setTitle("회원가입");
		setSize(800, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		hintRun = true;
	}

	public void falseRegist(String error) {
		JOptionPane.showMessageDialog(null, error, "가입실패", JOptionPane.ERROR_MESSAGE);
	}

	public void successRegist() {
		JOptionPane.showMessageDialog(null, "가입을 축하드립니다!", "가입완료", JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean checkPattern(String id, String pw, String repw, String name, String email) {
		String pwPtrn = "^[A-Za-z[0-9]]{8,20}$";
		String emailPtrn = "[0-9a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+$";

		if (id.length() < 4 || 20 < id.length()) {
			falseRegist("아이디는 4~20자 사이입니다.");
			return false;
		}

		if (!Pattern.matches(pwPtrn, pw)) {
			falseRegist("비밀번호는 영문+숫자 조합 8~20자 입니다.");
			return false;
		}

		if (!repw.equals(pw)) {
			falseRegist("비밀번호가 일치하지 않습니다.");
			return false;
		}

		if (name.length() == 0) {
			falseRegist("이름을 써주세요.");
			return false;
		}

		if (!Pattern.matches(emailPtrn, email)) {
			falseRegist("이메일 형식을 확인해주세요.");
			return false;
		}

		return true;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbRegist) {
			DBMS dbms = new DBMS();

			String id = jtfId.getText();
			String pw = new String(jpfPw.getPassword());
			String repw = new String(jpfPwcheck.getPassword());
			String name = jtfName.getText();
			String email = jtfEmail.getText();

			if (checkPattern(id, pw, repw, name, email)) {
				int result = dbms.regist(id, pw, name, email);
				if (result > 0) {
					successRegist();
					uic.goLogin();
				} else if (result == 0) {
					falseRegist("이미 존재하는 아이디 입니다.");
				}
			}
		} else if(e.getSource()==jbBack) {
			uic.goLogin();
		}
	}
}
