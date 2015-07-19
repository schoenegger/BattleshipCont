package gameSounds;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSoundPlayer
{
	private enum SoundTypes
	{
		BOMB, BACKGROUND, WATERSHOOT;
	}

	private Clip soundClip;

	public GameSoundPlayer()
	{
		startBackgroundSound();
	}

	public void startBackgroundSound()
	{
		try
		{
			this.soundClip = AudioSystem.getClip();
			this.soundClip.open(AudioSystem.getAudioInputStream(new File(
					"sound\\sound_menue.wav")));
		}
		catch (Exception e)
		{
			// logger no file loading possible
			e.printStackTrace();
		}

		soundClip.loop(Clip.LOOP_CONTINUOUSLY);

	}

	public void startBombSound()
	{

	}

	// Clip clip = AudioSystem.getClip();
	// clip.open(AudioSystem.getAudioInputStream(soundFileName));

}
