package lv.buzdin.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import lv.buzdin.gwt.client.bridge.JSNIBridge;
import lv.buzdin.gwt.client.testapp.MainPresenter;

/**
 * @author Dmitry Buzdin
 */
public class MainModule implements EntryPoint {

    @Override
    public void onModuleLoad() {
        JSNIBridge bridge = new JSNIBridge();
        bridge.registerJsFunctions();

        MainPresenter presenter = new MainPresenter();
        presenter.display();
    }

}
