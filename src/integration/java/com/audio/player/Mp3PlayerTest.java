package com.audio.player;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mongo.gridfs.GridFSFileLoader;

public class Mp3PlayerTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final String FILE_NAME = "./Like_Popeye_64kb.mp3";

	@Test
	public void playThatFunkyMusicWhiteBoy() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		GridFSFileLoader loader = new GridFSFileLoader();
		loader.loadFile(FILE_NAME, null, null);
		Mp3Player player = new Mp3Player();
		player.playMp3(FILE_NAME);

		loader.remove(FILE_NAME);
	}

}
