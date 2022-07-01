package net.sjl.annotation;

import net.sjl.annotation.TestInfo.Priority;

@TestInfo(
  priority = Priority.HIGH,
  createdBy = "Allen",
  tags = {"sales", "test"}
)
public class TestCase {
  public void test1() {
    System.out.println("test1");
  }
}
