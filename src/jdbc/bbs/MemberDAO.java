package jdbc.bbs;
import java.sql.*;
import java.util.*;
//DAO (Data Access Object) : Database에 접근하여 CRUD로직을 수행하는 객체
//==> Data Layer (Persistence Layer) ==> Model에 해당
public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	/** 회원가입 처리 - CRUD 중 C(INSERT) */
	public int insertMember(MemberVO user) throws SQLException {
		// try catch로 하면 화면에 Exception 보여주기 어려움 -> console에만 출력 가능
		try {
			con=DBUtil.getCon();
			String sql = "INSERT INTO JAVA_MEMBER(ID,NAME,PW,TEL,INDATE)";
				   sql+= " VALUES(?,?,?,?,SYSDATE)";
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPw());
			ps.setString(4, user.getTel());
			
			int n = ps.executeUpdate();
			return n;
		}finally {
			close();
		}
	}
	/**회원 목록 가져오기 - R (SELECT) => 다중행을 반환하는 경우*/
	public ArrayList<MemberVO> selectAll() throws SQLException{
		try {
			con=DBUtil.getCon();
			String sql = "SELECT * FROM java_member ORDER BY indate DESC";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			return makeList(rs);
		}finally {
			close();
		}
	}//---------------------------
	private ArrayList<MemberVO> makeList(ResultSet rs) throws SQLException {
		ArrayList<MemberVO> arr = new ArrayList<>();
		while(rs.next()) {
			String id = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			String tel = rs.getString("tel");
			java.sql.Date indate = rs.getDate("indate");
			MemberVO record = new MemberVO(id,pw,name,tel,indate);
			arr.add(record);
		}//---while
		return arr;
	}
	/**PK- id로 회원정보 가져오기 - R(SELECT) => 단일행을 반환하는 경우*/
	public MemberVO findById(String id) throws SQLException{
		try {
			con=DBUtil.getCon();
			String sql = "SELECT * FROM java_member WHERE id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			
			ArrayList<MemberVO> arr=makeList(rs);
			if(arr!=null && arr.size()==1) {
				MemberVO user = arr.get(0);
				return user;
			}else {
				return null;
			}
			
		}finally {
			close();
		}
	}//---------------------------
	// ID가 없을 경우 -1, 비번이 틀릴 경우 -2, 인증받은 경우 1반환
	public int loginCheck(String id, String pw) throws SQLException{
		MemberVO tmpUser = this.findById(id);
		if(tmpUser==null) return -1;//ID가 없는 경우
		//id가 있는 경우는 비밀번호 일치 여부 체크
		if(!tmpUser.getPw().equals(pw)) return -2;//비번 불일치
		
		return 1; // 회원 인증 받은 경우
	}
	
	/**회원 탈퇴 처리 - D(DELETE)*/
	public int deleteMember(String id) throws SQLException{
		try {
			con=DBUtil.getCon();
			//delete문 작성
			String sql ="DELETE FROM JAVA_MEMBER WHERE ID = ?";
			//ps 얻기
			ps = con.prepareStatement(sql);
			//? setting
			ps.setString(1, id);
			//실행 => 실행결과 반환
			int n = ps.executeUpdate();
			return n;
		}finally {
			close();
		}
	}
	
	public void close() {
		try {
			if(con!=null) con.close();
			if(ps!=null) ps.close();
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}//////////////////////////////////////////////
