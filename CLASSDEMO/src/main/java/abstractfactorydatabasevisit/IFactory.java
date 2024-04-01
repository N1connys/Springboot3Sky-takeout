package abstractfactorydatabasevisit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface IFactory { //就是远程数据库的影子
	public void connectToDb(); //连接远程数据库
	//table(user)->pojo(user)
	public IUserOperate createUserEntity(); //创建一个可以管理User对象到User表数据记录转换对象
	//pojo(depat)->table(depat)
	public IDepatmentOperate createDepatentEntity();
	//创建一个可以管理Department对象到Department表数据记录转换对
}
class MySQLFactory implements IFactory { //具体的MYSQL
	private static final String USER_PASSWORD="123456";
	private static final String USER_USERNAME="root";
	private static final String URL = "jdbc:mysql://localhost:3306/student?characterEncoding=UTF-8";
	private Connection conn;
	public MySQLFactory() {
		super();
		connectToDb();
	}
	@Override
	public void connectToDb() {
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
		System.out.println("连接到MySQL数据库");
	}
	@Override
	public IUserOperate createUserEntity() {
		return new MySQLUserEntity();
	}
	@Override
	public IDepatmentOperate createDepatentEntity() {
		return new MySQLDepartmentEntity();
	}
}

class PostgresqlFactory implements IFactory { //具体的POSTSQL
	private Connection con;
	public PostgresqlFactory() {
		super();
		connectToDb();
	}
	@Override
	public void connectToDb() {
		System.out.println("连接到Postgresql数据库");
	}

	@Override
	public IUserOperate createUserEntity() {
		return new PostgresqlUserEntity();
	}

	@Override
	public IDepatmentOperate createDepatentEntity() {
		return new PostgresqlDepartmentEntity();
	}
}