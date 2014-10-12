package mx.cinvestav.gdl.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Matching
{
	private List<Edge> edgeList;
	private List<Integer> saturatedX, saturatedY;

	public Matching()
	{
		edgeList = new ArrayList<Edge>();
	}

	public void setSaturatedX(List<Integer> saturatedX)
	{
		this.saturatedX = saturatedX;
	}

	public void setSaturatedY(List<Integer> saturatedY)
	{
		this.saturatedY = saturatedY;
	}

	public boolean addEdge(Edge e)
	{
		return edgeList.add(e);
	}

	@Override
	public String toString()
	{
		String out = "";
		for (Edge e : edgeList)
		{
			if (e.isMatching()) out += e + ", ";
		}
		return out;
	}

	public Vertex isXSaturated(Matrix gl)
	{
		Vertex u = null;
		for (int i = 0; i < gl.getSizeX(); i++)
		{
			if (!saturatedX.contains(i))
			{
				u = new Vertex(i);
				break;
			}
		}
		return u;
	}

	public Vertex isYSaturated(Vertex y, Set<Vertex> s)
	{
		Vertex z = null;
		for(Edge e:edgeList)
		{
			if(e.isMatching() && (y.getV()==e.getY()))
			{
				Vertex v = new Vertex(e.getX());
				if(!s.contains(v))
				{
					z = v;
					break;
				}
			}
		}
		return z;
	}

	public Matching getAugmentingPath(Vertex u, Vertex y)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Matching xor(Matching path)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
