package classdemo;

public class multip  extends Operation{
    @Override
    public double GetResult() {
        double res= super.getNumberA()*super.getNumberB();
        return res;
    }
}
