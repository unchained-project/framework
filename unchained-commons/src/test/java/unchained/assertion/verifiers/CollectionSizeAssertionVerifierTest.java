package unchained.assertion.verifiers;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CollectionSizeAssertionVerifierTest {

    @Test
    public void testVerifier() throws Exception {
        for (int i = 0; i < 10; i++) {
            final int size = new Random().nextInt(1000) + 2;
            final CollectionSizeAssertionVerifier<Collection<?>> verifier = new CollectionSizeAssertionVerifier<>(size);
            final List<Object> list = new LinkedList<>();
            for (int j = 0; j < size; j++) {
                list.add(new Object());
            }
            assertThat(verifier.test(list), is(true));
            assertThat(verifier.test(list.subList(0, list.size() / 2)), is(false));
        }
    }

}
