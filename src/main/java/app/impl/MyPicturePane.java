package app.impl;

import javafx.scene.layout.BorderPane;
import support.seamcarve.*;

/**
 * This class is your seam carving picture pane. It is a subclass of
 * PicturePane, an abstract class that takes care of all the drawing,
 * displaying, carving, and updating of seams and images for you. Your job is to
 * override the abstract method of PicturePane that actually finds the lowest
 * cost seam through the image.
 * 
 * See method comments and handouts for specifics on the steps of the seam
 * carving algorithm.
 *
 * 
 * @version 01/07/2017
 */

/*
 * I create an instance variable to store the importance values of each pixel in
 * the array. I also create instance variables to store the costArray and the
 * direction array
 */
public class MyPicturePane extends PicturePane {

	private int[][] _importanceValues;
	private int[][] _costArray;
	private int[][] _directionArray;

	/**
	 * The constructor accepts an image filename as a String and passes it to
	 * the superclass for displaying and manipulation.
	 * 
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);

	}

	/**
	 * In this method, you'll implement the dynamic programming algorithm that
	 * you learned on the first day of class to find the lowest cost seam from
	 * the top of the image to the bottom. BEFORE YOU START make sure you fully
	 * understand how the algorithm works and what it's doing. See the handout
	 * for some helpful resources and use hours/piazza to clarify conceptual
	 * blocks before you attempt to write code.
	 * 
	 * This method returns an array of ints that represents a seam. This size of
	 * this array is the height of the image. Each entry of the seam array
	 * corresponds to one row of the image. The data in each entry should be the
	 * x coordinate of the seam in this row. For example, given the below
	 * "image" where s is a seam pixel and - is a non-seam pixel
	 * 
	 * - s - - s - - - - s - - - - s -
	 * 
	 * 
	 * the following code will properly return a seam:
	 * 
	 * int[] currSeam = new int[4]; currSeam[0] = 1; currSeam[1] = 0;
	 * currSeam[2] = 1; currSeam[3] = 2; return currSeam;
	 * 
	 *
	 * This method is protected so it is accessible to the class MyPicturePane
	 * and is not accessible to other classes. PLEASE DO NOT CHANGE THIS!
	 *
	 * @return the lowest cost seam of the current image
	 */

	/*
	 * In this method, I first call two methods to fill in the array of
	 * importance values and to fill in the Cost/Direction array. I then
	 * utilized a for loop to find the lowest cost starting pixel in the top
	 * row, and then another for loop to find the lowest cost seam by cycling
	 * down the Direction array. I then return this seam at the end of the methodF
	 */

	protected int[] findLowestCostSeam() {
		// TODO: Your code here

		this.fillInImportanceValues();
		this.fillInCostDirectionArray();

		int minimumColumn = 0;
		int [] seam = new int[this.getPicHeight()];

		for (int j = 0; j < _costArray[0].length; j++) {

			if (_costArray[0][j] < _costArray[0][minimumColumn]) {

				minimumColumn = j;
			}
		}
		
		seam [0] = minimumColumn;
		
		for (int i = 0; i<this.getPicHeight()-1; i++){
			seam [i+1]= seam[i] + _directionArray[i][seam[i]];
		}

		return seam;
	}

	/*
	 * This class is used to fill in the importance values of each pixel in the
	 * array. Since the findLowestCostSeam method is only called every time the
	 * slider is moved, I have this method set the importanceValues array to
	 * null and then recreate a new array with the current picture size to
	 * prevent indexing issues
	 */

	private void fillInImportanceValues() {
		_importanceValues = null;
		_importanceValues = new int[this.getPicHeight()][this.getPicWidth()];

		for (int i = 0; i < this.getPicHeight(); i++) {
			for (int j = 0; j < this.getPicWidth(); j++) {

				// Case for the pixel in the top left corner of the image

				if (i == 0 && j == 0) {

					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1))))/2;
					
