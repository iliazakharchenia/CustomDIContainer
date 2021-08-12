package injector;

import com.dic.exception.*;
import com.dic.injector.InjectorImpl;
import com.dic.provider.ProviderImpl;
import injector.test_classes.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.lang.reflect.InvocationTargetException;

public class InjectorImplTests {
    @Test
    public void InjectorImpl_InstanceShouldBeNotNull() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertNotNull(injector);
    }

    @Test
    public void bind_BindingAlreadyExistsExceptionExpectedWhenSuchBindingIsAlreadyExists() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(BindingAlreadyExistsException.class, new ThrowingRunnable() {
            @Override
            public void run() {
                injector.bind(SecondInterface.class, SecondClass.class);
                injector.bind(SecondInterface.class, SecondClass.class);
            }
        });
    }

    @Test
    public void bind_BindingAlreadyExistsExceptionExpectedWhenSuchSingletonBindingIsAlreadyExists() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(BindingAlreadyExistsException.class, new ThrowingRunnable() {
            @Override
            public void run() {
                injector.bindSingleton(SecondInterface.class, SecondClass.class);
                injector.bind(SecondInterface.class, SecondClass.class);
            }
        });
    }

    @Test
    public void bind_InCaseParametersAreCorrectExceptionsNotExpected() {
        InjectorImpl injector = new InjectorImpl();
        injector.bind(SecondInterface.class, SecondClass.class);
    }

    @Test
    public void bindSingleton_BindingAlreadyExistsExceptionExpectedWhenSuchBindingIsAlreadyExists() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(BindingAlreadyExistsException.class, new ThrowingRunnable() {
            @Override
            public void run() {
                injector.bind(SecondInterface.class, SecondClass.class);
                injector.bindSingleton(SecondInterface.class, SecondClass.class);
            }
        });
    }

    @Test
    public void bindSingleton_BindingAlreadyExistsExceptionExpectedWhenSuchSingletonBindingIsAlreadyExists() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(BindingAlreadyExistsException.class, new ThrowingRunnable() {
            @Override
            public void run() {
                injector.bindSingleton(SecondInterface.class, SecondClass.class);
                injector.bindSingleton(SecondInterface.class, SecondClass.class);
            }
        });
    }

    @Test
    public void bindSingleton_InCaseParametersAreCorrectExceptionsNotExpected() {
        InjectorImpl injector = new InjectorImpl();
        injector.bindSingleton(SecondInterface.class, SecondClass.class);
    }

    @Test
    public void getProvider_TooManyConstructorsExceptionWhenMoreThanOneConstructorWithInjectAnnotation() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(TooManyConstructorsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                injector.getProvider(FourthClass.class);
            }
        });
    }

    @Test
    public void getProvider_ConstructorNotFoundExceptionWhenThereAreNoConstructorsWithInjectAnnotation() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(ConstructorNotFoundException.class, new ThrowingRunnable() {
            @Override
            public void run() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                injector.getProvider(SecondClass.class);
            }
        });
    }

    @Test
    public void getProvider_ParameterIsNotReferenceTypeExceptionWhenThereAreAnyConstructorParametersWithNotReferencedType() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(ParameterIsNotReferenceTypeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                injector.getProvider(ThirdClass.class);
            }
        });
    }

    @Test
    public void getProvider_BindingNotFoundExceptionWhenThereAreNoSuchBindingWithClassThatPassedToMethod() {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertThrows(BindingNotFoundException.class, new ThrowingRunnable() {
            @Override
            public void run() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                injector.getProvider(FirstClass.class);
            }
        });
    }

    @Test
    public void getProvider_ReturnProviderWithInstanceWhenInstanceClassHasConstructorWithNoParametersWithInjectAnnotation()
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        Assert.assertNotNull(injector.getProvider(FifthClass.class).getInstance());
    }

    @Test
    public void getProvider_ReturnProviderWithInstanceWithSamePointerForInstanceClassConstructorParameterWhenParameterClassWasBindedAsSingletonAndProviderWithThatParameterClassWasRequested()
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bindSingleton(SecondInterface.class, SecondClass.class);
        injector.bind(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> firstProvider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        ProviderImpl<FirstClass> secondProvider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertEquals(firstProvider.getInstance().getSecondInterface().toString(), secondProvider.getInstance().getSecondInterface().toString());
    }

    @Test
    public void getProvider_ReturnProviderWithInstanceForInstanceClassConstructorParameterWhenClassWasBindedAsSingletonAndProviderWithThatClassWasNotRequested()
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bindSingleton(SecondInterface.class, SecondClass.class);
        injector.bind(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> provider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertNotNull(provider.getInstance());
    }

    @Test
    public void getProvider_ReturnProviderWithInstanceWhenParametersClassesWasBinded() throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bind(SecondInterface.class, SecondClass.class);
        injector.bind(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> provider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertNotNull(provider.getInstance());
    }

    @Test
    public void getProvider_InstanceParametersClassInProviderHasSameTypeThatWasBinded()
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bind(SecondInterface.class, SecondClass.class);
        injector.bind(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> provider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertEquals(provider.getInstance().getSecondInterface().getClass().getName(), SecondClass.class.getName());
        Assert.assertEquals(provider.getInstance().getFifthInterface().getClass().getName(), FifthClass.class.getName());
    }

    @Test
    public void getProvider_InstanceParametersClassInProviderHasSameTypeThatWasBindedAsSingleton()
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bindSingleton(SecondInterface.class, SecondClass.class);
        injector.bindSingleton(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> provider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertEquals(provider.getInstance().getSecondInterface().getClass().getName(), SecondClass.class.getName());
        Assert.assertEquals(provider.getInstance().getFifthInterface().getClass().getName(), FifthClass.class.getName());
    }

    @Test
    public void getProvider_ReturnNotNullProvider() throws IllegalAccessException, InstantiationException,
            InvocationTargetException {
        InjectorImpl injector = new InjectorImpl();
        injector.bindSingleton(SecondInterface.class, SecondClass.class);
        injector.bindSingleton(FifthInterface.class, FifthClass.class);
        ProviderImpl<FirstClass> provider = (ProviderImpl<FirstClass>) injector.getProvider(FirstClass.class);
        Assert.assertNotNull(provider);
    }
}
