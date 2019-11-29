package PanelControl;

import javax.sound.sampled.SourceDataLine;

public abstract class VolumeStatus implements PanelControl{
    public static boolean muted = false;
    protected abstract void muteOperation(SourceDataLine line);

    @Override
    public void execute(SourceDataLine line) {
        muteOperation(line);
    }
}
