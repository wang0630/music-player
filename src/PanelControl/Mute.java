package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.SourceDataLine;

public class Mute extends VolumeStatus {
    @Override
    protected void muteOperation(SourceDataLine line) {
        BooleanControl muteControl = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
        muteControl.setValue(true);
        Mute.muted = true;
        BoundedBuffer.l3.setText("SONG MUTED!");
    }
}
