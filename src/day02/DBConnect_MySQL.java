package day02;

import java.sql.*;
import java.util.*;

//MySQL jdbc driver 다운로드
//https://downloads.mysql.com/archives/c-j/
//C:\multicampus\SQL\mysql-connector-j-8.0.33\mysql-connector-j-8.0.33\mysql-connector-j-8.0.33.jar
public class DBConnect_MySQL {

	public static void main(String[] args)
	throws Exception
	{
		//1. Driver Loading
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("드라이버 로딩 성공!!");
		//2. DB 연결
		String url="jdbc:mysql://localhost:3306/mydb?useUnicode=true&serverTimezone=Asia/Seoul";
		String user="root", pwd="1234";
		Connection con=DriverManager.getConnection(url,user,pwd);
		System.out.println("MySQL DB 연결 성공!!");
		//테이블명: java_member
		/*id varchar(20) pk
		 *pw varchar(10) nn
		 *name varchar(30) nn
		 *tel varchar(15)
		 *indate date default (current_date))
		 */
		String sql = "create table java_member(";
		sql+="id varchar(20) primary key,";
		sql+="pw varchar(10) not null,";
		sql+="name varchar(30) not null,";
		sql+="tel varchar(15),";
		sql+="indate date default (current_date))";
		
		System.out.println(sql);
		
		Statement stmt = con.createStatement();
		boolean b = stmt.execute(sql);
		System.out.println("b: "+b);
		if(stmt!=null) stmt.close();
		if(con!=null) con.close();
	}// main()--------

}// class
