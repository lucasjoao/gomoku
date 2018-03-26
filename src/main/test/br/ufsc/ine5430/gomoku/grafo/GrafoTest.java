package br.ufsc.ine5430.gomoku.grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class GrafoTest {

	@Test
	public void testeAddVertice() throws Exception {

		Grafo grafo = new Grafo();
		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Assert.assertTrue(grafo.getVertices().size() == 2);
	}

	@Test
	public void testeRemoverVertice() throws Exception {

		Grafo grafo = new Grafo();
		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Assert.assertTrue(grafo.getVertices().size() == 2);

		grafo.removeVertice(nomeVertice1);
		Assert.assertTrue(grafo.getVertices().size() == 1);
	}

	@Test
	public void testeConectarVertice() throws Exception {

		Grafo grafo = new Grafo();
		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		grafo.conectar(nomeVertice1, nomeVertice2);

		List<Vertice> vertices = new ArrayList<Vertice>(grafo.getVertices());
		Assert.assertTrue(vertices.get(0).getAdjacentes().contains(vertices.get(1)));
		Assert.assertTrue(vertices.get(1).getAdjacentes().contains(vertices.get(0)));
	}

	@Test
	public void testeDesconectarVertice() throws Exception {

		Grafo grafo = new Grafo();
		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		grafo.conectar(nomeVertice1, nomeVertice2);

		grafo.desconectar(nomeVertice1, nomeVertice2);

		List<Vertice> vertices = new ArrayList<Vertice>(grafo.getVertices());
		Assert.assertTrue(vertices.get(0).getAdjacentes().size() == 0);
		Assert.assertTrue(vertices.get(1).getAdjacentes().size() == 0);
	}

	@Test
	public void testeOrdem() throws Exception {

		Grafo grafo = new Grafo();
		Assert.assertTrue(grafo.ordem() == 0);

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Assert.assertTrue(grafo.ordem() == 1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Assert.assertTrue(grafo.ordem() == 2);

	}

	@Test
	public void testeUmVertice() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Assert.assertNotNull(grafo.umVertice());
	}

	@Test
	public void testeAdjacentes() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);

		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice1, nomeVertice3);
		grafo.conectar(nomeVertice2, nomeVertice3);

		List<Vertice> vertices = new ArrayList<Vertice>(grafo.getVertices());
		Vertice vertice = vertices.get(0);
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(1)));
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(2)));

		vertice = vertices.get(1);
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(0)));
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(2)));

		vertice = vertices.get(2);
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(0)));
		Assert.assertTrue(vertice.getAdjacentes().contains(vertices.get(1)));

	}

	@Test
	public void testeGrau() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);
		Assert.assertTrue(grafo.grau(nomeVertice1) == 0);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		Assert.assertTrue(grafo.grau(nomeVertice2) == 0);

		grafo.conectar(nomeVertice1, nomeVertice2);

		Assert.assertTrue(grafo.grau(nomeVertice1) == 1);
		Assert.assertTrue(grafo.grau(nomeVertice2) == 1);
	}

	@Test
	public void testeEhRegularTrue() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		grafo.conectar(nomeVertice1, nomeVertice2);

		Assert.assertTrue(grafo.ehRegular());

		grafo.desconectar(nomeVertice1, nomeVertice2);
		Assert.assertTrue(grafo.ehRegular());
	}

	@Test
	public void testeEhRegularFalse() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		grafo.conectar(nomeVertice1, nomeVertice2);

		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);

		Assert.assertFalse(grafo.ehRegular());
	}

	@Test
	public void testeEhCompletoTrue() throws Exception {

		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Assert.assertTrue(grafo.ehCompleto());

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);

		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice1, nomeVertice3);
		grafo.conectar(nomeVertice2, nomeVertice3);

		Assert.assertTrue(grafo.ehCompleto());
	}

	@Test
	public void testeEhCompletoFalse() throws Exception {
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);

		Assert.assertFalse(grafo.ehCompleto());

	}
	
	@Test
	public void testeFechoTransitivo() throws Exception{
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		
		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);
		
		grafo.conectar(nomeVertice1, nomeVertice2);
		
		Set<Vertice> alcancaveis = grafo.fechoTransitivo(nomeVertice1);
		Assert.assertTrue(alcancaveis.size() == 2);
		
		for (Vertice vertice : alcancaveis) {
			Object chaveAlcancavel = vertice.getChave();
			if (chaveAlcancavel.equals(nomeVertice3)) {
				Assert.fail();
			}
			Assert.assertTrue(chaveAlcancavel.equals(nomeVertice1) || chaveAlcancavel.equals(nomeVertice2));
		}
	}
	
	@Test
	public void testeEhConexoTrue() throws Exception{
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		
		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);
		
		Object nomeVertice4 = "vertice4";
		grafo.criarVertice(nomeVertice4);
		
		Object nomeVertice5 = "vertice5";
		grafo.criarVertice(nomeVertice5);
		
		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice1, nomeVertice4);
		grafo.conectar(nomeVertice2, nomeVertice3);
		grafo.conectar(nomeVertice2, nomeVertice5);
		grafo.conectar(nomeVertice4, nomeVertice5);
		
		Assert.assertTrue(grafo.ehConexo());
	}
	
	@Test
	public void testeEhConexoFalse() throws Exception{
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		
		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);
		
		Object nomeVertice4 = "vertice4";
		grafo.criarVertice(nomeVertice4);
		
		Object nomeVertice5 = "vertice5";
		grafo.criarVertice(nomeVertice5);
		
		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice2, nomeVertice3);
		grafo.conectar(nomeVertice4, nomeVertice5);
		
		Assert.assertFalse(grafo.ehConexo());
	}
	
	@Test
	public void testeEhArvoreTrue() throws Exception{
		Grafo grafo = new Grafo();

		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		
		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);
		
		Object nomeVertice4 = "vertice4";
		grafo.criarVertice(nomeVertice4);
		
		Object nomeVertice5 = "vertice5";
		grafo.criarVertice(nomeVertice5);
		
		Object nomeVertice6 = "vertice6";
		grafo.criarVertice(nomeVertice6);
		
		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice1, nomeVertice4);
		grafo.conectar(nomeVertice2, nomeVertice3);
		grafo.conectar(nomeVertice2, nomeVertice5);
		grafo.conectar(nomeVertice5, nomeVertice6);
		
		Assert.assertTrue(grafo.ehArvore());
		
	}
	
	@Test
	public void testeEhArvoreFalse() throws Exception{
		Grafo grafo = new Grafo();
		
		Object nomeVertice1 = "vertice1";
		grafo.criarVertice(nomeVertice1);

		Object nomeVertice2 = "vertice2";
		grafo.criarVertice(nomeVertice2);
		
		Object nomeVertice3 = "vertice3";
		grafo.criarVertice(nomeVertice3);
		

		grafo.conectar(nomeVertice1, nomeVertice2);
		grafo.conectar(nomeVertice1, nomeVertice3);
		grafo.conectar(nomeVertice2, nomeVertice3);
		
		Assert.assertFalse(grafo.ehArvore());
	}
}