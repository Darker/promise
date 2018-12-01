package io.github.darker.promise.exception;

public class MultipleResolutionsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MultipleResolutionsException() {
		super("Promise was resolved multiple times!");
	}

	public MultipleResolutionsException(String message) {
		super(message);
	}

	public MultipleResolutionsException(Throwable cause) {
		super(cause);
	}

	public MultipleResolutionsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleResolutionsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
