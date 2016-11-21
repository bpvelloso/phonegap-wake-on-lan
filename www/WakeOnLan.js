var WakeOnLan  = {
	wake : function( $ipAddress, $macAddress, successCallback, errorCallback ){
		cordova.exec( successCallback, errorCallback, "WakeOnLan", "wake", [$macAddress, $ipAddress] );
	},

	install : function(){
		if (!window.plugins) {
			window.plugins = {};
		}

		window.plugins.WakeOnLan = this;
		return window.plugins.WakeOnLan;
	}
};

cordova.addConstructor(WakeOnLan.install);