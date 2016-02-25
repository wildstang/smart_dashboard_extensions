# README #

The Wildstang Smart Dashboard extensions allow for custom controls on the WPI Smart Dashboard

### Extensions ###

* BBBCamera
** Sends an image from the beaglebone to the smart dashboard
** On the beaglebone side, an image is saved to a folder served by a webserver on port 8888. It sends the smart dashboard the URL and a toggle that indicates the image is updated and the smart dashboard should refresh its image.
** On the smart dashboard extension side, it listens for the toggle to change, then loads an image control using the URL specified

### How do I get set up? ###

* Export the extension as a BBBCamera.jar and copy it to %USER%/wpilib/tools/plugins