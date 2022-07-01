package net.sjl.alg.api;

import net.sjl.alg.impl.SkipList;
import org.junit.Test;

public class SkipListUTest {

    @Test
    public void testAdd() {

        net.sjl.alg.api.SkipList<Integer, String> skipList1 = new net.sjl.alg.api.SkipList<Integer, String>();
        skipList1.insert(1, "one");
        skipList1.insert(2, "two");
        skipList1.insert(3, "three");
        skipList1.insert(4, "four");

        System.out.println("1 => " + skipList1.findByKey(1));

    }
}
