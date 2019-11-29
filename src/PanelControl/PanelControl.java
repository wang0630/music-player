package PanelControl;

import javax.sound.sampled.SourceDataLine;

public interface PanelControl {
    public abstract void execute(SourceDataLine line);
}
