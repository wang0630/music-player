package Threading;

import BoundedBuffer.BoundedBuffer;

public class Producer extends AbstractThreading//producer thread read audio data to be written
{
    public Producer(BoundedBuffer bb) {
        super(bb);
    }

    @Override
    public boolean threadingAction() {
        return boundedBuffer.insertChunk();
    }
}
