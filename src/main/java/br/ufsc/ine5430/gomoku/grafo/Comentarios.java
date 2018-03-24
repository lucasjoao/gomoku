package br.ufsc.ine5430.gomoku.grafo;

public class Comentarios {

}



/*
G.éRegularBoolean

   "Verifica se todos os vértices de G possuem o mesmo grau"

   n := G.grau(G.umVértice)
   Para cada vG.vértices faça 
      Se G.grau(v) ~= n Então
         retorna falso
      Fim Se
   Fim
   retorna verdade
G.háCicloCom(v,vAnterior,jáVisitados)Boolean

   "Privado - verifica se v faz parte de algum ciclo no grafo"

   Se jáVisitados.pertence(v) Então
	retorna verdade
   Fim Se
   jáVisitados.adiciona(v)
   Para cada vAdjG.adjacentes(v) faça 
      Se vAdj ~= vAnterior Então
         Se háCiclo(vAdj, v, jáVisitados) Então
            retorna verdade
         Fim Se
      Fim Se
   Fim
   jáVisitados.remova(v)
   retorna falso



G.éCompletoBoolean
	  G.háCicloCom(v,vAnterior,jáVisitados)Boolean

   "Privado - verifica se v faz parte de algum ciclo no grafo"

   Se jáVisitados.pertence(v) Então
	retorna verdade
   Fim Se
   jáVisitados.adiciona(v)
   Para cada vAdjG.adjacentes(v) faça 
      Se vAdj ~= vAnterior Então
         Se háCiclo(vAdj, v, jáVisitados) Então
            retorna verdade
         Fim Se
      Fim Se
   Fim
   jáVisitados.remova(v)
   retorna falso

	  "Verifica se cada vértice de
	   G está conectados a todos os outros vértices"

   n := G.ordem - 1
   Para cada vG.vértices faça 
      Se G.grau(v) ~= n Então
         retorna falso
      Fim Se
   Fim
   retorna verdade
   
   
   
G.fechoTransitivo(v)Conjunto

   "Retorna um conjunto contendo todos os vértices de G que
    são transitivamente alcancáveis partindo-se de v"

   retorna G.procuraFechoTransitivo(v,Conjunto.novo)
   
   
   
  G.procuraFechoTransitivo(v,jáVisitados)Conjunto

   "Privado - utilizada por G.fechoTransitivo"

   jáVisitados.adiciona(v)
   Para cada vAdjG.adjacentes(v) faça 
      Se ~ jáVisitados.pertence(vAdj) Então
         G.procuraFechoTransitivo(vAdj,jáVisitados)
      Fim Se
   Fim
   retorna jáVisitados
   
   
   
   G.éConexoBoolean

   "Verifica se existe pelo menos um caminho que entre
    cada par de vértices de G"

   retorna (G.vertices).igual(G.fechoTransitivo(G.umVértice))
   
   
   
   
   G.éÁrvoreBoolean

   "Verifica se G é uma árvore, ou seja, 
		se não possue ciclos se é conexo"

	v := G.umVértice
	retorna G.éConexo e ~ G.háCicloCom(v,v,Conjunto.novo)
	
	
	
	
	
	G.háCicloCom(v,vAnterior,jáVisitados)Boolean

   "Privado - verifica se v faz parte de algum ciclo no grafo"

   Se jáVisitados.pertence(v) Então
	retorna verdade
   Fim Se
   jáVisitados.adiciona(v)
   Para cada vAdjG.adjacentes(v) faça 
      Se vAdj ~= vAnterior Então
         Se háCiclo(vAdj, v, jáVisitados) Então
            retorna verdade
         Fim Se
      Fim Se
   Fim
   jáVisitados.remova(v)
   retorna falso





*/