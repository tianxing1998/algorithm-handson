package com.algorithm.arch;

import java.util.concurrent.atomic.AtomicInteger;

public class DoubleBufferData {
    public static void main(String[] args) {
        DoubleBufferData bufferData = new DoubleBufferData();
        Data data = bufferData.get();
        data.Unref();
    }
    private Data[] storage = new Data[2];
    private AtomicInteger idx = new AtomicInteger(0);
    private Lock lock = new Lock();

    public void set(int value) {
        this.lock.Lock();
        int target = otherIdx();
        this.storage[target].value = value;
        this.storage[target].Ref();
        this.idx.set(target);
        target = otherIdx();
        this.storage[target].Unref();
        while (this.storage[target].cnt != 0) {
        }
        this.lock.UnLock();
    }

    public Data get() {
        return (Data) this.storage[this.idx.get()].Ref();
    }

    private int otherIdx() {
        return (this.idx.get() + 1) % 2;
    }
}

class Lock {
    void Lock() {}
    void UnLock() {}
}

class DataRef<T extends DataRef> {
    int cnt = 0;
    DataRef Ref() {
        this.cnt++;
        return this;
    }
    DataRef Unref() {
        this.cnt--;
        return this;
    }
}

/**
 * readonly
 */
class Data extends DataRef<Data> {
    int value;
}





