package org.fregelang.plugin.idea.framework;

public class DownloadException extends RuntimeException {

    private static final long serialVersionUID = 6648267548517843577L;

    public DownloadException(Throwable cause) {
        super(cause);
    }

    public DownloadException(String message) {
        super(message);
    }
}
