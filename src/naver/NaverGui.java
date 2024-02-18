package naver;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.sql.*;
import java.sql.Date;

public class NaverGui extends JFrame{
	Container cp;
	private JTextField idTextField;
	private JPasswordField passwordField;
	private JTextField nameTextField;
	private JTextField telTextField;
	PreparedStatement preparedStatement = null;
	public JPanel panel;
	private DialogFrame dialogFrame;
	
	public NaverGui() {
		super(":::NAVER 회원가입:::");
		cp=this.getContentPane();
		
		dialogFrame = new DialogFrame(this);
		dialogFrame.setBounds(100, 200, 500, 200);
		panel = new JPanel();
		panel.setBackground(new Color(81, 192, 33));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\multicampus\\java-workspace\\JDBC\\src\\naver\\logo.png"));
		lblNewLabel.setBounds(57, 35, 267, 154);
		panel.add(lblNewLabel);
		
		idTextField = new JTextField();
		idTextField.setForeground(new Color(192, 192, 192));
		idTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		idTextField.setText(" 아이디");
		HintFocusListener idh = new HintFocusListener(idTextField, " 아이디");
		idTextField.addFocusListener(idh);
		idTextField.setBounds(57, 203, 267, 41);
		panel.add(idTextField);
		idTextField.setColumns(10);
		
		
		JLabel hintLabel = new JLabel("비밀번호");
		hintLabel.setLabelFor(hintLabel);
		hintLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		hintLabel.setForeground(new Color(192, 192, 192));
		hintLabel.setBounds(65, 267, 65, 15);
//		panel.add(hintLabel);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.LIGHT_GRAY);
		passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		HintFocusListener pwh = new HintFocusListener(passwordField, hintLabel);
		passwordField.addFocusListener(pwh);
		passwordField.setColumns(10);
		passwordField.setBounds(57, 254, 267, 41);
//		panel.add(passwordField);
		//hintLabel을 passwordField 위에 올리기
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 400, 600);
		panel.add(layeredPane);

		passwordField.setBounds(57, 254, 267, 41);
		layeredPane.add(passwordField, JLayeredPane.DEFAULT_LAYER);

		hintLabel.setBounds(65, 267, 65, 15);
		layeredPane.add(hintLabel, JLayeredPane.PALETTE_LAYER);
		
		
		nameTextField = new JTextField();
		nameTextField.setForeground(Color.LIGHT_GRAY);
		nameTextField.setText(" 이름");
		nameTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		HintFocusListener nameh = new HintFocusListener(nameTextField, " 이름");
		nameTextField.addFocusListener(nameh);
		nameTextField.setColumns(10);
		nameTextField.setBounds(57, 305, 267, 41);
		panel.add(nameTextField);
		
		telTextField = new JTextField();
		telTextField.setForeground(Color.LIGHT_GRAY);
		telTextField.setText(" 연락처");
		telTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		HintFocusListener telh = new HintFocusListener(telTextField, " 연락처");
		telTextField.addFocusListener(telh);
		telTextField.setColumns(10);
		telTextField.setBounds(57, 356, 267, 41);
		panel.add(telTextField);
		
		JButton joinButton = new JButton("회원 가입");
		joinButton.addActionListener(new ButtonActionListener("회원 가입"));
		joinButton.setBounds(56, 407, 268, 30);
		panel.add(joinButton);
		
		JButton deleteButton = new JButton("회원 탈퇴");
		deleteButton.addActionListener(new ButtonActionListener("회원 탈퇴"));
		deleteButton.setBounds(57, 447, 268, 30);
		panel.add(deleteButton);
		
