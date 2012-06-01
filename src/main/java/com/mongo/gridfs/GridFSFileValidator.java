/*
 * GridFSFileLoader
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
package com.mongo.gridfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GridFSFileValidator {

	private File fileToLoad;

	public InputStream loadFile(String fileName) {
		validateMp3FileName(fileName);

		fileToLoad = new File(fileName);

		if (!fileToLoad.exists()) {
			throwIllegalArgumentException("The audio file " + fileName
					+ " does not exist.");
		}
		return getFileStream();

	}

	private void validateMp3FileName(String fileName) {
		if (fileName == null) {
			throwIllegalArgumentException("The file name must not be null.");
		}
		if (!fileName.endsWith(".mp3")) {
			throwIllegalArgumentException("Only mp3 files are currently supported.");
		}
	}

	private void throwIllegalArgumentException(String message) {
		throw new IllegalArgumentException(message);
	}

	public InputStream getFileStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileToLoad);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(
					"A valid mp3 file has not been set.");
		}
		return fis;
	}

}
