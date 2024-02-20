package day04;

import java.sql.*;
//import java.sql.Date;
import java.util.*;
import jdbc.util.*;

public class Select_Java_Member {

	public static void main(String[] args) 
	throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("검색할 회원명: ");
		String name = sc.nextLine();
		String sql = "SELECT id,name,tel,indate FROM java_member WHERE name=?";

		//DBUtil util = new DBUtil(); // [x]
		
		//try with resource ==> try(자원){}catch(예외){}
		//==>이 안에 들어간 resource들을 자동으로 close해줌
		try(Connection con = DBUtil.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
			){
			ps.setString(1, name);
			//실행 => executeQuery(select문)
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString(1);
				String name2 = rs.getString(2);
				String tel = rs.getString(3);
				java.sql.Date indate = rs.getDate(4);
				System.out.printf("%s\t%s\t%s\t%s\n", id, name2, tel, indate.toString());
			}
			if(rs!=null) rs.close();
			System.out.println("DB 연결 성공!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//ps.close();// 자동으로 close()된다
		//con.close();// 자동으로 close()된다
	}

}
