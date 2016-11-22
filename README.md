# PhoneGap Wake On Lan
Very basic plugin for sending wake on lan requests from a phonegap app.

# Installation
1. phonegap plugin add https://github.com/lipemat/phonegap-wake-on-lan.git

# Usage in JS
~~~~
document.addEventListener( 'deviceready', function(){
    //replace the broadcast ip and mac
    var BROADCAST = '192.168.1.255';
    var MAC = '48-2C-6A-1E-59-3D';
    window.plugins.WakeOnLan.wake( BROADCAST, MAC, function( r ){
        console.log( r )
    }, function( r){
        console.error( r )
    } );
}, false );
~~~~

### That's it! Super straight forward. 



Wake On Lan Plugin For Cordova/Phonegap
