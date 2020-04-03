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