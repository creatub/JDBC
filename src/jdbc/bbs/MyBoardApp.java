package jdbc.bbs;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/*디자인 패턴
 * - MVC 패턴
 * 	- M: Model - 데이터를 가지는 부분 XXDAO, XXVO ==> Data layer
 *  - V: View  - 화면(UI)를 구성하는 부분 MyBoard(Swing) ==> Presentation Layer
 *  - C: Controller - Model과 View 사이에서 제어하는 부분
 *  				사용자가 입력한 값을 모델에 넘긴다
 *  				DB에서 가져온 결과를 화면쪽에 보여준다
 *  				...
 *  				제어 흐름을 담당하는 부분
 */
//화면 계층 => GUI를 구성 (Presentation Layer)
public class MyBoardApp extends JFrame{ // View
	JTextField loginId; // 생략형은 같은 패키지 내에서는 조회 가능
	JPasswordField loginPwd;
	JTextField tfId;
	JTextField tfPw;
	JTextField tfName;
	JTextField tfTel;
	CardLayout card;
	JTextField tfNo;
	JTextField tfWriter;
	JTextField tfTitle;
	JButton btLogin, btJoin, btDel, btList, btClear;
	JButton bbsWrite, bbsDel, bbsFind, bbsList;
	JTextArea taMembers, taList, taContent;
	JTabbedPane tabbedPane;
	
	MyEventHandler handler; // Controller
	
