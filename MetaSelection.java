/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.*;
import ec.util.*;
import ec.select.*;


public class MetaSelection extends TournamentSelection 
    {
                   
    public int produce(final int subpopulation,
        final EvolutionState state,
        final int thread)
        {
      	  	if(((GraphVectorIndividual)((GraphSimpleStatistics)state.statistics).getBestCurrent()[0]).fitness.fitness() < Graph.walkCutoff){
			return ((GraphSimpleStatistics)state.statistics).bestindex;
		}else{
			return super.produce(subpopulation,state,thread);
		}
            
       
        }

       
    }
