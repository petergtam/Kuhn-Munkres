package mx.cinvestav.gdl.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Matching
{
	private List<Edge> edgeList;

	public Matching()
	{
		edgeList = new ArrayList<Edge>();
	}

	public boolean addEdge(Edge e)
	{
		return edgeList.add(e);
	}

	public List<Edge> getEdgeList()
	{
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList)
	{
		this.edgeList = edgeList;
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

	public double weight(Matrix g)
	{
		double w = 0;
		for (Edge e : edgeList)
		{
			if (e.isMatching()) w += g.getWeight(e.getX().getValue(), e.getY().getValue());
		}
		return w;
	}

	public Vertex isXSaturated(Matrix g)
	{
		Set<Vertex> xsat = new HashSet<Vertex>();
		for (Edge e : edgeList)
		{
			if (e.isMatching()) xsat.add(e.getX());
		}
		for (int i = 0; i < g.getSizeX(); i++)
		{
			Vertex x = new Vertex(i, 'x');
			if (!xsat.contains(x)) return x;
		}
		return null;
	}

	public Vertex isYSaturated(Vertex y)
	{
		for (Edge e : edgeList)
		{
			if (y.equals(e.getY()) && e.isMatching()) return e.getX();
		}
		return null;
	}

	public Matching getAugmentingPath(Matrix equalitySubgraph_GL, Vertex start_x, Vertex end_y)
	{
		Queue<Vertex> open = new LinkedList<Vertex>();
		List<Vertex> closed = new ArrayList<Vertex>();
		open.add(start_x);

		Vertex end = null;
		tree: while (!open.isEmpty())
		{
			Vertex v = open.poll();
			closed.add(v);
			List<Vertex> children = getChildren(equalitySubgraph_GL, v);
			for (Vertex c : children)
			{
				if (!closed.contains(c) && !open.contains(c))
				{
					c.setParent(v);
					if (c.equals(end_y))
					{
						end = c;
						break tree;
					}
					open.add(c);
				}
			}
		}
		Vertex i = end;

		List<Edge> path = new ArrayList<Edge>();
		while (i != null && i.getParent() != null)
		{
			if (i.getType() == 'y') path.add(new Edge(i.getParent().getValue(), i.getValue()));
			else path.add(new Edge(i.getValue(), i.getParent().getValue()));
			i = i.getParent();
		}
		for (Edge e : this.edgeList)
		{
			if (e.isMatching())
			{
				int indexOf = path.indexOf(e);
				if (indexOf != -1) path.get(indexOf).setMatching(true);
			}
		}

		Matching p = new Matching();
		p.edgeList = path;
		return p;
	}

	private List<Vertex> getChildren(Matrix equalitySubgraph_GL, Vertex v)
	{
		List<Vertex> children = new ArrayList<Vertex>();
		if (v.getType() == 'x')
		{
			for (Edge e : edgeList)
			{
				if (v.equals(e.getX()) && !e.isMatching()) children.add(e.getY());
			}
		}
		else
		{
			for (Edge e : edgeList)
			{
				if (v.equals(e.getY()) && e.isMatching())
				{
					children.add(e.getX());
					break;
				}
			}
		}
		return children;
	}

	public void xor(Matching path)
	{
		for (Edge e : edgeList)
		{
			if (path.edgeList.contains(e)) e.setMatching(!e.isMatching());
		}
	}
}
