package puzzle;

import java.util.ArrayList;
import java.util.HashMap;

public class EightPuzzle
{
	public static int SIZE = 3;
	public static int X = SIZE - 1;
	public static int Y = SIZE - 1;
	public static HashMap<Node, HashMap<Node, Byte>> tree;
	public static HashMap<Node, Integer> depths;
	public static Node rootNode;

	public static void main( String[] args )
	{
		int movements = 10, iterations = 30;
		Puzzle puzzle = new Puzzle( SIZE );
		byte[][] initialConfiguration;
		initialConfiguration = puzzle.getInitialConfiguration();

		//printConfiguration( initialConfiguration );
		byte[][] newConfiguration = initialConfiguration;
		int totalNodesBFS = 0, totalNodesDFS = 0, totalNodesIterativeDFS = 0, totalNodesManhattanHeuristic = 0, 
			totalNodesChildrenHeuristic = 0, totalNodesInQueueBFS = 0, totalNodesInStackDFS = 0, totalNodesInStackIterativeDFS = 0,
			totalNodesInQueueManhattanHeuristic = 0, totalNodesInQueueChildrenHeuristic = 0;
		for( int i = 0; i < iterations; ++i )
		{
			for( int j = 0; j < movements; ++j )
			{
				newConfiguration = disArrayConfiguration( newConfiguration );
				//printConfiguration( newConfiguration );
			}

			makeTree( newConfiguration );
	
			Node initialNode = new Node( initialConfiguration );
			Searches.bfs( tree, rootNode, initialNode );
			totalNodesBFS += Searches.totalNodes;
			totalNodesInQueueBFS += Searches.totalNodesInStructure;
			Searches.dfs( tree, rootNode, initialNode );
			totalNodesDFS += Searches.totalNodes;
			totalNodesInStackDFS += Searches.totalNodesInStructure;
			Searches.iterativeDFS( tree, depths, rootNode, initialNode );
			totalNodesIterativeDFS += Searches.totalNodes;
			totalNodesInStackIterativeDFS += Searches.totalNodesInStructure;
			Searches.manhattanHeuristic( tree, rootNode, initialNode );
			totalNodesManhattanHeuristic += Searches.totalNodes;
			totalNodesInQueueManhattanHeuristic += Searches.totalNodesInStructure;
			Searches.childrenHeuristic( tree, rootNode, initialNode );
			totalNodesChildrenHeuristic += Searches.totalNodes;
			totalNodesInQueueChildrenHeuristic += Searches.totalNodesInStructure;
		}
		
		System.out.println( "Total nodes visited in BFS: " + ( totalNodesBFS / iterations ) );
		System.out.println( "Longest nodes in queue - BFS: " + ( totalNodesInQueueBFS / iterations ) );
		System.out.println( "Total nodes visited in DFS: " + ( totalNodesDFS / iterations ) );
		System.out.println( "Longest nodes in stack - DFS: " + ( totalNodesInStackDFS / iterations ) );
		System.out.println( "Total nodes visited in Iterative DFS: " + ( totalNodesIterativeDFS / iterations ) );
		System.out.println( "Longest nodes in stack - Iterative DFS: " + ( totalNodesInStackIterativeDFS / iterations ) );
		System.out.println( "Total nodes visited in Manhattan Heuristic: " + ( totalNodesManhattanHeuristic / iterations ) );
		System.out.println( "Longest nodes in queue - Manhattan Heuristic: " + ( totalNodesInQueueManhattanHeuristic / iterations ) );
		System.out.println( "Total nodes visited in Children Heuristic: " + ( totalNodesChildrenHeuristic / iterations ) );
		System.out.println( "Longest nodes in queue - Children Heuristic: " + ( totalNodesInQueueChildrenHeuristic / iterations ) );
	}
	
