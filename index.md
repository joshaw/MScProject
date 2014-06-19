---
layout: default
title: Home
---

# MSc Project

## About
- Sub-diffraction-limit imaging with quadtrees.

## Papers
- [Ultra-High Resolution Imaging by Flourescence Photoactivation Localization
  Microscopy](Papers/hess2006ultra.pdf)
- [PALM Imaging and Cluster Analysis of Protein Heterogeneity at the Cell
  Surface](Papers/owen2010palm.pdf)
- [Sub-Diffraction-Limit Imaging by Stochastic Optical Reconstruction
  Microscopy (STORM)](Papers/rust2006sub.pdf)
- [Pre-Existing Clusters of the Adaptor Lat do not Participate in Early T Cell
  Signaling Events](Papers/williamson2011pre.pdf)

## Info
- [Quadtrees](infoposts/quadtrees.html)
- [Quadtree algorithm for adding points](infoposts/point_adding_to_quadtree.md)
- [Randomly generated data](infoposts/randomdata.html)

## Links

#### Directed Percolation
- <http://en.wikipedia.org/wiki/Directed_percolation>
- Not needed (maybe) if using adjacency detection/pointers

#### Adjacency Detection using quad codes
- <http://dl.acm.org/citation.cfm?id=28574>
- Allows for standard quadtree to be used
- Simply uses the tree to work out if nodes are adjacent

#### Quad Trees and scan orders
- <http://www.ncgia.ucsb.edu/giscc/units/u057/u057.html>
- Applications
- Traversal orders

#### Morton Order
<http://en.wikipedia.org/wiki/Z-order_curve>

#### Un-even quadtrees
- <https://www.youtube.com/watch?v=Wnbm9j8PFWg>
- Add nodes to tree, location of each node defines how the current node is 
  divided
- Check the existing nodes for which child to place new nodes into.

#### Spatial Indexing with Quadtrees
- <http://architects.dzone.com/articles/algorithm-week-spatial>
- Add coordinates to a quadtree
