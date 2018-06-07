package unchained.assertion;

public class ImmutableAssertionResult<O> extends AbstractAssertionResult<O> {

    private final String error;
    private final O value;

    public ImmutableAssertionResult(Assertion<O> assertion, String error, O value) {
        super(assertion);
        this.error = error;
        this.value = value;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public O getValue() {
        return value;
    }

}
