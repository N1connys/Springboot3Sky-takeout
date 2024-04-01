package classdemo;

import java.util.Scanner;

public class ClientMethod {
    public static void Start() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("按数字回车运算符回车数字的方式输入");
            System.out.println("输出结果后输入'2'退出");
            double numberA = scanner.nextDouble();
            // 处理换行符
            scanner.nextLine();
            String commit = scanner.nextLine();
            double numberB = scanner.nextDouble();
            Operation operation=ClientMethod.operation(commit);
            if (operation != null) {
                operation.setNumberA(numberA);
                operation.setNumberB(numberB);
                System.out.println("结果是:");
                System.out.println(operation.GetResult());
            } else {
                System.out.println("无效的运算符");
            }

            System.out.println("退出输入2,继续输入1");
            int a = scanner.nextInt();
            if (a == 2) {
                scanner.close();
                break;
            }
        }
    }
    //封装一下创建算数类的方法
    public static Operation operation(String commit)
    {
        //创建工厂类
        OpFactory factory = null;
        Operation operation=null;
        //根据情况创建不同算术类
        switch (commit) {
            case "+":
                factory = new AddFactory();
                break;
            case "-":
                factory = new SubFactory();
                break;
            case "*":
                factory = new MulFactory();
                break;
            case "/":
                factory = new DivFactory();
                break;
        }
        return factory.createOperation();
    }
}
