/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.*;
import ec.simple.*;
import ec.vector.*;

public class EdgeConflicts extends Problem implements SimpleProblemForm
    {
    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum)
        {
        if (ind.evaluated) return;

        if (!(ind instanceof GraphVectorIndividual))
            state.output.fatal("Whoa!  It's not a GraphVectorIndividual!!!",null);
        
        GraphVectorIndividual ind2 = (GraphVectorIndividual)ind;
        
        double rawfitness = ind2.cost(state);
        
        //state.output.warning(rawfitness );
	boolean ideal = false;
        if (rawfitness == 0) ideal = true;
        if (!(ind2.fitness instanceof SimpleCost))
            state.output.fatal("Whoa!  It's not a SimpleCost!!!",null);
        ((SimpleCost)ind2.fitness).setFitness(state, rawfitness, ideal);
        ind2.evaluated = true;
        }
    

public void describe(final EvolutionState state, final Individual ind,  final int subpopulation, final int threadnum,  final int log)
    {
        state.output.message(ind.toString());
    return;
    }
}

