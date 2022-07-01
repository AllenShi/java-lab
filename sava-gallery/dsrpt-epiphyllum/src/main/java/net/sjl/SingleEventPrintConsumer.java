package net.sjl;

import com.lmax.disruptor.EventHandler;

public class SingleEventPrintConsumer {

  public EventHandler<ValueEvent>[] getEventHandler() {
    EventHandler<ValueEvent> eventHandler = 
      (event, sequence, endOfBatch) -> 
        print(event.getKey(), event.getValue(), sequence);
    
    return new EventHandler[] {eventHandler};
  }

  private void print(String key, int id, long sequenceId) {
    System.out.println("Key is " + key + ", sequence id that was used is " + sequenceId);
  }
} 

