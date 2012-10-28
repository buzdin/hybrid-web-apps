#import('dart:html');
#import('dart:json');

#import("../../packages/JsonObject/JsonObject.dart");
#import('../../packages/js/js.dart', prefix: 'js');

class EventBridge {

// TODO convert response from JSON to Map and pass to callback
  void subscribe(String eventId, Function callback) {
    var bridge = js.context.$bridge;
    bridge.subscribe(eventId, new js.Callback.many(callback));
  }

  void publish(String eventId, Map message, Function callback) {
    js.scoped(() {
      var json = js.map(message);
      var bridge = js.context.$bridge;
      bridge.publish(eventId, json, new js.Callback.many(callback));
    });
  }

}

void main() {

  callback(_) {
    js.scoped(() {
      js.context.console.log('Hello from Dart via JavaScript.');
    });
  }

  var eventBridge = new EventBridge();
  eventBridge.publish('dartEvent', {'value': 'Hello from Dart'}, callback);

}