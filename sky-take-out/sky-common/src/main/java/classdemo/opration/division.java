package classdemo.opration;

import classdemo.Operation;

public class division extends Operation {
    @Override
    public double GetResult() {
        double res=0;
        if (super.getNumberB() != 0) {
             res = super.getNumberA() / super.getNumberB();
        return res;}
        else{
            throw new ArithmeticException("除数不为0");
        }
    }

}

