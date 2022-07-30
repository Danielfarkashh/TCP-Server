package algorithems;

import model.Index;
import model.Node;
import model.Traversable;

import java.util.*;

    //this class will give us all the paths from a source index to a destination index
public class DFSVisit<T> {
    private List<List<Index>> allPaths; //list of lists of indices

    public List<List<Index>> getAllPathsFromSrcToDest(Traversable<T> partOfGraph, Index src, Index dest, boolean indlueDiagnoal) {
        allPaths = new ArrayList<>();                    //creates a list that will contain all paths
        List<Index> visited = new ArrayList<>();         //creates another list  that will contain indices we already visited
        ArrayList path = new ArrayList();                //create a new path
        path.add(src);                                   //add source index to the path
        dfs(partOfGraph, src, dest, path, visited, indlueDiagnoal); //call dfs with all variables above
        return allPaths;                                 //return all paths
    }


    public void dfs(Traversable<T> partOfGraph, Index src, Index dest, ArrayList path, List<Index> visited, boolean indlueDiagnoal) {
        visited.add(src);                                       //add the source index the the visited list
        if (src.equals(dest)) {                                 //in rucurtion this is the stopping sign (source=destination)
            allPaths.add(new ArrayList<>(path));                //add this path to all paths
        }

        for (Node<T> indexNode : partOfGraph.getReachableNodes(new Node<>((T) src), indlueDiagnoal)) {
            if (!visited.contains(indexNode.getData())) {  //if we didn't visit this index and is not 0
                path.add(indexNode.getData());             //add it to the path
                dfs(partOfGraph, (Index) indexNode.getData(), dest, path, visited, indlueDiagnoal);
                path.remove(indexNode.getData());
            }
        }

        visited.remove(src);

    }


}
