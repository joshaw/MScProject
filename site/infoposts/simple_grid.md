---
layout: default
title: Simple Grid
---

## Simple Grid Method

The simplest method for analysing the distribution of points is to use a 
regular grid of cells and place the points into the cells. Once all points have 
been added, the number of points per cell can be treated as a greyscale 
birghtness value. A thresholding filter can then be applied to remove the 
points that are isolated.

The implementation to acheive this outputs a single pixel value for each of the 
cells of the grid. If written to a file, this can easily be viewed by treating 
the resulting file as an pnm image format.

![palm1 data from a grid analysis](infoposts/real_data/data_set1_grid.png)

<!--
Created:  Sat 21 Jun 2014 05:46 pm
-->
