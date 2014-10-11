package mx.cinvestav.gdl.graph;

public class Vertex
{
	private int v;

	public Vertex(int number)
	{
		this.v = number;
	}

	public int getV()
	{
		return v;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + v;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vertex other = (Vertex) obj;
		if (v != other.v) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return Integer.toString(v);
	}
}
