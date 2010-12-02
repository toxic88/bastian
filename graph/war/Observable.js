(function() {
    window.Observable = Object.extend({

        /**
         * All listeners and types are registrated here
         * 
         * @example this.listeners["mouseOver"] = [ function() {}, function() {} ]
         * @type Object
         */
        listeners : {},

        /**
         * Fire all listeners for the event t with optional data d
         * returns false if one function returns false
         * 
         * @param t
         * @param d
         * @return boolean
         */
        fire : function(t, d) {
            var ret = true,
                r;
            this.listeners[t] && this.listeners[t].forEach(function(f) {
                    r = f.apply(this, [t, d].remove(undefined));
                    ret = ret && (r || r == undefined);
                }, this);

            return ret;
        },
        
        /**
         * Register a listener f for the event t
         * 
         * @param t
         * @param f
         */
        on : function(t, f) {
            this.listeners[t] && this.listeners[t].push(f);
        },

        /**
         * Unregister a listener f for the event t
         * 
         * @param t
         * @param f
         */
        un : function(t, f) {
            this.listeners[t] && this.listeners[t].remove(f);
        },

        /**
         * Add custom events to the object
         * 
         * @param t
         */
        addEvents : function(t) {
            if (arguments.length > 1 || !(t.length && typeof t == "object")) {
                t = Array.prototype.slice.call(arguments, 0);
            }

            t.forEach(function(e) {
                !this.listeners[e] && (this.listeners[e] = []);
            }, this);
        },

        /**
         * Remove custom events from the object. All listeners for this event were deleted!
         * 
         * @param t
         */
        removeEvents : function(t) {
            if (arguments.length > 1 || !(t.length && typeof t == "object")) {
                t = Array.prototype.slice.call(arguments, 0);
            } else if (arguments.length == 0) { // remove all
                t = [];
                for (var e in this.listeners) {
                    t.push(e);
                }
            }

            t.forEach(function(e) {
                this.listeners[e] && delete this.listeners[e];
            }, this);
        }

    });
    
    window.Observable.prototype.addListener = window.Observable.prototype.on;
    window.Observable.prototype.removeListener = window.Observable.prototype.un;

})();
