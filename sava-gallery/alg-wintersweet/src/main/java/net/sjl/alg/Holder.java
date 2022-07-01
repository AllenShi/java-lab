package net.sjl.alg;

public class Holder<T> {
  private T target;
  
  Holder() {
    this(null); 
  }

  Holder(T target) {
    this.target = target;
  } 
   
  public T getTarget() {
    return target;
  }

  public void setTarget(T target) {
    this.target = target;
  }
}
