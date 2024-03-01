package com.cricoscore.Socket;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {

    private Socket socket = null;

    public SocketManager() {
        try {
            socket = IO.socket("https://chat.criconetonline.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Exception e = (Exception) args[0];
                // Log.e("SocketManager", "Connection error: " + e.getMessage());
                //Toaster.customToast("connected");
                System.out.println("connected");
            }
        });
    }



    public void connect() {
        if (socket != null) {
            socket.connect();

            // Set a connection callback to handle errors
            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                Exception e = (Exception) args[0];
                Log.e("SocketManager", "Connection error: " + e.getMessage());
            });
        }
    }

    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
        }
    }

    public boolean isConnected() {
        Log.d("Connect","connect"+socket.connected());
        return socket != null && socket.connected();
    }

    public void onMessageReceived(Listener listener) {
        if (socket != null) {
            socket.on("chat-message", args -> {
                String message = (String) args[0];
                listener.onReceived(message);
            });


        }
    }

    public void sendMessage(String message) {
        if (socket != null) {
            socket.emit("chat message", message);
        }
    }

    @FunctionalInterface
    public interface Listener {
        void onReceived(String message);
    }
}

