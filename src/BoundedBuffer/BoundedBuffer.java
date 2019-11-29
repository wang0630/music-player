package BoundedBuffer;

import PanelControl.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoundedBuffer extends JFrame implements KeyListener
{
    // class.getResource will load the file relative to the classPath of this class
    // In which case from out/production/sd_music_recreate
    private String wavFile = BoundedBuffer.class.getResource("/example.wav").getFile(); //Enter name of wav file here eg: song.wav **Must be in same directory as player.java**
    private File fileIn = new File(wavFile);

    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private SourceDataLine line;

    // true indicates the data is ready to play
    private boolean dataAvailable = false;
    // true if the buffer still has spaces for data to be written
    private boolean roomAvailable = true;

    public static boolean alive = true;
    private int bufferSize = 282240;
    
    private int oneSecond = 28224;
    private int nextIn;
    private int nextOut;
    private int bytesRead;
    private int bytesWritten;
    private byte[] audioChunk;

    // The keyEvent for the control
    private HashMap<Character, PanelControl> panelControlMap = new HashMap<Character, PanelControl>();


    // Make labels static, allow PanelControl package to access it
    public static JLabel l1;
    public static JLabel l2;
    public static JLabel l3;

    public BoundedBuffer() //GUI from player JFrame
    {
        setTitle("Music Player");
        Panel p = new Panel();
        l1 = new JLabel ("NOW PLAYING: " + fileIn, SwingConstants.CENTER);
        l2 = new JLabel("<html><br/>MUSIC PLAYER CONTROLS<br/><br/> Stop: e <br/>Higher Volume: f <br/>Lower Volume: d <br/>Pause: p <br/>Resume: r <br/>Mute: m <br/>Unmute: u<br/><br/></html>", SwingConstants.CENTER);
        l3 = new JLabel("CURRENT VOLUME: " + 2.0f, SwingConstants.CENTER);
        p.add(l1);
        p.add(l2);
        p.add(l3);
        add(p);
        addKeyListener (this) ;
        setSize (400,400 );
        setVisible(true);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // Add panelControl to the map
        setPanelControlMap('f', new VolumeUp());
        setPanelControlMap('d', new VolumeDown());
        setPanelControlMap('m', new Mute());
        setPanelControlMap('u', new Unmute());
        setPanelControlMap('p', new Pause());
        setPanelControlMap('r', new Resume());
        setPanelControlMap('e', new Exit());
    }

    public void readFile() throws UnsupportedAudioFileException, IOException,  LineUnavailableException //reads in file, format and info
    {
        audioStream = AudioSystem.getAudioInputStream(fileIn);
        format = audioStream.getFormat();
        System.out.println(SourceDataLine.class);
        info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
    }

    private void setPanelControlMap(char key, PanelControl p) {
        panelControlMap.put(key, p);
    }

    private void doPanelControl(char key) {
        if (panelControlMap.containsKey(key)) {
            panelControlMap.get(key).execute(line);
        }
    }

    public void keyPressed (KeyEvent e) //checks for key presses
    {
        char control = e.getKeyChar();
        doPanelControl(control);
    }


    public synchronized boolean insertChunk()
    {
        while(!roomAvailable && dataAvailable)
        {
            try
            {
                wait(); // waits until all of data is written (played)
            }
            catch (InterruptedException e) { }
        }

        try
        {
            audioChunk = new byte[bufferSize];
            // If the chunk is full(10 secs), we break this while loop
            while(nextIn != bufferSize && alive)
            {
                if((bytesRead = audioStream.read(audioChunk, nextIn, oneSecond)) == -1 && !PlayProgress.paused)//reads in 10 one second chunks, if nothing is read, terminate thread
                {
                    l2.setText("Thanks For Listening");
                    line.drain();
                    line.stop();
                    line.close();
                    alive = false; // drains, stops and closes line. returns alive = false to end thread
                }
                nextIn += oneSecond;
            }
            nextIn = 0;
        }
        catch (IOException e) { }

        dataAvailable = true; // audio data is available to be written
        roomAvailable = false; // no room for audio data to be read in, buffer is full
        notifyAll(); // // notify threads which are waiting

        return alive; // alive, thread will loop if true
    }

    // We must implement these two functions since BoundedBuffer implements KeyListener
    public void keyTyped ( KeyEvent e ){}
    public void keyReleased ( KeyEvent e ){}

    public synchronized boolean removeChunk() {
        while(roomAvailable && !dataAvailable)
        {
            try
            {
                wait(); // waits until data is available to be written
            }
            catch (InterruptedException e) { }
        }

        while(nextOut != bufferSize && alive)
        {
            try {
                // We need to wait for some time for thread to restart
                Thread.sleep(1);
                if (!PlayProgress.paused) {
                    bytesWritten += line.write(audioChunk, nextOut, bytesRead); //writes 10 seconds to audio (plays)
                    nextOut += oneSecond;
                }
            } catch (InterruptedException e) { }
        }
        nextOut = 0;

        dataAvailable = false; //audio data is not available to be written
        roomAvailable = true; //room for audio data to be read in is available
        notifyAll(); // notify threads which are waiting

        return alive; // alive, thread will loop if true
    }
}
