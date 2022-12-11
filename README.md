# Project Gegevensstructuren en Algoritmen 2022-23
Arthur Cremelie

## Getting started

### Getting .tsp files
Making a Graph object requires a `.tsp` file with information about the nodes (cities) and their coordinates. Some can be found on the site of [TSPLIB from the Ruprecht-Karls-Universitat Heidelberg](http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/). There are also some `.tsp` files in the `data` folder.
All the edge weight types used for the Traveling Salesman Problem are accepted. These are: "EUC_2D", "GEO", "ATT", "EXPLICIT". In the case of "EXPLICIT", the following edge weight formats are accepted: "LOWER_DIAG_ROW", "UPPER_DIAG_ROW", "LOWER_DIAG_COL", "UPPER_DIAG_COL", "LOWER_ROW", "UPPER_ROW", "LOWER_COL", "UPPER_COL", "FULL_MATRIX".

## Usage

### Making a Graph object
In order to use the Tabu Search algorithm or the Ant Colony System algorithm implementations, we only have to make a `Graph` object. From this object, we can call methods to get results.

The constructor for a `Graph` object needs one argument: the name of the `.tsp` file without the '.tsp' extension. This file needs to be located in the `data` folder.

An example of making a Graph object for the berlin52.tsp file:
```java
Graph berlin = new Graph("berlin52");
```

### Using a Graph object
Now that we have created a `Graph` object, we're ready to use some methods and run the algorithms. Both algorithms will return a `Tour` object. More information about the usage of a `Tour` object can be found [here](#using-a-tour-object).

#### Making a tour with Tabu Search
Just run the method `.getTabuSearchBestTour()` with one argument: the maximum amount of iterations. For example, the following code will run Tabu Search with 200 iterations on the berlin52 problem.
```java
Tour berlinTourTS = berlin.getTabuSearchBestTour(200);
```

#### Making a tour with Ant Colony Optimization
Run the method `.getOtherHeuristicBestTour()` with one argument: the maximum amount of iterations. For example, the following code will run Ant Colony System with 50 iterations on the berlin52 problem.
```java
Tour berlinTourACS = berlin.getOtherHeuristicBestTour(50);
```
### Using a Tour object
Now that we have some `Tour` objects, we can use some methods of these objects in order to get some information of the created tour. The two most important methods are: `.getTour()` which returns the list with the cities in order and `.getTourLength()` which returns the length of the tour.
```java
List<Integer> berlinTourTSList = berlinTourTS.getTour();
int berlinTourTSLength = berlinTourTS.getTourLength();
```