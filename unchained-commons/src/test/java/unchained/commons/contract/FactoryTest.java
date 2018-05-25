package unchained.commons.contract;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import unchained.commons.assertion.Assertions;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.*;
import static unchained.commons.assertion.Assertions.isNotNull;

public class FactoryTest {

    private static class A {

        private String foo;

        A(String foo) { this.foo = foo; }

    }

    private static class ArgumentsA implements Factory.Arguments {

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
    public void testForType() throws Exception {
        final Factory<A, Factory.Arguments> factoryA = Factory.forType(A.class);
        assertThat(factoryA, "factory", isNotNull());
        assertThat(factoryA, "factory", isA(FactoryA.class));
    }

    @Test
    public void testType() {
        final Factory<A, Factory.Arguments> factoryA = Factory.forType(A.class);
        assertThat(factoryA.type(), "type", is(A.class));
    }

    @Test
    public void testCreate() throws Exception {
        final A a = Factory.forType(A.class).create(new ArgumentsA("FOO"));
        assertThat(a, "instance", isNotNull());
        assertThat(a.foo, "attribute", is("FOO"));
    }

}
