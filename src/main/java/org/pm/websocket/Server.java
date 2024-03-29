package org.pm.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * Simple Server
 *
 *
 *
 *
 */
public class Server extends WebSocketServer {

    public Server( int port ) throws UnknownHostException {
        super( new InetSocketAddress( port ) );
    }

    public Server( InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
        System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        this.sendToAll( conn + " has left the room!" );
        System.out.println( conn + " has left the room!" );
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        this.sendToAll( message );
        System.out.println( conn + ": " + message );
    }

    /***
     * Simple websocket Server
     * @param args port
     *
     * If no arguments are provided 8887 will be used
     * Example - java Server 8887
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main( String[] args ) throws InterruptedException , IOException {
        WebSocketImpl.DEBUG = true;
        int port = 8887; // 843 flash policy port
        try {
            port = Integer.parseInt( args[ 0 ] );
        } catch(ArrayIndexOutOfBoundsException ex) {

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        Server s = new Server( port );
        s.start();
        System.out.println( "ChatServer started on port: " + s.getPort() );
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text
     *            The String to send across the network.
     */
    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }
}