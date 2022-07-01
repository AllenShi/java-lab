package net.sjl;

import com.lmax.disruptor.EventFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ValueEvent {
  private int value;
  private String key;
  public final static EventFactory EVENT_FACTORY = () -> new ValueEvent();
}

