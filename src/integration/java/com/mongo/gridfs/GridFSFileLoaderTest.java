package com.mongo.gridfs;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;

public class GridFSFileLoaderTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private static final String FILE_NAME = "./Like_Popeye_64kb.mp3";
	private GridFSFileLoader loader;
	private static Mongo mon;
	private static GridFS gridfs;

	@BeforeClass
	public static void before() {
		try {
			mon = new Mongo();
			gridfs = new GridFS(mon.getDB("filernd"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	@Before
	public void setup() throws UnknownHostException, MongoException {
		loader = new GridFSFileLoader();
	}
	
	@After
	public void teardown() {
		loader = null;
		gridfs.remove(FILE_NAME);
	}

	@Test
	public void noErrorsThrownOnSuccess() {
		loader.loadFile(FILE_NAME, null, null);
	}

}
