package net.sjl.annotation;

import java.lang.annotation.Annotation;

public class TestRunner {
  public static void main(String[] args) throws Exception {
    Class<TestCase> obj = TestCase.class;
    if(obj.isAnnotationPresent(TestInfo.class)) {
      Annotation annotation = obj.getAnnotation(TestInfo.class);
      TestInfo testerInfo = (TestInfo)annotation;
   
      System.out.printf("%nPriority :%s", testerInfo.priority());
		System.out.printf("%nCreatedBy :%s", testerInfo.createdBy());
		System.out.printf("%nTags :");

		int tagLength = testerInfo.tags().length;
		for (String tag : testerInfo.tags()) {
			if (tagLength > 1) {
				System.out.print(tag + ", ");
			} else {
				System.out.print(tag);
			}
			tagLength--;
		}
    }
  }
}
