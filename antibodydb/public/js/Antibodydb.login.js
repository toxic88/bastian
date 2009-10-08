(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif');

Ext.MessageBox.error = Ext.Msg.error = function(title, msg, fn, scope) {
    this.show({
        title   : title,
        msg     : msg,
        buttons : this.OK,
        icon    : this.ERROR,
        fn      : fn,
        scope   : scope
    });
    return this;
}.createDelegate(Ext.Msg);

Antibodydb = {
    getIconCls : function() {
        var preIconCls = 'icon-';
        return function(cls) {
            if(typeof cls == 'object') {
                cls = cls.iconCls ? cls.iconCls : false;
            }
            return cls ? (preIconCls + cls) : null;
        }
    }(),
    urls : {
        login : './auth/login/format/json',
        index : './'
    }
};

Antibodydb.LoginWindow = new Ext.Window({
    title     : 'Antibodydb - Login',
    iconCls   : Antibodydb.getIconCls('lock'),
    height    : 135,
    width     : 300,
    layout    : 'fit',
    resizable : false,
    draggable : false,
    closable  : false,
    border    : false,
    items     : {
        xtype        : 'form',
        ref          : 'form',
        url          : Antibodydb.urls.login,
        frame        : true,
        border       : false,
        monitorValid : true,
        labelWidth   : 60,
        defaultType  : 'textfield',
        defaults     : {
            allowBlank : false,
            anchor     : '100%'
        },
        items : [
            {
                ref        : 'username',
                fieldLabel : 'Username',
                name       : 'Username',
                listeners  : {
                    specialkey : function(field, e) {
                        if(e.getKey() == e.ENTER) {
                            this.ownerCt.password.focus();
                        }
                    },
                    render : function(field, e) {
                        this.focus(false, 400);
                    }
                }
            },
            {
                ref        : 'password',
                fieldLabel : 'Password',
                inputType  : 'password',
                name       : 'Password',
                listeners  : {
                    specialkey : function(field, e) {
                        if(e.getKey() == e.ENTER) {
                            this.ownerCt.send();
                        }
                    }
                }
            }
        ],
        buttons : [
            {
                text     : 'Login',
                formBind : true, // only enabled if the fields have values
                handler  : function(btn, e) {
                    this.ownerCt.ownerCt.send();
                }
            }
        ],
        send : function() { // add new function NOTE: this function is also added in the BasicForm
            if((this.username.isValid() || this.username.focus()) && !this.password.isValid()) {
                return false;
            }
            return this.getForm().submit({ // submit the form
                method    : 'POST',
                waitMsg   : 'Sending data...',
                success   : this.handleSuccess,
                failure   : this.handleFailure,
                scope     : this // scope of the handler functions
            });
        },
        handleFailure : function(bform, action) {
            Ext.Msg.error('Action Failed!', action.result ? action.result.error : 'Server Error', function() {
                bform.items.first().focus();
            });
            bform.reset();
        },
        handleSuccess : function(bform, action) {
            Ext.fly('loading-mask').fadeIn({
                callback : function() {
                    window.location = Antibodydb.urls.index;
                }
            });
        }
    }
});

Ext.onReady(function() {
    Ext.QuickTips.init();

    Antibodydb.LoginWindow.show();
    
    Ext.fly('loading-mask').fadeOut({
         useDisplay : true
    });
});
