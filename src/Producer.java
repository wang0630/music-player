class Producer implements Runnable //producer thread read audio data to be written
{

    private BoundedBuffer BoundedBuffer;
    private boolean alive = true;

    public Producer(BoundedBuffer bb)
    {
        BoundedBuffer = bb;
    }

    public void run() //loops until alive = false is return (when 'x' is entered or song ends)
    {
        while(alive)
        {
            alive = BoundedBuffer.insertChunk();
        }
        System.exit(0);
    }
}
