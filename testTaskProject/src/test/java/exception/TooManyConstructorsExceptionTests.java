package exception;

import com.dic.exception.TooManyConstructorsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class TooManyConstructorsExceptionTests {
    @Test
    public void TooManyConstructorsExceptionTests_MessageShouldBeSameWithPassedToConstructor() {
        Assert.assertThrows("Assert string", TooManyConstructorsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                throw new TooManyConstructorsException("Assert string");
            }
        });
    }
}
