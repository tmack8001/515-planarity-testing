
public class Edge {
	Object v1;
	Object v2;
	
	public Edge(Object v1, Object v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean equals(Edge edge) {
		if(v1 == edge.v1 && v2 == edge.v2) return true;
		if(v1 == edge.v2 && v2 == edge.v1) return true;
		return false;
	}
}
