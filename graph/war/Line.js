(function() {

    window.Line = ColorElem.extend({

        /**
         * A Line which can be drawn on a coordinate system
         * 
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         */
        constructor : function(x1, y1, x2, y2) {
            Line.super.constructor.call(this);

            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        },

        /**
         * The x1 value
         * 
         * @type Number
         */
        x1 : undefined,

        /**
         * The y1 value
         * 
         * @type Number
         */
        y1 : undefined,
        
        /**
         * The x2 value
         * 
         * @type Number
         */
        x2 : undefined,

        /**
         * The y2 value
         * 
         * @type Number
         */
        y2 : undefined,

        /**
         * Returns the x1 value
         * 
         * @return Number
         */
        getX1 : function() {
            return this.x1;
        },

        /**
         * Returns the y1 value
         * 
         * @return Number
         */
        getY1 : function() {
            return this.y1;
        },
        
        /**
         * Returns the x2 value
         * 
         * @return Number
         */
        getX2 : function() {
            return this.x2;
        },

        /**
         * Returns the y2 value
         * 
         * @return Number
         */
        getY2 : function() {
            return this.y2;
        },

        /**
         * Draw this line into the coordinate system
         */
        draw : function() {
            if (false === this.fire("beforeDraw")) {
                return;
            }

            Line.super.draw.call(this);

            this.coordinateSystem.drawLine(this.x1, this.y1, this.x2, this.y2, this.color);

            this.fire("afterDraw");
        }

    });

})();
