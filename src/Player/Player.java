package Player;
// Multi-threaded music player that plays a WAV file
// Threading.Producer thread reads in 10 seconds of file
// Threading.Consumer thread writes the 10 seconds to audio device
// @author Conor Hughes
import BoundedBuffer.BoundedBuffer;
import Threading.Consumer;
import Threading.Producer;

import javax.sound.sampled.*;
import java.io.*;
import java.lang.*;

public class Player
{
    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		BoundedBuffer bb = new BoundedBuffer();
		bb.readFile();
        Thread p1 = new Thread(new Producer(bb));
        Thread c1 = new Thread(new Consumer(bb));

        p1.start();
		c1.start();
		p1.join();
		c1.join();
    }
}
