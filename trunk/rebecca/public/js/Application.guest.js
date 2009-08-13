Application.Login = new Ext.Window({
    title       : 'Login',
    iconCls     : 'icon-lock',
    height      : 135,
    width       : 300,
    layout      : 'fit',
    resizable   : false,
    closeAction : 'hide',
    border      : false,
    modal       : true,
    listeners : {
        show : function() {
            this.form.getForm().clearInvalid();
            this.form.username.focus(false, 400);
        },
        hide : function() {
            this.form.getForm().reset();
        }
    },
    items : {
        xtype        : 'form',
        ref          : 'form',
        url          : Application.urls.login,
        frame        : true,
        border       : false,
        monitorValid : true,
        labelWidth   : 80,
        defaultType  : 'textfield',
        defaults     : {
            allowBlank : false,
            anchor     : '100%'
        },
        items : [
            {
                ref        : 'username',
                fieldLabel : 'Benutzername',
                name       : 'Username'
            },
            {
                ref        : 'password',
                fieldLabel : 'Passwort',
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
            },
            {
                text    : 'Abbrechen',
                handler : function(btn, e) {
                    this.ownerCt.ownerCt.ownerCt.hide();
                }
            }
        ],
        send : function() { // add new function NOTE: this function is also added in the BasicForm
            if((this.username.isValid() || this.username.focus()) && !this.password.isValid()) {
                return false;
            }
            return this.getForm().submit({ // submit the form
                method  : 'POST',
                waitMsg : 'Einloggen...',
                success : this.handleSuccess,
                failure : this.handleFailure,
                scope   : this // scope of the handler functions
            });
        },
        handleFailure : function(bform, action) {
            Ext.Msg.error('Login fehlgeschlagen!', action.result ? action.result.message : 'Server Error', function() {
                bform.items.first().focus();
            });
            bform.reset();
        },
        handleSuccess : function(bform, action) {
            Ext.fly('loading-mask').fadeIn({
                callback : function() {
                    window.location.reload();
                }
            });
        }
    }
});Application.addLinks([
    {
        title : 'Rebecca',
        items : [
            {
                iconCls : 'icon-home',
                html    : 'Willkommen',
                href    : '#Welcome'
            }
        ]
    },
    {
        title : 'User',
        iconCls : 'icon-user',
        items : [
            {
                iconCls : 'icon-userkey',
                html    : 'Login',
                handler : function() {
                    Application.Login.show();
                }
            }
        ]
    }
]);