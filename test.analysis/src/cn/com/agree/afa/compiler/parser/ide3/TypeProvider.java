package cn.com.agree.afa.compiler.parser.ide3;

import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;

import cn.com.agree.afa.compiler.parser.CompilerException;

public class TypeProvider {
	private final Map<String, JavaType[]> ARG_TYPE_CACHE = new WeakHashMap();
	private final INameEnvironment ENV;

	TypeProvider(INameEnvironment env) {
		this.ENV = env;
	}

	public JavaType[] getArgTypes(String className, String methodName) throws CompilerException {
		String identifier = className + "." + methodName;
		JavaType[] types = (JavaType[]) this.ARG_TYPE_CACHE.get(identifier);
		if (types == null) {
			NameEnvironmentAnswer answer = this.ENV.findType(getCompoundTypeName(className));
			if (answer == null) {
				throw new CompilerException("找不到" + className + ".class");
			}
			IBinaryMethod targetMethod = null;
			IBinaryMethod[] allMethods = answer.getBinaryType().getMethods();
			if (allMethods != null) {
				for (IBinaryMethod method : allMethods) {
					if (methodName.equals(new String(method.getSelector()))) {
						targetMethod = method;
						break;
					}
				}
			}
			if (targetMethod == null) {
				throw new CompilerException(className + "类中没有" + methodName + "方法");
			}
			types = JavaType.getArgumentTypes(new String(targetMethod.getMethodDescriptor()));
			this.ARG_TYPE_CACHE.put(identifier, types);
		}
		return types;
	}

	private char[][] getCompoundTypeName(String className) {
		String[] segments = className.split("[.]");
		char[][] compoundTypeName = new char[segments.length][];
		for (int i = 0; i < compoundTypeName.length; i++) {
			compoundTypeName[i] = segments[i].toCharArray();
		}
		return compoundTypeName;
	}
}
