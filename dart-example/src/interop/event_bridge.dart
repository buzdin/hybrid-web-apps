#library('event_bridge');

#import('dart:html');
#import('dart:json');

#import("../../packages/json_object/json_object.dart");
#import('../../packages/js/js.dart', prefix: 'js');


class EventBridge {

  void subscribe(String eventId, Function callback) {
    js.scoped(() {
      var bridge = js.context.$bridge;
      var jsCallback = new js.Callback.many((attrs, fn) {
        callback(attrs,

            (response) {
          var json = js.map(response);
          fn(json);
        });
      });

      bridge.subscribe(eventId, jsCallback);
    });
  }

  void unsubscribe(Function callback) {
// TODO implement
  }

  void publish(String eventId, Map message, Function callback) {
    js.scoped(() {
      var json = js.map(message);
      var bridge = js.context.$bridge;
      var jsCallback = new js.Callback.many(callback);
      bridge.publish(eventId, json, jsCallback);
    });
  }

}
