package mx.cinvestav.gdl.graph;

public class Edge
{
	private int x, y;
	private boolean matching = false;

	public Edge(int i, int j)
	{
		this.x = i;
		this.y = j;
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

	@Override
	public String toString()
	{
		if (matching) return "X" + x + "Y" + y;
		return "x" + x + "y" + y;
	}
}
