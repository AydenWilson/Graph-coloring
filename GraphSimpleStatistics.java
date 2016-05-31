/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.*;
import ec.steadystate.*;
import ec.simple.*;
import java.io.IOException;
import ec.util.*;
import java.io.File;

// Edited to save he current best individual each generation to save having to find it later
 /*
 * @author Sean Luke
 * @author Adam Fleming
 * @version 1.0 
 */

public class GraphSimpleStatistics extends SimpleStatistics 
    {
   	public Individual[] best_i = null;
	public Individual[] getBestCurrent() { return best_i; }
	public int bestindex=0;
    boolean warned = false;
    long starttime = 0;
    // same as in simple statistics but added the hooks to save info
    public void postEvaluationStatistics(final EvolutionState state)
        {
        //super.postEvaluationStatistics(state);
        if(starttime==0){
            starttime= System.nanoTime();
        }
        // for now we just print the best fitness per subpopulation.
        best_i = new Individual[state.population.subpops.length];  // quiets compiler complaints
        for(int x=0;x<state.population.subpops.length;x++)
            {
            best_i[x] = state.population.subpops[x].individuals[0];
            for(int y=1;y<state.population.subpops[x].individuals.length;y++)
                {
                if (state.population.subpops[x].individuals[y] == null)
                    {
                    if (!warned)
                        {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                        }
                    }
                else if (best_i[x] == null || state.population.subpops[x].individuals[y].fitness.betterThan(best_i[x].fitness)){
                    best_i[x] = state.population.subpops[x].individuals[y];
			bestindex = y;
		}
                if (best_i[x] == null)
                    {
                    if (!warned)
                        {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                        }
                    }
                }
        
            // now test to see if it's the new best_of_run
            if (best_of_run[x]==null || best_i[x].fitness.betterThan(best_of_run[x].fitness))
                best_of_run[x] = (Individual)(best_i[x].clone());
            }
        
        // print the best-of-generation individual
        if (doGeneration) state.output.println("\nGeneration: " + state.generation,statisticslog);
        if (doGeneration) state.output.println("Best Individual:",statisticslog);
        for(int x=0;x<state.population.subpops.length;x++)
            {
            if (doGeneration) state.output.println("Subpopulation " + x + ":",statisticslog);
            if (doGeneration) best_i[x].printIndividualForHumans(state,statisticslog);
            if (doMessage && !silentPrint) state.output.message("Subpop " + x + " best fitness of generation" + 
                (best_i[x].evaluated ? " " : " (evaluated flag not set): ") +
                best_i[x].fitness.fitnessToStringForHumans());

            
                
            // describe the winner if there is a description
            if (doGeneration && doPerGenerationDescription) 
                {
                
                if (state.evaluator.p_problem instanceof SimpleProblemForm)
                    ((SimpleProblemForm)(state.evaluator.p_problem.clone())).describe(state, best_i[x], x, 0, statisticslog);   
                }   
            }
        }

        public void finalStatistics(final EvolutionState state, final int result){

            super.finalStatistics(state,result);
            state.output.message("Time to run: "+((System.nanoTime()-starttime)/1e6));
        }

   
    }
