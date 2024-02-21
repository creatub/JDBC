package jdbc.bbs;

import java.sql.*;
import java.util.*;
//게시판 관련 crud 수행 => data layer
public class BbsDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//게시글 쓰기 (시퀀스 - BBS_NO_SEQ)
	public int insertBbs(BbsVO vo) throws SQLException{
		try {
			con = DBUtil.getCon();
			String sql = "INSERT INTO BBS(NO, TITLE, WRITER, CONTENT, WDATE)";
				   sql += " VALUES(BBS_NO_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getWriter());
			ps.setString(3, vo.getContent());
			
			int n = ps.executeUpdate();
			return n;
		}finally {
			close();
		}
	}
	//게시판 번호 가져오기
	public int callBbsNo() throws SQLException{
		try {
			con = DBUtil.getCon();
			String sql = "SELECT LAST_NUMBER FROM user_sequences";
				   sql+= " WHERE SEQUENCE_NAME = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, "BBS_NO_SEQ");
			rs =ps.executeQuery();
			rs.next();
			int n = rs.getInt("LAST_NUMBER");
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
}
