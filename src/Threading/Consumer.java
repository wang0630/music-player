package Threading;

import BoundedBuffer.BoundedBuffer;

public class Consumer extends Thread //consumer thread that writes audio to device
{
    private BoundedBuffer boundedBuffer;
    private boolean alive = true;

    public Consumer(BoundedBuffer bb)
    {
        boundedBuffer = bb;
    }

    public void run()
    {
        while(alive) //loops until alive = false is return (when 'x' is entered or song ends)
        {
            alive = boundedBuffer.removeChunk();
        }
    }
}
