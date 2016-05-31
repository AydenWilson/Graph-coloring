
package ec.app.graph;
import java.util.Arrays;
import java.util.ArrayList;
import ec.*;
import ec.simple.*;
import ec.vector.*;
import ec.util.Parameter;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.InputStream;

public class Graph {


	public static int[][] adjacencyTable;
	public static int colourNum, walkCutoff;
	public static final String P_COLOURNUM = "colours";  // number of colours to use
	public static final String P_GRAPHFILE = "graphfile";  // representation of graph (.col)
	public static final String P_WALKCUTOFF = "walkcutoff";  // representation of graph (.col)

	public static void setup(final EvolutionState state){
      	  	Parameter p = new Parameter(P_COLOURNUM);
		colourNum = state.parameters.getInt(p,null,3);
		if(colourNum == 3) state.output.fatal("couldn’t read colournum");
		p = new Parameter(P_WALKCUTOFF);
		walkCutoff = state.parameters.getInt(p,null,5);
		if(walkCutoff == 2) state.output.fatal("couldn’t read walkcutoff");
		p = new Parameter(P_GRAPHFILE);

        	File  graphInput = state.parameters.getFile(p,null);
		if(graphInput == null)state.output.fatal("couldn’t read graph file");
		try{
		parse(graphInput,state);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	static void parse (File input,final EvolutionState state)throws FileNotFoundException{
		Scanner s = new Scanner(input);
		
		String line = "";
		while((line = s.nextLine()).charAt(0)!='p')continue;
		
		String[] dims = line.split(" ");
		int nodeNum = 0;
		state.parameters.set(new Parameter("pop.subpop.0.species.genome-size"),""+(nodeNum = Integer.parseInt(dims[2])));
		state.parameters.set(new Parameter("pop.subpop.0.species.max-gene"),""+(colourNum-1));

		state.output.warning("wrote genome size of "+ (state.parameters.getInt(new Parameter("pop.subpop.0.species.genome-size"),null,0)));
		int edgeNum = Integer.parseInt(dims[3]);		
		adjacencyTable = new int[nodeNum][];
		List<Integer>[] adjacencyTableTemp = new ArrayList[nodeNum];
		for(int i = 0; i < nodeNum; i++){
			adjacencyTableTemp[i] = new ArrayList<Integer>();
		}
		for(int i = 0; i < edgeNum; i++){
			line = s.nextLine();
			dims = line.split(" ");
			int node1 = Integer.parseInt(dims[1])-1;
			int node2 = Integer.parseInt(dims[2])-1;
			if(node2<node1)continue;
			adjacencyTableTemp[node1].add(node2);
			adjacencyTableTemp[node2].add(node1);
		}
		for(int i = 0; i < nodeNum; i++){
			adjacencyTable[i] = new int[adjacencyTableTemp[i].size()];
			for(int j = 0; j < adjacencyTable[i].length; j++){
				adjacencyTable[i][j] = adjacencyTableTemp[i].get(j);
			}
		}
	
	}
	/*
	
	public static void doShit(int popsize, int colournum){
		GraphVector[] population = new GraphVector[popsize];
		GraphVector[] temppop = new GraphVector[popsize];		
		BEST_EVER = Integer.MAX_VALUE;
		GraphVector best = null;
		for(int i = 0 ; i < popsize; i++){
			population[i] = new GraphVector(colournum);
			int cost = population[i].cost();
			if(cost<BEST_EVER){
				BEST_EVER = cost;
				BEST_CURRENT = cost;
					
				best = population[i];
				bestcurrent = best;
			}
		}
		
		while (BEST_EVER > 0 ) {
			for(int i = 0 ; i < popsize; i++){
				temppop[i] = mutate(crossover(getParents(population)));
			}
			System.arraycopy(temppop, 0, population, 0, popsize);
			BEST_CURRENT = Integer.MAX_VALUE;
			for(int i = 0 ; i < popsize; i++){
				int cost = population[i].cost();
				if(cost<BEST_EVER){
					BEST_EVER = cost;
					best = population[i];
				}
				if(cost<BEST_CURRENT){
					BEST_CURRENT = cost;
					bestcurrent = population[i];					
				}
			}
			System.out.println(BEST_CURRENT +" ");
		}
			System.out.println(BEST_EVER +" "+best);
	}
	
	public static GraphVector[] getParents(GraphVector[] pop){
		if(BEST_CURRENT < 5){
			GraphVector temp1 = pop[r.nextInt(pop.length)];
			GraphVector temp2 = pop[r.nextInt(pop.length)];

			GraphVector parent1 = temp1.getCost()<=temp2.getCost()?temp1:temp2;
		
			temp1 = pop[r.nextInt(pop.length)];
			temp2 = pop[r.nextInt(pop.length)];

			GraphVector parent2 = temp1.getCost()<=temp2.getCost()?temp1:temp2;

			return new GraphVector[]{parent1, parent2};
		}else{
			return new GraphVector[]{bestcurrent, bestcurrent};
		}
	}
	
	public static GraphVector crossover(GraphVector[] parents){
		int p = r.nextInt(parents[0].nodeNum-1);
		
		GraphVector child =  parents[0].copy();
		child.fill(parents[1],p);
		child.cost();
		return child;
	}
	
	public static GraphVector mutate(GraphVector hoopla){		
		if(BEST_CURRENT < 5){
			return hoopla.mutate0();
		}else{
			return hoopla.mutate();
		}
	}*/
}

