(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif');

Antibodydb = Ext.apply(new Ext.util.Observable, (function(){
    var preIconCls = 'icon-';

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
        },
        
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

Ext.ns('Antibodydb.forms', 'Antibodydb.tables', 'Antibodydb.user');

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
