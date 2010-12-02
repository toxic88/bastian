/**
 * Removes an element from an array
 * 
 * @param x
 * @return Array
 */
if (!Array.prototype.remove) {
    Array.prototype.remove = function(x) {
        var i = this.indexOf(x);
        if (i != -1){
            this.splice(i, 1);
            return this.remove(x);
        }
        return this;
    };
}

/**
 * Extends a class with an JSON object
 * 
 * @param sp
 * @param o
 * @return Object
 */
if (!Object.extend) {
    Object.extend = function() {
        var oc = Object.prototype.constructor;

        return function(sp, o) {
            if (o == undefined) {
                o = sp;
                sp = Object;
            }

            var sb = o.constructor != oc ? o.constructor : function() { sp.apply(this, arguments); },
                F = function() {},
                sbp,
                spp = sp.prototype;

            F.prototype = spp;
            sbp = sb.prototype = new F();
            sbp.constructor = sb;

            if (spp.constructor == oc){
                spp.constructor = sp;
            }

            sbp.super = sb.super = spp;

            for (var i in o) {
                sbp[i] = o[i];
            }

            sb.extend = function(o){
                return Object.extend(sb, o);
            };

            return sb;
        };
    }();
}
