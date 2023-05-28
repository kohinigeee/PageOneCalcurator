package com.example.pageoneculator;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Queue;

public class IdManageQueue implements java.io.Serializable {

    private final static long serializeUID = 20;
    private Queue<Integer> deque;
    private int maxID;

    public IdManageQueue(int maxID ) {
        this.deque = new ArrayDeque<>();
        this.maxID = maxID;
        for ( int i = 0; i <= maxID; ++i ) {
            deque.add(i);
        }
    }

    public Integer getNext() {
        return deque.poll();
    }

    public void setNext( Integer next ) {
        deque.add(next);
    }


}
