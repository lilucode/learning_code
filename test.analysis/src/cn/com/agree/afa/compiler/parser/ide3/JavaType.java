package cn.com.agree.afa.compiler.parser.ide3;

import java.util.HashMap;
import java.util.Map;

public class JavaType {
	public static final int VOID = 0;
	public static final int BOOLEAN = 1;
	public static final int CHAR = 2;
	public static final int BYTE = 3;
	public static final int SHORT = 4;
	public static final int INT = 5;
	public static final int FLOAT = 6;
	public static final int LONG = 7;
	public static final int DOUBLE = 8;
	public static final int ARRAY = 9;
	public static final int OBJECT = 10;
	public static final int METHOD = 11;
	public static final JavaType VOID_TYPE = new JavaType(0, null, 1443168256, 1);

	public static final JavaType BOOLEAN_TYPE = new JavaType(1, null, 1509950721, 1);

	public static final JavaType CHAR_TYPE = new JavaType(2, null, 1124075009, 1);

	public static final JavaType BYTE_TYPE = new JavaType(3, null, 1107297537, 1);

	public static final JavaType SHORT_TYPE = new JavaType(4, null, 1392510721, 1);

	public static final JavaType INT_TYPE = new JavaType(5, null, 1224736769, 1);

	public static final JavaType FLOAT_TYPE = new JavaType(6, null, 1174536705, 1);

	public static final JavaType LONG_TYPE = new JavaType(7, null, 1241579778, 1);

	public static final JavaType DOUBLE_TYPE = new JavaType(8, null, 1141048066, 1);

	public static final JavaType OBJECT_TYPE = new JavaType(10, null, 0, 1);
	private final int sort;
	private final char[] buf;
	private final int off;
	private final int len;
	private static final Map<String, String> PRIMITIVE_WRAP_MAPPING = new HashMap<>();

	static {
		PRIMITIVE_WRAP_MAPPING.put("boolean", "Boolean");
		PRIMITIVE_WRAP_MAPPING.put("char", "Character");
		PRIMITIVE_WRAP_MAPPING.put("byte", "Byte");
		PRIMITIVE_WRAP_MAPPING.put("short", "Short");
		PRIMITIVE_WRAP_MAPPING.put("int", "Integer");
		PRIMITIVE_WRAP_MAPPING.put("long", "Long");
		PRIMITIVE_WRAP_MAPPING.put("float", "Float");
		PRIMITIVE_WRAP_MAPPING.put("double", "Double");
	}

	private JavaType(int sort, char[] buf, int off, int len) {
		this.sort = sort;
		this.buf = buf;
		this.off = off;
		this.len = len;
	}

	public static JavaType[] getArgumentTypes(String methodDescriptor) {
		char[] buf = methodDescriptor.toCharArray();
		int off = 1;
		int size = 0;
		while (true) {
			char car = buf[(off++)];
			if (car == ')')
				break;
			if (car == 'L') {
				while (buf[(off++)] != ';')
					;
				size++;
				continue;
			}
			if (car != '[') {
				size++;
			}
		}
		JavaType[] args = new JavaType[size];
		off = 1;
		size = 0;
		while (buf[off] != ')') {
			args[size] = getType(buf, off);
			off += args[size].len + (args[size].sort == 10 ? 2 : 0);
			size++;
		}
		return args;
	}

	public static JavaType getReturnType(String methodDescriptor) {
		char[] buf = methodDescriptor.toCharArray();
		return getType(buf, methodDescriptor.indexOf(')') + 1);
	}

	private static JavaType getType(char[] buf, int off) {
		switch (buf[off]) {
		case 'V':
			return VOID_TYPE;
		case 'Z':
			return BOOLEAN_TYPE;
		case 'C':
			return CHAR_TYPE;
		case 'B':
			return BYTE_TYPE;
		case 'S':
			return SHORT_TYPE;
		case 'I':
			return INT_TYPE;
		case 'F':
			return FLOAT_TYPE;
		case 'J':
			return LONG_TYPE;
		case 'D':
			return DOUBLE_TYPE;
		case '[':
			int len = 1;
			while (buf[(off + len)] == '[') {
				len++;
			}
			if (buf[(off + len)] == 'L') {
				len++;
				while (buf[(off + len)] != ';') {
					len++;
				}
			}
			return new JavaType(9, buf, off, len + 1);
		case 'L':
			int _len = 1;
			while (buf[(off + _len)] != ';') {
				_len++;
			}
			return new JavaType(10, buf, off + 1, _len - 1);
		case 'E':
		case 'G':
		case 'H':
		case 'K':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'T':
		case 'U':
		case 'W':
		case 'X':
		case 'Y':
		}
		return new JavaType(11, buf, 0, buf.length);
	}

	private int getDimensions() {
		int i = 1;
		while (this.buf[(this.off + i)] == '[') {
			i++;
		}
		return i;
	}

	private JavaType getElementType() {
		return getType(this.buf, this.off + getDimensions());
	}

	public String getQualifiedClassName() {
		switch (this.sort) {
		case 0:
			return "void";
		case 1:
			return "boolean";
		case 2:
			return "char";
		case 3:
			return "byte";
		case 4:
			return "short";
		case 5:
			return "int";
		case 6:
			return "float";
		case 7:
			return "long";
		case 8:
			return "double";
		case 9:
			StringBuffer b = new StringBuffer(getElementType().getQualifiedClassName());
			for (int i = getDimensions(); i > 0; i--) {
				b.append("[]");
			}
			return b.toString();
		case 10:
			return new String(this.buf, this.off, this.len).replace('/', '.');
		}
		return null;
	}

	public String getSimpleClassName() {
		String qualifiedClassName = getQualifiedClassName();
		int lastDotIndex = qualifiedClassName.lastIndexOf(".");
		if (lastDotIndex > 0) {
			return qualifiedClassName.substring(lastDotIndex + 1);
		}
		return qualifiedClassName;
	}

	public String getClassNameForImport() {
		String className = null;
		switch (this.sort) {
		case 9:
			JavaType elementType = getElementType();
			if (elementType.sort != 10)
				break;
			className = elementType.getQualifiedClassName();

			break;
		case 10:
			className = new String(this.buf, this.off, this.len).replace('/', '.');
		}

		if (className != null) {
			int lastDotIndex = className.lastIndexOf(".");
			if (lastDotIndex > 0) {
				String packageName = className.substring(0, lastDotIndex);
				if (!"java.lang".equals(packageName)) {
					return className;
				}
			}
		}
		return null;
	}

	public int getSort() {
		return this.sort;
	}

	public static String toWrapType(String primitiveType) {
		String wrapType = (String) PRIMITIVE_WRAP_MAPPING.get(primitiveType);
		return wrapType != null ? wrapType : primitiveType;
	}
}
