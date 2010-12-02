(function() {

    window.Point = {};

    /**
     * The drawing types of a point
     */
    window.Point.types = {
        "DOT"   : "Dot",
        "CROSS" : "Cross"
    };

    window.Point = ColorElem.extend({

        /**
         * A point wich can be drawn on a coordinate system with different types
         * 
         * @param x
         * @param y
         */
        constructor : function(x, y) {
            Point.super.constructor.call(this);

            this.x = x;
            this.y = y;
        },

        /**
         * The x value
         * 
         * @type Number
         */
        x : undefined,

        /**
         * The y value
         * 
         * @type Number
         */
        y : undefined,

        /**
         * The drawing type of this point
         * 
         * @type Point.types
         */
        type : Point.types.DOT,

        /**
         * Returns the x value
         * 
         * @return Number
         */
        getX : function() {
            return this.x;
        },

        /**
         * Returns the y value
         * 
         * @return Number
         */
        getY : function() {
            return this.y;
        },

        /**
         * Returns the drawing type
         * 
         * @return Point.types
         */
        getType : function() {
            return Point.types[this.type];
        },

        /**
         * Sets the drawing type
         * 
         * @param t
         */
        setType : function(t) {
            t = t.toUpperCase();

            if (!Point.types[t]) {
                throw new Error("There is no type: " + t);
            }

            this.type = t;
        },

        /**
         * Draw this point into the coordinate system
         */
        draw : function() {
            if (false === this.fire("beforeDraw")) {
                return;
            }

            Point.super.draw.call(this);

            this.coordinateSystem["draw" + this.type](this.x, this.y, this.color);

            this.fire("afterDraw");
        }

    });

    /**
     * The drawing types of a point
     */
    window.Point.types = {
        "DOT"   : "Dot",
        "CROSS" : "Cross"
    };

})();
