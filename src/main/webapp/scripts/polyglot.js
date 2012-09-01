// TODO Unit test this
// Set up cross-language bridge.
$bridge = {

    eventBus:{},

    registerEventBus:function (eventBus) {
        this.eventBus = eventBus;
    },

    subscribe:function (eventId, handler) {
        this.eventBus.subscribe(eventId, handler);
    },

    unsubscribe:function (handler) {
        this.eventBus.unsubscribe(handler);
    },

    publish:function (eventId, data, callback) {
        this.eventBus.publish(eventId, data, callback);
    },

    suspend:function () {
        // TODO implement
    },

    activate:function () {
        // TODO implement
    }
};

console.log('Native bridge registered');