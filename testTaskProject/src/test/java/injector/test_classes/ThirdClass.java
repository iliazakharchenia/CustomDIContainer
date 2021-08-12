package injector.test_classes;

import com.dic.injector.Inject;

public class ThirdClass implements ThirdInterface {
    @Inject
    public ThirdClass(int number) {
    }
}
