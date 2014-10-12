package mx.cinvestav.gdl.graph;

public class Edge
{
	private Vertex x, y;
	private boolean matching = false;

	public Edge(int i, int j)
	{
		this.x = new Vertex(i, 'x');
		this.y = new Vertex(j, 'y');
		matching = false;
	}

	public boolean isMatching()
	{
		return matching;
	}

	public void setMatching(boolean matching)
	{
		this.matching = matching;
	}

	public Vertex getX()
	{
		return x;
	}

	public Vertex getY()
	{
		return y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Edge other = (Edge) obj;
		if (x == null)
		{
			if (other.x != null) return false;
		}
		else if (!x.equals(other.x)) return false;
		if (y == null)
		{
			if (other.y != null) return false;
		}
		else if (!y.equals(other.y)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		if (matching) return x.toString().toUpperCase() + y.toString().toUpperCase();
		return x.toString() + y.toString();
	}
}
