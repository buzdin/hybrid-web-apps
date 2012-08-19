// TODO Unit test this
// Set up cross-language bridge.
$bridge = {

    eventBusInstances: [],

    addEventBus: function(eventBus) {
        this.eventBusInstances.push(eventBus);
    },

    subscribe: function(eventId, handler) {
        this.eventBusInstances.map(function(eventBus) {
            eventBus.subscribe(eventId, handler);
        });
    },

    unsubscribe: function(handler) {
        this.eventBusInstances.map(function(eventBus) {
            eventBus.unsubscribe(handler);
        });
    },

    publish: function(eventId, data, callback) {
        this.eventBusInstances.map(function(eventBus) {
            eventBus.publish(eventId, data, callback);
        });
    },

    suspend: function() {
        // TODO implement
    },

    activate : function() {
        // TODO implement
    }
};

var jsEventBus = {

    subscribers: {},

    subscribe: function(eventId, handler) {
        var subscribers = this.subscribers[eventId];
        if (subscribers == null) {
            subscribers = [];
            this.subscribers[eventId] = subscribers;
        }
        subscribers.push(handler);
    },

    unsubscribe: function(handler) {
        // TODO implement
    },

    publish: function(eventId, data, callback) {
        var subscribers = this.subscribers[eventId];
        if (subscribers != null) {
            subscribers.map(function(subscriber) {
                subscriber(data, callback);
            });
        }
    }

};

$bridge.addEventBus(jsEventBus);
console.log('Native bridge registered');