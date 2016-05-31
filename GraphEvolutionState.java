/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.graph;
import ec.util.*;
import ec.*;
import java.util.*;
import java.io.*;
import ec.simple.*;


public class GraphEvolutionState extends SimpleEvolutionState
    {
    
    public void setup(final EvolutionState state, final Parameter base)
        {
       		super.setup(state,base);  
 
		Graph.setup(this);
        }


   
    }
