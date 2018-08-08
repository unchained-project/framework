package unchained.contract;

import org.testng.annotations.Test;

import static unchained.assertion.Assert.assertThat;
import static unchained.assertion.Assertions.*;

public class RegistryTest {

    private static class TestRegistrable implements Registrable<String> {

        private String registryKey;

        TestRegistrable(String registryKey) {
            this.registryKey = registryKey;
        }

        @Override
        public String registryKey() {
            return registryKey;
        }

    }

    @Test
    public void testLookup() {
        final Registry<String, TestRegistrable> registry = Registry.lookup(TestRegistrable.class);
        assertThat(registry, "registry", isNotNull());
        assertThat(registry, "registry", isSameAs(Registry.lookup(TestRegistrable.class)));
    }

    @Test
    public void testRegister() {
        final Registry<String, TestRegistrable> registry = Registry.lookup(TestRegistrable.class);
        final TestRegistrable registrable = new TestRegistrable("registered");
        registry.register(registrable);
        assertThat(registry.get("registered"), "registered", isSameAs(registrable));
        assertThat(registry.get("unregistered"), "unregistered", isNull());
    }


    @Test
    public void testRegisterAgain() {
        final Registry<String, TestRegistrable> registry = Registry.lookup(TestRegistrable.class);
        final TestRegistrable registrable = new TestRegistrable("registered");
        if (!registry.has("registered")) {
            registry.register(new TestRegistrable("registered"));
        }
        registry.register(registrable);
        assertThat(registry.get("registered"), "registered", isSameAs(registrable));
    }

    @Test
    public void testKeysAndValues() {
        final Registry<String, TestRegistrable> registry = Registry.lookup(TestRegistrable.class);
        final TestRegistrable newRegistrable = new TestRegistrable("new-registrable");
        registry.register(newRegistrable);
        assertThat(registry.keys().contains("new-registrable"), "existingKey", is(true));
        assertThat(registry.values().contains(newRegistrable), "existingValue", is(true));
        assertThat(registry.keys().contains("unregistered"), "nonExistingKey", is(false));
    }

}
