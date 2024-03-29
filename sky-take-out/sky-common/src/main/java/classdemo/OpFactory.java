package classdemo;


public interface OpFactory {
    Operation createOperation();
}

 class AddFactory implements OpFactory {
    @Override
    public Operation createOperation() {
        return new add();
    }
}
class SubFactory implements OpFactory {
    @Override
    public Operation createOperation() {
        return new sub();
    }
}
class MulFactory implements OpFactory {
    @Override
    public Operation createOperation() {
        return new multip();
    }
}
class DivFactory implements OpFactory {
    @Override
    public Operation createOperation() {
        return new division();
    }
}


