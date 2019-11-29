package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.SourceDataLine;

public class Exit implements PanelControl{
    @Override
    public void execute(SourceDataLine line) {
        BoundedBuffer.l2.setText("Thanks");
        BoundedBuffer.l3.setText("For");
        BoundedBuffer.l3.setText("Listening");
        BoundedBuffer.alive = false; // drains, stops and closes line. returns alive = false to end thread
    }
}
