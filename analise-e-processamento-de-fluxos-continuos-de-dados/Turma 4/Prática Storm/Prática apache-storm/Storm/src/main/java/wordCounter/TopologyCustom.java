package wordCounter;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyCustom {
	public static void main(String[] args) throws InterruptedException {

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-counter", new WordCounter(),2)
				.customGrouping("word-reader", new alphaGrouping());


		Config conf = new Config();
		conf.put("fileToRead", "/home/CIT/pkassio/Documents/Storm/sample.txt");
		conf.put("dirToWrite", "/home/CIT/pkassio/Documents/Storm/output/");
		conf.setDebug(true);

		LocalCluster cluster = new LocalCluster();
		try{
			cluster.submitTopology("WordCounter-Topology", conf, builder.createTopology());
			Thread.sleep(10000);
		}
		finally {
			cluster.shutdown();
		}

	}

}
