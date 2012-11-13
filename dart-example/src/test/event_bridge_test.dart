#import('../../packages/unittest/unittest.dart');

void main() {

  test('Empty',

      () {
    var eventBridge = new EventBridge();
    eventBridge.subscribe("message",

        () {});
  });

}

