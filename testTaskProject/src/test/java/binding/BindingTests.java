package binding;

import com.dic.binding.Binding;
import org.junit.Assert;
import org.junit.Test;

public class BindingTests {
    @Test
    public void Binding_InstancesShouldBeSameWithPassedToConstructor() {
        Binding binding = new Binding(Number.class, Integer.class, false);
        Assert.assertEquals(binding.getBindedInterface(), Number.class);
        Assert.assertEquals(binding.getBindedClass(), Integer.class);
        Assert.assertEquals(binding.isSingleton(), false);
    }

    @Test
    public void equals_ShouldReturnTrueForSameBindings() {
        Binding firstBinding = new Binding(Number.class, Integer.class, false);
        Binding secondBinding = new Binding(Number.class, Integer.class, false);
        Assert.assertEquals(firstBinding.equals(secondBinding),true);
    }

    @Test
    public void equals_ShouldReturnFalseForDifferentBindings() {
        Binding firstBinding = new Binding(Number.class, Integer.class, false);
        Binding secondBinding = new Binding(Number.class, Double.class, false);
        Assert.assertEquals(firstBinding.equals(secondBinding),false);
    }
}
