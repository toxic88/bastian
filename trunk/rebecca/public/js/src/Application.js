(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif'); /* only need this for IE6 / IE7 */

Ext.state.Manager.setProvider(
    new Ext.state.CookieProvider(/*{
        expires : new Date( new Date().getTime() + (1000 * 60 * 60 * 24 * 7) ) // 7 Days is default
    }*/)
);

Ext.History.on({
    'change' : function(token) {
        var str = 'Application.modules.' + (Application.modules[token] ? token : Application.pageNotFoundModule);
        /*
        if ( !str.render ) {
            str = Application.addModule( str );
        }
        Application.changePage( str );
        */
        new Function('if(!' + str + '.render){' + str + '=Application.addModule(' + str + ');}Application.changePage(' + str + ');')();
    }
});

Application = Ext.apply(new Ext.util.Observable, (function(){
    var _initHistory = function() {
        var token = this.getToken() || Application.defaultModule;
        this.add(token);
        this.fireEvent('change', token);
    }

    return {
        init : function() {
            var me = this;
            me.addEvents('shutdown', 'start');
            
            Ext.EventManager.on(window, 'beforeunload', function(e){
                if(me.fireEvent('shutdown') === false) {
                    /* e.browserEvent.returnValue = 'geh nicht weg!'; */
                    e.stopEvent();
                    return false;
                }
            });
            
            me.fireEvent('start');
            
            Ext.History.init(_initHistory, Ext.History);
            delete me.init;
        },
        
        cookie  : Ext.copyTo({}, Ext.state.Manager, 'get,set,clear'),
        history : Ext.copyTo({
            get : function() {
                return this.getToken();
            }
        }, Ext.History, 'getToken,add,back,forward'),

        modules : {}, /* Namespace for all modules */
        defaultModule : 'Welcome',
        pageNotFoundModule : 'PageNotFound',

        urls    : {
            logout : './auth/logout/format/json',
            login  : './auth/login/format/json'
        }
    };
}()));

/**
 * Base Modules
 */
Application.modules.Welcome = {
    title   : 'Willkommen',
    iconCls : 'icon-home',
    html    : '<p>Was soll hier nur stehen?</p>'
};
Application.modules.PageNotFound = {
    title   : '404 - Seite nicht gefunden',
    iconCls : 'icon-error',
    html    : '<h2>Die Seite konnte nicht gefunden werden...</h2>'
};


Ext.onReady(function() {
    Ext.QuickTips.init();
	
    Ext.apply(Ext.QuickTips.getQuickTip(), {
        maxWidth : 500
    });
    
    Application.init();
    
    Ext.fly('loading-mask').fadeOut({
         useDisplay : true
    });
});
