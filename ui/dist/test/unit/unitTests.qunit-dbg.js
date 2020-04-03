/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"localui5run/localui5run/test/unit/AllTests"
	], function () {
		QUnit.start();
	});
});