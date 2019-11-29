package PanelControl;

import javax.sound.sampled.SourceDataLine;

public abstract class PlayProgress implements PanelControl{
    public static boolean paused = false;
    protected abstract void progressOperation(SourceDataLine line);

    @Override
    public void execute(SourceDataLine line) {
        progressOperation(line);
    }
}
