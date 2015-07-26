package gameSounds;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

import logging.Logging;

public class GameSoundPlayer extends JButton
{
	// Didi ADD
	public final static String SOUND_MENUE_WAV = "sound_menue.wav";
	public final static String SOUND_SETTING_WAV = "sound_settings.wav";
	public final static String SOUND_GAME_WAV = "sound_game.wav";
	public final static String SOUND_CANNON_HIT = "sound_cannon_hit";
	public final static String SOUND_CANNON_MISS = "sound_cannon_miss";
	// public final static String SOUND_GAME_WAV = "";

	private Clip soundClip;
	private boolean soundIsOn;
	private String currentWavFileNameToPlay;

	// private enum SoundTypes
	// {
	// BOMB, BACKGROUND, WATERSHOOT;
	// }

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

	public void startBombSound(String wavFilename)
	{
		synchronized (wavFilename)
		{
			try
			{
				this.soundClip = AudioSystem.getClip();
				this.soundClip.open(AudioSystem.getAudioInputStream(new File(
						"sound\\" + wavFilename)));
			}
			catch (Exception e)
			{
				Logging.writeErrorMessage("Methode startBombSound Cannot play sound "
						+ wavFilename + " !");
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

	}
}
