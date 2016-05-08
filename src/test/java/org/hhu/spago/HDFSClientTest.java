package org.hhu.spago;

import java.io.IOException;

import org.hhu.spago.hdfs.HDFSClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HDFSClientTest {
static HDFSClient client;

	@Before
	public  void setUpBeforeClass() throws Exception {
		System.out.println("@Before");
		client = new HDFSClient("F:\\hadoop-2.6.0\\etc\\hadoop");
	}

	@After
	public  void tearDownAfterClass() throws Exception {
		System.out.println("@After");
		client.close();
	}

	@Test
	public void CopyFileFromLocal(){
		System.out.println("testCopyFileFromLocal");
		try {
			client.copyFileFromLocal("F:\\tradata\\Data\\000\\Trajectory\\20081023025304.plt","/plt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
