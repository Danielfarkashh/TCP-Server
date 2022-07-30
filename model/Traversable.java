package model;

import model.Node;

import java.util.Collection;

public interface Traversable<T> {
    Node<T> getOrigin(); // the starting point of the graph(root)

    Collection<Node<T>> getReachableNodes(Node<T> someNode, boolean shouldIncludeDiagnoal); //when a node is given
                                                               // this will return a collection of reachable nodes
}
