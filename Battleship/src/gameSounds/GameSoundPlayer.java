package gameSounds;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;

import logging.Logging;

public class GameSoundPlayer extends JButton
{
	// Didi ADD
	public final static String SOUND_MENUE_WAV = "sound_menue.wav";
	public final static String SOUND_SETTING_WAV = "sound_settings.wav";
	public final static String SOUND_GAME_WAV = "sound_game.wav";
	public final static String SOUND_CANNON_HIT = "sound_hit.wav";
	public final static String SOUND_CANNON_MISS = "sound_miss.wav";

	public final static Float VOLUME_ENEMY_CANNON_SOUND = (float) -10.0;
	public final static Float VOLUME_OWN_CANNON_SOUND = (float) 0.0;

	private Clip soundClip;
	private boolean soundIsOn;
	private String currentWavFileNameToPlay;
	private FloatControl gainControl;

	public GameSoundPlayer()
	{
		soundIsOn = true;
		currentWavFileNameToPlay = GameSoundPlayer.SOUND_MENUE_WAV;
		startBackgroundSound(GameSoundPlayer.SOUND_MENUE_WAV);
	}

	public void turnSoundOnOrOFF()
	{
		if (soundIsOn)
		{
			soundIsOn = false;
			stopBackGroundSounds();
		}
		else
		{
			soundIsOn = true;
			this.startBackgroundSound(this.currentWavFileNameToPlay);
		}
	}

	/**
	 * Use this Method in your code only by switching windows
	 * (open-close-events).
	 * 
	 * @see #turnSoundOnOrOFF()
	 */
	public void stopBackGroundSounds()
	{
		soundClip.stop();
	}

	/**
	 * start the background Sound
	 * 
	 * @param wavFilename
	 */
	public void startBackgroundSound(String wavFilename)
	{
		currentWavFileNameToPlay = wavFilename;
		if (soundIsOn)
		{
			synchronized (wavFilename)
			{
				try
				{
					this.soundClip = AudioSystem.getClip();
					this.soundClip.open(AudioSystem
							.getAudioInputStream(new File("sound\\"
									+ wavFilename)));
				}
				catch (Exception e)
				{
					Logging.writeErrorMessage("Methode startBackgroundSound Cannot play sound "
							+ wavFilename + " !");
					System.err.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
				soundClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}

	}

	/**
	 * start the bomb sound
	 * 
	 * @param wavFilename
	 * @param soundVolume
	 */
	public void startBombSound(String wavFilename, float soundVolume)
	{
		if (soundIsOn)
		{
			synchronized (wavFilename)
			{
				try
				{
					this.soundClip = AudioSystem.getClip();
					this.soundClip.open(AudioSystem
							.getAudioInputStream(new File("sound\\"
									+ wavFilename)));
				}
				catch (Exception e)
				{
					Logging.writeErrorMessage("Methode startBombSound Cannot play sound "
							+ wavFilename + " !");
					System.err.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
				soundClip.start();
				gainControl = (FloatControl) soundClip
						.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(soundVolume);
			}
		}
	}
}
