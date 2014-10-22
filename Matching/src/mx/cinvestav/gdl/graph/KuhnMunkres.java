package mx.cinvestav.gdl.graph;

import java.util.HashSet;
import java.util.Set;

public class KuhnMunkres
{
	public void resolve(Matrix graph)
	{
		graph.generateFeasibleLabelling();
		Matrix equalitySubgraph_GL = graph.produceGL();
		Matching matching = equalitySubgraph_GL.getArbitraryMatching();

		Vertex u = null; // Unsaturated vertex of X
		Vertex y = null; // Unsaturated vertex of Y

		// while there is not yet a perfect matching
		while ((u = matching.isXSaturated(graph)) != null)
		{
			Set<Vertex> S = new HashSet<Vertex>(); // collection of Xs
			S.add(u);

			Set<Vertex> T = new HashSet<Vertex>(); // collection of Ys
			boolean isYsaturated;
			do
			{
				Set<Vertex> neighborsS_GL = equalitySubgraph_GL.getNeighbors(S);
				if (neighborsS_GL.equals(T))
				{
					double alpha = graph.calculateAlpha(S, T);
					graph.updateLabelling(S, T, alpha);
					equalitySubgraph_GL = graph.produceGL();

					// recalculate neighbors
					neighborsS_GL = equalitySubgraph_GL.getNeighbors(S);
				}
				neighborsS_GL.removeAll(T);

				// y is guaranteed to have value
				y = neighborsS_GL.iterator().next();

				Vertex z = matching.isYSaturated(y);
				isYsaturated = (z == null) ? false : true;
				if (isYsaturated)
				{
					S.add(z);
					T.add(y);
				}
			} while (isYsaturated);

			// matching edges might have changed
			equalitySubgraph_GL.updateMatching(matching);

			// calculate augmenting path from u to y
			Matching path = matching.getAugmentingPath(equalitySubgraph_GL, u, y);

			// Invert matching on the path edges
			matching.xor(path);
		}
		System.out.println(matching);
		System.out.println("weight: " + matching.weight(graph));
	}

	public static void main(String[] args)
	{
		// double[][] matriz = { { 3, 5, 5, 4, 1 }, { 2, 2, 0, 2, 2 }, { 2, 4,
		// 4, 1, 0 }, { 0, 1, 1, 0, 0 },
		// { 1, 2, 1, 3, 3 } };

		double[][] matriz = { { 4, 5, 8, 10, 11 }, { 7, 6, 5, 7, 4 }, { 8, 5, 12, 9, 6 }, { 6, 6, 13, 10, 7 },
				{ 4, 5, 7, 9, 8 } };

		Matrix G = new Matrix(matriz);
		G.setDescending(false);

		KuhnMunkres km = new KuhnMunkres();

		km.resolve(G);

	}
}
