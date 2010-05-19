/**
 * filename: Graph.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class is a data representation and implementation of a Graph. The data
 * structure used for this representation is an adjacency list (a modified adjacency matrix),
 * which only uses space for edges that actually exist in the represented graph.
 * 
 * @author Trevor Mack		(tmm9274@rit.edu)
 * @author David Sweeney	(dts7079@rit.edu)
 *
 */
public class Graph {

	//adjacency list: data structure of graph
	protected HashMap<Object, List<Object>> adjacencyMap;
	//states to be applied to a node in graph specific methods
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
	 * Removes a node from the graph, and all cooresponding edges.
	 * 
	 * @param vertex	- the node to remove
	 * @return true		- if the vertex was removed, false otherwise
	 */
	public boolean removeVertex (Object vertex) {
		if (!adjacencyMap.containsKey(vertex))
			return false;
		
		for(Object node : getNodes()) {
			List<Object> neighbors = adjacencyMap.get(node);
			if(neighbors.contains(vertex)) {
				removeEdge(node, vertex);
			}
		}
		adjacencyMap.remove(vertex);
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
	 * Removes an edge from the graph (undirected)
	 * 
	 * @param v1	- a node in the graph connected to v2
	 * @param v2	- another node in the graph connected to v1
	 * @return true - if the edge was removed, otherwise false
	 */
	public boolean removeEdge (Object v1, Object v2) {
		if ( this.isEdge(v1, v2) ) {
			//remove edge from v1
			adjacencyMap.get(v1).remove(v2);
			//remove edge from v2
			adjacencyMap.get(v2).remove(v1);
			return true;
		}
		return false;
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
	 * Adds all the nodes and edges that are in the graph to this graph.
	 * 
	 * @param graph	- the graph to add to this graph
	 */
	public void addGraph(Graph graph) {
		for( Object vertex : getNodes()) {
			for( Object neighbor : getNeighbors(vertex) ) {
				this.addEdge(vertex, neighbor);
			}
		}
	}
	
	/**
	 * Get all the nodes in this graph as a list.
	 * 
	 * @return list of node objects
	 */
	public List<Object> getNodes() {
		return Arrays.asList(adjacencyMap.keySet().toArray());
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
	
	/**
	 * Tests to see if the given graph is a path
	 * 
	 * @return	true	- if graph is a path
	 * 			false	- if graph is not a path
	 */
	public boolean isPath() {
		for( Object node : getNodes() ) {
			if( this.getNeighbors(node).size() > 2 ) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A bipartite graph (or bigraph) is a graph whose vertices can be divided into 
	 * two disjoint sets U and V such that every edge connects a vertex in U to one 
	 * in V; that is, U and V are independent sets. A graph is bipartite if it has a
	 * 2-coloring that can be "applied" to the graph nodes.
	 * 
	 * @return	true	- graph is a bipartite graph
	 * 			false	- graph is not a bipartite graph
	 */
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
					
					for( Object neighbor : getNeighbors(u) ) {
						//neighbor of u
						int index = graphNodes.indexOf(neighbor);
						if(color[index] == color[uIndex]) {
							return false;
						}else if(color[index] == 0) {
							nodeStack.push(neighbor);
							color[index] = 3 - color[uIndex];
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isBiconnected() {
		for( Object node : getNodes() ) {
			if( this.getNeighbors(node).size() < 2 ) {
				return false;
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
		//System.out.println(graphNodes.get(0)); //display it
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
				//System.out.println(graphNodes.get(unvisited)); //display it
				stack.push(graphNodes.get(unvisited)); //push it
			}
		}
		return null;
	}
	
	/**
	 * The implementation of the planarity algorithm description taken from di Battista et al
	 * which tests whether or not a graph is planar.
	 * 
	 * @param 	cycle		- a cycle in the graph (seperating)
	 * @return	true		- graph is indeed planar
	 * 			false		- graph is not planar
	 * @throws PlanarityException if input graph is not bipartite (algorithm description)
	 */
	public boolean isPlanar(Graph cycle) throws PlanarityException {
		if(!this.isBiconnected()) throw new PlanarityException("Graph must be biconnected"); 
		// 1. If the graph has more than 3n -6 edges, return "nonplanar."
		if( this.getEdgeCount() > 3*this.size()-6 ) return false;
		
		// 2. Compute the pieces of G with respect to C...if no C then whole graph is 1 peice
		List<Graph> pieces = new ArrayList<Graph>();
		if(cycle == null) {
			pieces.add(this);
		}else {
			pieces = PlanarityTest.FindPieces(this, cycle);
		}
		
		// 3. For each piece P of G that is not a path,
		for( Graph piece : pieces ) {
			if( piece.isPath() ) continue;
			Graph p1 = new Graph();
			Graph c1 = new Graph();
			// 1. let P' be that graph obtained by adding P to C
			p1.addGraph(piece);
			p1.addGraph(cycle);
			
			// 2. let C' be the cycle of P' obtained from C by replacing the portion of C between two consecutive attachments with a path of P between them
			c1.addGraph(cycle);
			
			// replacement with the consecutive attachments of P and C
			List<Object> attachments=PlanarityTest.Attach(piece.getNodes(), cycle);
			Stack<Object> interval = PlanarityTest.Intervalize(cycle,attachments.get(0), attachments.get(attachments.size()-1));
			
			for (Object node : interval) {
				if (!attachments.contains(node))
					c1.removeVertex(node);
			}
			
			c1.addGraph(piece);
			
			// 3. apply the algorithm recursively to graph P' and cycle C'. If P' is nonplanar, return "nonplanar."
			if(!p1.isPlanar(c1)) return false; 
		}
		
		// 4. Compute the interlacement graph I of the pieces.
		Graph interlacement = PlanarityTest.InterlacementGraph(pieces, cycle);
		
		// 5. Test whether I is bipartite. If I is bipartite, return "planar".
		if( interlacement.isBipartite() ) return true;
		
		// 6. Return "non-planar."
		return false;
	}
	
}