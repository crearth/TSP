# Project Gegevensstructuren en Algoritmen 2022-23
Arthur Cremelie

## Making tours with Tabu Search and Ant Colony System
In order to use the Tabu Search algorithm or the Ant Colony System algorithm implementations, you only have to make a `Graph` object. From this object, you can call methods to get results.

Making a Graph object requires a `.tsp` file with information about the nodes (cities) and their coordinates. Some can be found on the site of [TSPLIB from the Ruprecht-Karls-Universitat Heidelberg](http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/). There are also some `.tsp` files in the `data` folder.

The constructor for a `Graph` object needs one argument: the name of the .tsp file without the '.tsp' extension. This file needs to be located in the `data` folder.

An example of making a Graph object for the berlin52.tsp file:
```java
Graph berlin = new Graph("berlin52");
```
