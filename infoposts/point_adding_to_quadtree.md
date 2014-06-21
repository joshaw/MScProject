---
layout: default
title: Adding Points to a Quadtree
---

## Adding Points to a Quadtree

This is an overview of the algorithm used to add points to a new quadtree.

1. A new quadtree is created. This root node contains four empty children, each 
   one represents one quadrant of the plane, limited by the extent of the 
   original image.
1. The first point is added. It is placed in the child correponding to the 
   quadrant it lies is.
1. Each point that is added in the same way.
1. When the number of points in a child node reaches a given limit, all the 
   points from that node are removed, four new children are added to the node 
   so that it is no longer a leaf, and the points are re-added, this time into 
   the correct position in the children.

TODO

<!--
Created:  Thu 19 Jun 2014 09:01 PM
Modified: Sat 21 Jun 2014 07:26 PM
-->
