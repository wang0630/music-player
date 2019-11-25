package Threading;

import BoundedBuffer.BoundedBuffer;

public class Producer implements Runnable //producer thread read audio data to be written
{

    private BoundedBuffer boundedBuffer;
    private boolean alive = true;

    public Producer(BoundedBuffer bb)
    {
        boundedBuffer = bb;
    }

    public void run() //loops until alive = false is return (when 'x' is entered or song ends)
    {
        while(alive)
        {
            alive = boundedBuffer.insertChunk();
        }
        System.exit(0);
    }
}
