/**
 * filename: Graph.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class Graph
 * 
 * @author Trevor Mack
 * @author David Sweeney
 *
 */
public class Graph {

	protected HashMap<Object, List<Object>> adjacencyMap;
	enum VertexState {
		//unvisited, visiting, visited
        White, Gray, Black
    }
	/**
	* Initialize this Graph object to be empty.
	*/
	public Graph() {
		adjacencyMap = new HashMap<Object, List<Object>>();
	}
	
	/**
	 * Create a Graph from a file Object. File format are line delimited edges,
	 * where edges are space delimited vertices.
	 * 
	 * @param file - input file containing a list of edges
	 * @throws Exception - Invalid File Format
	 */
	public Graph( File file ) throws Exception {
		this();
		try {
			Scanner scanner = new Scanner(file);
			//for each edge
			while(scanner.hasNextLine()) {
				//parse edge
				String[] vertices = (scanner.nextLine()).split(" ", 2);
				if(vertices.length == 2) {
					addEdge(vertices[0], vertices[1]);
				}else {
					throw new Exception("Invalid File Format");
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
	
	/**
	 * Create a graph from a list of points. Points are assumed to be connected to adjacent nodes
	 * in the list.
	 * 
	 * @param list - the list of nodes
	 * 				(ex {0,1,2,3} 2 is connected to 1 and 3)
	 */
	private Graph( List<Object> list ) {
		this();
		if( list.size() > 1 ) {
			Object prev = list.get(0);
			for( int i=1; i<list.size(); i++ ) {
				addEdge(prev, list.get(i));
			}
		}
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
			List<Object> edges = (ArrayList<Object>)adjacencyMap.get(it.next());
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
		adjacencyMap.put (vertex, new ArrayList<Object>());
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
		adjacencyMap.get(v1).add(v2);
		adjacencyMap.get(v2).add(v1);
		return true;
	}
	
	/**
	 * Tests to see if 2 vertices are connected by exacted one edge.
	 * 
	 * @param v1	- the first vertex of the "edge"
	 * @param v2	- the second vertex of the "edge"
	 * @return true	- if v1 and v2 are connected by 1 edge
	 */
	public boolean isEdge (Object v1, Object v2) {
		return getNeighbors(v1).contains(v2);
	}
	
	/**
	 * Finds and returns all adjacent nodes to a vertex.
	 * 
	 * @param v		- the vertex to find neighbors of
	 * @return list	- the list of neighbors
	 */
	public List<Object> getNeighbors (Object v) {
		return adjacencyMap.get(v);
	}
	
	public boolean isBipartite() {
		//3 arrays: graphNodes = HashMap.keyset,  
		//			color=value of graphNodes color (0=>unvisited, 1=>red, 2=>blue)
		List<Object> graphNodes = Arrays.asList(adjacencyMap.keySet().toArray());
		int[] color = new int[graphNodes.size()];
		
		for( int i=0; i < graphNodes.size(); i++ ) {
			if( color[i] == 0 ) {
				Object currentNode = graphNodes.get(i);
				//mark the currentNode as visited and as color one
				color[i] = 1;
				// create stack containing currentNode
				Stack<Object> nodeStack = new Stack<Object>();
				nodeStack.push(currentNode);
				while( !nodeStack.isEmpty() ) {
					//collect neighboring nodes
					Object u = nodeStack.pop();
					int uIndex = graphNodes.indexOf(u);
					List<Object> neighbors = getNeighbors(u);
					
					while( !neighbors.isEmpty() ) {
						//neighbor of u
						Object v = neighbors.remove(0);
						int index = graphNodes.indexOf(v);
						if(color[index] == color[uIndex]) {
							return false;
						}else if(color[index] == 0) {
							nodeStack.push(v);
							color[index] = 3 - color[uIndex];
						}
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Finds and creates a simpleCycle is there is one in the graph.
	 * 
	 * @return cycle	- a simpleCycle in the graph, or null if none exist
	 */
	public Graph simpleCycle() {
		List<Object> graphNodes = Arrays.asList(adjacencyMap.keySet().toArray());
		Stack<Object> stack = new Stack<Object>();
		VertexState[] state = new VertexState[size()];
		for( int i=0; i<graphNodes.size(); i++) {
			//set as unvisited
			state[i] = VertexState.White;
		}
		state[0] = VertexState.Gray; //mark it
		System.out.println(graphNodes.get(0)); //display it
		stack.push(graphNodes.get(0)); //push it
		
		while( !stack.isEmpty() ) {
			//get an unvisited vertex adjacent to stack top
			List<Object> neighbors = getNeighbors(stack.peek());
			int unvisited = -1;
			for (int i = 0; i < neighbors.size(); i++) {
				int index = graphNodes.indexOf(neighbors.get(i));
				if(state[index] == VertexState.White) {
					unvisited = index;
					i = neighbors.size();
				}
				if(graphNodes.get(index) == stack.get(0)) {
					System.out.println("loop");
					stack.push(graphNodes.get(index));
					return new Graph(stack);
				}
			}
			if( unvisited == -1 ) {
				state[graphNodes.indexOf(stack.peek())] = VertexState.Black; //finish it
				stack.pop();
			}else {
				state[unvisited] = VertexState.Gray; //mark it
				System.out.println(graphNodes.get(unvisited)); //display it
				stack.push(graphNodes.get(unvisited)); //push it
			}
		}
		return null;
	}
}