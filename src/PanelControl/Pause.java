package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.SourceDataLine;

public class Pause extends PlayProgress {
    @Override
    protected void progressOperation(SourceDataLine line) {
        line.stop();
        PlayProgress.paused = true;
        BoundedBuffer.l3.setText("SONG PAUSED!");
    }
}
