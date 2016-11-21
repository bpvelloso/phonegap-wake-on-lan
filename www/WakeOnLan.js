var WakeOnLan  = {
	wake : function( $broadcastIpAddress, $macAddress, successCallback, errorCallback ){
		cordova.exec( successCallback, errorCallback, "WakeOnLan", "wake", [$broadcastIpAddress, $macAddress] );
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