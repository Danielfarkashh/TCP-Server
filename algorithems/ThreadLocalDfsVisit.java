package algorithems;

import model.Index;
import model.Node;
import model.Traversable;
import model.TraversableMatrix;

import java.util.*;
    //takes a large problem and breaks it to smaller problems that are separated into different threads
    //and for each thread, no other thread can get access to its data. our motivation to use this class
    //is to prevent problems related to memory. This is called TLS - Thread Local Storage
public class ThreadLocalDfsVisit<T> {
    protected final ThreadLocal<Stack<Node<T>>> stackThreadLocal //surrounds stack
            = ThreadLocal.withInitial(Stack::new);  //create a new stack
    protected final ThreadLocal<Set<Node<T>>> setThreadLocal =  //surrounds set
            ThreadLocal.withInitial(() -> new LinkedHashSet<>()); //new linked hashset

    //this method and the next one(Pop) allow us to reach
    //the stack that originally was thread local

    protected void threadLocalPush(Node<T> node) { //will implement data into the stack that is in the concrete thread
        stackThreadLocal.get().push(node);         //push node
    }

    protected Node<T> threadLocalPop() {
        return stackThreadLocal.get().pop();      //return the top value of the stack and removes it from the stack
    }

    public Set<T> traverse(HashSet<Index> visitedIndexes, Traversable<T> partOfGraph) {
        threadLocalPush(partOfGraph.getOrigin());     //push into the stack
        while (!stackThreadLocal.get().isEmpty()) {   //as long as the stack is not empty continue
            Node<T> poppedNode = threadLocalPop();    //pops the top index and return its value
            setThreadLocal.get().add(poppedNode);     //add this node to the list we already checked
            Collection<Node<T>> reachableNodes = partOfGraph.getReachableNodes(poppedNode, true);
            for (Node<T> singleReachableNode : reachableNodes) {    //for all singleReachableNode from reachableNodes:
                if (!setThreadLocal.get().contains(singleReachableNode) && //if not in finished and not in the stack
                        !stackThreadLocal.get().contains(singleReachableNode)) { //add it to the stack
                    threadLocalPush(singleReachableNode);
                }
            }
        }
        HashSet<T> blackList = new HashSet<>();
        for (Node<T> node : setThreadLocal.get()) {
            visitedIndexes.add((Index) node.getData());   //mark all indices that where already visited
            blackList.add(node.getData());
        }
        stackThreadLocal.remove();
        setThreadLocal.remove();
        return blackList;
    }


}
