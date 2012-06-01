package com.mongo.gridfs;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GridFSFileValidatorTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private GridFSFileValidator fileLoader;

	@Before
	public void setup() {
		fileLoader = new GridFSFileValidator();
	}
	
	@After
	public void teardown() {
		fileLoader = null;
	}
	
	@Test
	public void nullArgumentToLoadFileThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("The file name must not be null.");
		fileLoader.loadFile(null);
		
	}
	
	@Test
	public void onlyMp3sAreCurrentlyAllowedOthersThrowIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Only mp3 files are currently supported.");
		fileLoader.loadFile("file.ogg");
	}

	@Test
	public void noSuchFileThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("The audio file ./noFile.mp3 does not exist.");
		fileLoader.loadFile("./noFile.mp3");
	}
	
	@Test
	public void canGetFileStream() {
		fileLoader.loadFile("./ultimate_showdown.mp3");
		assertNotNull(fileLoader.getFileStream());
	}

}
