package unchained.http;

import unchained.Selector;

public interface HttpSelector extends Selector<HttpRequest, HttpRequestLine> {

    interface Compiler extends Selector.Compiler<HttpSelector> {

    }

}
