package net.sjl.sample.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void shouldAnswerWithTrue() {
        System.out.println("java agent test method");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            System.err.println(writer.toString());
        }

        assertTrue( true );
    }
}
