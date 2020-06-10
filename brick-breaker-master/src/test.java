import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class test {

	public static void main(String[] args) {
				try {
		            //데이터베이스 Connection
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://15.164.215.115/:8888/url","user","karase123");
					//select 쿼리
		            //테이블 불러오기
					String qu = "select * from INFO";
		            //java statement 생성
					java.sql.Statement st = con.createStatement();
		            //쿼리 execute , 객체형성
					ResultSet rs = st.executeQuery(qu);
					//각 열을 반복적으로 나타내줌
					while(rs.next()) {
						String id = rs.getString("id");
						String password = rs.getString("password");
						String score = rs.getString("score");
						String salt = rs.getString("salt");
						
						//결과값 도출 (%s당 id 같은 정보를 출력해줌)
						 System.out.format("%s, %s, %s, %s\n", id, password, score, salt);
					}
		            //닫아줍시다.
					st.close();
					
					
					//캐치
				}catch(Exception e){ 
					System.err.println("Got an exception! ");
				    System.err.println(e.getMessage());
					
				}
	}

}