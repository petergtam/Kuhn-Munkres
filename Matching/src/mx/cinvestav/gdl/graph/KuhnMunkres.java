package mx.cinvestav.gdl.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class Matrix
{
	public Matrix(int[][] matriz)
	{
		this.w = matriz;
	}

	int w[][];
}

class Matching
{
	List<Arista> m;
}

class Arista
{

	Vertice x, y;
	boolean matching = false;
}

class Vertice
{
	int x;

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
		Vertice other = (Vertice) obj;
		if (x != other.x) return false;
		return true;
	}

}

public class KuhnMunkres
{
	public void resolve(Matrix G)
	{
		// parte 1
		List<Vertice> lx = null, ly = null;
		etiquetadoFactible(G, lx, ly);
		Matrix Gl = obtenerGl(G, lx, ly);
		Matching M = obtenerMatching(Gl);
		Vertice u = null;
		Vertice y = null;
		while ((u = isXSaturated(M, Gl)) != null)
		{
			// parte 1

			Set<Vertice> S = new HashSet<Vertice>();
			S.add(u);
			Set<Vertice> T = new HashSet<Vertice>();
			boolean flag;
			do
			{

				// parte 2
				Set<Vertice> ngl = vecinos(Gl, S);
				if (ngl.equals(T))
				{
					double alpha = calculateAlpha(G, S, T, lx, ly);
					actualizarL(G, S, T, lx, ly, alpha);
					Gl = obtenerGl(G, lx, ly);
				}
				ngl.removeAll(T);
				Iterator<Vertice> i = ngl.iterator();
				y = null;
				if (i.hasNext())
				{
					y = i.next();
				}
				if (y == null)
				{
					System.out.println("Y ES VACIO!!!!");
					System.exit(1);
				}
				Vertice z = isYSaturated(M, y);
				// parte 2

				flag = (z == null) ? false : true;
				if (flag)
				{
					S.add(z);
					T.add(y);
				}
			} while (flag);

			// parte 3
			Matching path = caminoAumentante(M, u, y);
			M = xor(M, path);
			// parte3
		}
	}

	private Matching xor(Matching m, Matching path)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Matching caminoAumentante(Matching m, Vertice u, Vertice y)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Vertice isYSaturated(Matching m, Vertice y)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void actualizarL(Matrix g2, Set<Vertice> s, Set<Vertice> t, List<Vertice> lx, List<Vertice> ly, double alpha)
	{
		// TODO Auto-generated method stub

	}

	private double calculateAlpha(Matrix g2, Set<Vertice> s, Set<Vertice> t, List<Vertice> lx, List<Vertice> ly)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private Set<Vertice> vecinos(Matrix gl, Set<Vertice> s)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Vertice isXSaturated(Matching m, Matrix gl)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Matching obtenerMatching(Matrix gl)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Matrix obtenerGl(Matrix g2, List<Vertice> lx, List<Vertice> ly)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void etiquetadoFactible(Matrix g2, List<Vertice> lx, List<Vertice> ly)
	{
		// TODO Auto-generated method stub

	}

	public static void main(String[] args)
	{
		int[][] matriz = { { 3, 5, 5, 4, 1 }, { 2, 2, 0, 2, 2 }, { 2, 4, 4, 1, 0 }, { 0, 1, 1, 0, 0 },
				{ 1, 2, 1, 3, 3 } };

		Matrix G = new Matrix(matriz);

		KuhnMunkres km = new KuhnMunkres();

		km.resolve(G);

	}
}
