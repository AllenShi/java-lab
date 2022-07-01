package net.sjl;

import com.lmax.disruptor.RingBuffer;

public class SingleEventProducer {
  private final RingBuffer<ValueEvent> ringBuffer;
  private int count;

  public SingleEventProducer(RingBuffer<ValueEvent> ringBuffer, int count) {
    this.ringBuffer = ringBuffer;
    this.count = count;
  }

  public void produce() {
    assert (count > 0) && (ringBuffer != null);
    for(int eventcount = 0; eventcount < count; eventcount++) {
      long sequenceId = ringBuffer.next();
      ValueEvent event = ringBuffer.get(sequenceId);
      event.setKey(""+System.currentTimeMillis());
      event.setValue(eventcount);
      ringBuffer.publish(sequenceId);
    }
  }  

}
