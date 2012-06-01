/*
 * Mp3Player.
 * 
 * 
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package com.audio.player;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.mongo.gridfs.GridFSFileLoader;

public class Mp3Player {
	
	GridFSFileLoader loader = new GridFSFileLoader();
	
	public void playMp3(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		InputStream in = new BufferedInputStream(loader.retrieveFileStream(fileName));

		AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
		AudioFormat decodeFormat = buildDecodeFormat(audioIn.getFormat());
		
		AudioInputStream din  = AudioSystem.getAudioInputStream(decodeFormat, audioIn);
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class,
				decodeFormat));
		line.open(decodeFormat);
		if (line != null) {
			playAudio(din, line);
		}
	}

	private AudioFormat buildDecodeFormat(AudioFormat baseFormat) {
		return new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
				16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(), false);
	}

	private void playAudio(AudioInputStream din, SourceDataLine line)
			throws IOException {
		byte[] data = new byte[4096]; // 4k is a reasonable transfer size.
		// Start
		line.start(); // Start the line.

		int nBytesRead;
		while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
			line.write(data, 0, nBytesRead);
		}
		// Stop
		line.drain();
		line.stop();
		line.close();
		din.close();
	}

}
