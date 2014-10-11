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

	public int getSizeX()
	{
		return weights.length;
	}

	public Matrix(double[][] matrix)
	{
		this.weights = matrix;
		x_labels = new ArrayList<Double>();
		y_labels = new ArrayList<Double>();
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

	public void feasibleLabelling()
	{
		x_labels.clear();
		y_labels.clear();

		// Ly siempre es un vector de 0
		for (int i = 0; i < weights.length; i++)
		{
			double max = weights[i][0];
			for (int j = 1; j < weights[i].length; j++)
			{
				if (weights[i][j] > max) max = weights[i][j];
			}
			x_labels.add(max);
			y_labels.add((double)0);
		}
	}

	public Matrix getGL()
	{
		Matrix Gl = this.clone();
		double[][] w = Gl.weights;
		for (int i = 0; i < w.length; i++)
		{
			for (int j = 0; j < w[i].length; j++)
			{
				if (w[i][j] < x_labels.get(i)) w[i][j] = 0;
			}
		}
		return Gl;
	}

	public Matching getMatching()
	{
		Matching matching = new Matching();

		double[][] w = this.weights;
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
		double min = Double.POSITIVE_INFINITY;
		for(Vertex x : s)
		{
			for(int i = 0; i<weights[x.getX()].length;i++)
			{
				if(!t.contains(i))
				{
					double sum = x_labels.get(x.getX()) + y_labels.get(i) - weights[x.getX()][i]; 
					if(sum<min) min = sum;
				}	
			}
		}
		return min;
	}

	public void updateLabelling(Set<Vertex> s, Set<Vertex> t, double alpha)
	{
		for(int i = 0; i < weights.length;i++)
		{			
			if(s.contains(x_labels.get(i)))
			{
				double value = x_labels.get(i)-alpha;
				x_labels.set(i,value);
			}
			for(int j = 0 ; j<weights[i].length;j++)
			{
				if(t.contains(y_labels.get(j)))
				{
					double value = y_labels.get(j)+alpha;
					y_labels.set(j, value);
				}
			}
		}
	}

	public Set<Vertex> getNeighbors(Set<Vertex> s)
	{
		Set<Vertex> neighbors = new HashSet<>();
		for(Vertex v : s){
			int counter = 0;
			for(double vertex : weights[v.getX()])
			{
				if(vertex!=0)
				{
					neighbors.add(new Vertex(counter++));
				}
			}
		}
		return neighbors;
	}
}
