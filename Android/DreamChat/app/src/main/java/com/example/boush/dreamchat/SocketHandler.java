package com.example.boush.dreamchat;

import android.os.AsyncTask;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.drafts.Draft_17;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * Created by Client on 4.5.2016.
 */
public class SocketHandler {
    //Server IP address
    private static String localipaddress = "10.0.2.2";
    private static final String localWSSIP = "wss://" + localipaddress + ":1337/socket";

    private static final int TIMEOUT = 10000;

    public SocketHandler(String output, MainActivity main){
        SocketConnector socketConnector = new SocketConnector();
        socketConnector.sendString = output;
        socketConnector.main = main;

        socketConnector.execute();
    }

    private class SocketConnector extends AsyncTask<Void, Void, Void> {
        private String sendString;
        private MainActivity main;

        @Override
        protected Void doInBackground(Void... voids) {
            //PersistentCookieStore cookieStore = SingletonPersistentCookieStore.getInstance(main);
            //final Cookie cookie = cookieStore.getCookies().get(0);
            try {
                Map<String, String> cmap = new HashMap<String, String>();
                /*String cookieString = cookie.getName()+"="+cookie.getValue();*/
                String cookieString="cookieName=cookieValue";
                cmap.put("cookie", cookieString);

                URI uri = new URI(localWSSIP);

                WebSocketExampleClient webSocketExampleClient = new WebSocketExampleClient(uri, new Draft_17(), cmap, TIMEOUT);

                //This part is needed in case you are going to use self-signed certificates
                /*TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                }};*/

                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    /*sc.init(null, trustAllCerts, new java.security.SecureRandom());

                    //Otherwise the line below is all that is needed.*/
                    //sc.init(null, null, null);
                    webSocketExampleClient.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                webSocketExampleClient.connectBlocking();
                webSocketExampleClient.send(sendString);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}