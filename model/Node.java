package model;

import java.util.Objects;

/**
 * This class wraps a concrete object and supplies getters and setters
 *
 * @param <T>
 *
 */
public class Node<T> {
    private T data;                                                          //value
    private Node<T> parent;                                                  //parent


    public Node(T someObject, final Node<T> discoveredBy){                  //constructor
        this.data = someObject;
        this.parent = discoveredBy;
    }

    public Node(T someObject){
        this(someObject,null);                                   //constructor
    }


    public T getData() {
        return data;
    }                                     //gets the  and returns the value

    public Node(){
        this(null);
    }                                 //constructor

    public void setData(T data) {
        this.data = data;
    }                       //updated the data

    public Node<T> getParent() {
        return parent;
    }                           //returns the parent

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }         //updated the parent


    @Override
    public int hashCode() {
        return data != null ? data.hashCode():0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node<?> state1 = (Node<?>) o;

        return Objects.equals(data, state1.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

}
