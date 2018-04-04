package unchained.web;

import unchained.ChainLifecycle;

/**
 * This interface encapsulates the lifecycle of a request which is being handled by a chain of middlewares.
 * Although in principle there is no significant difference between request lifecycle and the chain that
 * is processing it, this interface provides an opportunity to define request-specific lifecycle operations.
 */
public interface RequestLifecycle extends ChainLifecycle {
}
