package unchained;

import java.util.Map;

import static unchained.Utils.forceNotNull;

/**
 * TODO: doc
 */
public class CompositeApplicationContext extends AbstractCompositeContext<
    ApplicationLifecycle, ApplicationContext, CompositeApplicationContext> {

    /**
     * TODO: doc
     *
     * @param lifecycle
     */
    public CompositeApplicationContext(ApplicationLifecycle lifecycle) {
        super(lifecycle);
    }

    /**
     * TODO: doc
     *
     * @param parent
     */
    public CompositeApplicationContext(ApplicationContext parent) {
        super(forceNotNull(parent, "parent"), parent.lifecycle());
    }

    @Override
    protected Map<String, ? super Object> properties() {
        return Utils.environment;
    }

}
