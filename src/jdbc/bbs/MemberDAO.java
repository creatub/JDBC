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
