import java.util.ArrayDeque;
import java.util.Queue;

//----------------------------------------------------------------------------
// WeightedGraph.java            by Dale/Joyce/Weems                Chapter 10
//
// Implements a directed graph with weighted edges.
// Vertices are objects of class T and can be marked as having been visited.
// Edge weights are integers.
// Equivalence of vertices is determined by the vertices' equals method.
//
// General precondition: Except for the addVertex and hasVertex methods, 
// any vertex passed as an argument to a method is in this graph.
//----------------------------------------------------------------------------


public class WeightedGraph<T> implements WeightedGraphInterface<T>
{
	public static final int NULL_EDGE = 0;
	private static final int DEFCAP = 50;  // default capacity
	private int numVertices;
	private int maxVertices;
	private T[] vertices;
	private int[][] edges;
	private boolean[] marks;  // marks[i] is mark for vertices[i]

	public WeightedGraph()
	// Instantiates a graph with capacity DEFCAP vertices.
	{
		numVertices = 0;
		maxVertices = DEFCAP;
		vertices = (T[]) new Object[DEFCAP];
		marks = new boolean[DEFCAP];
		edges = new int[DEFCAP][DEFCAP];
	}

	public WeightedGraph(int maxV)
	// Instantiates a graph with capacity maxV.
	{
		numVertices = 0;
		maxVertices = maxV;
		vertices = (T[]) new Object[maxV];
		marks = new boolean[maxV];
		edges = new int[maxV][maxV];
	}

	/**
	 * Returns true if this graph is empty; otherwise, returns false.
	 */
	public boolean isEmpty() {
		return (numVertices == 0);
	}

	/**
	 * Returns true if this graph is full; otherwise, returns false.
	 */
	public boolean isFull(){
		return (numVertices==maxVertices);
	}

	/**Preconditions:   This graph is not full, vertex is not already in this graph, vertex is not null.
	 *Adds vertex to this graph.
	 */
	public void addVertex(T vertex) {
		vertices[numVertices] = vertex;

		for (int i=0;i<numVertices;i++) {
			edges[numVertices][i] = NULL_EDGE;	//row constant, increment column
			edges[i][numVertices] = NULL_EDGE;	//column constant, increment row
		}
		numVertices++;
	}

	/**
	 * Returns true if this graph contains vertex; otherwise, returns false.
	 */
	public boolean hasVertex(T vertex) {
		for (int i=0;i<numVertices;i++) {
			if (vertices[i].equals(vertex))
				return true;
		}
		return false;
	}

	/**
	 * @param vertex
	 * @return the index of vertex in Vertices.
	 */
	public int indexIs(T vertex) {
		for (int i=0;i<numVertices;i++) {
			if (vertices[i].equals(vertex))
				return i;
		}
		return -1;
	}

	/**
	 * Adds an edge with the specified weight from fromVertex to toVertex.
	 */
	public void addEdge(T fromVertex, T toVertex, int weight) {
		int row = indexIs(fromVertex);
		int column = indexIs(toVertex);

		edges[row][column] = weight;
	}

	/**
	 * If edge from fromVertex to toVertex exists, returns the weight of edge;
	 * otherwise, returns a special “null-edge” value.
	 */
	public int weightIs(T fromVertex, T toVertex) {
		int row = indexIs(fromVertex);
		int column = indexIs(toVertex);

		return edges[row][column];
	}

	/**
	 * Returns a queue of the vertices that vertex is adjacent to.		
	 */
	public Queue<T> getToVertices(T vertex){		//check all columns to the right
		Queue<T> queue = new ArrayDeque<>();
		int row = indexIs(vertex);
		for (int col=0;col<numVertices;col++) {	//get what it's connected TO
			if (edges[row][col] != NULL_EDGE)
				queue.add(vertices[col]);
		}
		return queue;
	}

	/**
	 * Unmarks all vertices.
	 */
	public void clearMarks() {
		boolean[] temp = new boolean[DEFCAP];
		
		for (int i=0; i<temp.length; i++) {
			temp[i] = false;
		}	
		marks = temp;
	}

	/**
	 * Marks vertex.
	 */
	public void markVertex(T vertex) {
		int b = indexIs(vertex);
		marks[b] = true;
	}

	/**
	 * Returns true if vertex is marked; otherwise, returns false.
	 */
	public boolean isMarked(T vertex) {
		int b = indexIs(vertex);
		if (marks[b] == true)
			return true;
		else
			return false;
	}

	/**
	 * Returns an unmarked vertex if any exist; otherwise, returns null.
	 */
	public T getUnmarked(){
		for (int i=0;i<marks.length;i++) {
			if (marks[i] = false)
				return vertices[i];
		}
		return null;
	}

	/**
	 * Preconditions:  vertex1 and vertex2 are in the set of vertices
	 * @param vertex1
	 * @param vertex2
	 * @return = (vertex1, vertex2) is in the set of edges
	 */
	public boolean edgeExists(T vertex1, T vertex2) {
		return (edges[indexIs(vertex1)][indexIs(vertex2)] != NULL_EDGE);
	}

	/**
	 * Preconditions:  vertex1 and vertex2 are in the set of vertices
	 * @param vertex1
	 * @param vertex2
	 * @return true if edge was in the graph (and has been removed), false if edge was not in the graph
	 */
	public boolean removeEdge(T vertex1, T vertex2) {
		boolean existed = edgeExists(vertex1, vertex2);
		edges[indexIs(vertex1)][indexIs(vertex2)] = NULL_EDGE;
		return existed;
	}

}