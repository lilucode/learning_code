package cn.com.agree.afa.compiler.parser;

import java.io.File;

public abstract interface IParser<T> {
	public abstract T parse(File paramFile) throws XmlParseException;
}
