package mx.cinvestav.gdl.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Matrix
{
	private double weights[][];
	private List<Double> x_labels;
	private List<Double> y_labels;
	private boolean descending;

	public int getSizeX()
	{
		return weights.length;
	}

	public void setDescending(boolean descending)
	{
		this.descending = descending;
	}

	public Matrix(double[][] matrix)
	{
		this.weights = matrix;
		this.descending = false;
	}

	@Override
	public Matrix clone()
	{
		double[][] clone = new double[weights.length][];
		for (int i = 0; i < weights.length; i++)
		{
			clone[i] = weights[i].clone();
		}
		return new Matrix(clone);
	}

	@Override
	public String toString()
	{
		String out = "";
		for (int i = 0; i < weights.length; i++)
		{
			out += "\n" + Arrays.toString(weights[i]);
		}
		return out + "\n";
	}

	public void generateFeasibleLabelling()
	{
		x_labels = new ArrayList<Double>();
		y_labels = new ArrayList<Double>();

		// Ly siempre es un vector de 0
		for (int i = 0; i < weights.length; i++)
		{
			double max = weights[i][0];
			for (int j = 1; j < weights[i].length; j++)
			{
				if (descending && weights[i][j] < max) max = weights[i][j];
				else if (!descending && weights[i][j] > max) max = weights[i][j];
			}
			x_labels.add(max);
			y_labels.add(0d);
		}
	}

	public Matrix produceGL()
	{
		Matrix equalitySubgraph = this.clone();
		double[][] weights = equalitySubgraph.weights;
		for (int i = 0; i < weights.length; i++)
		{
			for (int j = 0; j < weights[i].length; j++)
			{
				if (weights[i][j] != x_labels.get(i) + y_labels.get(j))
				{
					weights[i][j] = 0;
				}
			}
		}
		return equalitySubgraph;
	}

	public Matching getArbitraryMatching()
	{
		Matching matching = new Matching();

		double[][] w = this.weights;
		List<Integer> satY = new ArrayList<>();
		for (int i = 0; i < w.length; i++)
		{
			boolean flag = true;
			for (int j = 0; j < w[i].length; j++)
			{
				if (w[i][j] != 0)
				{
					Edge e = new Edge(i, j);
					if (flag && !satY.contains(j))
					{
						e.setMatching(true);
						satY.add(j);
						flag = false;
					}
					matching.addEdge(e);
				}
			}
		}
		return matching;
	}

	public void updateMatching(Matching matching)
	{
		double[][] w = this.weights;
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < w.length; i++)
		{
			for (int j = 0; j < w[i].length; j++)
			{
				if (w[i][j] != 0)
				{
					Edge e = new Edge(i, j);
					edges.add(e);
				}
			}
		}
		for (Edge e : matching.getEdgeList())
		{
			if (e.isMatching())
			{
				int indexOf = edges.indexOf(e);
				edges.get(indexOf).setMatching(true);
			}
		}
		matching.setEdgeList(edges);
	}

	public double calculateAlpha(Set<Vertex> s, Set<Vertex> t)
	{
		double alpha;
		if (descending) alpha = Double.NEGATIVE_INFINITY;
		else alpha = Double.POSITIVE_INFINITY;
		for (Vertex x : s)
		{
			for (int i = 0; i < weights[x.getValue()].length; i++)
			{
				if (!t.contains(new Vertex(i, 'y')))
				{
					double sum = x_labels.get(x.getValue()) + y_labels.get(i) - weights[x.getValue()][i];
					if (descending && sum > alpha) alpha = sum;
					else if (!descending && sum < alpha) alpha = sum;
				}
			}
		}
		return alpha;
	}

	public void updateLabelling(Set<Vertex> s, Set<Vertex> t, double alpha)
	{
		for (Vertex x : s)
		{
			int index = x.getValue();
			x_labels.set(index, x_labels.get(index) - alpha);
		}
		for (Vertex y : t)
		{
			int index = y.getValue();
			y_labels.set(index, y_labels.get(index) + alpha);
		}
	}

	public Set<Vertex> getNeighbors(Set<Vertex> S)
	{
		Set<Vertex> neighbors = new HashSet<Vertex>();
		for (Vertex x : S)
		{
			for (int i = 0; i < weights[x.getValue()].length; i++)
			{
				if (weights[x.getValue()][i] != 0) neighbors.add(new Vertex(i, 'y'));
			}
		}
		return neighbors;
	}
}
