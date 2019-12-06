package com.woople.tinkerpop.gremlin;

import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.tinkerpop.gremlin.hadoop.structure.HadoopGraph;
import org.apache.tinkerpop.gremlin.process.computer.clone.CloneVertexProgram;
import org.apache.tinkerpop.gremlin.spark.process.computer.SparkGraphComputer;

import java.io.File;
import java.util.Map;

public class HadoopGraphSparkComputerDemo {
    public static void main(String[] args) throws Exception {

        FileConfiguration configuration = new PropertiesConfiguration();
        configuration.load(new File(args[0]));

        String[] resourceFiles = configuration.getStringArray("woople.resource.files");

        final Configuration hadoopConfig = new Configuration(false);

        for (String path : resourceFiles) {
            hadoopConfig.addResource(new File(path).toURI().toURL());
        }

        //String graphProFile = configuration.getString("woople.graph.properties");

        HadoopGraph graph = HadoopGraph.open(configuration);//"conf/hadoop-graphson.properties"

        //System.setProperty("HADOOP_USER_NAME", "ocsp");

        for (Map.Entry<String, String> p : hadoopConfig) {
            graph.configuration().addProperty(p.getKey(), p.getValue());
            graph.configuration().addProperty("spark.hadoop."+p.getKey(), p.getValue());
        }

        graph.compute(SparkGraphComputer.class).program(CloneVertexProgram.build().create()).submit().get();
    }
}
