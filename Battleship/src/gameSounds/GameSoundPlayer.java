package gameSounds;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSoundPlayer
{
	// Didi ADD
	public final static String SOUND_MENUE_WAV = "sound_menue.wav";
	public final static String SOUND_SETTING_WAV = "sound_settings.wav";

	// public final static String SOUND_GAME_WAV = "";

	private enum SoundTypes
	{
		BOMB, BACKGROUND, WATERSHOOT;
	}

	private Clip soundClip;

	public GameSoundPlayer()
	{
		startBackgroundSound(GameSoundPlayer.SOUND_MENUE_WAV);
	}

	public void startBackgroundSound(String wavFilename)
	{
		try
		{
			this.soundClip = AudioSystem.getClip();
			this.soundClip.open(AudioSystem.getAudioInputStream(new File(
					"sound\\" + wavFilename)));
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

	public void stopBackGroundSounds()
	{
		soundClip.stop();
	}
	// Clip clip = AudioSystem.getClip();
	// clip.open(AudioSystem.getAudioInputStream(soundFileName));

}
