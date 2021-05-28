package UI;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DBMS.DBMS;

public class LoginUI extends UI implements ActionListener {
	Font font;

	JPanel jpContent;
	String strContent[] = { "ID : ", "PassWord : " };
	JLabel jlContent[];
	JTextField jtfId;
	JPasswordField jpfPw;
	JButton jbLogin, jbRegist;
	JLabel jlResult;

	LoginUI(UIChanger uic) {
		super(uic);
		font = new Font("굴림", Font.BOLD, 15);

		// Init_Center
		jpContent = new JPanel(new GridLayout(strContent.length, 2));
		jpContent.setBounds(50, 50, 300, 60);
		jlContent = new JLabel[strContent.length];
		for (int i = 0; i < jlContent.length; i++) {
			jlContent[i] = new JLabel(strContent[i]);
			jlContent[i].setHorizontalAlignment(JLabel.RIGHT);
			jlContent[i].setFont(font);
		}
		jtfId = new JTextField();
		jpfPw = new JPasswordField();

		// Inut_Bottom
		jbLogin = new JButton("로그인");
		jbLogin.setBounds(355, 50, 80, 60);
		jbLogin.setFont(font);
		jbLogin.addActionListener(this);
		jbRegist = new JButton("회원가입");
		jbRegist.setBounds(200, 115, 150, 30);
		jbRegist.setFont(font);
		jbRegist.addActionListener(this);

		Container ct = getContentPane();
		ct.setLayout(null);
		ct.add(jpContent);
		ct.add(jbLogin);
		ct.add(jbRegist);

		setTitle("로그인");
		setSize(550, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		jpContent.add(jlContent[0]);
		jpContent.add(jtfId);
		jpContent.add(jlContent[1]);
		jpContent.add(jpfPw);
	}

	public void warning(String msg) {
		JOptionPane.showMessageDialog(null, msg, "로그인 실패", JOptionPane.ERROR_MESSAGE);
	}
	
	public void tryLogin() {
		String id = jtfId.getText();
		String passwd=new String(jpfPw.getPassword());
		if (id.length()==0) {
			warning("아이디를 입력해주세요.");
		} else if (passwd.length()==0) {
			warning("비밀번호를 입력해주세요.");
		} else {
			DBMS dbms = new DBMS();
			if (dbms.login(id, passwd.toString())) {
				setVisible(false);
				uic.setId(jtfId.getText());
				uic.goMenu(0);
			} else {
				warning("아이디 혹은 비밀번호가 올바르지 않습니다.");
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jbLogin) {
			tryLogin();
		} else if (e.getSource() == jbRegist) {
			uic.goRegister();
		}
	}
}