	public MyBoardApp() {
		super("::MyBoardApp::");
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "Center");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_1.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(50, 205, 50));
		tabbedPane.addTab("로그인", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MyBoardApp.class.getResource("/jdbc/bbs/logo2.png")));
		lblNewLabel_1.setBounds(49, 77, 280, 114);
		panel_2.add(lblNewLabel_1);
		
		loginId = new JTextField();
		loginId.setBounds(49, 201, 280, 59);
		panel_2.add(loginId);
		loginId.setColumns(10);
		loginId.setBorder(new TitledBorder("::ID::"));
		
		loginPwd = new JPasswordField();
		loginPwd.setBounds(49, 270, 280, 57);
		panel_2.add(loginPwd);
		loginPwd.setBorder(new TitledBorder("::PASSWORD::"));
		
		btLogin = new JButton("로  그  인");
		btLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
		btLogin.setForeground(new Color(245, 255, 250));
		btLogin.setBackground(new Color(0, 139, 139));
		btLogin.setBounds(49, 342, 280, 45);
		panel_2.add(btLogin);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 245, 238));
		tabbedPane.addTab("회원가입", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("::회원 가입::");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_2.setBounds(80, 23, 199, 46);
		panel_3.add(lblNewLabel_2);
		
		tfId = new JTextField();
		tfId.setBounds(85, 92, 260, 46);
		panel_3.add(tfId);
		tfId.setColumns(10);
		
		tfPw = new JTextField();
		tfPw.setColumns(10);
		tfPw.setBounds(85, 149, 260, 46);
		panel_3.add(tfPw);
		
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(85, 205, 260, 46);
		panel_3.add(tfName);
		
		tfTel = new JTextField();
		tfTel.setColumns(10);
		tfTel.setBounds(85, 261, 260, 46);
		panel_3.add(tfTel);
		
		JLabel lblNewLabel_3 = new JLabel("아 이 디:");
		lblNewLabel_3.setBounds(12, 92, 61, 46);
		panel_3.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("비밀번호:");
		lblNewLabel_3_1.setBounds(12, 149, 61, 46);
		panel_3.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("이    름:");
		lblNewLabel_3_2.setBounds(12, 205, 61, 46);
		panel_3.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("연 락 처:");
		lblNewLabel_3_3.setBounds(12, 261, 61, 46);
		panel_3.add(lblNewLabel_3_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(12, 336, 355, 46);
		panel_3.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 0, 0, 0));
		
		btJoin = new JButton("회원가입");
		btJoin.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btJoin.setBackground(Color.PINK);
		panel_6.add(btJoin);
		
		btDel = new JButton("회원탈퇴");
		btDel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btDel.setForeground(new Color(240, 255, 255));
		btDel.setBackground(Color.BLUE);
		panel_6.add(btDel);
		
		btList = new JButton("회원목록");
		btList.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btList.setForeground(new Color(240, 248, 255));
		btList.setBackground(Color.MAGENTA);
		
		panel_6.add(btList);
		
		btClear = new JButton("지 우 기");
		btClear.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btClear.setBackground(new Color(135, 206, 235));
		
		panel_6.add(btClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 392, 355, 230);
		panel_3.add(scrollPane);
		
		taMembers = new JTextArea();
		scrollPane.setViewportView(taMembers);
		taMembers.setBorder(new TitledBorder(":::회원목록:::"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(249, 255, 242));
		tabbedPane.addTab("게시판 글쓰기", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(237, 255, 219));
		panel_4.setLayout(null);
		panel_4.setBounds(12, 20, 344, 477);
		panel.add(panel_4);
		
		tfNo = new JTextField();
		tfNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		tfNo.setColumns(10);
		tfNo.setBounds(12, 66, 312, 45);
		panel_4.add(tfNo);
		tfNo.setBorder(new TitledBorder("글번호(NO)"));
		
		tfTitle = new JTextField();
		tfTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		tfTitle.setColumns(10);
		tfTitle.setBounds(12, 121, 312, 53);
		panel_4.add(tfTitle);
		tfTitle.setBorder(new TitledBorder("글제목(Title)"));
		
		tfWriter = new JTextField();
		tfWriter.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		tfWriter.setColumns(10);
		tfWriter.setBounds(12, 182, 312, 46);
		panel_4.add(tfWriter);
		tfWriter.setBorder(new TitledBorder("작성자(Writer)"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 238, 312, 218);
		panel_4.add(scrollPane_1);
		
		taContent = new JTextArea();
		taContent.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		scrollPane_1.setViewportView(taContent);
		taContent.setBorder(new TitledBorder("글내용(Content)"));
		//-게시판 목록--------------------------------------
		JLabel lblNewLabel = new JLabel("::나의 게시판::");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(57, 10, 221, 46);
		panel_4.add(lblNewLabel);
		
		JPanel panel_6_1 = new JPanel();
		panel_6_1.setBounds(12, 528, 355, 46);
		panel.add(panel_6_1);
		panel_6_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		bbsWrite = new JButton("글쓰기");
		bbsWrite.setActionCommand("글쓰기");
		bbsWrite.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		bbsWrite.setBackground(new Color(243, 225, 244));
		panel_6_1.add(bbsWrite);
		
		bbsDel = new JButton("글삭제");
		bbsDel.setActionCommand("글삭제");
		bbsDel.setForeground(new Color(0, 0, 0));
		bbsDel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		bbsDel.setBackground(new Color(255, 217, 217));
		panel_6_1.add(bbsDel);
		
		bbsFind = new JButton("글검색");
		bbsFind.setActionCommand("글검색");
		bbsFind.setForeground(new Color(0, 0, 0));
		bbsFind.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		bbsFind.setBackground(new Color(255, 204, 230));
		panel_6_1.add(bbsFind);
		
		bbsList = new JButton("글목록");
		bbsList.setActionCommand("글목록");
		bbsList.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		bbsList.setBackground(new Color(227, 255, 255));
		panel_6_1.add(bbsList);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("게시판 목록", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("::나의 게시판  글목록::");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_4.setBounds(40, 22, 287, 46);
		panel_5.add(lblNewLabel_4);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 91, 355, 531);
		panel_5.add(scrollPane_2);
		
		taList = new JTextArea();
		scrollPane_2.setViewportView(taList);
		taList.setBorder(new TitledBorder("글 목 록"));
		
		//이벤트 핸들러 생성 => 외부클래스로 구성했다면 this 정보를 전달하자
		//생성자에 this가 없으면 에러남
		handler = new MyEventHandler(this); // this를 넘겨줘야 핸들러를 연동할 수 있음 
		//이벤트 소스와 연결
		btLogin.addActionListener(handler);
		btJoin.addActionListener(handler); // MyEventHandler에서 implements ActionListener하면 에러 없어짐
		btList.addActionListener(handler);
		btDel.addActionListener(handler);
		btClear.addActionListener(handler);
		
		bbsWrite.addActionListener(handler);
		bbsList.addActionListener(handler);
		bbsDel.addActionListener(handler);
		bbsFind.addActionListener(handler);
		tabbedPane.addChangeListener(handler);
		
		//초기에 글쓰기 탭은 비활성화 ==> 로그인해야 활성화
		setEnableBBS(false);
//		tabbedPane.setEnabledAt(2, false); // 글쓰기
//		tabbedPane.setEnabledAt(3, false); // 글목록
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,700);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyBoardApp();
	}

	public void clear1() {
		this.tfId.setText("");
		this.tfName.setText("");
		this.tfPw.setText("");
		this.tfTel.setText("");
		this.taMembers.setText("");
		this.tfId.requestFocus();
	}//clear1------
	
	public void clear2() {
		this.tfTitle.setText("");
		this.taContent.setText("");		
	}
	public void showMsg(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}//showMsg()-------

	public void showMembers(ArrayList<MemberVO> userList) {
		if(userList==null) return;
		if(userList.size()==0) {
			taMembers.setText("등록된 회원은 없습니다");
			return;
		}
		taMembers.setText("");
		taMembers.append("===========================================================\n");
		taMembers.append("ID\tName\tTel\t\tIndate\n");
		taMembers.append("===========================================================\n");
		for(MemberVO user:userList) {
			taMembers.append(user.getId()+"\t"+user.getName()+"\t");
			taMembers.append(user.getTel()+"\t\t"+user.getIndate()+"\n");
		}
		taMembers.append("===========================================================\n");
	}//showMembers()-----------------

	public void setEnableBBS(boolean b) {
		tabbedPane.setEnabledAt(2, b); // 글쓰기
		tabbedPane.setEnabledAt(3, b); // 글목록
	}//setEnableBBS()------------------

	
}
