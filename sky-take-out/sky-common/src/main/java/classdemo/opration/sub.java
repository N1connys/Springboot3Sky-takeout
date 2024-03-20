package classdemo.opration;

import classdemo.Operation;

public class sub  extends Operation {
    @Override
    public double GetResult() {
        double res= super.getNumberA()- super.getNumberB();
        return res;
    }
}
