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
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		boolean result = false;
		if( WAKE_ACTION.equals( action ) ){
			if( wake( args ) ){
				callbackContext.success( "Device has been woken up" );
				result = true;
			} else {
				callbackContext.error( "Device did not wake up" );
			}
		} else {
			callbackContext.error( "Invalid action : " + action + " passed" );
		}

		return result;
	}


	private Boolean wake( JSONArray args ) {
		Boolean result = false;
		try{
			String ipStr = args.getString( 0 );
			String macStr = args.getString( 1 );


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
