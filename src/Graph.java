/**
 * filename: Graph.java
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class Graph
 * 
 * @author Trevor Mack
 * @author David Sweeney
 *
 */
public class Graph {

	protected HashMap<Object, LinkedList<Object>> adjacencyMap;
	
	/**
	* Initialize this Graph object to be empty.
	*/
	public Graph() {
		adjacencyMap = new HashMap<Object, LinkedList<Object>>();
	}
	
	/**
	* Determines if this Graph contains no vertices.
	*
	* @return true - if this Graph contains no vertices, otherwise false
	*/
	public boolean isEmpty() {
		return adjacencyMap.isEmpty();
	}
	
	/**
	* Determines the number of vertices in this Graph.
	*
	* @return the number of vertices.
	*/
	public int size() {
		return adjacencyMap.size();
	}
	
	/**
	* Returns the number of edges in this Graph object.
	*
	* @return the number of edges.
	*/
	public int getEdgeCount() {
		int count = 0;
		//iterate over the hashmap's keys counting edges
		Iterator<Object> it = adjacencyMap.keySet().iterator();
		while( it.hasNext() ) {
			LinkedList<Object> edges = (LinkedList<Object>)adjacencyMap.get(it.next());
			count += edges.size();
		}
		return count;
	}
	
	/**
	* Adds a specified object as a vertex
	*
	* @param vertex - the specified object
	* @return true  - if object was added by this call, 
	* 		  false - if the object already exists
	*/
	public boolean addVertex (Object vertex) {
		if (adjacencyMap.containsKey(vertex))
			return false;
		adjacencyMap.put (vertex, new LinkedList<Object>());
		return true;
	}
	
	/**
	* Adds an edge, and vertices if not already present
	*
	* @param v1 	- the beginning vertex object of the edge
	* @param v2 	- the ending vertex object of the edge
	* @return true 	- if the edge was added by this call
	*/
	public boolean addEdge (Object v1, Object v2) {
		addVertex (v1); addVertex (v2);
		LinkedList<Object> l = adjacencyMap.get(v1);
		l.add(v2);
		return true;
	}
}