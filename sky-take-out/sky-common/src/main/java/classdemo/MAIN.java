package classdemo;

import java.util.Scanner;

public class MAIN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("输入两个数字和运算符号:");
        double numberA = scanner.nextDouble();

        double numberB = scanner.nextDouble();

        // 处理换行符
        scanner.nextLine();

        System.out.println("输入运算符:");
        String commit = scanner.nextLine();

        Operation operation = SimpleStaticFactory.createoperation(commit);
        if (operation != null) {
            operation.setNumberA(numberA);
            operation.setNumberB(numberB);
            System.out.println("结果是:");
            System.out.println(operation.GetResult());
        } else {
            System.out.println("无效的运算符!");
        }

        scanner.close();
    }
}
