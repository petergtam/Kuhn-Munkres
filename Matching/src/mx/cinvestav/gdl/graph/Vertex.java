package mx.cinvestav.gdl.graph;

public class Vertex
{
	private int x;

	public Vertex(int number)
	{
		this.x = number;
	}

	public int getX()
	{
		return x;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vertex other = (Vertex) obj;
		if (x != other.x) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return Integer.toString(x);
	}
}
