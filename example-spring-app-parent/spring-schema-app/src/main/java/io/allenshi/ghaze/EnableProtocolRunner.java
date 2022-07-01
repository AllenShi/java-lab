package io.allenshi.ghaze;

import io.allenshi.ghaze.autoconfigure.EnableProtocol;
import io.allenshi.ghaze.autoconfigure.Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableProtocol(name = "gracehaze", port = 6400)
public class EnableProtocolRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EnableProtocolRunner.class, args);
        Protocol protocol1 = context.getBean(Protocol.class);
        System.out.format("Protocol bean info, name: %s, port: %d\n", protocol1.getName(), protocol1.getPort());

        Protocol protocol2 = (Protocol)context.getBean("protocol");
        System.out.format("Protocol bean info, name: %s, port: %d\n", protocol2.getName(), protocol2.getPort());
        context.close();
    }
}
