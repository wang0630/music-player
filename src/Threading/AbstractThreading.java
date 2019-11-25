package Threading;

import BoundedBuffer.BoundedBuffer;

public abstract class AbstractThreading implements Runnable {
    protected BoundedBuffer boundedBuffer;
    protected boolean alive = true;
    abstract boolean threadingAction();

    public AbstractThreading(BoundedBuffer bb) {
        boundedBuffer = bb;
    }

    public void run() {
        while(alive) {
            alive = threadingAction();
        }
    }
}
