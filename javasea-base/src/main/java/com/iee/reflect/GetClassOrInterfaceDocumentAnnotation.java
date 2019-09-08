package com.iee.reflect;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 *  将一个java类通过文件方式读取,获取里面的属性, 值和注释
 * @author qinli
 */
public class GetClassOrInterfaceDocumentAnnotation {

	public static void main(String[] args) throws FileNotFoundException {
//		a();
//		b();
		c();
	}

	private static void c() throws FileNotFoundException {
		File file = new File("file/GBDataKeyConst.java");
		CompilationUnit compilationUnit = JavaParser.parse(file);
		compilationUnit.accept(new ClassVisitor(), null);
	}
	private static class ClassVisitor extends VoidVisitorAdapter<Void> {
		private FieldVisitor fieldVisitor = new FieldVisitor();
		@Override
		public void visit(ClassOrInterfaceDeclaration classOrInterface, Void arg) {
			if (classOrInterface.isInterface()) {
				boolean innerClass = classOrInterface.isInnerClass();
				if (innerClass) {
					Node node = classOrInterface.getParentNode().get();
					System.out.println("内部类: "+ node);
				}
				System.out.println("类名: "+ classOrInterface.getNameAsString());
				classOrInterface.accept(fieldVisitor, arg);
			}
			super.visit(classOrInterface, arg);
		}
	}
	private static class FieldVisitor extends VoidVisitorAdapter<Void> {
		private VariableVisitor variableVisitor = new VariableVisitor();
		private CommentVisitor commentVisitor = new CommentVisitor();
		@Override
		public void visit(FieldDeclaration field, Void arg) {
			field.accept(commentVisitor, arg);
			field.accept(variableVisitor, arg);
			super.visit(field, arg);
		}
	}
	private static class VariableVisitor extends VoidVisitorAdapter<Void> {
		@Override
		public void visit(VariableDeclarator variable, Void arg) {
			String name = variable.getNameAsString();
			Expression expression = variable.getInitializer().orElse(null);
			if (expression instanceof LiteralStringValueExpr) {
				String value = ((LiteralStringValueExpr) expression).getValue();
				System.out.println("变量名: "+ name + "\t变量值: "+ value);
			}
			super.visit(variable, arg);
		}
	}
	private static class CommentVisitor extends VoidVisitorAdapter<Void> {
		@Override
		public void visit(JavadocComment docComment, Void arg) {
			String content = docComment.getContent();
			String trimContent = null;
			if (StringUtils.contains(content, "*")) {
				trimContent = StringUtils.substringAfter(content, "*").trim();
			}
			if (StringUtils.contains(content, ":")) {
				trimContent = StringUtils.substringAfter(trimContent, ":").trim();
			}
			System.out.println("注释: "+ trimContent);
			super.visit(docComment, arg);
		}
	}
	private static class MethodVisitor extends VoidVisitorAdapter<Void> {
		@Override
		public void visit(MethodDeclaration method, Void arg) {
			SimpleName name = method.getName();
			String identifier = name.getIdentifier();
			System.out.println(identifier);
			super.visit(method, arg);
		}
	}

	private static void b() throws FileNotFoundException {
		File file = new File("file/GBDataKeyConst.java");
		CompilationUnit compilationUnit = JavaParser.parse(file);
		List<ClassOrInterfaceDeclaration> classOrInterface = compilationUnit.findAll(ClassOrInterfaceDeclaration.class);
		for (ClassOrInterfaceDeclaration declaration : classOrInterface) {
			if (declaration.isInterface()) {
//				System.out.println(declaration.getName());
				List<FieldDeclaration> fields = declaration.getFields();
				for (FieldDeclaration field : fields) {
					VariableDeclarator variable = field.getVariable(0);
					SimpleName name = variable.getName();
					String identifier = name.getIdentifier();
					System.out.print(identifier);
					Optional<Expression> initializer = variable.getInitializer();
					Expression expression = initializer.orElseGet(null);
					if (expression instanceof LiteralStringValueExpr) {
						String value = ((LiteralStringValueExpr) expression).getValue();
						System.out.println("\t"+ value);
					}
					Optional<JavadocComment> comment = field.getJavadocComment();
					JavadocComment docComment = comment.orElse(null);
					if (docComment != null) {
						String content = docComment.getContent();
						String trimContent = null;
						if (StringUtils.contains(content, "*")) {
							trimContent = StringUtils.substringAfter(content, "*").trim();
						}
						if (StringUtils.contains(content, ":")) {
							trimContent = StringUtils.substringAfter(trimContent, ":").trim();
						}
						System.out.println(trimContent);
					} else {
						System.out.println("无注释----");
					}
				}
			}
		}
	}

	private static void a() throws FileNotFoundException {
		File file = new File("file/GBDataKeyConst.java");
		CompilationUnit compilationUnit = JavaParser.parse(file);
		compilationUnit.findAll(ClassOrInterfaceDeclaration.class)
			.stream()
			.forEach(f -> {
				System.out.println(f.getName());
//				System.out.println("\t"+ f.getComment().orElse(null));
				List<FieldDeclaration> fields = f.getFields();
				for (FieldDeclaration field : fields) {
					System.out.println("\t"+ field);
					VariableDeclarator variable = field.getVariable(0);
					System.out.println("\t"+ variable);
					System.out.println("\t"+ variable.getName());
				}
			});

	}

}
