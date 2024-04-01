package abstractfactorydatabasevisit;

interface IUserOperate { //User对象到User表数据转换有关的数据库操作
    public void insert(User user); //增
    public User getUser(int id); //查
    //还有改，和删除操作
    //修改
    public void update(User user);
    //删除
    public void delete(int id);
}

public class User {
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

class MySQLUserEntity implements IUserOperate { //具体在MYSQL的转换过程
    @Override
    public void insert(User user) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中添加一条User记录");
    }

    @Override
    public User getUser(int id) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中根据ID得到User表的一条记录");
        return null;
    }

    @Override
    public void update(User user) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中更新一条记录");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中删除一条数据");
    }
}

class PostgresqlUserEntity implements IUserOperate { //POstSQL的转换过程

    @Override
    public void insert(User user) {
        // TODO Auto-generated method stub
        System.out.println("在Postgresql中添加一条User记录");
    }

    @Override
    public User getUser(int id) {
        // TODO Auto-generated method stub
        System.out.println("在Postgresqls中根据ID得到User表的一条记录");
        return null;
    }

    @Override
    public void update(User user) {
        // TODO Auto-generated method stub
        System.out.println("在Postgresql中更新一条User记录");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        System.out.println("在Postgresql中删除一条User记录");
    }
}



