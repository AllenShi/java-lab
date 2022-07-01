package net.sjl.fragrans.log;

import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import org.slf4j.MDC;

public class LoggerContext {
    private static String TRACE_ID = "TRACE_ID";

    public static void enableTrace() {
        UUID uuid = UUID.randomUUID();
        String traceId = uuid.toString().replace("-", "");
        MDC.put(TRACE_ID, traceId);
    }

    public static void disableTrace() {
        MDC.remove(TRACE_ID);
    }

    public static String getTraceKey() {
        return TRACE_ID;
    }

    public static void attachProperties(Properties properties) {
        if(Objects.isNull(properties)) return;

        properties.forEach((K, V) -> {
            MDC.put(K.toString(), V.toString());
        });
    }

    public static void detachProperties(Properties properties) {
        if(Objects.isNull(properties)) return;

        properties.forEach((K, V) -> {
            MDC.remove(K.toString());
        });
    }

}
