package PanelControl;

import BoundedBuffer.BoundedBuffer;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class VolumeDown extends Volume{
    @Override
    protected void adjustVolume(SourceDataLine line) {
        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

        if(Volume.volume == Volume.VOLUME_MIN)
        {
            BoundedBuffer.l3.setText("MINIMUM VOLUME REACHED!"); //update label with current volume
        }
        else
        {
            Volume.volume -=1.0f;
            gainControl.setValue(Volume.volume);
            setVolumeToLabel();
        }
    }
}
