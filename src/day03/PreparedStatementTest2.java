package day03;

import java.sql.*;
import java.util.*;
//실습: DEPT 테이블에 부서 정보를 입력받아 INSERT하는 문장을 실행시키세요
//부서 번호, 부서명, 근무지
public class PreparedStatementTest2 {

	public static void main(String[] args) 
	throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("부서 번호를 입력하세요: ");
		int deptno = sc.nextInt();
		sc.nextLine();
		System.out.println("부서명을 입력하세요: ");
		String dname = sc.nextLine();
		System.out.println("근무지를 입력하세요: ");
		String loc = sc.nextLine();
		
		//1. Driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//2. db 연결
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user="scott", pwd="tiger";
		
		Connection con = DriverManager.getConnection(url, user, pwd);
		System.out.println("DB Connected...");
		
		//3. sql문 작성
		String sql = "INSERT INTO DEPT VALUES(?, ?, ?)";
		
		//4. PreparedStatement 객체 얻기
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		//? 를 제외한 sql문 전처리
		
		pstmt.setInt(1, deptno);
		pstmt.setString(2, dname);
		pstmt.setString(3, loc);
		
		//6. 쿼리문 실행
		int cnt = pstmt.executeUpdate();
		System.out.println(cnt + "개의 레코드 수정 완료");
		
		//7.자원 반납
		pstmt.close();
		con.close();
		
	}
}
