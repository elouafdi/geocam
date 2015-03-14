This page explains how the menu system is laid out in Java as well as how a developer can make changes to the menus through the image editing program GIMP.

# Menu Displays #

Within the current version of the project, there is a basic menu design that allows the user to navigate through a series of menus by using either a keyboard or SNES controller. There are several components to these menus: buttons that the user can press to navigate from one page to the next, menus that display the buttons, and backgrounds on which the menus sit.

## Layout in Java ##

This section will provide a short description of the java classes that control the menu displays and what they do.

**MenuImage**

This class takes two images (which are saved as .png files) called the background and the currentMenu image and composes them into one image that will be displayed to the user.

**MenuUI**

This class reads input from the controller and, based on this input, displays the appropriate images to the user. Each menu is implemented as a method (for example, there is a startMenu method and a gameMenu method). There is a while loop that monitors the controller input and navigates within that menu as long as the user only presses the up/down/left/right buttons. When either the A button or the B button are selected, this while loop is broken and the user is either taken to the next menu (A) or the previous menu (B).

**Data Folder**

Under Data/frontEnd, there are three folders: buttons, menus, backgrounds. Each of these folders holds both the png files that are used to construct the displayed images and the xcf file that can be used by developers to change the properties of these images.


## Buttons ##
Each screen that is displayed to the user has a number of buttons that can be selected. This section covers how one can modify the buttons if needed.

The buttons were created using an image editing program called GIMP. One can open the file "buttonTemplate.xcf" to inspect how the button is laid out. The button template features a number of layers that hold graphic information about the button. There are currently six layers that describe a button: an "active" layer that gives the color of the button when it is selected, an "inactive" layer that gives the button color when it is not selected, "shadow" and "shine" layers that help give the button a 3-D look, a transparent background layer, and a text layer. In the template, the text layers are stored for all buttons used in the simulation. If one wishes to add a button, simply add a text layer to this template.

The standard font that was used is Sans Bold, size 45.

## Menus ##
A menu consists of a title and a number of buttons that can be selected by the user. The standard size for each menu is 700 x 700.

Each menu is stored separately in an xcf file with the name "Template". If one wishes to modify a particular menu, simply open the file under Data/frontEnd/Menus that corresponds to the appropriate menu. The template holds a text layer, an outline layer that supplements the text, a transparent background, and both an "active" and "inactive" button layer for each button that will appear to the user.

If one wishes to add/delete buttons from a menu, simply delete the layers for the button or add the other buttons as layers to the image. If buttons are added or deleted, the correct spacing should be maintained. The first button should be placed 25 from the bottom of the image (use the alignment tool and the offset feature). Subsequent buttons should be placed 125 from each other and centered in the image.

If one wishes to modify the text, the standard font should be used and the size should be maintained as much as possible. The standard font is Sans Bold, and the size is 75. The text box should be placed 25 from the top of the image.

## Backgrounds ##
The background images are saved as png files under Data/frontEnd/Backgrounds. One can add images to the collection by creating a png file that is at least 700 x 700. One can change which menu is associated with which background by modifying the background file in the initFiles method in MenuUI.