(function() {
    window.Func = ColorElem.extend({

        /**
         * Represents a function
         * 
         * @param fe
         */
        constructor : function(fe) {
            Func.super.constructor.call(this);

            this.setFunctionalEquation(fe);
        },

        /**
         * The function itself as a string
         * 
         * @type String
         */
        functionalEquation : undefined,

        /**
         * The x intersections
         * 
         * @type Array
         */
        xIntersections : [],

        /**
         * The y intersection
         * 
         * @type Number
         */
        yIntersection : Infinity,

        /**
         * The slope of the linear function
         * 
         * @type Number
         */
        slope : null,

        /**
         * The slope angle of the linear function
         * 
         * @type Number
         */
        slopeAngle : null,

        /**
         * Returns the functional equation
         * 
         * @return String
         */
        getFunctionalEquation : function() {
            return this.functionalEquation;
        },

        /**
         * Sets the functional equation
         * 
         * @param fe
         */
        setFunctionalEquation : function(fe) {
            r.forEach(function(e) {
                fe = fe.replace(e[0], e[1]);
            });

            this.functionalEquation = fe;

            try {
                this.yIntersection = this.calc(0); // calculate the y intersection

                if ((fe = this.calc(2) - this.calc(1)) == this.calc(1) - this.calc(0)) { // check if linear function
                    this.slope = fe;
                    this.slopeAngle = Math.atan(this.slope) / Math.PI / 2 * 360;
                    if (this.slopeAngle < 0) {
                        this.slopeAngle += 180;
                    }
                }
            } catch (e) {
                throw new Error("The function " + this.functionalEquation + " has not the right format!");
            }
        },

        /**
         * Returns the x intersections from this function
         * 
         * @return Array
         */
        getXIntersections : function() {
            return this.xIntersections;
        },

        /**
         * Adds a x intersection of this function
         * 
         * @param x
         */
        addXIntersection : function(x) {
            this.xIntersections.push(x);
        },

        /**
         * Clears all x intersections from previous draws
         */
        clearXIntersections : function() {
            this.xIntersections = [];
        },

        /**
         * Returns the y intersection from this function
         * 
         * @return Number
         */
        getYIntersection : function() {
            return this.yIntersection;
        },

        /**
         * Returns the slope of a linear function
         * 
         * @return Number
         */
        getSlope : function() {
            return this.slope;
        },

        /**
         * Returns the slope angle of a linear function
         * 
         * @return Number
         */
        getSlopeAngle : function() {
            return this.slopeAngle;
        },

        /**
         * Evals this function for a specific x value
         * 
         * @param x
         * @return Number
         */
        calc : function(x) {
            return (new Function("return " + this.functionalEquation.replace(rx, "(" + x + ")")))(); // brakets because of -- values
        },

        /**
         * Draw this function into the coordinate system
         */
        draw : function() {
            if (false === this.fire("beforeDraw")) {
                return;
            }
            
            Func.super.draw.call(this);

            var x2, y2;

            this.clearXIntersections();

            for (var x1 = this.coordinateSystem.getMinX(); x1 < this.coordinateSystem.getMaxX() + 1; x1 = x1 + 1 / this.coordinateSystem.getZoom()) {
                var y1 = this.calc(x1);
                if (!isNaN(x1 + y1 + x2 + y2)) {
                    this.coordinateSystem.drawLine(x2, y2, x1, y1, this.color);

                    if (y1 == 0 || (y2 > 0 && y1 <= 0) || (y2 < 0 && y1 >= 0)) {
                        this.addXIntersection(x1 + -y1); // adding a x intersection
                    }
                }
                x2 = x1;
                y2 = y1;
            }

            this.fire("afterDraw");
        }

    });

    /**
     * Regular expressions
     */
    var rx = /x/gi,
        r = [
             [ /(\d|pi|e)x/gi, "$1*x"],
             [ /([\d|x|pi|e]+|\(.+\))\^([\dx]+|\(.+\))/gi, "Math.pow($1,$2)"],

             // [ /acos\((.+)\)/gi, "Math.acos($1)" ],
             // [ /asin\((.+)\)/gi, "Math.asin($1)" ],
             // [ /atan\((.+)\)/gi, "Math.atan($1)" ],
             [ /sin\((.+)\)/gi, "Math.sin($1)" ],
             [ /cos\((.+)\)/gi, "Math.cos($1)" ],
             [ /tan\((.+)\)/gi, "Math.tan($1)" ],
             [ /log\((.+)\)/gi, "Math.log($1)" ],
             [ /sqrt\((.+)\)/gi, "Math.sqrt($1)" ],

             [ /pi/gi, "Math.PI" ],
             [ /e/gi, "Math.E" ]
        ];

})();
