package provider;

import com.dic.provider.ProviderImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ProviderImplTests {
    @Test
    public void getInstance_InstanceNotNullExpected() {
        ProviderImpl provider = new ProviderImpl(Integer.class);
        Assert.assertNotNull(provider.getInstance());
    }

    @Test
    public void getInstance_NullPointerExceptionExpectedWhenInstanceIsNull() {
        ProviderImpl provider = new ProviderImpl(null);
        Assert.assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                provider.getInstance();
            }
        });
    }

    @Test
    public void ProviderImpl_InstanceShouldBeSameWithPassedToConstructor() {
        ProviderImpl provider = new ProviderImpl(Integer.class);
        Assert.assertEquals(Integer.class, provider.getInstance());
    }
}
