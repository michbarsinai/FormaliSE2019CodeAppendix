# BPjs Performance Test

This application measures the performance of BPjs as a verification tool, by measuring the duration required to fully traverse the state space of a maze simulation b-program. Mazes used have no walls, so that the state space is as large as possible, both in terms of states and edges. Various sizes of mazes are used, and each size is measured 10 times.

To run the model verification performance measurement application from a terminal:

    MAVEN_OPTS=-Xmx16G mvn exec:java

To run the model execution performance measurement application, use:

    MAVEN_OPTS=-Xmx16G mvn exec:java -Dexec.mainClass="il.ac.bgu.cs.bp.performancetest.ExecutionPerformanceTest"

## Notes
* This project uses [BPjs](https://github.com/bThink-BGU/BPjs).
* BPjs uses the Mozilla Rhino Javascript engine. See [project page and source code](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino).

