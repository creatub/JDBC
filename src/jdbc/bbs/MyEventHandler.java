package jdbc.bbs;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.event.*;
//이벤트 핸들러 ==> Application Layer
//버튼을 누르면 이벤트 실행 해주고 유효성 처리 해주고 DAO에 넘겨줌, 유효성 통과 못하면 돌려보냄
//UI<== Application Layer ==> Data Layer ==> DB
public class MyEventHandler implements ActionListener, ChangeListener{

	private MyBoardApp gui;//View
	private MemberDAO userDAO;//Model
	private BbsDAO bbsDao;//Model
	
	public MyEventHandler(MyBoardApp app) {
		this.gui = app;
		userDAO = new MemberDAO();
		bbsDao = new BbsDAO();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == gui.btLogin) {//로그인
			login();
		}else if(obj == gui.btJoin) {//회원가입
			joinMember();
		}else if(obj == gui.btClear) {//지우기
			gui.clear1();
		}else if(obj == gui.btList) {//회원목록
			listMember();
		}else if(obj == gui.btDel) {//회원탈퇴
			removeMember();
		}else if(obj == gui.bbsWrite) { // 게시판 글쓰기
			writeBBS();
		}else if(obj == gui.bbsList) { // 게시판 글목록
			
		}else if(obj == gui.bbsDel) { // 게시글 삭제
			
		}else if(obj == gui.bbsFind) {
			//title로 검색
		}
	}//--------------------------------------
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int selectedIndex = gui.tabbedPane.getSelectedIndex();
		if(selectedIndex==2) {
			try {
				int no = bbsDao.callBbsNo();
				gui.tfNo.setText(" "+Integer.toString(no)+"번글");
				gui.tfNo.setEnabled(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private void login() {
		//1. 입력값 받기
		String id = gui.loginId.getText();
		char[] ch = gui.loginPwd.getPassword();
		String pw = new String(ch);
		//2. 유효성 체크
		if(id==null||id.trim().isEmpty()) {
			gui.showMsg("id를 입력해주세요");
			gui.loginId.requestFocus();
		}else if(ch==null||pw.trim().isEmpty()) {
			gui.showMsg("password를 입력해주세요");
			gui.loginPwd.requestFocus();
		}else {
			try {
				int n = userDAO.loginCheck(id, pw);
				if(n==-1) {
					gui.showMsg("존재하지 않는 id입니다.");
					gui.loginId.requestFocus();
				}else if(n==-2) {
					gui.showMsg("비밀번호가 틀렸습니다.");
					gui.loginPwd.requestFocus();
				}else if(n==1) {
					gui.showMsg("로그인 성공 - 게시판으로 이동합니다.");
					gui.loginId.setText("");
					gui.loginPwd.setText("");
					gui.setEnableBBS(true);
					gui.tabbedPane.setSelectedIndex(2);
					gui.tfWriter.setText(id);
					gui.tfWriter.setEnabled(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void joinMember() {
		//1. 입력값 받기
		String id = gui.tfId.getText();
		String name = gui.tfName.getText();
		String pw = gui.tfPw.getText();
		String tel = gui.tfTel.getText();
		//2. 유효성 체크(id,pw,name)
//		if(id==null||id.trim().isEmpty())//빈문자열 체크
		if(id==null||id.trim().isEmpty()) {
			gui.showMsg("id를 입력해주세요");
			gui.tfId.requestFocus();
		}else if(pw==null||pw.trim().isEmpty()) {
			gui.showMsg("password를 입력해주세요");
			gui.tfPw.requestFocus();
		}else if(name==null||name.trim().isEmpty()) {
			gui.showMsg("이름을 입력해주세요");
			gui.tfName.requestFocus();
		}else {
		//3. 입력값들을 MemberVO객체에 담아주기
			MemberVO user = new MemberVO(id,pw,name,tel,null);
			
		//4. userDAO의 insertMember()호출
			try {
				int n = userDAO.insertMember(user);
				//5. 결과에 따른 메시지 처리
				String msg = (n>0) ? "회원가입 완료-로그인으로 이동합니다.":"회원가입 실패";
				gui.showMsg(msg);
				if(n>0) {
					gui.tabbedPane.setSelectedIndex(0);
					//로그인 탭 선택
					gui.clear1();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				gui.showMsg("중복된 id를 입력하셨습니다.");
			}
		}
	}// joinMember()-----------
	
	private void listMember() {
		//userDao의 selectAll()호출
		try {
			ArrayList<MemberVO> userList=userDAO.selectAll();
			//반환 받은 ArrayList에서 회원정보 꺼내서 taMembers에 출력
			gui.showMembers(userList);
		}catch(SQLException e) {
			gui.showMsg(e.getMessage());
		}

	}// listMember()--------------
	
	private void removeMember() {
		//1.입력값 받기
		String delId = gui.tfId.getText();
		//2.유효성 체크
		if(delId==null || delId.trim().isEmpty()) {
			gui.showMsg("id를 입력해주세요.");
		}else {
			try {
				int n = userDAO.deleteMember(delId.trim());
				String msg = (n>0) ? "회원 탈퇴가 완료되었습니다":"회원가입 정보가 없습니다.";
				gui.showMsg(msg);
				if(n>0) {
					gui.setEnableBBS(false);
					gui.clear1();
					gui.tabbedPane.setSelectedIndex(0);//로그인 탭 선택
				}
			} catch (SQLException e) {
				e.printStackTrace();
				gui.showMsg("회원가입 정보가 없습니다.");
			}
		}
	}// dropMember()--------------
	
	
	private void writeBBS() {
		//1. 입력값 받기
		String title = gui.tfTitle.getText();
		String writer = gui.tfWriter.getText();
		String content = gui.taContent.getText();
		//2.유효성 체크(title,writer)
		if(title==null||title.trim().isEmpty()) {
			gui.showMsg("글 제목을 입력해주세요");
		}else if(content==null||content.trim().isEmpty()) {
			gui.showMsg("글 내용을 입력해주세요");
		}else {
			//3.입력값을 BbsVO에 담아주기
			BbsVO bbs = new BbsVO(title, writer, content, null);
			//4.bbsDao의 insertBbs()호출
			try {
				int n = bbsDao.insertBbs(bbs);
				//5.결과에 따른 메시지 처리
				String msg = (n>0)?"게시물이 업로드 되었습니다.":"게시물 등록에 실패했습니다.";
				gui.showMsg(msg);
				if(n>0) {
					gui.clear2();
					int no  = bbsDao.callBbsNo();
					gui.tfNo.setText(" "+Integer.toString(no)+"번글");
					gui.tabbedPane.setSelectedIndex(3);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				gui.showMsg("게시판 업로드에 실패했습니다.");
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	
}////////////////////////////////////////
