package net.sjl.bytecode.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.test.context.junit4.SpringRunner;

import net.sjl.spring.simple.bean.SampleClass;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
public class CGLibTest {

	@Test
	public void testFixedValue() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback((FixedValue) () -> "Hello cglib!");
		SampleClass proxy = (SampleClass) enhancer.create();

		assertThat(proxy).isNotNull().isNotExactlyInstanceOf(SampleClass.class);
		assertThat(proxy.test("")).isEqualTo("Hello cglib!");
	}

	@Test(expected = RuntimeException.class)
	public void testInvocationHandler() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback((InvocationHandler) (Object proxy, Method method, Object[] args) -> {
			if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
				return "Hello cglib!";
			} else {
				throw new RuntimeException("Do not know what to do.");
			}
		});
		
		SampleClass proxy = (SampleClass) enhancer.create();
		assertThat(proxy.test(null)).isEqualTo("Hello cglib!");
		proxy.toString();
	}

	@Test
	public void testMethodInterceptor() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SampleClass.class);
		enhancer.setCallback((MethodInterceptor) (Object obj, Method method, Object[] args, MethodProxy proxy) -> {
			if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
				return "Hello cglib!";
			} else {
				return proxy.invokeSuper(obj, args);
			}
		});
		SampleClass proxy = (SampleClass) enhancer.create();
		assertThat(proxy.test(null)).isEqualTo("Hello cglib!");
		assertThat(proxy.toString()).isNotEqualTo("Hello cglib!");
	}

}
