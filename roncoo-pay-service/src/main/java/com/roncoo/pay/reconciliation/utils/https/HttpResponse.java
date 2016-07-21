package com.roncoo.pay.reconciliation.utils.https;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

public class HttpResponse implements Closeable {

    private InputStream inputStream;
    private HttpsURLConnection connection;

    public HttpResponse(HttpsURLConnection connection) throws IOException {
        this.connection = connection;
        this.inputStream = connection.getInputStream();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public HttpsURLConnection getConnection() {
        return connection;
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
    }

}