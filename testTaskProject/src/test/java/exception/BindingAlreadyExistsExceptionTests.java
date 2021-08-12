package exception;

import com.dic.exception.BindingAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class BindingAlreadyExistsExceptionTests {
    @Test
    public void BindingAlreadyExistsException_MessageShouldBeSameWithPassedToConstructor() {
        Assert.assertThrows("Assert string", BindingAlreadyExistsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                throw new BindingAlreadyExistsException("Assert string");
            }
        });
    }
}