					// Case for the pixel in the top right corner of the image

				} else if (i == 0 && j == (this.getPicWidth() - 1)) {

					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1))))/2;

					// Case for the pixel in the bottom left corner of the image

				} else if (i == (this.getPicHeight() - 1) && j == 0) {
					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1))))/2;

					// Case for the pixel in the bottom right corner of the
					// image

				} else if (i == (this.getPicHeight() - 1) && j == (this.getPicWidth() - 1)) {

					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1))))/2;

					// Case for the pixels in the top row of the image, not
					// including the corner pieces

				} else if (i == 0) {

					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1))))/3;

					// Case for the pixels in the bottom row of the image, not
					// including the corner pieces

				} else if (i == (this.getPicHeight() - 1)) {

					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))
									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1))))/3;

					// Case for the pixels in the leftmost column of the image,
					// not including the corner pieces

				} else if (j == 0) {
					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1))))/3;

					// Case for the pixels in the rightmost column of the image

				} else if (j == (this.getPicWidth() - 1)) {
					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1))))/3;
					// Case for non-edge case scenarios
				} else {
					_importanceValues[i][j] =

							(Math.abs(this.getColorRed(this.getPixelColor(i, j))
									- this.getColorRed(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i - 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i - 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i + 1, j)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i + 1, j)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j + 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j + 1)))

									+

									Math.abs(this.getColorRed(this.getPixelColor(i, j))
											- this.getColorRed(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorGreen(this.getPixelColor(i, j))
											- this.getColorGreen(this.getPixelColor(i, j - 1)))
									+ Math.abs(this.getColorBlue(this.getPixelColor(i, j))
											- this.getColorBlue(this.getPixelColor(i, j - 1))))/4;

				}

			}
		}
	}

	/*
	 * This class is used to fill in the array of costs and directions for the
	 * algorithm. Since the findLowestCostSeam method is called every time the
	 * slider is pulled, the cost array and the direction array is cleared to
	 * null every time the method is called
	 */

	public void fillInCostDirectionArray() {

		_costArray = null;
		_directionArray = null;

		_costArray = new int[this.getPicHeight()][this.getPicWidth()];
		_directionArray = new int[this.getPicHeight() - 1][this.getPicWidth()];

		// This initializes the bottom row of the cost array to be equal to the
		// importance values
		_costArray[this.getPicHeight() - 1] = _importanceValues[this.getPicHeight() - 1];

		/*
		 * This loops through the entirety of the cost array. It fills the array
		 * from bottom to top, finding the lowest cost path from the current row
		 * to the row directly below. It then fills out the direction array
		 * accordingly. If the lowest path mini-seam was down and to the left,
		 * the direction array fills with a -1. If it is directly below, it
		 * fills with a 0. If it is down and to the right, it fills with a 1
		 */

		for (int i = this.getPicHeight() - 2; i > -1; i--) {
			for (int j = 0; j < this.getPicWidth(); j++) {

				// Case for when the pixel is the leftmost pixel of the image

				if (j == 0) {

					_costArray[i][j] = Math.min(_costArray[i + 1][j], _costArray[i + 1][j + 1])
							+ _importanceValues[i][j];

					if (Math.min(_costArray[i + 1][j], _costArray[i + 1][j + 1]) == _costArray[i + 1][j]) {
						_directionArray[i][j] = 0;
					}

					if (Math.min(_costArray[i + 1][j], _costArray[i + 1][j + 1]) == _costArray[i + 1][j + 1]) {
						_directionArray[i][j] = 1;
					}

				}

				// Case for when the pixel is the rightmost pixel of the image

				else if (j == this.getPicWidth() - 1) {

					_costArray[i][j] = Math.min(_costArray[i + 1][j], _costArray[i + 1][j - 1])
							+ _importanceValues[i][j];

					if (Math.min(_costArray[i + 1][j], _costArray[i + 1][j - 1]) == _costArray[i + 1][j]) {
						_directionArray[i][j] = 0;
					}

					if (Math.min(_costArray[i + 1][j], _costArray[i + 1][j - 1]) == _costArray[i + 1][j - 1]) {
						_directionArray[i][j] = -1;
					}
				}

				else {
					_costArray[i][j] = Math.min(_costArray[i + 1][j],
							Math.min(_costArray[i + 1][j - 1], _costArray[i + 1][j + 1])) + _importanceValues[i][j];

					if (Math.min(_costArray[i + 1][j],
							Math.min(_costArray[i + 1][j - 1], _costArray[i + 1][j + 1])) == _costArray[i + 1][j - 1]) {
						_directionArray[i][j] = -1;
					}

					if (Math.min(_costArray[i + 1][j],
							Math.min(_costArray[i + 1][j - 1], _costArray[i + 1][j + 1])) == _costArray[i + 1][j]) {
						_directionArray[i][j] = 0;
					}

					if (Math.min(_costArray[i + 1][j],
							Math.min(_costArray[i + 1][j - 1], _costArray[i + 1][j + 1])) == _costArray[i + 1][j + 1]) {
						_directionArray[i][j] = 1;
					}

				}

			}
		}

	}

}
