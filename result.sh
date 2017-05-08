#!/bin/bash
i=0
cat $1 | while read ligne
do
	if [ $(($i % 2)) == 0 ] 
	then
		echo $ligne;
	fi
i=$(($i+1))
done
echo $ligne;