package mx.cinvestav.gdl.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Matrix
{
	private int weights[][];
	private List<Vertex> x_labels;
	private List<Vertex> y_labels;

	public int getSizeX()
	{
		return weights.length;
	}

	public Matrix(int[][] matrix)
	{
		this.weights = matrix;
	}

	@Override
	public Matrix clone()
	{
		int[][] clone = new int[weights.length][];
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
		x_labels = new ArrayList<Vertex>();
		y_labels = new ArrayList<Vertex>();

		// Ly siempre es un vector de 0
		for (int i = 0; i < weights.length; i++)
		{
			int max = weights[i][0];
			for (int j = 1; j < weights[i].length; j++)
			{
				if (weights[i][j] > max) max = weights[i][j];
			}
			x_labels.add(new Vertex(max));
			y_labels.add(new Vertex(0));
		}
	}

	public Matrix produceGL()
	{
		Matrix equalitySubgraph = this.clone();
		int[][] weights = equalitySubgraph.weights;
		for (int i = 0; i < weights.length; i++)
		{
			for (int j = 0; j < weights[i].length; j++)
			{
				if (weights[i][j] != x_labels.get(i).getV() + y_labels.get(j).getV())
				{
					weights[i][j] = 0;
				}
			}
		}
		return equalitySubgraph;
	}

	public Matching getMatching()
	{
		Matching matching = new Matching();

		int[][] w = this.weights;
		List<Integer> satX = new ArrayList<>();
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
						satX.add(i);
						satY.add(j);
						flag = false;
					}
					matching.addEdge(e);
				}
			}
		}
		matching.setSaturatedX(satX);
		matching.setSaturatedY(satY);
		return matching;
	}

	public double calculateAlpha(Set<Vertex> s, Set<Vertex> t)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateLabelling(Set<Vertex> s, Set<Vertex> t, double alpha)
	{
		// TODO Auto-generated method stub

	}

	public Set<Vertex> getNeighbors(Set<Vertex> s)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
