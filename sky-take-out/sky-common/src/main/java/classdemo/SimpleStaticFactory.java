package classdemo;

public class SimpleStaticFactory {
    public static Operation createoperation(String commit) {
        Operation operation = null;
        switch (commit) {
            case "+":
                operation = new add();
                break;
            case "-":
                operation = new sub();
                break;
            case "*":
                operation = new multip();
                break;
            case "/":
                operation = new division();
                break;
        }
        return operation;
    }
}
