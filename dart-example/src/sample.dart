#import('./interop/event_bridge.dart');
#import('../packages/js/js.dart', prefix: 'js');

void main() {

  void _print(message) {
    js.scoped(() {
      js.context.console.log(message);
    });
  }

  _print("Dart: STARTED");

  var eventBridge = new EventBridge();

  eventBridge.subscribe('dartEvent',

      (attrs, fn) {
    _print('Dart: SUB:');
    _print(attrs.value);
    fn({'response' : 'Dart: OK'});
  });

  eventBridge.publish('dartEvent', {'value': 'Hello from Dart!'},

      (responses) {
    _print("Dart: PUB");
    _print(responses);
  });

  _print("Dart: FINISHED");
}