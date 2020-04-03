sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("localui5run.localui5run.controller.Home", {
		onInit: function () {
 
			var model = [];
			this.getView().setModel(new JSONModel({
				"servers": []
			})); 

			var that=this;
			setInterval(function(){ 
				that.getServers(that)
			},1000);
		},
		load:function()
		{

			var ob=JSON.parse(this.getView().byId("area0").getValue());
			var q="name="+encodeURI(ob.name)+"&cmd="+encodeURI(ob.cmd)+"&env="+encodeURI(ob.env)+"&dir="+encodeURI(ob.dir);
			$.get('api/load?'+q, function (res) {


				console.log(res);

			});
		},
		startstop:function(oEvent)
		{

			var name = oEvent.getSource().getBindingContext().getProperty("alive");
			if(name)
			{
				this.stop(oEvent)
			}
			else{
				this.start(oEvent);
			}
		},
		stop:function(oEvent)
		{
			var name = oEvent.getSource().getBindingContext().getProperty("name");
			$.get('api/stop?name='+name, function (res) {


				console.log(res); 
			});
		},
		
		start:function(oEvent)
		{
			var name = oEvent.getSource().getBindingContext().getProperty("name");
			var q="name="+encodeURI( oEvent.getSource().getBindingContext().getProperty("name"))+"&cmd="+encodeURI(
				oEvent.getSource().getBindingContext().getProperty("cmd")
			)+"&env="+encodeURI( oEvent.getSource().getBindingContext().getProperty("env"))+"&dir="+encodeURI(
				oEvent.getSource().getBindingContext().getProperty("dir")
			);
			$.get('api/load?'+q, function (res) {


				console.log(res);

			});
			 
		},
		getServers: function (that) {
 
			$.get('api/getall', function (res) {


				console.log(res);
				that.getView().setModel(new JSONModel({
					"servers": res
				}));
			})
		}
	});
});