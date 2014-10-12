package mx.cinvestav.gdl.graph;

public class Vertex
{
	private int value;
	private char type;
	private Vertex parent = null;

	public Vertex(int number, char type)
	{
		this.value = number;
		this.type = type;
	}

	public char getType()
	{
		return type;
	}

	public int getValue()
	{
		return value;
	}

	public Vertex getParent()
	{
		return parent;
	}

	public void setParent(Vertex parent)
	{
		this.parent = parent;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vertex other = (Vertex) obj;
		if (type != other.type) return false;
		if (value != other.value) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return type + Integer.toString(value);
	}
}
