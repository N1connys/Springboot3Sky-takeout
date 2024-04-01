package factorydatabasevisit;

import java.sql.Connection;

public interface IUserOperate {
    public void insert(User user);
    public User getUser(int id);
}

interface IFactory {
	public void connectToDb();
    public IUserOperate createUserEntity();
}


class MySQLServerFactory implements IFactory {
    public MySQLServerFactory() {
        super();
        connectToDb();
    }
    public void connectToDb() {
//		配置链接数据库的信息
        System.out.println("连接到MySQL数据库");
    }
    @Override
    public IUserOperate createUserEntity() {
        return new MysqlServerUserEntity();
    }
}

class PostgreSQLServerFactory implements IFactory {
	private Connection conn;
	@Override
	public void connectToDb() {
		 System.out.println("连接到PostgreSQL数据库");
	}

	public PostgreSQLServerFactory() {
		super();
		connectToDb();
	}
	@Override
	public IUserOperate createUserEntity() {
		return new PostgreSQLServerUserEntity();
	}
}
class MysqlServerUserEntity implements IUserOperate
{
    @Override
    public void insert(User user) {
        System.out.println("插入数据");
    }

    @Override
    public User getUser(int id) {
        System.out.println("返回数据");
        return null;
    }
}
class PostgreSQLServerUserEntity implements IUserOperate
{
    @Override
    public void insert(User user) {
        System.out.println("postgre 插入数据");
    }

    @Override
    public User getUser(int id) {
        System.out.println("postgre 获取数据");
        return null;
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