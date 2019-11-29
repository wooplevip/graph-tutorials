package com.woople.tinkerpop.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class StayingSimplePlus {
    public static void main(String[] args) {
        TinkerGraph tg = TinkerFactory.createModern();
        GraphTraversalSource g = tg.traversal();
        //Who are the people that marko develops software with?

        GraphTraversal graphTraversal = g.V()
                .has("person", "name", "marko")
                .as("exclude")
                .out("created")
                .in("created")
                .where(P.neq("exclude"))
                .values("name");

        while (graphTraversal.hasNext()) {
            Object object = graphTraversal.next();
            System.out.println(object);
        }
    }
}
