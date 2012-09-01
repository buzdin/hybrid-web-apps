package lv.buzdin.gwt.client.testapp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import lv.buzdin.gwt.client.bridge.*;
import lv.buzdin.gwt.client.bridge.jsni.JSNIBridge;

/**
 * @author Dmitry Buzdin
 */
public class MainPresenter implements ClickHandler, ModelCommand {

    EventBridge bridge;
    private TextArea textArea;
    private Button broadcast;
    private Button send;

    public MainPresenter() {
        bridge = new JSNIBridge();
    }

    public void display() {
        RootPanel root = RootPanel.get("gwt-view");
        VerticalPanel panel = new VerticalPanel();
        root.add(panel);

        panel.addStyleName("well");
        broadcast = createButton("Broadcast");
        send = createButton("Send");
        panel.add(broadcast);
        panel.add(send);

        textArea = new TextArea();
        panel.add(textArea);

        broadcast.addClickHandler(this);

        bridge.subscribe("broadcast", this);
        bridge.subscribe("jsEvent", this);
    }

    private Button createButton(String name) {
        Button button = new Button(name);
        button.addStyleName("btn");
        button.addStyleName("btn-primary");
        return button;
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == broadcast) {
            ModelAttributes data = Responses.attributes();
            data.set("value", "Broadcast");
            bridge.publish("broadcast", data);
        } else if (event.getSource() == send) {
            ModelAttributes data = Responses.attributes();
            data.set("value", "from GWT");
            bridge.publish("gwtEvent", data, new ModelEventCallback() {
                @Override
                public void resolve(ModelAttributes... responses) {
                    Window.alert("Resolved GWT Event! " + responses.length);
                }
            });
        }
    }

    @Override
    public void execute(ModelAttributes attributes, ModelEventCallback callback) {
        // TODO send event id in ModelAttributes
        textArea.setText(attributes.get("value"));
        ModelAttributes result = Responses.attributes();
        result.set("result", "gwt");
        callback.resolve(result);
    }

}
