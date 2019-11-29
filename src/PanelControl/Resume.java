package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.SourceDataLine;

public class Resume extends PlayProgress {
    @Override
    protected void progressOperation(SourceDataLine line) {
        PlayProgress.paused = false;
        line.start();
        if(VolumeStatus.muted == true)
        {
            BoundedBuffer.l3.setText("SONG MUTED!");
        }
        else
        {
            Volume.setVolumeToLabel();
        }
    }
}