	public static void makeTree( byte[][] configuration )
	{
		tree = new HashMap<>();
		depths = new HashMap<>();

		byte weight = 1;
		rootNode = new Node( null, configuration, X, Y, 0 );
		Node parentNode = rootNode;
		depths.put( parentNode, 0 );
		ArrayList<Node> queue = new ArrayList<>();
		queue.add( parentNode );
		while( true )
		{
			parentNode = queue.remove( 0 );
			int x = parentNode.getX();
			int y = parentNode.getY();
			int depth = depths.get( parentNode ) + 1;
			if( depth == 11 )
				break;
			if( x - 1 >= 0 )
			{
				byte[][] newConfiguration = newConfiguration( parentNode.getConfiguration(), x, y, x - 1, y );
				Node childrenNode = new Node( parentNode, newConfiguration, x - 1, y, 1 );
				if( tree.containsKey( parentNode ) )
				{
					HashMap<Node, Byte> childrens = tree.get( parentNode );
					childrens.put( childrenNode, weight );
					tree.replace( parentNode, childrens );
				}
				else
				{
					HashMap<Node, Byte> children = new HashMap<>();
					children.put( childrenNode, weight );
					tree.put( parentNode, children );
				}
				depths.put( childrenNode, depth );
				queue.add( childrenNode );
			}
			if( x + 1 <= SIZE - 1 )
			{
				byte[][] newConfiguration = newConfiguration( parentNode.getConfiguration(), x, y, x + 1, y );
				Node childrenNode = new Node( parentNode, newConfiguration, x + 1, y, 1 );
				if( tree.containsKey( parentNode ) )
				{
					HashMap<Node, Byte> childrens = tree.get( parentNode );
					childrens.put( childrenNode, weight );
					tree.replace( parentNode, childrens );
				}
				else
				{
					HashMap<Node, Byte> children = new HashMap<>();
					children.put( childrenNode, weight );
					tree.put( parentNode, children );
				}
				depths.put( childrenNode, depth );
				queue.add( childrenNode );
			}
			if( y - 1 >= 0 )
			{
				byte[][] newConfiguration = newConfiguration( parentNode.getConfiguration(), x, y, x, y - 1 );
				Node childrenNode = new Node( parentNode, newConfiguration, x, y - 1, 1 );
				if( tree.containsKey( parentNode ) )
				{
					HashMap<Node, Byte> childrens = tree.get( parentNode );
					childrens.put( childrenNode, weight );
					tree.replace( parentNode, childrens );
				}
				else
				{
					HashMap<Node, Byte> children = new HashMap<>();
					children.put( childrenNode, weight );
					tree.put( parentNode, children );
				}
				depths.put( childrenNode, depth );
				queue.add( childrenNode );
			}
			if( y + 1 <= SIZE - 1 )
			{
				byte[][] newConfiguration = newConfiguration( parentNode.getConfiguration(), x, y, x, y + 1 );
				Node childrenNode = new Node( parentNode, newConfiguration, x, y + 1, 1 );
				if( tree.containsKey( parentNode ) )
				{
					HashMap<Node, Byte> childrens = tree.get( parentNode );
					childrens.put( childrenNode, weight );
					tree.replace( parentNode, childrens );
				}
				else
				{
					HashMap<Node, Byte> children = new HashMap<>();
					children.put( childrenNode, weight );
					tree.put( parentNode, children );
				}
				depths.put( childrenNode, depth );
				queue.add( childrenNode );
			}
		}
		
		//printTree();
	}

	public static void printTree()
	{
		Node parentNode = rootNode;
		ArrayList<Node> queue = new ArrayList<>();
		queue.add( parentNode );
		while( queue.size() > 0 )
		{
			parentNode = queue.remove( 0 );
			HashMap<Node, Byte> childrens = tree.get( parentNode );
			if( childrens != null )
			{
				System.out.println( depths.get( parentNode ) );
				System.out.println( parentNode );
				System.out.println( "X: " + parentNode.getX() + ", Y: " + parentNode.getY() );
				System.out.println( childrens.keySet() );
				for( Node node: childrens.keySet() )
					queue.add( node );
			}
		}
	}

	public static void printConfiguration( byte[][] configuration )
	{
		System.out.println();
		for( int i = 0; i < SIZE; ++i )
		{
			for( int j = 0; j < SIZE; ++j )
				System.out.print( configuration[i][j] + " " );
			System.out.println();
		}
	}
	
	public static byte[][] disArrayConfiguration( byte[][] configuration )
	{
		byte[][] newConfiguration = configuration;
		byte[] possibilities = { -1, 1 };
		int x;
		int y;
		
		while( true )
		{
			if( ( int ) ( 2 * Math.random() ) == 0 )
			{
				x = possibilities[( int ) ( 2 * Math.random() )];
				y = 0;
			}
			else
			{
				x = 0;
				y = possibilities[( int ) ( 2 * Math.random() )];
			}
			
			int auxX = X + x, auxY = Y + y;
			if( auxX >= 0 && auxX <= 2 && auxY >= 0 && auxY <= 2 && ( auxX != X || auxY != Y ) )
			{
				x += X;
				y += Y;
				break;
			}
		}

		newConfiguration = newConfiguration( newConfiguration, X, Y, x, y );
		
		X = x;
		Y = y;

		return newConfiguration;
	}

	public static byte[][] newConfiguration( byte[][] oldConfiguration, int oldX, int oldY, int newX, int newY )
	{
		byte[][] newConfiguration = new byte[SIZE][SIZE];
		for( int i = 0; i < SIZE; ++i )
			for( int j = 0; j < SIZE; ++j )
				newConfiguration[i][j] = oldConfiguration[i][j];
		byte aux = newConfiguration[oldX][oldY];
		newConfiguration[oldX][oldY] = newConfiguration[newX][newY];
		newConfiguration[newX][newY] = aux;

		return newConfiguration;
	}
}