(function() {
    /**
     * An abstract class for all elements which can be drawn by the CoordinateSystem
     */
    window.Elem = Observable.extend({

        /**
         * Just adds the beforeDraw and afterDraw event
         */
        constructor : function() {
            this.addEvents("beforeDraw", "afterDraw");
        },

        /**
         * The coordinate system to which this function is bounded
         * 
         * @type CoordinateSystem
         */
        coordinateSystem : null,

        /**
         * Returns the coordinate system to which this point is bound
         * 
         * @return CordinateSystem
         */
        getCoordinateSystem : function() {
            return this.coordinateSystem;
        },

        /**
         * Binds the point to a coordinate system
         * 
         * @param cs
         */
        setCoordinateSystem : function(cs) {
            if (!cs instanceof CoordinateSystem) {
                throw new Error("This is not an object of CoordinateSystem: " + cs);
            }

            if (this.coordinateSystem) {
                this.coordinateSystem.removeElement(this);
            }

            this.coordinateSystem = cs;

            if (!this.coordinateSystem.hasElement(this)) {
                this.coordinateSystem.addElement(this);
            }

            this.draw();
        },

        /**
         * Draws this element
         */
        draw : function() {
            if (!this.coordinateSystem) {
                throw new Error("No coordinate system specified");
            }
        }

    });
    
})();
