package basicdatabasevisit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLServer {
	private static final String USER_PASSWORD="123456";
	private static final String USER_USERNAME="root";
	private static final String URL = "jdbc:mysql://localhost:3306/student?characterEncoding=UTF-8";
	private Connection conn;
	public MySQLServer() {
		connectToDB();
	}
	public void connectToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(URL, USER_USERNAME, USER_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void insert(User user) {
		String sql = "insert into user values (?,?)";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1,user.getID());
			ps.setString(2,user.getName());
			System.out.println("将对象User中的信息转换成SQL");
			System.out.println("Insert into user values()");
			System.out.println("使用connect的PrepareStatement执行SQL向SQLserver中添加一条记录");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public User getUser(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        System.out.println("将条件的信息转换成 where SQL");
        System.out.println("select * from user");
        System.out.println("使用connect的Statement执行SQL向SQLserver中查询一条记录");
        System.out.println("将返回的数据集，填充的User对象中");
        String sql = "select * from user where id = " + id + " ;";
        User user = new User();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int ID = resultSet.getInt("id");
            String name = resultSet.getString("name");
            user.setID(ID);
            user.setName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
class User {
	private int ID;
	private String Name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}