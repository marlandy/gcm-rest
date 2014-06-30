package com.autentia.tutorial.gcm.service;


import com.google.android.gcm.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Workaround to avoid issue #13 of gcm-server
 * @see https://code.google.com/p/gcm/issues/detail?id=13&q=encoding
 *
 */
public class UTF8Sender extends Sender {

    private static final Logger LOG = LoggerFactory.getLogger(UTF8Sender.class);

    private final String key;

    @Autowired
    public UTF8Sender(@Value("${gcmserverkey}") String key) {
        super(key);
        this.key = key;
    }

    @Override
    protected HttpURLConnection post(String url, String contentType, String body) throws IOException {
        if (url == null || body == null) {
            LOG.error("URL and body are required: URL {}, body {}", url, body);
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!url.startsWith("https://")) {
            LOG.warn("URL does not use https: " + url);
        }
        logger.fine("Utf8Sender Sending POST to " + url);
        logger.finest("POST body: " + body);
        byte[] bytes = body.getBytes(UTF8);
        HttpURLConnection conn = getConnection(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + key);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            close(out);
        }
        LOG.trace("Connection successfully created " + conn);
        return conn;
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore error
            }
        }
    }

}


