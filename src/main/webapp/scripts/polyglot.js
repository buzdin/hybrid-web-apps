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
        var results = [];

        if (subscribers != null) {
            subscribers.map(function(subscriber) {
                subscriber(data, function(response) {
                    console.log(response);
                    results.push(response);
                });
            });
        }

        // Returning array of subscriber results
        if (typeof(callback) == "function") {
            callback({results: results});
        }
    }

};

$bridge.registerEventBus(jsEventBus);
console.log('Native bridge registered');