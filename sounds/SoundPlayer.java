package sounds;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundPlayer extends JFrame {

   /**
    *
    */
   private static final long serialVersionUID = 1384723297941390677L;
   private String path;
   private URL url;
   private AudioInputStream audioIn;
   private Clip clip;
   public Volume volume = Volume.ON;
   

   public SoundPlayer(String path) {
      this.path = path;
      // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // this.setTitle("Test Sound Clip");
      // this.setSize(300, 200);
      // this.setVisible(true);
   }

   public void playSound() {
      try {
         // Open an audio input stream.
         this.url = this.getClass().getClassLoader().getResource("sounds\\"+this.path+".wav");
         this.audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         this.clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         this.clip.open(audioIn);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void stop(){
      this.volume = Volume.MUTE;
   }

   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
      else
         clip.stop();
   }

   

   public static void main(String[] args){
      SoundPlayer play = new SoundPlayer("sounds\\dark-forest.wav");
      try{Thread.sleep(1000);}catch(Exception e){}
      play.stop();
      
   }

   public enum Volume{
      ON,
      MUTE,
   }
   
}