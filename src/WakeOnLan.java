package com.matlipe;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WakeOnLan extends CordovaPlugin {

	private static final String WAKE_ACTION = "wake";
	private static final int PORT = 9;


	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		if( WAKE_ACTION.equals( action ) ){
			return wake( data );
		} else {
System.out.println( "Invalid action : " + action + " passed" );
        }

		return false;
	}


	private Boolean wake( JSONArray data ) {
		Boolean result = false;
		try{
			String macStr = data.getString( 0 );
			String ipStr = data.getString( 1 );

			byte[] macBytes = getMacBytes( macStr );
			byte[] bytes = new byte[ 6 + 16 * macBytes.length ];

			for( int i = 0; i < 6; i++ ){
				bytes[ i ] = ( byte ) 0xff;
			}
			for( int i = 6; i < bytes.length; i += macBytes.length ){
				System.arraycopy( macBytes, 0, bytes, i, macBytes.length );
			}

			InetAddress address = InetAddress.getByName( ipStr );
			DatagramPacket packet = new DatagramPacket( bytes, bytes.length, address, PORT );
			DatagramSocket socket = new DatagramSocket();
			socket.send( packet );
			socket.close();

			System.out.println( "Wake-on-LAN packet sent." );

			result = true;
		} catch( Exception e ){
			System.out.println( "WakeOnLan Exception thrown" + e.toString() );
		}

		return result;

	}


	private byte[] getMacBytes( String macStr ) throws IllegalArgumentException {
		byte[] bytes = new byte[ 6 ];
		String[] hex = macStr.split( "(\\:|\\-)" );
		if( hex.length != 6 ){
			throw new IllegalArgumentException( "Invalid MAC address." );
		}
		try{
			for( int i = 0; i < 6; i++ ){
				bytes[ i ] = ( byte ) Integer.parseInt( hex[ i ], 16 );
			}
		} catch( NumberFormatException e ){
			throw new IllegalArgumentException( "Invalid hex digit in MAC address." );
		}
		return bytes;
	}

}
