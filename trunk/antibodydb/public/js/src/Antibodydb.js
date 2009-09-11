(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif');

Ext.History.on({
    'change' : function(token) {
        var modules = 'Antibodydb.modules.',
            str = modules + (new Function(['return ', modules, token, ';'].join(''))() ? token : Antibodydb.pageNotFoundModule);
        /*
        if ( !str.render ) {
            str = Antibodydb.addModule( str );
        }
        Antibodydb.changePage( str );
        */
        new Function(['if(!', str, '.render){', str, '=Antibodydb.addModule(', str, ');}Antibodydb.changePage(', str, ');'].join(''))();
    }
});

Antibodydb = Ext.apply(new Ext.util.Observable, (function(){
    var preIconCls = 'icon-';

    var _initHistory = function() {
        var token = this.getToken() || Antibodydb.defaultModule;
        this.add(token);
        this.fireEvent('change', token);
    }

    return {
        
        init: function() {
            var me = this;
            
            me.addEvents('shutdown', 'start');
            
            Ext.EventManager.on(window, 'beforeunload', function(e){
                if(me.fireEvent('shutdown') === false) {
                    //e.browserEvent.returnValue = 'geh nicht weg!';
                    e.stopEvent();
                    return false;
                }
            });
            
            me.fireEvent('start');
            Ext.History.init(_initHistory, Ext.History);
            delete me.init;
        },

        history : Ext.copyTo({
            get : function() {
                return this.getToken();
            }
        }, Ext.History, 'getToken,add,back,forward'),

        modules : {},
        defaultModule : 'Welcome',
        pageNotFoundModule : 'PageNotFound',

        getIconCls :function(cls) {
            if(typeof cls == 'object') {
                cls = cls.iconCls ? cls.iconCls : false;
            }
            return cls ? (preIconCls + cls) : null;
        },
        
        urls : {
            Logout                   : './auth/logout',

            ChangePassword           : './user/change.password/format/json',

            TargetproteinSave        : './targetprotein/save/format/json',
            TargetproteinDelete      : './targetprotein/delete/format/json',
            TargetproteinLoad        : './targetprotein/load/format/json',
            TargetproteinSelect      : './targetprotein/select/format/json',

            IncubationprotocolSave   : './incbuationprotocol/save/format/json',
            IncubationprotocolDelete : './incubationprotocol/delete/format/json',
            IncubationprotocolLoad   : './incbuationprotocol/load/format/json',
            IncubationprotocolSelect : './incubationprotocol/select/format/json',

            AntibodySave             : './antibody/save/format/json',
            AntibodyDelete           : './antibody/delete/format/json',
            AntibodyLoad             : './antibody/load/format/json',
            AntibodySelect           : './antibody/select/format/json'
        }
        
    };
}()));

/**
 * Base Modules
 */
Antibodydb.modules.Welcome = {
    title   : 'Willkommen',
    iconCls : 'icon-home',
    html    : '<p>Ich habe die komplette Seite neu gemacht! Falls Fehler auftreten oder etwas nicht geht bitte mir eine E-Mail schreiben!</p><p>Danke <a href="mailto:b.buchholz@dkfz-heidelberg.de">Bastian Buchholz</a></p>'
};
Antibodydb.modules.PageNotFound = {
    title   : '404 - Seite nicht gefunden',
    iconCls : 'icon-error',
    html    : '<h2>Die Seite konnte nicht gefunden werden...</h2>'
};

Ext.ns('Antibodydb.modules.forms', 'Antibodydb.modules.tables', 'Antibodydb.user');

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.apply(Ext.QuickTips.getQuickTip(), {
        maxWidth : 500
    });
            
    Antibodydb.init(); // start the application
    
    Ext.fly('loading-mask').fadeOut({
         useDisplay : true
    });
});
