package unchained;

import java.util.LinkedList;
import java.util.List;

import static unchained.Utils.forceNotNull;

public abstract class CompositeApplicationContext extends AbstractNestableContext<ApplicationLifecycle> implements ApplicationContext {

    private final List<ApplicationContext> contexts;

    public CompositeApplicationContext(ApplicationLifecycle lifecycle) {
        this(null, lifecycle, null);
    }

    public CompositeApplicationContext(ApplicationContext parent) {
        this(parent, parent.lifecycle(), null);
    }

    protected CompositeApplicationContext(ApplicationContext parent, ApplicationLifecycle lifecycle, List<ApplicationContext> contexts) {
        super(parent, forceNotNull(lifecycle, "lifecycle"));
        this.contexts = contexts == null ? new LinkedList<>() : contexts;
    }

}
