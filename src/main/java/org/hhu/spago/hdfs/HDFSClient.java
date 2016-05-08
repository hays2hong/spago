package org.hhu.spago.hdfs;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSClient implements Closeable{
	private File coreSiteConfPath;
	private File hdfsSiteConfPath;

	private Path coreSitePath;
	private Path hdfsSitePath;

	private Configuration conf;
	private FileSystem fileSystem;

	// The config directory is passed by the user's main program
	public HDFSClient(String siteConfDir) throws Exception {
		parseSiteConfDir(siteConfDir);

		coreSitePath = new Path(coreSiteConfPath.getPath());
		hdfsSitePath = new Path(hdfsSiteConfPath.getPath());

		conf = new Configuration();
		// Conf object will read the HDFS configuration parameters from these
		// XML files.
		conf.addResource(coreSitePath);
		conf.addResource(hdfsSitePath);

		try {
			fileSystem = FileSystem.get(conf);
		} catch (IOException e) { // failed or interrupted I/O operations
			throw e;
		}
	}

	private void parseSiteConfDir(String siteConfDir) throws Exception {
		File siteConfDirPath = new File(siteConfDir);

		if (siteConfDirPath.exists() == false) {
			throw new Exception("The argument " + siteConfDirPath.getPath() + " does not exist.");
		}
		if (siteConfDirPath.isDirectory() == false) {
			throw new Exception("The argument " + siteConfDirPath.getPath() + " must be a directory.");
		}
		if (siteConfDirPath.canRead() == false) {
			throw new Exception("The argument " + siteConfDirPath.getPath() + " is not readable by me.");
		}

		coreSiteConfPath = new File(siteConfDirPath, "core-site.xml");
		if (coreSiteConfPath.exists() == false) {
			throw new Exception("The argument " + coreSiteConfPath.getPath() + " does not exist.");
		}
		if (coreSiteConfPath.isFile() == false) {
			throw new Exception("The argument " + coreSiteConfPath.getPath() + " must be a file.");
		}
		if (coreSiteConfPath.canRead() == false) {
			throw new Exception("The argument " + coreSiteConfPath.getPath() + " is not readable by me.");
		}

		hdfsSiteConfPath = new File(siteConfDirPath, "hdfs-site.xml");
		if (hdfsSiteConfPath.exists() == false) {
			throw new Exception("The argument " + hdfsSiteConfPath.getPath() + " does not exist.");
		}
		if (hdfsSiteConfPath.isFile() == false) {
			throw new Exception("The argument " + hdfsSiteConfPath.getPath() + " must be a file.");
		}
		if (hdfsSiteConfPath.canRead() == false) {
			throw new Exception("The argument " + hdfsSiteConfPath.getPath() + " is not readable by me.");
		}
	}

	public void copyFileFromLocal(String source, String dest) throws IOException {
		// Check if the file already exists
		Path destPath = new Path(dest);
		if (fileSystem.exists(destPath)) {
			throw new IOException("File " + dest + " already exists");
		}

		// Create a new file and write data to it
		fileSystem.copyFromLocalFile(false, true, new Path(source), destPath);
		
	}

	public InputStream getInputStream(String file) throws IOException {
		Path path = new Path(file);
		if (!fileSystem.exists(path)) {
			throw new IOException("File " + file + " does not exist");
		}

		return fileSystem.open(path);
	}

	public void deleteFile(String file) throws IOException {
		Path path = new Path(file);
		if (!fileSystem.exists(path)) {
			throw new IOException("File " + file + " does not exist");
		}

		fileSystem.delete(new Path(file), true);
	}

	public void mkdir(String dir) throws IOException {
		Path path = new Path(dir);
		if (fileSystem.exists(path)) {
			throw new IOException("Dir " + dir + " already exists");
		}

		fileSystem.mkdirs(path);
	}

	public FileStatus[] listStatus(String dir) throws IOException {
		Path path = new Path(dir);
		if (fileSystem.exists(path)) {
			throw new IOException("Dir " + dir + " already exists");
		}

		return fileSystem.listStatus(new Path(dir));
	}

	public boolean renameFile(String oldFileName, String newFileName) throws IOException {
		Path path = new Path(oldFileName);
		if (!fileSystem.exists(path)) {
			throw new IOException("File " + oldFileName + " does not exist");
		}

		Path oldPath = new Path(oldFileName);
		Path newPath = new Path(newFileName);
		return fileSystem.rename(oldPath, newPath);
	}
	
	public void close() throws IOException {
		fileSystem.close();
	}
}
