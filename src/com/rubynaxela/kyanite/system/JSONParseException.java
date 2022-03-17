package com.rubynaxela.kyanite.system;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

/**
 * This exception is thrown when a JSON parsing operation has failed.
 */
public class JSONParseException extends JSONException {

    /**
     * Constructs a new {@code JSONParseException} with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public JSONParseException(@NotNull String message) {
        super(message);
    }
}
