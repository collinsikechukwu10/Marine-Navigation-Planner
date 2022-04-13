# CS5011 A1: Marine Navigation Planner 
#### St Andrews Practical 1 Assignment on Marine navigation using different search strategies

###### Implementation checklist
- [x] Basic Agent: The Depth First Search (DFS) and Breadth First Search (BFS) were attempted and fully functional
with the BFS algorithm passing all the provided tests.
- [x] Intermediate Agent: The Best First Search (BestF) and A* Search (AStar) were attempted and fully functional.
- [x] Advanced Agent: One advanced strategy was implemented “Bidirectional Search (BDS)”.
- [x] Additional Implementations:
- [x] Advanced (Diagonal) moves were added alongside the basic moves.
- [x] Randomly generated configurations.

## Compilation and Running

### Running
To perform a search using the implemented strategies, the following command below must be run
```
java A1main <Algo> <ConfID> <additionalParameters>
```

| Parameter                | Description                                                                                                                                                                                                                               |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Algo**                 | The Algo parameter should be replaced with any of the implemented strategies below <br>`DFS`: Depth First Search <br>`BFS`: Breadth First Search <br>`BestF`:Best First Search <br>`AStar`: A Star Search <br>`BDS`: Bidirectional Search |
| **ConfID**               | The ConfID should be replaced with any of the implemented configurations:<br>`JCONF00` – `JCONF05` (uses for testing)<br>`CONF0` – `CONF24` (used for evaluation)                                                                         |
| **additionalParameters** | Additional parameters implemented for search are: <br><br>`useAdvancedMoves`: allows moves in diagonal directions in addition to the basic moves provided                                                                                 | 


An example for running a bidirectional search for configuration CONF8 would be:
```
java A1main BDS CONF8
```


### Evaluation
An evaluator tool was implemented to evaluate the search strategies with. To perform an evaluation on all search strategies, the command below must be run, this would generate a csv file
(eval.csv) that can be explored using excel.
```
java A1Main eval <additionalParameters>
```


| Parameter                | Description                                                                                                                                                                                                                |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **additionalParameters** | Additional parameters for evaluation are: <br><br>`randomMap`: generates evaluation based on a large set of random maps. If no additional parameter is used, evaluation is done using all the provided configuration maps. | 
