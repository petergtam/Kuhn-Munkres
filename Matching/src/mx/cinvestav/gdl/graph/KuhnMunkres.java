package mx.cinvestav.gdl.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class KuhnMunkres
{
	public void resolve(Matrix graph)
	{
		// parte 1
		graph.generateFeasibleLabelling();
		Matrix equalitySubgraph_GL = graph.produceGL();
		Matching matching = equalitySubgraph_GL.getMatching();
		
		// Unsaturated vertex of X
		Vertex u = null;
		
		// Unsaturated vertex of Y
		Vertex y = null;

		while ((u = matching.isXSaturated(equalitySubgraph_GL)) != null)
		{
			// parte 1

			Set<Vertex> S = new HashSet<Vertex>();
			S.add(u);
			Set<Vertex> T = new HashSet<Vertex>();
			boolean flag;
			do
			{

				// parte 2
				Set<Vertex> ngl = equalitySubgraph_GL.getNeighbors(S);
				if (ngl.equals(T))
				{
					double alpha = graph.calculateAlpha(S, T);
					graph.updateLabelling(S, T, alpha);
					equalitySubgraph_GL = graph.produceGL();
				}
				ngl.removeAll(T);
				Iterator<Vertex> i = ngl.iterator();
				y = null;
				if (i.hasNext())
				{
					y = i.next();
				}
				if (y == null)
				{
					System.out.println("Y ES VACIO!!!!");
					System.exit(1);
				}
				Vertex z = matching.isYSaturated(y);
				// parte 2

				flag = (z == null) ? false : true;
				if (flag)
				{
					S.add(z);
					T.add(y);
				}
			} while (flag);

			// parte 3
			Matching path = matching.getAugmentingPath(u, y);
			matching = matching.xor(path);
			// parte3
		}
		System.out.println(matching);
	}

	

	public static void main(String[] args)
	{
		int[][] matriz = { { 3, 5, 5, 4, 1 }, { 2, 2, 0, 2, 2 }, { 2, 4, 4, 1, 0 }, { 0, 1, 1, 0, 0 },
				{ 1, 2, 1, 3, 3 } };

		Matrix G = new Matrix(matriz);

		KuhnMunkres km = new KuhnMunkres();

		km.resolve(G);

	}
}
