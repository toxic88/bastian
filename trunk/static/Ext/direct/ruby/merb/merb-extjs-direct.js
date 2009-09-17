/**
 * @overview merb-ext-direct.js
 * A couple of minor overrides to the Ext.Direct package which simply redefines HTTP params to be
 * Merb/Rails friendly.  By default, Ext.Direct uses the words "action" and "method", where with Merb/Rails,
 * we prefer "controller" and "action".
 * This file should be included immediately after ext-all.js
 */

/**
 * Ext.Direct.Transaction
 */
Ext.override(Ext.Direct.Transaction, {
    /**
     * toHash
     * compile the Transaction object into a hash.  re-arrange the action/method params to controller/action to
     * be compatible with standard routing terminology.  The Ext3 framework should be changed IMHO, since the words
     * "action" and "method" are both verb-like whereas controller/action is more sentence-like (ie: noun/verb)
     * @return {Object}
     * @author Chris Scott
     */
    toHash : function() {
        return {
            tid: this.tid,
            xcontroller: this.action,
            xaction: this.method,
            data: this.data
        }
    }
});

/**
 * Ext.direct.RemotingProvider
 */
Ext.override(Ext.direct.RemotingProvider, {
    /**
     * getCallData
     * get data from Transaction#getHash instead of composing by hand.
     * @param {Object} t
     * @author Chris Scott
     */
    getCallData: function(t){
        var req =  t.toHash();
        req.type = 'rpc';
        return req;
    }
});