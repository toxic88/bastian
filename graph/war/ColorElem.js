(function() {
    /**
     * An abstract class for all color elements which can be drawn by the CoordinateSystem
     */
    window.ColorElem = Elem.extend({

        /**
         * The color of this element
         * 
         * @type String
         */
        color : "#F00",

        /**
         * Returns the color of this element
         * 
         * @return String
         */
        getColor : function() {
            return this.color;
        },

        /**
         * Sets the color of this element
         * 
         * @param c
         */
        setColor : function(c) {
            this.color = c;
        }

    });

})();
