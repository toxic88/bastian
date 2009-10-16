(function(){var a=[["7:50 - 8:35","Mathe","Spanisch","BWL","Deutsch","Deutsch",1],["8:35 - 9:20","Mathe","Spanisch","BWL","Deutsch","Deutsch",2],["9:25 - 10:10","Info","Religion","Chemie","VWL","BWL",3],["10:30 - 11:15","Info","Religion","Chemie","VWL","BWL",4],["11:20 - 12:05","GGK","Englisch","Mathe","Spanisch","Englisch",5],["12:10 - 12:55"," - ","Englisch","Mathe","Spanisch","Englisch",6],["13:00 - 13:45","Sport",""," - ","","GGK",7],["13:45 - 14:30","Sport","","WGEO","","",8],["14:35 - 15:20","","","WGEO","","",9]];Application.modules.Stundenplan={title:"Stundenplan",xtype:"grid",iconCls:"icon-table",columnLines:true,enableColumnMove:false,viewConfig:{forceFit:true},store:{xtype:"arraystore",data:a,fields:["zeit","montag","dienstag","mittwoch","donnerstag","freitag","stunde"]},cm:new Ext.grid.ColumnModel({defaults:{align:"center",resizable:false,sortable:false,menuDisabled:true},columns:[{header:"Zeit"},{header:"Montag"},{header:"Dienstag"},{header:"Mittwoch"},{header:"Donnerstag"},{header:"Freitag"},{header:"Stunde",width:50}]})}})();Ext.apply(Application.urls,{vokabeln:{read:"./vokabeln/select/format/json",create:"./vokabeln/create/format/json",update:"./vokabeln/update/format/json",destroy:"./vokabeln/destroy/format/json"}});Application.modules.Vokabeln={title:"Vokabeln",iconCls:"icon-table",xtype:"editorgrid",loadMask:true,layout:"fit",autoEncode:true,listeners:{afterlayout:{fn:function(){this.store.load()},single:true},rowcontextmenu:function(c,a,d){var b=this;b.getSelectionModel().selectRow(a);if(!b.menu){b.menu=new Ext.menu.Menu({items:[{text:"Vokablen entfernen",iconCls:"icon-delete",handler:function(){b.onRemove()}},{text:"Vokabeln hinzuf&uuml;gen",iconCls:"icon-add",handler:function(){b.onAdd()}}]})}d.stopEvent();b.menu.showAt(d.getXY())}},keys:[{key:Ext.EventObject.ENTER,fn:function(){var b=Application.modules.Vokablen;if(b.activeEditor===null){var a=b.getSelectionModel().getSelected();b.startEditing(b.store.indexOf(a),1)}}},{key:Ext.EventObject.DELETE,fn:function(){Application.modules.Vokabeln.onRemove()}}],getKeyMap:function(){if(!this.keyMap){this.keyMap=new Ext.KeyMap(this.body,this.keys)}return this.keyMap},viewConfig:{forceFit:true},onRemove:function(){var b=this.getSelectionModel();if(b.hasSelection()){b=b.getSelections();for(var a=0,c;c=b[a];a++){this.store.remove(c)}}},onAdd:function(){var a=new this.store.recordType({});this.stopEditing();this.store.insert(0,a);this.startEditing(0,0)},tbar:{listeners:{afterlayout:{fn:function(){this.search.store=this.ownerCt.store},single:true}},items:[{text:"Vokabeln hinzuf&uuml;gen",iconCls:"icon-add",handler:function(){this.ownerCt.ownerCt.onAdd()}},{ref:"../removeBtn",text:"Vokabeln entfernen",iconCls:"icon-delete",disabled:true,handler:function(){this.ownerCt.ownerCt.onRemove()}},"-","Suchen: ",{ref:"search",xtype:"searchfield"}]},store:{xtype:"jsonstore",autoSave:true,root:"data",remoteSort:true,sortInfo:{field:"deutsch",direction:"ASC"},proxy:new Ext.data.HttpProxy({api:{read:Application.urls.vokabeln.read,create:Application.urls.vokabeln.create,update:Application.urls.vokabeln.update,destroy:Application.urls.vokabeln.destroy}}),fields:[{name:"id",type:"int"},{name:"deutsch",allowBlank:false},{name:"englisch"},{name:"spanisch"}],writer:new Ext.data.JsonWriter(),listeners:{exception:function(d,e,f,b,c,a){if(e=="remote"){if(f==Ext.data.Api.actions.update){a.reject()}else{if(f==Ext.data.Api.actions.create){this.remove(a)}}Ext.Msg.error("Fehler!",'"'+a.data.deutsch+'" konnte nicht ver&auml;ndert werden.<br />'+c.message)}}}},sm:new Ext.grid.RowSelectionModel({listeners:{selectionchange:function(){this.grid.removeBtn.setDisabled(!this.hasSelection())}}}),cm:new Ext.grid.ColumnModel({defaults:{sortable:true},columns:[{header:"Deutsch",dataIndex:"deutsch",editor:{xtype:"textfield",allowBlank:false}},{header:"Englisch",dataIndex:"englisch",editor:{xtype:"textfield",allowBlank:false}},{header:"Spanisch",dataIndex:"spanisch",editor:{xtype:"textfield",allowBlank:false}}]})};Ext.apply(Application.urls,{user:{read:"./user/select/format/json",create:"./user/create/format/json",update:"./user/update/format/json",destroy:"./user/destroy/format/json"}});Application.modules.UserManager={title:"Benutzer Manager",iconCls:"icon-table",xtype:"editorgrid",loadMask:true,layout:"fit",listeners:{afterlayout:{fn:function(){this.store.load()},single:true},rowcontextmenu:function(c,a,d){var b=this;b.getSelectionModel().selectRow(a);if(!b.menu){b.menu=new Ext.menu.Menu({items:[{text:"Benutzer entfernen",iconCls:"icon-user-delete",handler:function(){b.onRemove()}},{text:"Benutzer hinzuf&uuml;gen",iconCls:"icon-user-add",handler:function(){b.onAdd()}}]})}d.stopEvent();b.menu.showAt(d.getXY())}},keys:[{key:Ext.EventObject.ENTER,fn:function(){var b=Application.modules.UserManager;if(b.activeEditor===null){var a=b.getSelectionModel().getSelected();b.startEditing(b.store.indexOf(a),1)}}},{key:Ext.EventObject.DELETE,fn:function(){Application.modules.UserManager.onRemove()}}],getKeyMap:function(){if(!this.keyMap){this.keyMap=new Ext.KeyMap(this.body,this.keys)}return this.keyMap},viewConfig:{forceFit:true},onRemove:function(){var b=this.getSelectionModel();if(b.hasSelection()){b=b.getSelections();for(var a=0,c;c=b[a];a++){this.store.remove(c)}}},onAdd:function(){var a=new this.store.recordType({});this.stopEditing();this.store.insert(0,a);this.startEditing(0,1)},tbar:{listeners:{afterlayout:{fn:function(){this.search.store=this.ownerCt.store},single:true}},items:[{text:"Benutzer hinzuf&uuml;gen",iconCls:"icon-user-add",handler:function(){this.ownerCt.ownerCt.onAdd()}},{ref:"../removeBtn",text:"Benutzer entfernen",iconCls:"icon-user-delete",disabled:true,handler:function(){this.ownerCt.ownerCt.onRemove()}},"-","Suchen: ",{ref:"search",xtype:"searchfield"}]},store:{xtype:"jsonstore",autoSave:true,root:"data",remoteSort:true,proxy:new Ext.data.HttpProxy({api:{read:Application.urls.user.read,create:Application.urls.user.create,update:Application.urls.user.update,destroy:Application.urls.user.destroy}}),fields:[{name:"id",type:"int"},{name:"username",allowBlank:false},{name:"password",allowBlank:false},{name:"rights",type:"int",allowBlank:false}],writer:new Ext.data.JsonWriter(),listeners:{exception:function(d,e,f,b,c,a){if(e=="remote"){if(f==Ext.data.Api.actions.update){a.reject()}else{if(f==Ext.data.Api.actions.create){this.remove(a)}}Ext.Msg.error("Fehler!",'"'+a.data.username+'" konnte nicht ver&auml;ndert werden.<br />'+c.message)}}}},sm:new Ext.grid.RowSelectionModel({listeners:{selectionchange:function(){this.grid.removeBtn.setDisabled(!this.hasSelection())}}}),cm:new Ext.grid.ColumnModel({defaults:{sortable:true},columns:[{header:"id",dataIndex:"id",width:20},{header:"Benutzername",dataIndex:"username",editor:{xtype:"textfield",allowBlank:false}},{header:"Passwort",dataIndex:"password",editor:{xtype:"textfield",allowBlank:false,selectOnFocus:true}},{header:"Rechte",dataIndex:"rights",renderer:function(a){switch(a){case 1:return"Admin";break;case 2:return"Benutzer";break;default:return a;break}},editor:{xtype:"selectbox",displayField:"text",valueField:"value",store:{xtype:"arraystore",data:[[1,"Admin"],[2,"Benutzer"]],fields:["value","text"]}}}]})};Application.addLinks([{title:"Rebecca",items:[{iconCls:"icon-home",html:"Willkommen",href:"#Welcome"}]},{title:"Schule",items:[{iconCls:"icon-table",html:"Stundenplan",href:"#Stundenplan"},{iconCls:"icon-table",html:"Vokabeln",href:"#Vokabeln"}]},{title:"Admin",items:[{iconCls:"icon-table",html:"Benutzer Manager",href:"#UserManager"}]},{title:"User",iconCls:"icon-user",items:[{iconCls:"icon-userkey",html:"Logout",handler:function(){var a=function(){Ext.Msg.hide();Ext.fly("loading-mask").fadeIn({callback:function(){window.location.reload()}})};Ext.Msg.wait("Ausloggen...","Bitte warten...");Ext.Ajax.request({url:Application.urls.logout,success:a,failure:a})}}]}]);