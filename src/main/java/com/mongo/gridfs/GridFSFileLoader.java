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

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class GridFSFileLoader {

	private GridFS gridfs;
	private Mongo mon;

	public GridFSFileLoader() {
		try {
			mon = new Mongo();
		} catch (UnknownHostException e) {
			throw new IllegalStateException(e);
		} catch (MongoException e) {
			throw new IllegalStateException(e);
		}

		gridfs = new GridFS(mon.getDB("filernd"));
	}

	public void loadFile(String fileToLoad, String contentType,
			Map<String, String> metaData) {
		InputStream is = new GridFSFileValidator().loadFile(fileToLoad);
		GridFSInputFile file = gridfs.createFile(is);
		file.setContentType(contentType);
		file.setFilename(fileToLoad);
		BasicDBObject metadata = new BasicDBObject();
		if (metaData != null) {
			for (Entry<String, String> entry : metaData.entrySet()) {
				metadata.put(entry.getKey(), entry.getValue());
			}
		}
		file.setMetaData(metadata);
		file.save();
	}
	
	public InputStream retrieveFileStream(String fileName) {
		return gridfs.findOne(fileName).getInputStream();
	}

	public void remove(String fileName) {
		gridfs.remove(fileName);
	}

}
