package model;

import algorithems.DFSVisit;
import algorithems.ThreadLocalDfsVisit;

import java.util.*;


    //here we implemented all of the algorithms that will be used to solve questions 1-4

public class TraversableMatrix implements Traversable<Index> { //adapter
    protected final ThreadLocalDfsVisit<Index> mThreadLocalDfsAlgorithem = new ThreadLocalDfsVisit<>();
    private DFSVisit<Index> mDfsVisit = new DFSVisit<>();
    protected final Matrix matrix;
    protected Index startIndex;

    public TraversableMatrix(Matrix matrix) {                          //constructor
        this.matrix = matrix;
    }

    public void setStartIndex(Index startIndex) {                      //setter
        this.startIndex = startIndex;
    }

    @Override
    public Node<Index> getOrigin() throws NullPointerException {       //exception if theres no starting index
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new Node<>(this.startIndex);

    }

    public int getRowLength() {
        return matrix.getPrimitiveMatrix().length;
    }

    public int getColumnLength() {
        return matrix.getPrimitiveMatrix()[0].length;
    }

    //this method creates a list of the reachable indices from a given index and returns that list
    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> someNode, boolean shouldIncludeDiagnol) {
        //we check who are its neighbors that their also their value is not 0
        List<Node<Index>> reachableIndex = new ArrayList<>(); //creates a new list that will store reachable nodes
        for (Index index : this.matrix.getNeighbors(someNode.getData(), shouldIncludeDiagnol)) {
            if (matrix.getValue(index) != 0) {                       //checks if the neighbor value is not 0
                Node<Index> indexNode = new Node<>(index, someNode); //index with data inside
                reachableIndex.add(indexNode);                       //add the nodes that are not 0 to the list
            }
        }
        return reachableIndex;                     //return the list of all the reachable indices
    }


    //represent the matrix as a string
    @Override
    public String toString() {
        return matrix.toString();
    }

    //algorithm that finds SSC
    public List<HashSet<Index>> findSCC() {
        List<HashSet<Index>> connectionComponentsList = new ArrayList<>(); //will contain a list of connected components
        HashSet<Index> visited = new HashSet<>();                          //hashset of indices we visited
        for (int i = 0; i < getRowLength(); i++) {                         //go over the rows
            for (int j = 0; j < getColumnLength(); j++) {                  //go over the colls
                Index indexToToStartForm = new Index(i, j);                //the index to start from
                //if the value of the index in the matrix is 1 and
                //is not equal to indexToStars then set it to indexToStart
                //then it adds its other components through traverse as DFS using TLS
                if (this.matrix.primitiveMatrix[i][j] == 1 && !visited.contains(indexToToStartForm)) {
                    setStartIndex(indexToToStartForm);
                    connectionComponentsList.add((HashSet<Index>) mThreadLocalDfsAlgorithem.traverse(visited, this));
                }
            }
        }
        //method reference (::)
        //sort a list of lists of components by the size of the lists
        connectionComponentsList.sort(Comparator.comparingInt(Set::size)); //sort by size
        return connectionComponentsList;                                   //returns the list
    }


    //find the shortest path
    public List<List<Index>> getMinimumPath(Index src, Index dest, boolean indlueDiagnoal) {
        List<List<Index>> allPathes = mDfsVisit.getAllPathsFromSrcToDest(this, src, dest, indlueDiagnoal); //find all paths
        return getShortetPathWithWeight(allPathes);                                          //return the lightest path from all paths
    }

    //find the lightest path by taking all paths and filter them until finds the lightest
    private List<List<Index>> getShortetPathWithWeight(List<List<Index>> lists) {  //list of lists of indices
        int min = Integer.MAX_VALUE;                                        //minimal weight
        List<List<Index>> minList = new ArrayList<>();
        for (List<Index> list : lists) {                                    //find the lightest path
            int result = 0;
            for (Index i : list) {
                result += matrix.getValue(i);
            }
            if (result < min) {                                            //updates the minimum path
                min = result;
            }
        }
        for (List<Index> list : lists) {                                   //go over the list again
            int result = 0;
            for (Index i : list) {
                result += matrix.getValue(i);
            }
            if(result == min){         //if another path is the same size add it to the final list
                minList.add(list);
            }
        }

        return minList;               //return the lightest paths
    }

    //finding the number of submarines
    public int getNumOfSubMarine() {
        int result = 0;
        List<HashSet<Index>> list = findSCC();  //use findSCC method to find SCC's
        for (HashSet<Index> set : list) {
            if (checkIfSetIsSubMarine(set)) {
                result++;
            }
        }
        return result;
    }

    // check if set is a submarine
    //after defining each corner(top left, top right, bottom left, bottom right) check
    //if all combined creates a rectangle, if it does then that a submarine
    private boolean checkIfSetIsSubMarine(HashSet<Index> set) {
        Index leftTop = getLeftTop(set);
        Index rightTop = getRightTop(set);
        Index leftBottom = getLeftBottom(set);
        Index rightBottom = getRightBottom(set);
        if (leftTop.getCol() == leftBottom.getCol()
                && leftBottom.getRow() == rightBottom.getRow()
                && rightBottom.getCol() == rightTop.getCol()
                && rightTop.getRow() == leftTop.getRow()
        ) {
            if (set.size() >= 2) {                      //if the length of the component is at least 2 return true
                return true;                            //else doesn't match our limitations (submarine >= 2 indices)
            }
            return false;

        } else {                                        //if the component is not a rectangle
            return false;
        }
    }

    //4 methods that define what are the corners of the submarine
    //by comparing right/left columns and top/bottom rows


    //check who has the largest row and largest col
    private Index getRightBottom(HashSet<Index> set) {
        int maxCol = Integer.MIN_VALUE;                              //int that will save the column index
        int maxRow = Integer.MIN_VALUE;                              //int that will save the row index
        Index result = null;
        for (Index index : set) {                                    //get the max column (right)
            if (index.getCol() > maxCol) {
                maxCol = index.getCol();
            }
        }

        for (Index index : set) {
            if (index.getCol() == maxCol && index.getRow() > maxRow) { //get the max row (down)
                maxRow = index.getRow();
                result = index;
            }
        }
        return result;                                               //return index of right bottom
    }

    //check who has the largest row and smallest col
    private Index getLeftBottom(HashSet<Index> set) {
        int minCol = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        Index result = null;
        for (Index index : set) {
            if (index.getCol() < minCol) {
                minCol = index.getCol();
            }
        }
        for (Index index : set) {
            if (index.getCol() == minCol && index.getRow() > maxRow) {
                maxRow = index.getRow();
                result = index;
            }
        }
        return result;
    }

    //check who has the smallest row and smallest col
    private Index getLeftTop(HashSet<Index> set) {
        int minCol = Integer.MAX_VALUE;
        int minRow = Integer.MAX_VALUE;
        Index result = null;
        for (Index index : set) {
            if (index.getCol() < minCol) {
                minCol = index.getCol();
            }
        }
        for (Index index : set) {
            if (index.getCol() == minCol && index.getRow() < minRow) {
                minRow = index.getRow();
                result = index;
            }
        }
        return result;
    }
    //check who has the smallest row and largest col
    private Index getRightTop(HashSet<Index> set) {
        int maxCol = Integer.MIN_VALUE;
        int minRow = Integer.MAX_VALUE;
        Index result = null;
        for (Index index : set) {
            if (index.getCol() > maxCol) {
                maxCol = index.getCol();
            }
        }
        for (Index index : set) {
            if (index.getCol() == maxCol && index.getRow() < minRow) {
                minRow = index.getRow();
                result = index;
            }
        }
        return result;
    }
}
