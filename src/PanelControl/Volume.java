package PanelControl;

import javax.sound.sampled.SourceDataLine;

public abstract class Volume implements PanelControl {
    public static final float VOLUME_MAX = 6.0f;
    public static final float VOLUME_MIN = -10.0f;
    public static float volume = 2.0f;
    protected abstract void adjustVolume(SourceDataLine line);
    @Override
    public void execute(SourceDataLine line) {
        adjustVolume(line);
    }
}
