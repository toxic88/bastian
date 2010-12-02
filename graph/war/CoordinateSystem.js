(function() {
    window.CoordinateSystem = Observable.extend({

        /**
         * Draws a coordinate system and functions
         * 
         * @param el
         */
        constructor : function(el) {
            if (!el.getContext) {
                throw new Error("Your browser does not support the canvas element");
            }
    
            this.el = el; // the canvas element
    
            this.ctx = this.el.getContext("2d");
    
            this.setZoom(1); // default zoom level
            this.setOffsetX(0); // default offset x
            this.setOffsetY(0); // default offset y
            
            this.initEvents(); // init event handlers

            this.addEvents("beforeDraw",
                           "afterDraw",
                           "beforeAddElement",
                           "afterAddElement",
                           "beforeRemoveElement",
                           "afterRemoveElement",
                           "beforeZoom",
                           "afterZoom",
                           "beforeMove",
                           "afterMove"
                          );
            
            this.drawCoordinateSystem();
        },

        /**
         * The canvas element
         * 
         * @type CanvasElement
         */
        el : null,

        /**
         * The 2d context of the canvas element
         * 
         * @type CanvasRenderingContext2
         */
        ctx : null,

        /**
         * Elements to draw
         * 
         * @type Array
         */
        elements : [],

        /**
         * The zoom size
         * 
         * @type Number
         */
        zoom : 1,

        /**
         * The distance between the steps in pixel
         * 
         * @type Number
         */
        stepSizeSpacing : 50,

        /**
         * The maximum x-value wich can be displayed
         * 
         * @type Number
         */
        maxX : undefined,

        /**
         * The minimum x-value wich can be displayed
         * 
         * @type Number
         */
        minX : undefined,

        /**
         * The maximum y-value wich can be displayed
         * 
         * @type Number
         */
        maxY : undefined,

        /**
         * The minimum y-value wich can be displayed
         * 
         * @type Number
         */
        minY : undefined,

        /**
         * x shift
         * 
         * @type Number
         */
        offsetX : 0,

        /**
         * y shift
         * 
         * @type Number
         */
        offsetY : 0,

        /**
         * The width of the virtual coordinate system
         * 
         * @type Number
         */
        width : undefined,

        /**
         * The width of the virtual coordinate system
         * 
         * @type Number
         */
        height : undefined,
        
        /**
         * The speed you drag
         * - 1 means normal
         * - 2 means twice as fast
         * 
         * @type Number
         */
        dragSpeed : 1,

        /**
         * How fast you zoom in with your mousewheel
         * 
         * @type Number
         */
        zoomFactor : 2,

        /**
         * Returns the current functions
         * 
         * @return Array
         */
        getElements : function() {
            return this.elements;
        },

        /**
         * Checks if this coordinate system has an element and returns it
         * 
         * @param e
         * @return Object
         */
        hasElement : function(e) {
            if ((e = this.elements.indexOf(e)) != -1) {
                return this.elements[e];
            }
            return null;
        },

        /**
         * Adds an element to the stack wich will be drawn automatically
         * 
         * @param e
         */
        addElement : function(e) {
            if (false === this.fire("beforeAddElement", e)) {
                return;
            }
            
            if (!e instanceof Elem) {
                throw new Error("This is not an element: " + e);
            }

            this.elements.push(e);

            if (e.getCoordinateSystem() != this) {
                e.setCoordinateSystem(this);
            }
            
            this.fire("afterAddElement", e);
        },

        /**
         * Adds an array of elements to the stack wich will be drawn automatically
         * 
         * @param es
         */
        addElements : function(es) {
            if (!es.length || !(es.length && typeof es == "object")) {
                es = Array.prototype.slice.call(arguments, 0);
            }

            es.forEach(function(e) {
                this.addElement(e);
            }, this);
        },
        
        /**
         * Removes an element from the stack
         * 
         * @param e
         */
        removeElement : function(e) {
            this.removeElements(e);
        },
        
        /**
         * Removes elements from the stack
         * 
         * @param es
         */
        removeElements : function(es) {
            if (arguments.length > 1 || !(es.length && typeof es == "object")) {
                es = Array.prototype.slice.call(arguments, 0);
            } else if (arguments.length == 0) {
                es = [];
                for (var e in this.elements) {
                    es.push(this.elements[e]);
                }
            }

            for (var i = 0; i < es.length; i++) {
                if (!this.hasElement(es[i]) || false === this.fire("beforeRemoveElement", es[i])) {
                    continue;
                }

                this.elements.remove(es[i]);

                this.fire("afterRemoveElement", es[i]);
            }

            this.draw(); // redraw all
        },

        /**
         * Removes all elements from the stack
         */
        clearElements : function() {
            this.elements = [];
        },

        /**
         * Returns the zoom level
         * 
         * @return Number
         */
        getZoom : function() {
            return this.zoom;
        },

        /**
         * Set the zoom level
         * 
         * @param z
         */
        setZoom : function(z) {
            this.zoom = z;

            this.width = this.el.width / this.zoom;
            this.height = this.el.height / this.zoom;

            this.calcMinMaxXY();
        },

        /**
         * Returns the current stepsize
         */
        getStepSize : function() {
            return this.stepSizeSpacing / this.getZoom();
        },
        
        /**
         * Set the step size; this is easier to understand than zoom
         * 
         * @example 4: 4, 8, 12, ...
         * @param s
         */
        setStepSize : function(s) {
            this.setZoom(this.stepSizeSpacing / s);
        },

        /**
         * Returns the step size spacing
         * 
         * @return Number
         */
        getStepSizeSpacing : function() {
            return this.stepSizeSpacing;
        },

        /**
         * Set the step size spacing in pixel
         * 
         * @example 50
         * @param sss
         */
        setStepSizeSpacing : function(sss) {
            var old = this.stepSizeSpacing;

            this.stepSizeSpacing = sss;

            this.setStepSize(old * this.zoom);
            
            this.draw();
        },

        /**
         * Returns the highest x value wich is currently visible
         * 
         * @type Number
         */
        getMaxX : function() {
            return this.maxX;
        },

        /**
         * Returns the lowest x value wich is currently visible
         * 
         * @type Number
         */
        getMinX : function() {
            return this.minX;
        },

        /**
         * Returns the highest y value wich is currently visible
         * 
         * @type Number
         */
        getMaxY : function() {
            return this.maxY;
        },

        /**
         * Returns the lowest y value wich is currently visible
         * 
         * @type Number
         */
        getMinY : function() {
            return this.minY;
        },

        /**
         * Calculates the min x, max x, min y, max y values
         */
        calcMinMaxXY : function() {
            this.maxX = this.width / 2 - this.offsetX / this.zoom;
            this.minX = -(this.width / 2 + this.offsetX / this.zoom);

            this.maxY = this.height / 2 + this.offsetY / this.zoom;
            this.minY = -(this.height / 2 - this.offsetY / this.zoom);
        },

        /**
         * Returns the offsetX
         * 
         * @return Number
         */
        getOffsetX : function() {
            return this.offsetX * this.zoom;
        },

        /**
         * Sets the offset for x
         * 
         * @param x
         */
        setOffsetX : function(x) {
            this.offsetX = -x / this.zoom;

            this.calcMinMaxXY();
        },

        /**
         * Returns the offsetY
         * 
         * @return Number
         */
        getOffsetY : function() {
            return this.offsetY * this.zoom;
        },

        /**
         * Sets the offset for y
         * 
         * @param y
         */
        setOffsetY : function(y) {
            this.offsetY = y / this.zoom;

            this.calcMinMaxXY();
        },

        /**
         * Gets the drag speed
         * 
         * @return Number
         */
        getDragSpeed : function() {
            return this.dragSpeed;
        },

        /**
         * Sets the drag speed
         * 
         * @param s
         */
        setDragSpeed : function(s) {
            this.dragSpeed = s;
        },

        /**
         * Gets the zoom factor
         * 
         * @return Number
         */
        getZoomFactor : function() {
            return this.zoomFactor;
        },

        /**
         * Sets the zoom factor
         * 
         * @param z
         */
        setZoomFactor : function(z) {
            this.zoomFactor = z;
        },

        /**
         * Draws the x axis with numbers and strokes
         */
        drawXAxis : function() {
            if (Math.abs(this.offsetY) > this.el.height / 2) {
                return;
            }
            
            this.ctx.moveTo(0, this.el.height / 2 + this.offsetY);
            this.ctx.lineTo(this.el.width, this.el.height / 2 + this.offsetY);

            for (var x = this.minX, tw; x < this.maxX + 1; x = x + this.stepSizeSpacing / this.zoom) {
                if (x == 0) {
                    continue;
                }

                tw = this.ctx.measureText(CoordinateSystem.round(x)); // textwidth to center the numbers

                this.ctx.fillText(CoordinateSystem.round(x), this.el.width / 2 + x * this.zoom - tw.width / 2 + this.offsetX, this.el.height / 2 + this.offsetY + 12);

                this.ctx.moveTo(this.el.width / 2 + x * this.zoom + this.offsetX, this.el.height / 2 + this.offsetY); // little strokes right
                this.ctx.lineTo(this.el.width / 2 + x * this.zoom + this.offsetX, this.el.height / 2 + this.offsetY + 3);
            }
        },

        /**
         * Draws the y axis with numbers and strokes
         */
        drawYAxis : function() {
            if (Math.abs(this.offsetX) > this.el.width / 2) {
                return;
            }
            
            this.ctx.moveTo(this.el.width / 2 + this.offsetX, 0);
            this.ctx.lineTo(this.el.width / 2 + this.offsetX, this.el.height);

            for (var y = this.minY; y < this.maxY + 1; y = y + this.stepSizeSpacing / this.zoom) {
                if (y == 0) {
                    continue;
                }

                this.ctx.fillText(CoordinateSystem.round(y), this.el.width / 2 + this.offsetX + 5, this.el.height / 2 - y * this.zoom + this.offsetY + 3); // +3 because the textheight can not be calculated

                this.ctx.moveTo(this.el.width / 2 + this.offsetX, this.el.height / 2 - y * this.zoom + this.offsetY); // little strokes top
                this.ctx.lineTo(this.el.width / 2 + this.offsetX + 3, this.el.height / 2 - y * this.zoom + this.offsetY);
            }
        },

        /**
         * Calls drawXAxis() and drawYAxis() and draws the 0 to the coordinate
         * system
         */
        drawCoordinateSystem : function() {
            this.startDraw();

            this.drawXAxis();
            this.drawYAxis();

            this.ctx.fillText("0", this.el.width / 2 + 5 + this.offsetX, this.el.height / 2 + 10 + this.offsetY); // draw the 0

            this.endDraw();
        },

        /**
         * Loops through all elements and calls the draw() method
         */
        drawElements : function() {
            this.elements.forEach(function(e) {
                e.draw();
            });
        },

        /**
         * Draws a line from (x1,y1) to (x2,y2)
         * 
         * @param x1
         * @param y2
         * @param x2
         * @param y2
         * @param c
         */
        drawLine : function(x1, y1, x2, y2, c) {
            this.startDraw(c);

            this.ctx.moveTo(this.el.width / 2 + x1 * this.zoom + this.offsetX, this.el.height / 2 - y1 * this.zoom + this.offsetY);
            this.ctx.lineTo(this.el.width / 2 + x2 * this.zoom + this.offsetX, this.el.height / 2 - y2 * this.zoom + this.offsetY);

            this.endDraw();
        },

        /**
         * Draws a dot at a specific position
         * 
         * @param x
         * @param y
         * @param c
         */
        drawDot : function(x, y, c) {
            this.startDraw(c);

            this.ctx.arc(this.el.width / 2 + x * this.zoom + this.offsetX, this.el.height / 2 - y * this.zoom + this.offsetY, 3, 0, Math.PI * 2, true);

            this.ctx.fillText("P( " + x + " / " + y + " )", this.el.width / 2 + x * this.zoom + this.offsetX + 5, this.el.height / 2 - y * this.zoom + this.offsetY + 10);

            this.endDraw();
        },

        /**
         * Draws a cross at a specific position
         * 
         * @param x
         * @param y
         * @param c
         */
        drawCross : function(x, y, c) {
            this.drawLine(x - 5, y - 5, x + 5, y + 5, c); // 10 pixel long lines
            this.drawLine(x - 5, y + 5, x + 5, y - 5, c); // 10 pixel long lines

            this.startDraw(c);

            this.ctx.fillText("P(" + x + "/" + y + ")", this.el.width / 2 + x * this.zoom + this.offsetX + 5, this.el.height / 2 - y * this.zoom + this.offsetY + 10);

            this.endDraw();
        },

        /**
         * Prepare to draw anything with a custom color
         * 
         * @param c
         */
        startDraw : function(c) {
            this.ctx.save();
            this.ctx.strokeStyle = this.ctx.fillStyle = c || "#000";
            this.ctx.beginPath();
        },

        /**
         * Draws lines arcs and so on since startDraw was called
         */
        endDraw : function() {
            this.ctx.closePath();
            this.ctx.stroke();
            this.ctx.fill();
            this.ctx.restore();
        },

        /**
         * Clears the hole canvas and redraws it
         */
        draw : function() {
            if (false === this.fire("beforeDraw")) {
                return;
            }
            
            this.clear();

            this.drawCoordinateSystem();

            this.drawElements();
            
            this.fire("afterDraw");
        },

        /**
         * Clear the hole canvas
         */
        clear : function() {
            this.ctx.clearRect(0, 0, this.el.width, this.el.height);
        },

        /**
         * Setting the event handlers for the coordinatesystem
         * - zoom in and out with the mousewheel
         * - drag to change the visible area
         */
        initEvents : function() {
            var self = this;

            if (!self.el.dataset) { // for browser wich are not supporting dataset (firefox)
                self.el.dataset = {};
            }

            // drag and drop to move around
            self.el.addEventListener("mousedown", function(e) {
                self.el.dataset["ondrag"] = true;
                self.el.dataset["offsetx"] = e.pageX - self.el.offsetLeft;
                self.el.dataset["offsety"] = e.pageY - self.el.offsetTop;
            }, false);

            self.el.addEventListener("mouseup", function(e) {
                delete self.el.dataset["ondrag"];
            }, false);

            self.el.addEventListener("mousemove", function(e) {
                if (self.el.dataset["ondrag"]) {
                    var deltaX  = self.el.dataset["offsetx"] - (e.pageX - self.el.offsetLeft),
                        deltaY  = self.el.dataset["offsety"] - (e.pageY - self.el.offsetTop),
                        minGrid = self.getStepSizeSpacing() / self.getDragSpeed();

                    if (deltaX >= minGrid) {
                        self.el.dataset["offsetx"] = e.pageX - self.el.offsetLeft;
                        self.move(CoordinateSystem.Directions.RIGHT);
                    } else if (deltaX <= -minGrid) {
                        self.el.dataset["offsetx"] = e.pageX - self.el.offsetLeft;
                        self.move(CoordinateSystem.Directions.LEFT);
                    }

                    if (deltaY >= minGrid) {
                        self.el.dataset["offsety"] = e.pageY - self.el.offsetTop;
                        self.move(CoordinateSystem.Directions.DOWN);
                    } else if (deltaY <= -minGrid) {
                        self.el.dataset["offsety"] = e.pageY - self.el.offsetTop;
                        self.move(CoordinateSystem.Directions.UP);
                    }
                }
            }, false);
            
            // zoom in with mousewheel
            // firefox: DOMMouseScroll and e.detail
            self.el.addEventListener(window.WheelEvent || window.opera ? "mousewheel" : "DOMMouseScroll", function(e) {
                e.preventDefault();

                if (false === self.fire("beforeZoom", e)) {
                    return;
                }

                if ((e.wheelDelta || -e.detail) > 0) { // e.detail for firefox
                    self.setZoom(self.getZoom() * self.getZoomFactor());
                } else {
                    self.setZoom(self.getZoom() / self.getZoomFactor());
                }

                self.fire("afterZoom", e);

                self.draw();
            }, false);
            
//            if (window.Point) { // dependency
//                self.el.addEventListener("mouseup", function(e) {
//                    if (self.el.lastPoint) {
//                        self.removeElement(self.el.lastPoint);
//                    }
//    
//                    var x = ((e.pageX - self.el.offsetLeft) - (self.el.width / 2 + self.offsetX)) / self.zoom,
//                        y = ((self.el.height / 2 + self.offsetY) - (e.pageY - self.el.offsetTop)) / self.zoom,
//                        p = self.el.lastPoint = new Point(x, y);
//    
//                    self.addElement(p);
//                }, false);
//            }
            
            if (window.Line && window.Point) { // dependency
                self.el.addEventListener("mousemove", function(e) {
                    self.removeElements(self.el.lastLineX, self.el.lastLineY, self.el.lastPoint);
                    delete self.el.lastLineX;
                    delete self.el.lastLineY;
                    delete self.el.lastPoint;
    
                    var x  = ((e.pageX - self.el.offsetLeft) - (self.el.width / 2 + self.offsetX)) / self.zoom,
                        y  = ((self.el.height / 2 + self.offsetY) - (e.pageY - self.el.offsetTop)) / self.zoom,
                        lx = self.el.lastLineX = new Line(x, y, x, 0),
                        ly = self.el.lastLineY = new Line(x, y, 0, y),
                        p  = self.el.lastPoint = new Point(x, y);

                    self.addElements(lx, ly, p);
                }, false);
            }
        },

        /**
         * Moves the coordinate system in a specific direction
         * 
         * @param d
         */
        move : function(d) {
            d = d.toLowerCase();

            if (false === this.fire("beforeMove", d)) {
                return;
            }

            var spacingAndZoom = this.getStepSizeSpacing() * this.getZoom();

            switch(d) {
                case CoordinateSystem.Directions.UP:
                    this.setOffsetY(this.getOffsetY() + spacingAndZoom);
                    break;
                case CoordinateSystem.Directions.DOWN:
                    this.setOffsetY(this.getOffsetY() - spacingAndZoom);
                    break;
                case CoordinateSystem.Directions.RIGHT:
                    this.setOffsetX(-this.getOffsetX() + spacingAndZoom);
                    break;
                case CoordinateSystem.Directions.LEFT:
                    this.setOffsetX(-this.getOffsetX() - spacingAndZoom);
                    break;
                default:
                    throw new Error(d + " is not a type of CoordinateSystem.Directions");
                    break;
            }

            this.fire("afterMove", d);

            this.draw();
        }

    });

    /**
     * Returns the number x with 3 decimal places
     * 
     * @param x
     * @return Number
     */
    CoordinateSystem.round = function(x) {
        return Math.round(x * 1000) / 1000;
    };

    /**
     * Constants in wich direction the coordinatesystem should be moved
     * 
     * @see CoordinateSystem.move()
     */
    CoordinateSystem.Directions = {
        UP    : "up",
        DOWN  : "down",
        RIGHT : "right",
        LEFT  : "left"
    };

})();
