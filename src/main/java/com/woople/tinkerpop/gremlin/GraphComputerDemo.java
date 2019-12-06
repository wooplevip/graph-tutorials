package com.woople.tinkerpop.gremlin;

import org.apache.tinkerpop.gremlin.process.computer.ComputerResult;
import org.apache.tinkerpop.gremlin.process.computer.ranking.pagerank.PageRankVertexProgram;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class GraphComputerDemo {
    public static void main(String[] args) throws Exception {
        TinkerGraph graph = TinkerFactory.createModern();
        ComputerResult result = graph.compute().program(PageRankVertexProgram.build().create()).submit().get();

        System.out.println(result.memory().getRuntime());

        GraphTraversalSource g = result.graph().traversal();

        GraphTraversal graphTraversal = g.V().valueMap();

        while (graphTraversal.hasNext()){
            Object object = graphTraversal.next();
            System.out.println(object);
        }

    }
}
