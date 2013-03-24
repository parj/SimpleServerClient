package org.pm.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Created with IntelliJ IDEA.
 * User: parj
 * Date: 24/03/2013
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Client extends WebSocketClient {

    public Client( URI serverUri , Draft draft ) {
            super( serverUri, draft );
    }

    public Client( URI serverURI ) {
            super( serverURI );
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        System.out.println( "Made connecton to WebServer" );
        // if you pan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
            System.out.println( "received: " + message );
    // send( "you said: " + message );
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
    }

    @Override
    public void onError( Exception ex ) {
            ex.printStackTrace();
    // if the error is fatal then onClose will be called additionally
    }

    /**
     * Simple Client
     * @param args Server and port
     *
     * If no arguments are provided "localhost:8887" will be used
     * Example - java Client "localhost:8887"
     * @throws URISyntaxException
     */

    public static void main( String[] args ) throws URISyntaxException {
        String connection = "localhost:8887";

        try {
            connection = args[0];
        } catch(ArrayIndexOutOfBoundsException ex) {

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        Client c = new Client( new URI( "ws://" + connection ), new Draft_10() ); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }

}