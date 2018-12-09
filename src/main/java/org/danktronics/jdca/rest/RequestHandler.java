package org.danktronics.jdca.rest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.danktronics.jdca.entities.impl.JDCAImpl;

import java.io.IOException;

public class RequestHandler {
    private final JDCAImpl jdca;

    public RequestHandler(JDCAImpl jdca) {
        this.jdca = jdca;
    }

    public Response request(String url) throws IOException {
        OkHttpClient okHttpClient = jdca.getOkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        return okHttpClient.newCall(request).execute();
    }
}
