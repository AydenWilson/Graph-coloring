/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.*;
import ec.simple.*;
import ec.vector.*;
import ec.util.Parameter;

public class GraphVectorIndividual extends IntegerVectorIndividual{

	private int[] conflicts;

	public void setup(final EvolutionState state, final Parameter base) {
        	super.setup(state,base); 
		 
        
		conflicts = new int[genome.length+1];
		conflicts[0] = -1;
		
	}

	boolean hasConflict(int i,final EvolutionState state){
		for(int j = 0; j < Graph.adjacencyTable[i].length; j++){
			if(genome[Graph.adjacencyTable[i][j]] == genome[i])return true;
		}
		return false;
	}

    	public void defaultMutate(EvolutionState state, int thread){
		for(int i = 0; i < genome.length; i++){
		//state.output.warning(i+" conflict i");
			if (conflicts[i]!=-1 ) {
				if(!hasConflict(conflicts[i],state))continue;
				int cnode = conflicts[i];
				boolean[] invalid = new boolean[Graph.colourNum];
               			int	conflictcount = 0;
            			for(int k : Graph.adjacencyTable[cnode]){
            				if(!invalid[genome[k]]){
            					invalid[genome[k]] = true; 
            					conflictcount++;
            				}               	
           	 		}
            			int[] valid = new int[Graph.colourNum - conflictcount];
            			int k = 0,k2=0;
            			for(boolean b : invalid){
            				if(!b){
						valid[k++] = k2; 
					}
					k2++;
            			}
            	
            			if(valid.length == 0){
            	
            				genome[cnode] = state.random[thread].nextInt(Graph.colourNum);
            			}else{
          				genome[cnode] = valid[state.random[thread].nextInt(valid.length)];
          				
            			}
			}else{
				break;
			}
		}
	}
	public void altMutate(EvolutionState state, int thread){
		for(int i = 0; i < genome.length; i++){
		//state.output.warning(i+" conflict i");
			if (conflicts[i]!=-1 ) {
				if(!hasConflict(conflicts[i],state))continue;
				int cnode = conflicts[i];
				genome[cnode] = state.random[thread].nextInt(Graph.colourNum);
            			
			}else{
				break;
			}
		}
	}

	public double cost(final EvolutionState state){
		double cost = 0;
		int k =0;
		for(int i = 0; i < genome.length; i++){
			
			for(int j = 0; j < Graph.adjacencyTable[i].length; j++){
				if(genome[Graph.adjacencyTable[i][j]] == genome[i]){
					cost++;
					if(k-1<0 || conflicts[k-1]!= i){
						conflicts[k++]=i;
						conflicts[k] = -1;
					}
				}
			}
			
		}
		
		cost/=2;		
		//state.output.warning(cost+"");
		return cost;
	}

	public String toString(){
		String s = "[";
		for(int i = 0; i < genome.length; i++){
			s += genome[i] + ((i == genome.length-1)?"]\n":", ");
		}
		
		for(int i = 0; i < genome.length; i++){
			s += (i+1) + " - [";
			for(int j = 0; j < Graph.adjacencyTable[i].length; j++){
				s += (Graph.adjacencyTable[i][j]+1) + ((j == Graph.adjacencyTable[i].length-1)?"]\n":", ");
			}
		}
		return s;
	}
}
