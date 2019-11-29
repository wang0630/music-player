package PanelControl;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.SourceDataLine;

public class Unmute extends VolumeStatus {
    @Override
    protected void muteOperation(SourceDataLine line) {
        BooleanControl muteControl = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
        muteControl.setValue(false);
        Mute.muted = false;
        // Unmute, so we have to put the current volume back to UI
        Volume.setVolumeToLabel();
    }
}