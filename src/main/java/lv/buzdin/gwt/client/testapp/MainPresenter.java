package lv.buzdin.gwt.client.testapp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import lv.buzdin.gwt.client.bridge.*;

/**
 * @author Dmitry Buzdin
 */
public class MainPresenter implements ClickHandler, ModelCommand {

    JSNIBridge jsniBridge;
    private TextArea textArea;

    public MainPresenter() {
        jsniBridge = new JSNIBridge();
    }

    public void display() {
        RootPanel root = RootPanel.get("gwt-view");
        SimplePanel panel = new SimplePanel();
        root.add(panel);

        panel.addStyleName("well");
        Button button = new Button("Test");
        button.addStyleName("btn");
        button.addStyleName("btn-primary");
        panel.add(button);

        textArea = new TextArea();
        panel.add(textArea);

        button.addClickHandler(this);

        jsniBridge.subscribe("broadcast", this);
    }

    @Override
    public void onClick(ClickEvent event) {
        ModelAttributes data = Responses.data();
        data.set("value", "from GWT");
        jsniBridge.publish("broadcast", data);
    }

    @Override
    public void execute(ModelAttributes attributes, ModelEventCallback callback) {
        textArea.setText(attributes.get("value"));
        callback.resolve();
    }

}
