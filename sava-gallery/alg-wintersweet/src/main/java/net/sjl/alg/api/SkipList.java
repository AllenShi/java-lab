package net.sjl.alg.api;

import java.util.Random;

public class SkipList<K extends Comparable<K>, V> {
    private SkipListNode head;
    private int level;
    private int size;
    private static Random random = new Random();

    public SkipList() {
        head = new SkipListNode(null, null, 0);
        size = 0;
        level = 0;
    }

     protected class SkipListNode<K extends Comparable<K>, V> {
        K key;
        V value;
        SkipListNode<K, V>[] forwards;

         protected SkipListNode(K key, V value, int level) {
             this.key = key;
             this.value = value;
             this.forwards = new SkipListNode[level + 1];
             for(int i = 0; i <= level; i++) {
                 this.forwards[i] = null;
             }
         }
    }

    public V findByKey(K key) {
        if (size == 0 || key == null) return null;

        SkipListNode[] targets = findMaxNodesLessThanKey(key);
        SkipListNode last = targets[0];

        if (last != null && last.forwards[0] != null && last.forwards[0].key.compareTo(key) == 0)
            return (V) last.forwards[0].value;

        return null;
    }

    private SkipListNode[] findMaxNodesLessThanKey(K key) {
        if(key == null) return new SkipListNode[0];
        if(size == 0) return head.forwards;

        SkipListNode last = head;
        SkipListNode[] targets = new SkipListNode[level + 1];
        for(int i = level; i >= 0; i--) {
            while(last.forwards != null && last.forwards[i].key.compareTo(key) < 0) {
                last = last.forwards[i];
            }
            targets[i] = last;
        }

        return targets;
    }

    public int randomLevel() {
        int level;
        for(level = 0; Math.abs(random.nextInt()) % 2 == 0; level++) ;
        return level;
    }

    public void insert(K key, V value) {
        int newLevel = randomLevel();
        if(newLevel > level) {
            adjustHead(newLevel);
        }

        SkipListNode node = new SkipListNode(key, value, newLevel);
        SkipListNode[] targets = findMaxNodesLessThanKey(key);
        for(int i = newLevel; i >= 0; i--) {
            node.forwards[i] = targets[i].forwards[i];
            targets[i].forwards[i] = node;
        }
        size++;
    }

    private void adjustHead(int newLevel) {
        SkipListNode newHead = new SkipListNode(null, null, newLevel);
        for(int i = 0; i <= level; i++) {
            newHead.forwards[i] = head.forwards[i];
        }
        head = newHead;
        level = newLevel;
    }
}




