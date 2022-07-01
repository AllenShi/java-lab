package net.sjl.sample.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MyAgent
{
    public static void premain(String args, Instrumentation instrument) {
        System.out.println( "premain " + args );

        String suffix = args == null || args.isEmpty() ? "net.sjl" : args;

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.any()) // Interception of arbitrary methods
                    .intercept(MethodDelegation.to(MethodCostTime.class)); // Entrust
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println( "onDiscovery " + s );

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println( "onTransformation ");
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println( "onIgnored ");
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                System.out.println( "onError ");
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println( "onComplete " + s);
            }
        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith(suffix)) // Specify the class to intercept
                .transform(transformer)
                .with(listener)
                .installOn(instrument);

    }
}
