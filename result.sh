#!/bin/bash
i=0
cat stats.csv | while read ligne
do
	if [ $(($i % 2)) == 0 ] 
	then
		echo $ligne;
	fi
i=$(($i+1))
done
echo $ligne;