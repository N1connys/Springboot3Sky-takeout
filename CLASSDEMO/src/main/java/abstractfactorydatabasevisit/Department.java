package abstractfactorydatabasevisit;


interface IDepatmentOperate { //Department对象到Department表数据转换有关的数据库操作
    public void insert(Department department); //增
    public Department getDepartment(int id); //查
    //改 自己定义
    public void update(Department department);
    //删 自己定义
    public void delete(int id);
}

public class Department {
    private int ID;
    private String DeptName;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }
}


class MySQLDepartmentEntity implements IDepatmentOperate { //具体在MYSQL的转换过程

    @Override
    public void insert(Department deparment) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中添加一条Deparment记录");
    }

    @Override
    public Department getDepartment(int id) {
        // TODO Auto-generated method stub
        System.out.println("在MySQL中根据ID得到Deparment表的一条记录");
        return null;
    }
    @Override
    public void update(Department department) {
        System.out.println("在MYSQL中添加一条Deparment记录");
    }
    @Override
    public void delete(int id) {
        System.out.println("在MYSQK中删除一条Deparment记录");
    }
}

class PostgresqlDepartmentEntity implements IDepatmentOperate { //具体的POSTSQL的实现
 
    @Override
    public void insert(Department deparment) {
        // TODO Auto-generated method stub
        System.out.println("在Postgresql中添加一条Deparment记录");
    }

    @Override
    public Department getDepartment(int id) {
        // TODO Auto-generated method stub
        System.out.println("在AAcesss中根据ID得到Deparment表的一条记录");
        return null;
    }

    @Override
    public void update(Department department) {
        System.out.println("在Postgresql中更新一条Deparment记录");
    }

    @Override
    public void delete(int id) {
        System.out.println("在Postgresql中删除一条Deparment记录");
    }


}

