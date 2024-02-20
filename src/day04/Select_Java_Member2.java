package day04;
import java.util.*;

import jdbc.util.DBUtil;

import java.sql.*;

public class Select_Java_Member2 {

	public static void main(String[] args) 
	throws Exception{
		//bbs에서 게시글을 검색하되 제목에 들어간 키워드로 검색하기
		Scanner sc = new Scanner(System.in);
		System.out.println("게시판 검색 키워드(title) 입력: ");
		String keyword = sc.nextLine();
		System.out.println("검색어: ["+keyword+"]");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//2. db 연결
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="scott", pwd="tiger";
		
		Connection con = DriverManager.getConnection(url,user,pwd);
		System.out.println("DB Connected...");
		
		String sql = "SELECT no,title,writer,content,wdate FROM bbs WHERE title like ?";
			   sql += " ORDER BY no DESC";
		System.out.println(sql);
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+keyword+"%");
		//실행 => executeQuery(select문)
		ResultSet rs = ps.executeQuery();
		System.out.println("==============================================");
		System.out.println("NO\tTitle\tWriter\twdate\tContent\n");
		System.out.println("==============================================");
		while(rs.next()) {
			String no = rs.getString(1);
			String title = rs.getString(2);
			String writer = rs.getString(3);
			String content = rs.getString(4);
			java.util.Date wdate = rs.getDate(5);
			System.out.printf("%s\t%s\t%s\t%s\t%s\n", no, title, writer, wdate.toString(),content);
		}
		System.out.println("==============================================");
		rs.close();
		ps.close();
		con.close();
	}

}
