package unchained.commons.contract;

import org.testng.annotations.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FactoryTest {

    static class A {

        private String foo;

        A(String foo) { this.foo = foo; }

    }

    static class B {

        private String foo;

        B(String foo) { this.foo = foo; }

    }

    static class A1 extends A {

        public A1() { super("Hello 1"); }

    }

    static class A2 extends A {

        public A2() { super("Hello 2"); }

    }

    static class FactoryA implements Factory<A> {

        @Override
        public A create(Object... arguments) {
            return new A("Hello");
        }

    }

    private static final FactoryA FACTORY_A = new FactoryA();
    private static final Factory<B> FACTORY_B = (arguments -> new B(arguments[0].toString()));

    @Test
    public void testType() {
        assertThat(FACTORY_A.type().toString(), is(A.class.toString()));
        assertThat(FACTORY_B.type().toString(), is(B.class.toString()));
    }

    @Test
    public void testCreate() throws Exception {
        A a = FACTORY_A.create();
        B b = FACTORY_B.create("World");
        assertThat(a, notNullValue());
        assertThat(b, notNullValue());
        assertThat(a.foo, is("Hello"));
        assertThat(b.foo, is("World"));
    }

}
