#!/bin/bash
for i in  10 20 30 40 50 60 70 80 90 100 200 300 400 500 600 700 800 900 1000 2000 3000 4000 5000 6000 7000 8000 9000 10000
do
	for j in 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0
	do
		for k in `seq 1 5`;
		do
			java $JVM_OPTS -jar SolverTest-1.0-SNAPSHOT.jar $i $j
			sleep 1s
		done
		
	done
done
