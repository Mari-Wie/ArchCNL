package org.archcnl.stardogwrapper.api.exceptions;

import com.complexible.stardog.protocols.http.client.BaseHttpClient.HttpClientException;

/**
 * This exception is thrown when a database operation fails while a connection has been established.
 */
public class DBAccessException extends Exception {
    public DBAccessException(String description, HttpClientException cause) {
        super(description, cause);
    }

    private static final long serialVersionUID = -1840231832072886139L;
}
