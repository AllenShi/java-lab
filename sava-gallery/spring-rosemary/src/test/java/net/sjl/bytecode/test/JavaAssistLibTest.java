package net.sjl.bytecode.test;

import java.io.IOException;
import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.sjl.spring.simple.bean.Point;

@RunWith(SpringRunner.class)
public class JavaAssistLibTest {

	@Test
	public void testCreateClass() {
		ClassPool pool = ClassPool.getDefault();
        pool.appendSystemPath();

        try {
        	
        	CtClass ctClass = pool.makeClass("net.sjl.bean.GeneratedBean");
        	ctClass.addInterface(pool.get(Serializable.class.getName()));
			CtMethod method = new CtMethod(pool.get("java.lang.String"), "getStatus", null, ctClass);

			String status = "abc";
			method.setBody(String.format("return \"%s\";", status));

			ctClass.addMethod(method);

			ctClass.writeFile("/tmp/");
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void testInjectCode() {
		try {
			ClassPool cp = ClassPool.getDefault();
			CtClass cc = cp.get("net.sjl.bean.Point");
			CtClass intParam = cp.get("int");
			CtMethod m = cc.getDeclaredMethod("move", new CtClass[] {intParam, intParam});
			m.insertBefore("{ System.out.println(\"Point.move(int, int):\"); }");
			Class c = cc.toClass();
			Point p = (Point)c.newInstance();
			p.move(1, 2);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
