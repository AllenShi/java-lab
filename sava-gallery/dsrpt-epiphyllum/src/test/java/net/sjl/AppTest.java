package net.sjl;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.concurrent.ThreadFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testDisruptor()
    {
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        WaitStrategy waitStrategy = new BusySpinWaitStrategy();
        Disruptor<ValueEvent> disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 16, threadFactory, ProducerType.SINGLE, waitStrategy); 
        SingleEventPrintConsumer consumer = new SingleEventPrintConsumer();
        disruptor.handleEventsWith(consumer.getEventHandler());
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();
        SingleEventProducer producer = new SingleEventProducer(ringBuffer, 16);
        producer.produce();
        disruptor.halt();
        disruptor.shutdown();
        assertTrue( true );
    }
}
