Application.Login=new Ext.Window({title:"Login",iconCls:"icon-lock",height:135,width:300,layout:"fit",resizable:false,closeAction:"hide",border:false,modal:true,listeners:{show:function(){this.form.getForm().clearInvalid();this.form.username.focus(false,400)},hide:function(){this.form.getForm().reset()}},items:{xtype:"form",ref:"form",url:Application.urls.login,frame:true,border:false,monitorValid:true,labelWidth:80,defaultType:"textfield",defaults:{allowBlank:false,anchor:"100%"},items:[{ref:"username",fieldLabel:"Benutzername",name:"Username"},{ref:"password",fieldLabel:"Passwort",inputType:"password",name:"Password",listeners:{specialkey:function(b,a){if(a.getKey()==a.ENTER){this.ownerCt.send()}}}}],buttons:[{text:"Login",formBind:true,handler:function(a,b){this.ownerCt.ownerCt.send()}},{text:"Abbrechen",handler:function(a,b){this.ownerCt.ownerCt.ownerCt.hide()}}],send:function(){if((this.username.isValid()||this.username.focus())&&!this.password.isValid()){return false}return this.getForm().submit({method:"POST",waitMsg:"Einloggen...",success:this.handleSuccess,failure:this.handleFailure,scope:this})},handleFailure:function(a,b){Ext.Msg.error("Login fehlgeschlagen!",b.result?b.result.message:"Server Error",function(){a.items.first().focus()});a.reset()},handleSuccess:function(a,b){Ext.fly("loading-mask").fadeIn({callback:function(){window.location.reload()}})}}});Application.addLinks([{title:"Rebecca",items:[{iconCls:"icon-home",html:"Willkommen",href:"#Welcome"}]},{title:"User",iconCls:"icon-user",items:[{iconCls:"icon-userkey",html:"Login",handler:function(){Application.Login.show()}}]}]);