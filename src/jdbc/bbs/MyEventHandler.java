package jdbc.bbs;
import java.awt.event.*;
import java.sql.*;

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
		if(obj == gui.btJoin) {//회원가입
			joinMember();
		}else if(obj == gui.btClear) {//지우기
			gui.clear1();
		}else if(obj == gui.btList) {//회원목록
			
		}else if(obj == gui.btDel) {//회원탈퇴
			
		}else if(obj == gui.bbsWrite) { // 게시판 글쓰기
			writeBBS();
		}
	}//--------------------------------------
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int selectedIndex = gui.tabbedPane.getSelectedIndex();
		if(selectedIndex==2) {
			try {
				int n = bbsDao.callBbsNo();
				gui.tfNo.setText(" "+Integer.toString(n)+"번글");
				gui.tfNo.setEnabled(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
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
				if(n>0) {
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				gui.showMsg("게시판 업로드에 실패했습니다.");
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	
}////////////////////////////////////////
