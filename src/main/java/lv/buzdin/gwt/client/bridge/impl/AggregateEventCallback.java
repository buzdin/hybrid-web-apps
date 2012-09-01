package lv.buzdin.gwt.client.bridge.impl;

import lv.buzdin.gwt.client.bridge.ModelAttributes;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Buzdin
 */
public class AggregateEventCallback implements ModelEventCallback {

    List<ModelAttributes> responses = new ArrayList<ModelAttributes>();

    @Override
    public void resolve(ModelAttributes... attributes) {
        List<ModelAttributes> responseList = Arrays.asList(attributes);
        responses.addAll(responseList);
    }

    public List<ModelAttributes> getResponses() {
        return responses;
    }

}
