class Consumer extends Thread //consumer thread that writes audio to device
{

    private BoundedBuffer BoundedBuffer;
    private boolean alive = true;

    public Consumer(BoundedBuffer bb)
    {
        BoundedBuffer = bb;
    }

    public void run()
    {
        while(alive) //loops until alive = false is return (when 'x' is entered or song ends)
        {
            alive = BoundedBuffer.removeChunk();
        }
    }
}
