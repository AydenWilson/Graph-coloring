/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.vector.*;
import ec.*;
import ec.util.*;

/*
 * OurMutatorPipeline.java
 */




public class GraphMutatorPipeline extends BreedingPipeline {
    	public static final String P_GRAPHMUTATION = "graph-mutation";

    
    	public Parameter defaultBase() { return VectorDefaults.base().push(P_GRAPHMUTATION); }
    
    	public static final int NUM_SOURCES = 1;

    	// Return 1 -- we only use one source
    	public int numSources() { return NUM_SOURCES; }

    
    	public int produce(final int min, final int max, final int start, final int subpopulation,final Individual[] inds,
		final EvolutionState state, final int thread){
        	// grab individuals from our source and stick 'em right into inds.
      	 	// we'll modify them from there
     	  	int n = sources[0].produce(min,max,start,subpopulation,inds,state,thread);


    	    	// clone the individuals if necessary -- if our source is a BreedingPipeline
   	    	// they've already been cloned, but if the source is a SelectionMethod, the
   	     	// individuals are actual individuals from the previous population
   	     	if (!(sources[0] instanceof BreedingPipeline))
      		      for(int q=start;q<n+start;q++)
                	inds[q] = (Individual)(inds[q].clone());

        	// Check to make sure that the individuals are GraphVectorIndividuals and
        	// grab their species.  For efficiency's sake, we assume that all the 
        	// individuals in inds[] are the same type of individual and that they all
        	// share the same common species -- this is a safe assumption because they're 
        	// all breeding from the same subpopulation.

        	if (!(inds[start] instanceof GraphVectorIndividual)) // uh oh, wrong kind of individual
            		state.output.fatal("OurMutatorPipeline didn't get an GraphVectorIndividual." +
                	"The offending individual is in subpopulation " + subpopulation + " and it's:" + inds[start]);
       
        	// mutate 'em!
        	for(int q=start;q<n+start;q++) {
            		GraphVectorIndividual i = (GraphVectorIndividual)inds[q];

			if(((GraphVectorIndividual)((GraphSimpleStatistics)state.statistics).getBestCurrent()[0]).fitness.fitness() < Graph.walkCutoff){
				i.altMutate(state, thread);
			}else{
            			i.defaultMutate(state,thread);
            		}
			i.evaluated=false;
         	}

        	return n;
        }

 }
    
    