		JButton listButton = new JButton("회원 목록");
		String query = "";
		listButton.addActionListener(new ButtonActionListener("회원 목록"));
		listButton.setBounds(56, 487, 268, 30);
		panel.add(listButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(26, 20, 337, 523);
		panel.add(panel_1);
		this.setSize(400,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel_1.requestFocus();
	}
	// 메시지 띄우기
	public void showMsg(JPanel panel, String str) {
		JOptionPane.showMessageDialog(panel, str);
	}
	class ButtonActionListener implements ActionListener {
		private String query;
		private String whichButton;
		PreparedStatement preparedStatement = null;
		public ButtonActionListener(String whichButton) {
			this.whichButton = whichButton;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//1. Driver 로딩
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//OracleDriver 객체를 생성해서 DriverManager에서 드라이버를 등록한다.
				System.out.println("Driver Loading Success...");
				//2. DB 연결
				String url="jdbc:oracle:thin:@localhost:1521:XE";
				String user="scott", pwd="tiger";
				Connection con=DriverManager.getConnection(url,user,pwd);
				System.out.println("DB Connected...");
				//4. Statement객체 얻어오기=con.createStatement()
				Statement stmt= con.createStatement();
				//5. Statement의 executeXXX() 메서드 이용해서 쿼리문 실행
				if(whichButton.equals("회원 목록")) {
					query="SELECT name,id,tel,indate ";
					query+="FROM java_member ORDER BY indate DESC, name ASC";
					System.out.println(query);
					ResultSet rs = stmt.executeQuery(query);
//					DialogFrame dialogFrame = new DialogFrame(this);
	                dialogFrame.setVisible(true);
	                String dialogText = "번호\t이름\t아이디\t연락처\t\t가입날짜\n";
	                int i = 1;
					while(rs.next()) {
						String name = rs.getString("name");
						String id= rs.getString("id");
						String tel= rs.getString("tel");
						Date indate= rs.getDate("indate");
						dialogText += i++ +"\t"+name+"\t"+id+"\t"+tel+"\t\t"+indate.toString()+"\n";
						System.out.printf("%s\t%s\t%s\t%s\n", name,id,tel,indate.toString());
					}
	                dialogFrame.setText(dialogText);
					
					if(rs!=null) rs.close();
				}else { // 회원 가입 or 회원 탈퇴
					String id = idTextField.getText();
					char[] ch = passwordField.getPassword();
					String pw = new String(ch);
					String name = nameTextField.getText();
					String tel = telTextField.getText();
					
					if (id == null || ch == null || name == null || tel == null ||
						id.equals(" 아이디") || pw.equals("") || name.equals(" 이름") || tel.equals(" 연락처")) {
						showMsg(panel, "회원 정보를 입력해주세요.");
						return;
					}else {
						System.out.println(id+"','"+pw+"','"+name+"','"+tel+"',sysdate");
						query = "SELECT COUNT(*) AS count FROM java_member WHERE id = ?";

						PreparedStatement preparedStatement = con.prepareStatement(query);
						preparedStatement.setString(1, id);
						ResultSet rs = preparedStatement.executeQuery();
						if (rs.next()) {
			                int count = rs.getInt("count");
			                if (count > 0) {
			                	if(whichButton.equals("회원 가입")) {
			                		showMsg(panel, "중복된 ID입니다.");
			                	}else if(whichButton.equals("회원 탈퇴")) {
			                		query = "SELECT pw FROM java_member WHERE id = ?"; // 비밀번호 확인 쿼리 생성
			                        // PreparedStatement를 사용하여 쿼리 실행 준비
			                        preparedStatement = con.prepareStatement(query);
			                        preparedStatement.setString(1, id); // 쿼리의 파라미터 값 설정
			                        rs = preparedStatement.executeQuery();
			                        if (rs.next()) {
			                        	System.out.println(rs.getString("pw"));
			                            if(pw.equals(rs.getString("pw"))) {
			                            	query = "DELETE FROM java_member WHERE id = ?"; // 삭제 쿼리 생성

					                        // PreparedStatement를 사용하여 쿼리 실행 준비
					                        preparedStatement = con.prepareStatement(query);
					                        preparedStatement.setString(1, id); // 쿼리의 파라미터(?) 값 설정

					                        int rowCount = preparedStatement.executeUpdate();
					                        if (rowCount > 0) {
					                            showMsg(panel, "회원 삭제가 완료되었습니다.");
					                        } 
			                            }else {
			                            	showMsg(panel, "비밀번호를 올바르게 입력해주십시오.");
			                            }
			                        }		                		

			                	}
			                } else {
			                	if(whichButton.equals("회원 가입")) {
			                		query = "INSERT INTO java_member(id,pw,name,tel,indate) ";
									query += "VALUES('"+id+"','"+pw+"','"+name+"','"+tel+"',sysdate)";
				                    boolean b = stmt.execute(query);
				                    showMsg(panel, "회원 가입이 완료되었습니다.");
			                	}else if(whichButton.equals("회원 탈퇴")) {
			                		showMsg(panel, "아이디가 없습니다.");
			                	}
			                    
			                }
			            }
						if(rs!=null) rs.close();
					}        
				}

				
				//6. DB 연결 자원 반납
				if(stmt!=null) stmt.close(); // 먼저 반납
				if(con!=null) con.close();
						
			} catch (ClassNotFoundException|SQLException e1) {
				System.out.println("Driver Loading Fail...");
				e1.printStackTrace();
				
			}
			
		}
	}
	
	
	
	public static void main(String[] args) {
		new NaverGui();
	}
}



/**
 * focus가 오면 공백으로 만들고 focus가 없어지면 hint message를 보여준다.
 */
class HintFocusListener implements FocusListener {
    private String hint;
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel hintLabel;

    public HintFocusListener(JTextField textField, String hint) {
        this.textField = textField;
        this.hint = hint;
    }
    public HintFocusListener(JPasswordField passwordField, JLabel hintLabel) {
    	this.passwordField=passwordField;
    	this.hintLabel = hintLabel;
    }
    @Override
    public void focusGained(FocusEvent e) {
    	 if (textField != null && textField.getText().equals(hint)) {
             textField.setText("");
             textField.setForeground(Color.BLACK);
         }
         if (passwordField != null) {
             passwordField.setText("");
             passwordField.setForeground(Color.BLACK);
             hintLabel.setVisible(false);
         }
    }
    

    @Override
    public void focusLost(FocusEvent e) {
    	if (textField != null && textField.getText().isEmpty()) {
            textField.setText(hint);
            textField.setForeground(Color.LIGHT_GRAY);
        }
        if (passwordField != null && String.valueOf(passwordField.getPassword()).isEmpty()) {
            hintLabel.setVisible(true);
        }
    }
}

class DialogFrame extends JDialog {
	private JTextArea textArea;
    public DialogFrame(NaverGui naverGui) {
        setTitle("Dialog Frame");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 다이얼로그에 포함될 컴포넌트들을 추가합니다.
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        add(scrollPane);
        setLocationRelativeTo(null);
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }
}

