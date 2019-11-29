package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class VolumeDown extends Volume{
    @Override
    protected void adjustVolume(SourceDataLine line) {
        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

        if(volume == Volume.VOLUME_MIN)
        {
            BoundedBuffer.l3.setText("MINIMUM VOLUME REACHED!"); //update label with current volume
        }
        else
        {
            volume -=1.0f;
            gainControl.setValue(volume);
            BoundedBuffer.l3.setText("CURRENT VOLUME: " + volume); //update label with current volume
        }
    }
}
