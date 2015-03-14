# To Do #

  * "Model" Tasks:
    * <strike> Mobius strip from .off </strike>
    * |Immersed Klein bottle .off| ??? |
|:-------------------------|:----|
    * |Surfaces with more genus .off files| ???|
    * |Set up marker models and surface models to use a consistent scale|
> > > That is to say, if the scales are consistent across our data files, we should never load an ant and a surface and find the ant is enormous in comparison to the surface. The easiest thing to do would be to start with the data we know we'll use in the museum exhibit (the ant model and some simple .off's), using blender 2.49 to rescale the data and export the .offs. We will still have an "object rescaling" slider, so the scales don't have to be perfect, they should just look reasonable by default.

  * "View" Tasks:
    * |1st person embedded view| ???|
|:-----------------------|:---|
      * Tie the embedded view to the perspective of the viewer
    * | Overlay the map (2D View)| ??? |
      * Place a black cutout over the view so that we do not see the boundary of the development.

  * "Picture" Tasks:
    * | Color scheme with patterns | ???|
|:---------------------------|:---|
      * Allow patterns in the faces, not just colors.
      * We now have a proof-of-concept for texturing faces. There are several issues to be addressed:
        * Revise the file TextureLibrary to include more textures. Also, textures in the library should be scaled so that they look good on our models (i.e. a checkboard texture shouldn't have squares that are too small).
        * We need a mechanism to read in texture specifications for each face. So, we need to extend our .off/.xml parser to read this data.
        * We also need to extend ColorScheme to something more general, like "FaceAppearanceScheme" to allow for a mapping of faces to textures to be stored.
        * We should think about how to make "seams" between triangles that have the same texture smooth. For example, the faces of a cube show seams on the diagonals.
        * Our current texturing code is sprinkled with "TODO"s (mostly about how jReality/AffineTransformation code works. We should resolve these (they're about 20 minutes apiece).

  * "Controller" Tasks:
    * |New controller| ???|
|:-------------|:---|
      * Probably nintendo controller?
      * SNES Controller is now up and running.
      * Added library lwjgl (LightWeight Java Game Library) which has better documentation of the jInput library (at least for the methods we use).
      * Now KeyboardController and SNESController extend from a base Controller class. This class can be re-used if/when we need to add other kinds of controllers.

  * "Animation" Tasks:
    * |Animation of changing perspective| ??? |
|:--------------------------------|:----|
      * Fly the camera from far away to the first person embedded view.
    * | Animation for unfolding | ??? |
      * Show unfolding from embedded view to first person


  * "Software Engineering" Tasks:
    * |Release version 0.1| ???|
|:------------------|:---|
      * This will have a limited choice of manifolds.

  * Bugs
    * |Fix bugs for non-embedded stuff| Staci/Taylor |
|:------------------------------|:-------------|
      * We now have a solution that checks the orientation of edges as faces are matched up, and if necessary changes the order of the edge's vertices in the underlying data structure (in Triangulation).  This solution appears to fix the bug for .xml files and for the first person view of non-orientable objects (such as the mobius strip).  However it creates a problem with the normal vectors of the embedded mobius strip.

  * Dynamics
    * Allow (live) changing geometry
      * | Changing radii tori| ??? |
|:-------------------|:----|
      * | Combinatorial Ricci flow| ??? |

  * Pie in the Sky Tasks:
    * | Multi-player games| ??? |
|:------------------|:----|
    * |Android pad functionality| ??? |
    * |VR goggles functionality|??? |
    * |Exotic controller functionality| ??? |
      * Kinect?

  * From related projects:
    * |xml functionality for geoquantViewer runnable jar| ??? |
|:------------------------------------------------|:----|
      * It will not read this right now.