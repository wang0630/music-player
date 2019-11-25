package Threading;

import BoundedBuffer.BoundedBuffer;

public class Consumer extends AbstractThreading //consumer thread that writes audio to device
{
    public Consumer(BoundedBuffer bb) {
        super(bb);
    }

    @Override
    public boolean threadingAction() {
        return boundedBuffer.removeChunk();
    }
}
