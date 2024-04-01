package factorydatabasevisit;

public class Client {

    public static void main(String[] args) {
        User user = new User();
        IFactory factory=new MySQLServerFactory();
        // IFactory factory = new PostgreSQLServerFactory();
        IUserOperate userEntity = factory.createUserEntity();
        userEntity.insert(user);
        userEntity.getUser(0);
    }

}
