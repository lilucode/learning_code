package cn.com.agree.afa.compiler.parser;

public class XmlParseException extends CompilerException {
	private static final long serialVersionUID = 6237732275026322896L;

	public XmlParseException(String message) {
		super(message);
	}

	public XmlParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
