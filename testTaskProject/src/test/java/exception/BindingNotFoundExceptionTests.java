package exception;

import com.dic.exception.BindingNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class BindingNotFoundExceptionTests {
    @Test
    public void BindingNotFoundExceptionTests_MessageShouldBeSameWithPassedToConstructor() {
        Assert.assertThrows("Assert string", BindingNotFoundException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                throw new BindingNotFoundException("Assert string");
            }
        });
    }
}
