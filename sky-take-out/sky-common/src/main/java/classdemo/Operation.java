package classdemo;

import lombok.Data;

//@Data
public abstract class Operation {
    private double numberA = 0;
    private double numberB = 0;

    public abstract double GetResult();

    public double getNumberA() {
        return numberA;
    }

    public void setNumberA(double numberA) {
        this.numberA = numberA;
    }

    public double getNumberB() {
        return numberB;
    }

    public void setNumberB(double numberB) {
        this.numberB = numberB;
    }
}

class add extends Operation {
    @Override
    public double GetResult() {
        double res = super.getNumberA() + super.getNumberB();
        return res;
    }
}

class division extends Operation {
    @Override
    public double GetResult() {
        double res = 0;
        if (super.getNumberB() != 0) {
            res = super.getNumberA() / super.getNumberB();
            return res;
        } else {
            throw new ArithmeticException("除数不为0");
        }
    }

}

class multip extends Operation {
    @Override
    public double GetResult() {
        double res = super.getNumberA() * super.getNumberB();
        return res;
    }
}

class sub extends Operation {
    @Override
    public double GetResult() {
        double res = super.getNumberA() - super.getNumberB();
        return res;
    }
}