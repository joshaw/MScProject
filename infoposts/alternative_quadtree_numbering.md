---
layout: default
title: Alternative Quadtree Numbering Systems
---

## Alternative Quadtree Numbering Systems

The representation of a quadtree in memory using a tree with 4 children for 
each non-lefa node is easy and simple. But it does not allow a single node in 
that tree structure to be refered to easily. To reference any node, each node 
can be assigned a code value when it is created. This code value has, as a 
prefix, the code value of its parent plus some other value that is unique to it 
among its siblings. The value of the root node is often chosen to be empty.

The choice of how to label the children is important if  the order in which the 
nodes are placed is important. For spatial indexing, for example, each node 
represents a quadrant in two dimensional speace, so being able to traverse the 
children in a sensible and predictable way is essential.

### Morton Code (Z-Order)

Perhaps the most natural order to give to the values in a spatial quadtree is 
to number them from 1 to 4, left to right, top to bottom. This can be made more 
appropriate for a computer to use by numbering from 0 to 3.

This has several useful features. 

1. First, this numbering system is easily extendable to any depth of tree that 
can be imagined.
	1. The root, as mentioned before, is given no value,
	1. each of the children are numbered 0 through 3.
	1. the children of these children are numbered 0 to 3 with the parent as a 
	prefix. So the children of node 0 are 00, 01, 02 and 03. Likewise, the 
	children of 3 are 30, 31, 32 and 33.
	1. The children are always numbered in the same order. If strting at the 
	top and going top to bottom, left to right, this is maintained for all 
	children.
1. The numbers can be converted to base 2.
	- 0 becomes 00
	- 1 becomes 01
	- 2 becomes 10 and
	- 3 becomes 11.
	1. This has advantages since binary is very efficient for computers to work 
	with and allows certain tricks to be employed (see [Morton order 
	coordinates](http://en.wikipedia.org/wiki/Z-order_curve)).

### Gray Codes

### Hilbert-Curve

### Custom Implementation

<!--
Created:  Wed 25 Jun 2014 10:49 pm
Modified: Wed 25 Jun 2014 10:49 pm
-->
