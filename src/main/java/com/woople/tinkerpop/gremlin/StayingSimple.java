package com.woople.tinkerpop.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class StayingSimple {
    public static void main(String[] args) {
        TinkerGraph tg = TinkerFactory.createModern();
        GraphTraversalSource g = tg.traversal();
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
