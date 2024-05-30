package AirTaxi.Classes;

import JGamePackage.JGame.GameObjects.Sound;
import JGamePackage.lib.task;

public class MusicQueue {

    private Sound[] queue;
    private Sound cursound;
    private int curSoundIndex = -1;
    public double Volume = 1;

    public MusicQueue(String[] paths){
        queue = new Sound[paths.length];
        for (int i = 0; i < queue.length; i++){
            queue[i] = new Sound(paths[i]);
        }
    }

    public void Pause(){
        if (cursound==null) return;
        cursound.Pause();
    }

    public void Continue(){
        if (cursound==null) return;
        cursound.UnPause();
    }

    public void StopCurrent(){
        if (cursound==null) return;
        cursound.Stop();
    }

    public void Start(){
        task.spawn(()->{
            while (true){
                playNextSound();
            }
        });
    }

    public void SetVolume(double volume){
        Volume = volume;
        for (Sound s : queue){
            s.SetVolume(volume);
        }
    }

    private void advanceSoundIndex(){
        curSoundIndex++;
        if (curSoundIndex >= queue.length){
            curSoundIndex = 0;
        }
    }
    

    private void playNextSound(){
        advanceSoundIndex();
        cursound = queue[curSoundIndex];
        cursound.SetFramePosition(0);
        cursound.Play();
        task.wait(cursound.Length);
    }
}
