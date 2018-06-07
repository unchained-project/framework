package unchained.assertion.verifiers;

import org.testng.annotations.Test;
import unchained.assertion.verifiers.model.StaticAssertionVerifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConjunctiveAssertionVerifierTest {

    @Test
    public void testFirstTrue() throws Exception {
        final StaticAssertionVerifier<Object> first = new StaticAssertionVerifier<>(true);
        final StaticAssertionVerifier<Object> second = new StaticAssertionVerifier<>(false);
        final ConjunctiveAssertionVerifier<Object> verifier = new ConjunctiveAssertionVerifier<>(first, second);
        final Object value = new Object();
        assertThat(verifier.test(value), is(false));
        assertThat(first.getValues(), hasSize(1));
        assertThat(first.getValues(), containsInAnyOrder(value));
        assertThat(second.getValues(), hasSize(1));
        assertThat(second.getValues(), containsInAnyOrder(value));
    }

    @Test
    public void testSecondTrue() throws Exception {
        final StaticAssertionVerifier<Object> first = new StaticAssertionVerifier<>(false);
        final StaticAssertionVerifier<Object> second = new StaticAssertionVerifier<>(true);
        final ConjunctiveAssertionVerifier<Object> verifier = new ConjunctiveAssertionVerifier<>(first, second);
        final Object value = new Object();
        assertThat(verifier.test(value), is(false));
        assertThat(first.getValues(), hasSize(1));
        assertThat(first.getValues(), containsInAnyOrder(value));
        assertThat(second.getValues(), hasSize(0));
    }

    @Test
    public void testNeitherTrue() throws Exception {
        final StaticAssertionVerifier<Object> first = new StaticAssertionVerifier<>(false);
        final StaticAssertionVerifier<Object> second = new StaticAssertionVerifier<>(false);
        final ConjunctiveAssertionVerifier<Object> verifier = new ConjunctiveAssertionVerifier<>(first, second);
        final Object value = new Object();
        assertThat(verifier.test(value), is(false));
        assertThat(first.getValues(), hasSize(1));
        assertThat(first.getValues(), containsInAnyOrder(value));
        assertThat(second.getValues(), hasSize(0));
    }
    @Test
    public void testBothTrue() throws Exception {
        final StaticAssertionVerifier<Object> first = new StaticAssertionVerifier<>(true);
        final StaticAssertionVerifier<Object> second = new StaticAssertionVerifier<>(true);
        final ConjunctiveAssertionVerifier<Object> verifier = new ConjunctiveAssertionVerifier<>(first, second);
        final Object value = new Object();
        assertThat(verifier.test(value), is(true));
        assertThat(first.getValues(), hasSize(1));
        assertThat(first.getValues(), containsInAnyOrder(value));
        assertThat(second.getValues(), hasSize(1));
        assertThat(second.getValues(), containsInAnyOrder(value));
    }

}
