package abstractfactorydatabasevisit;

public class Client {

    public static void main(String[] args) {
        User user = new User();
        Department department = new Department();
       IFactory factory=new MySQLFactory();
        // IFactory factory = new PostgresqlFactory();
        IUserOperate UserEntity = factory.createUserEntity();
        UserEntity.insert(user);
        UserEntity.getUser(0);

        IDepatmentOperate DepatmentEntity = factory.createDepatentEntity();
        DepatmentEntity.insert(department);
        DepatmentEntity.getDepartment(0);
    }

}
