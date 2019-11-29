package Threading;

import BoundedBuffer.BoundedBuffer;

public abstract class AbstractThreading implements Runnable {
    protected BoundedBuffer boundedBuffer;
    protected boolean alive = true;
    abstract boolean threadingAction();

    public AbstractThreading(BoundedBuffer bb) {
        boundedBuffer = bb;
    }

    // The function which executes in the thread
    public void run() {
        while(alive) {
            alive = threadingAction();
        }
    }
}
