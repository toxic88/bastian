<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="ExtDirectSample._Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <link rel="Stylesheet" type="text/css" href="resources/css/ext-all.css" />
    <script type="text/javascript" src="ext-base.js"></script>
    <script type="text/javascript" src="ext-all.js"></script>
    <script type="text/javascript" src="ApiHandler.ashx"></script>
    <script type="text/javascript">
        Ext.ns('Ext.app');
        Ext.Direct.addProvider(Ext.app.REMOTING_API);
        Ext.onReady(function() {
            var echo = new Ext.Panel({
                title: 'Echo check',
                columnWidth: 0.25,
                height: 400,
                tools: [{
                    id: 'refresh',
                    handler: function()
                    {
                        Sample.Echo('Echo please...', function(e, result)
                        {
                            echo.body.update(result.result);
                        });
                    }
                }]
            });

            var time = new Ext.Panel({
                title: 'Time check',
                columnWidth: 0.25,
                height: 400,
                tools: [{
                    id: 'refresh',
                    handler: function()
                    {
                        Sample.GetTime(function(e, result)
                        {
                            time.body.update(result.result);
                        });
                    }
                }]
            });
            
            var upload = new Ext.form.FormPanel({
                title: 'File Upload',
                columnWidth: 0.25,
                height: 410,
                bodyStyle: 'padding: 15px;',
                fileUpload: true,
                items: {
                    fieldLabel: 'Upload an image!',
                    xtype: 'textfield',
                    inputType: 'file'
                },
                buttons: [{
                    text: 'Submit',
                    handler: function(){
                        var f = upload.getEl().child('form');
                        Sample.Upload(f, function(e, data) {
                            upload.setTitle('File uploaded!');
                            upload.header.highlight();
                        });
                    }
                }]
            });
            
            var form = new Ext.form.FormPanel({
                title: 'Form Upload',
                columnWidth: 0.25,
                height: 410,
                bodyStyle: 'padding: 15px;',
                items: [{
                    xtype: 'textfield',
                    name: 'firstName',
                    fieldLabel: 'First Name'
                },{
                    xtype: 'textfield',
                    name: 'lastName',
                    fieldLabel: 'Last Name'
                },{
                    xtype: 'numberfield',
                    name: 'age',
                    fieldLabel: 'Age'
                }],
                buttons: [{
                    text: 'Submit',
                    handler: function(){
                        var f = form.getEl().child('form');
                        Sample.SaveForm(f, function(e, data) {
                        console.log(e, data);
                            var r = Ext.decode(data.result);
                            var name = r.firstName + ' ' + r.lastName;
                            Ext.Msg.alert('Hello', 'Hi ' + name + ', you are ' + r.age);
                        });
                    }
                }]
            });

            var ct = new Ext.Container({
                autoEl: {},
                layout: 'column',
                items: [echo, time, upload, form],
                renderTo: document.body
            });
        });
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    
    </div>
    </form>
</body>
</html>
