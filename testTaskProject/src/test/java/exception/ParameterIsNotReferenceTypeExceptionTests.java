package exception;

import com.dic.exception.ParameterIsNotReferenceTypeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ParameterIsNotReferenceTypeExceptionTests {
    @Test
    public void ParameterIsNotReferenceTypeExceptionTests_MessageShouldBeSameWithPassedToConstructor() {
        Assert.assertThrows("Assert string", ParameterIsNotReferenceTypeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                throw new ParameterIsNotReferenceTypeException("Assert string");
            }
        });
    }
}
