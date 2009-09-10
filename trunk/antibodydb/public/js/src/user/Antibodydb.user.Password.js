Antibodydb.user.Password = new Ext.Window({
    title       : 'Change Password',
    layout      : 'fit',
    modal       : true,
    closeAction : 'hide',
    resizable   : false,
    border      : false,
    iconCls     : Antibodydb.getIconCls('keygo'),
    width       : 370,
    height      : 165,
    listeners   : {
        show : function() {
            this.form.items.first().focus(false, 400);
        },
        hide : function() {
            this.form.getForm().reset();
        }
    },
    items : [
        {
            xtype        : 'form',
            ref          : 'form',
            url          : Antibodydb.urls.ChangePassword,
            frame        : true,
            monitorValid : true,
            labelWidth   : 135,
            defaultType  : 'textfield',
            defaults     : {
                allowBlank : false,
                anchor     : '100%',
                inputType  : 'password'
            },
            items : [
                {
                    fieldLabel : 'Current Password',
                    name       : 'oldpassword'
                },
                {
                    fieldLabel : 'New Password',
                    name       : 'newpassword1',
                    minLength  : 4
                },
                {
                    fieldLabel : 'Confirm New Password',
                    name       : 'newpassword2',
                    minLength  : 4
                }
            ],
            buttons : [
                {
                    formBind : true,
                    text     : 'Save',
                    handler  : function() {
                        this.ownerCt.ownerCt.send();
                    }
                },
                {
                    text     : 'Close',
                    handler  : function() {
                        this.ownerCt.ownerCt.ownerCt.hide();
                    }
                }
            ],
            handleSuccess : function(bform, action) {
            	this.ownerCt.hide();
            }
        }
    ]
});
