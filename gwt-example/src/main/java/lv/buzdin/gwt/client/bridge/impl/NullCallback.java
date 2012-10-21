package lv.buzdin.gwt.client.bridge.impl;

import lv.buzdin.gwt.client.bridge.ModelAttributes;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;

/**
 * @author Dmitry Buzdin
 */
public final class NullCallback implements ModelEventCallback {

    public static final NullCallback INSTANCE = new NullCallback();

    @Override
    public void resolve(ModelAttributes... attributes) {

    }

}
