package day02;
import java.sql.*;
import java.util.*;
public class Insert_Java_Member {

	public static void main(String[] args) 
	throws ClassNotFoundException, SQLException
	{
		Scanner sc= new Scanner(System.in);
		System.out.println(":::회원가입:::");
		System.out.println("아이디: ");
		String id = sc.nextLine();		
		System.out.println("비밀번호: ");
		String pw = sc.nextLine();
		System.out.println("이름: ");
		String name = sc.nextLine();
		System.out.println("연락처: ");
		String tel = sc.nextLine();
		System.out.println(id+"/"+pw+"/"+name+"/"+tel);
		
		
		//1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("드라이버 로딩 성공!!");
		//2. DB 연결
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="scott", pwd="tiger";
		Connection con = DriverManager.getConnection(url, user, pwd);
		System.out.println("DB연결 성공!!");
		//3. sql문 작성 (hong,123,홍길동,010-1111-1111,sysdate)
		String sql = "INSERT INTO java_member(id,pw,name,tel,indate) ";
		sql += "VALUES('"+id+"','"+pw+"','"+name+"','"+tel+"',sysdate)";
		System.out.println(sql);
		
		//4. Statement 객체 얻기
		Statement stmt=con.createStatement();
		//5. sql문 실행
		boolean b = stmt.execute(sql); // select일 경우만 true가 나옴
		System.out.println("b: "+b);
		//6. DB관련 자원 반납		
		if(stmt!=null) stmt.close();
		if(con!=null) con.close();

	}//main

}
