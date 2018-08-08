package unchained.contract;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import unchained.error.FactoryNotFoundException;

import static unchained.assertion.Assert.assertThat;
import static unchained.assertion.Assertions.*;

public class FactoryTest {

    private static class A {

        private String foo;

        A(String foo) { this.foo = foo; }

    }

    private static class ArgumentsA {

        private String foo;

        ArgumentsA(String foo) {
            this.foo = foo;
        }

        String foo() {
            return foo;
        }

    }

    private static class FactoryA implements Factory<A, ArgumentsA> {

        @Override
        public Class<A> type() {
            return A.class;
        }

        @Override
        public A create(ArgumentsA configuration) {
            return new A(configuration.foo());
        }

    }

    @BeforeTest
    public void setUp() {
        Factory.register(new FactoryA());
    }

    @Test
    public void testForExistingType() {
        final Factory<A, Void> factoryA = Factory.forType(A.class);
        assertThat(factoryA, "factory", isNotNull());
        assertThat(factoryA, "factory", isA(FactoryA.class));
    }

    @Test(expectedExceptions = FactoryNotFoundException.class)
    public void testForNonExistingType() {
        Factory<String, Void> dummy = Factory.forType(String.class);
    }

    @Test
    public void testType() {
        final Factory<A, Void> factoryA = Factory.forType(A.class);
        assertThat(factoryA.type(), "type", is(A.class));
    }

    @Test
    public void testCreate() throws Exception {
        final A a = Factory.forType(A.class).create(new ArgumentsA("FOO"));
        assertThat(a, "instance", isNotNull());
        assertThat(a.foo, "attribute", is("FOO"));
    }

}
