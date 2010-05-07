import java.io.File;

/**
 * filename: PlanarityTest.java
 */

/**
 * This class contains the main method of our Project. Implements the algorithm
 * outlined in "Graph Drawing: Algorithms for the Visualization of Graphs" book
 * written by Ioannis G. Tollis, Giuseppe Di Battista, Peter Eades, Roberto Tamassia.
 * Algorithm can be found in Chapter 3: Divide and Conquer page 80.
 * 
 * @author Trevor Mack
 * @author David Sweeney
 *
 */
public class PlanarityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub
		if( args.length != 1 ) {
			System.err.println("usage: java PlanarityTest filename");
		}else {
			File aFile = new File(args[0]); 
			Graph graph = null;
			try {
				graph = new Graph(aFile);
				System.out.println(graph.simpleCycle());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
