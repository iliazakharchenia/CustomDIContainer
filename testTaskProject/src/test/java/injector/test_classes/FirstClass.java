package injector.test_classes;

import com.dic.injector.Inject;

public class FirstClass {
    public FifthInterface fifthInterface;
    public SecondInterface secondInterface;

    public FifthInterface getFifthInterface() {
        return fifthInterface;
    }

    public SecondInterface getSecondInterface() {
        return secondInterface;
    }

    @Inject
    public FirstClass(FifthInterface fifthInterface, SecondInterface secondInterface) {
        this.fifthInterface = fifthInterface;
        this.secondInterface = secondInterface;
    }
}
