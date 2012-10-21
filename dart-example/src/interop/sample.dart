#import('dart:html');
#import('dart:json');

#import("../../packages/JsonObject/JsonObject.dart");
#import('../../packages/js/js.dart', prefix: 'js');

void main() {

  callback(_) {
    js.scoped(() {
      js.context.alert('Hello from Dart via JavaScript.');
    });
  }

  js.scoped(() {
    var json = js.map({'value': 'Hello from Dart'});
    var bridge = js.context.$bridge;
    bridge.publish('dartEvent', json, new js.Callback.once(callback));
  });

}