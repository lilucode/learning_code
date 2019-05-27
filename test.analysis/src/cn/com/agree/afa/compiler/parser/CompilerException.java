package cn.com.agree.afa.compiler.parser;

public class CompilerException extends Exception {
	private static final long serialVersionUID = 1136215135413270390L;
	private String detailMessage;

	public CompilerException(String message) {
		this.detailMessage = message;
	}

	public CompilerException(String message, Throwable cause) {
		super(cause);
		this.detailMessage = message;
	}

	public String getMessage() {
		return this.detailMessage;
	}

	public void setMessage(String message) {
		this.detailMessage = message;
	}
}
