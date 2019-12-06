package com.woople.tinkerpop.gremlin;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.tinkerpop.gremlin.hadoop.structure.HadoopGraph;
import org.apache.tinkerpop.gremlin.hadoop.structure.io.FileSystemStorage;
import org.apache.tinkerpop.gremlin.process.computer.ComputerResult;
import org.apache.tinkerpop.gremlin.process.computer.ranking.pagerank.PageRankVertexProgram;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HadoopGraphComputerDemo {
    public static void main(String[] args) throws Exception {

        final Configuration hadoopConfig = new Configuration(false);
        hadoopConfig.addResource(new File("/Users/peng/SandBox/Dev/MyBranch/graph-tutorials/conf/core-site.xml").toURI().toURL());
        hadoopConfig.addResource(new File("/Users/peng/SandBox/Dev/MyBranch/graph-tutorials/conf/hdfs-site.xml").toURI().toURL());
        hadoopConfig.addResource(new File("/Users/peng/SandBox/Dev/MyBranch/graph-tutorials/conf/yarn-site.xml").toURI().toURL());

//        FileSystemStorage hdfs = FileSystemStorage.open(hadoopConfig);
//        hdfs.copyFromLocal("./data/tinkerpop-modern.json", "/tmp");

        HadoopGraph graph = HadoopGraph.open("conf/hadoop-graphson.properties");

        for (Map.Entry<String, String> p : hadoopConfig) {
            graph.configuration().addProperty(p.getKey(), p.getValue());
        }

        GraphTraversalSource g = graph.traversal();
        //What software has Marko created?

        GraphTraversal graphTraversal = g.V()
                .has("person", "name","marko")
                .out("created")
                .values("name");

        while (graphTraversal.hasNext()){
            Object object = graphTraversal.next();
            System.out.println(object);
        }

    }
}
