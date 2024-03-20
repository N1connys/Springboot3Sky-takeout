package classdemo;

public class division extends Operation{
    @Override
    public double GetResult() {
        double res= super.getNumberA()/super.getNumberB();
        return res;
    }
}
