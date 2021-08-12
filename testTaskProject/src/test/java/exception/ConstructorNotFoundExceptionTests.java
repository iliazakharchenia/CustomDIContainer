package exception;

import com.dic.exception.ConstructorNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ConstructorNotFoundExceptionTests {
    @Test
    public void ConstructorNotFoundExceptionTests_MessageShouldBeSameWithPassedToConstructor() {
        Assert.assertThrows("Assert string", ConstructorNotFoundException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                throw new ConstructorNotFoundException("Assert string");
            }
        });
    }
}
