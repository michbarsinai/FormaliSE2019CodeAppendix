# BPJ+JPF

This directory contains code required to verify a b-program written in BPJ (a library for writing b-programs in Java) using [Java PathFinder](https://github.com/javapathfinder/jpf-core). Unlike BPjs, BPJ does not support modular event selection strategies. Thus, we had to fork it to support the event selection algorithm required for the maze simulation b-program. The updated BPJ version is included in link:BPJ-equal[]. The link:BPJ-Maze[] directory contains the BPJ-based maze simulation which was verified in Section 5 (Empirical Results).

> **NOTE**
> 
> JavaPathFinder needs to be downloaded from link:https://github.com/javapathfinder/jpf-core[here]. Note that it can only be used with a late version of Java 8 (our paper used 1.8.0_201).
