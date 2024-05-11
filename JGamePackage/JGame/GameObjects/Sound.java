package JGamePackage.JGame.GameObjects;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound extends GameObject{
    private Clip sound;
    private File file;
    public float Volume = 1f;
    public boolean Playing;

    public Sound(File file){
        this.file = file;


        try {
            sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(this.file));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public Sound(String path){
        this(new File(path));
    }

    /**Reumes this sounds' playback
     * 
     */
    public void Play(){
        Playing = true;
        this.sound.start();
    }

    /**Pauses playback of this sound
     * 
     */
    public void Pause(){
        Playing = false;
        this.sound.stop();
    }

    /**Pauses playback of this Sound and returns the frame position to 0
     * 
     */
    public void Stop(){
        this.Pause();
        this.sound.setFramePosition(0);
    }

    public void setInfiniteLoop(boolean shouldLoop){
        this.sound.loop(shouldLoop ? Clip.LOOP_CONTINUOUSLY : 0);
    }

    public void setLoop(int loops){
        this.sound.loop(loops);
    }

    public void SetVolume(float volume){
        FloatControl gainControl = (FloatControl) this.sound.getControl(FloatControl.Type.MASTER_GAIN);

        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
        this.Volume = volume;
    }
}
