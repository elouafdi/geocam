#This page lists the current tasks for the Geoquant Viewer project.

# Introduction #
Our goal is to make sure that the Geoquant viewer works correctly. However, this system is very complicated. To get started on getting the Geoquant viewer up and running, we want to write some simple unit tests for various Geoquants, and also to perform some simple refactoring on the Geoquant code.

# Writing a Unit Tests #
We want to write a unit test for basic quantities on the tetrahedron. This will require several steps.
  1. Figure out how to load the topology of a tetrahedron into the test.
  1. Test that the topology of the pentachoron data really makes sense.
  1. Figure out how to specify a regular (all side lengths 1) tetrahedron.
  1. Figure out how to set up needed Geoquants for this problem.
  1. Test that all face angles and all face areas make sense.
  1. Set up another test where one or more of the edge lengths is different. (Maybe, set things up so that the resulting triangles are still "nice" (30-60-90 triangles, for example).
  1. Repeat the steps above.

Quantities
  * Radius
  * Vertex Curvature 2D/3D
  * Eta
  * Edge Length
  * Edge Curvature
  * Premetric Length
  * Edge Height
  * Dihedral Angle
  * Dual Area
  * Face Angle
  * Face Height
  * Face Area
  * Tetra Volume
  * Total Volume
  * Dihedral Angle Sum
  * LEinstein
  * VEinstein
  * LCSC
  * VCSC
  * EHR
  * LEHR
  * VEHR

# To Do #
  * Remove two different ways of calling at/At on geoquants.

# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages