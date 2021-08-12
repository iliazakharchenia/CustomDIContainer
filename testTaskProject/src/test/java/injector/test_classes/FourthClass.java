package injector.test_classes;

import com.dic.injector.Inject;

public class FourthClass implements FourthInterface {
    public FourthInterface fourthInterface;

    @Inject
    public FourthClass(FourthInterface intf) {
        this.fourthInterface = intf;
    }

    @Inject
    public FourthClass() {
    }
}
