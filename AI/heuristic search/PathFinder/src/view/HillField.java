package view;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Map;

public class HillField extends Group{

	private static final double SIZE_PIXELS_DEFAULT = 600.0;				// Default value for the size (px) parameter.
	private static final Paint COLORPRIMARY_DEFAULT = Color.WHITE;
	
	private int lastModifiedIndex = -1;		// Index value of the last-clicked-upon HillStep contained within the Group node.
	private static int heightLimit;					// Upper limit of weight values.
	private static int hillSteps;							// Local value of the given input 'squares'.
	
	private double sizePixels = SIZE_PIXELS_DEFAULT;								// Local value N of the NxN size of the HillField in pixels. 						// Local value of the size of the generated HillSteps.
			
	private Paint colorPrimary = COLORPRIMARY_DEFAULT;					// Used to create the Paint objects contained within colorProgression.
	
	
	/**
	 * HillField. An NxN grid of HillStep objects.
	 * @param sizePixels The size of the HillField in pixels. The size of each HillStep object is {@link #sizePixels} divided by {@link #hillSteps}
	 * @param hillsteps Number of HillStep objects, N, on each side of the HillField. Limited by {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * @param heightLimit Highest allowed value for HillSteps' height values. Limited by {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public HillField(double sizePixels, int hillSteps, int heightLimit){
		
		// Store given attributes' values.
		this.sizePixels = sizePixels;
		this.hillSteps = hillSteps;
		this.heightLimit = heightLimit;
		
		// HillSteps are created, given random height values, and then assigned their colors.
		populateHillSteps();
		setTerrain();
		syncTerrainColors();
		
		this.printheights();
		
	} // END
	
	

	/*#############################################################################
	 * ################### - Get Methods - #########################################
	 * #############################################################################*/
	
	/**
	 * Returns the index of the HillStep within the one-dimensional Group which stores the HillStep objects.
	 * */
	public int getLastModifiedIndex(){
		return this.lastModifiedIndex;
	} // END

	/**
	 * Returns the pixel size of one side of the HillField.
	 * */
	public double getSizePixels(){
		return this.sizePixels;
	}
	
	/**
	 * Returns the number of squares (a.k.a. HillSteps) on one side of the grid. Value will be between {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * */
	public int getSizeHillSteps(){
		return this.hillSteps;
	} // END
	
	/**
	 * Returns the current height limit. Value will between {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public int getHeightLimit(){
		return this.heightLimit;
	} // END
	
	/**
	 * Returns the current value of the primary color.
	 * */
	public Paint getColorPrimary(){
		return this.colorPrimary;
	} // END
	
	
	
	/*#############################################################################
	 * ################### - Static Get Methods - ##################################
	 * #############################################################################*/
	
	/**
	 * Returns the default pixel size of one side of the HillField.
	 * */
	public static double getSizePixelsDefault(){
		return SIZE_PIXELS_DEFAULT;
	} // END
	
	/**
	 * Returns the absolute maximum value usable for the weight limit value.
	 * */
	public static int getHeightLimitMax(){
		return heightLimit;
	} // END
	/**
	 * Returns the absolute maximum value usable for the squares value.
	 * */
	public static int getHillStepsMax(){
		return hillSteps;
	} // END
	
	
	/*#############################################################################
	 * ################### - Set Methods - #########################################
	 * #############################################################################*/


	public void setTerrain()
	{
		Random r = new Random();
		for(int i=0;  i < (getChildrenUnmodifiable().size()); i++)
		{
			int randHeight = r.nextInt(getHeightLimit()+1);
			( (HillStep) getChildren().get(i)).setTerrainType(randHeight);
		}
	}
	
	
	
	/*#############################################################################
	 * ################### - Miscellaneous Methods - ###############################
	 * #############################################################################*/
	
	/**
	 * Following the creation or refreshment of the HillSteps' Terrain values,
	 *  the colors of each HillStep are assigned based on their stored height value.
	 * */
	private void syncTerrainColors()
	{
		for(int i=0; i < this.getChildrenUnmodifiable().size(); i++)
		{
			int height = ((HillStep) this.getChildren().get(i)).getTerrainType();
			Paint yanse = Color.BLACK;
			
			switch(height)
			{
			case Map.IMPASSABLE:
				yanse = Color.DARKGRAY;
				break;
			case Map.UNBLOCKED:
				yanse = Color.AQUAMARINE;
				break;
			case Map.HARD:
				yanse = Color.PURPLE;
				break;
			case Map.UNBLOCKEDHIGHWAY:
				yanse = Color.LIGHTBLUE;
				break;
			case Map.HARDHIGHWAY:
				yanse = Color.MEDIUMPURPLE;
				break;
			}
			
			((HillStep) this.getChildren().get(i)).setColor( yanse );
		}
	} // END

	/**
	 * Populates the HillField with generic HillStep objects.
	 * */
	private void populateHillSteps(){
		System.out.println("[HillField] HillSteps populated.");
		this.getChildren().clear();
		int hillStepsPerSide = this.getSizeHillSteps();
		double hillStepSize = this.getSizePixels() / hillStepsPerSide;
		
		// Generate HillSteps and populate the HillField.
		for(int col=0; col<this.getSizeHillSteps(); col++){
			// For each COLUMN
			for(int row=0; row<this.getSizeHillSteps(); row++){
				// For each ROW
				HillStep hs = new HillStep((hillStepSize*row), (hillStepSize*col), hillStepSize); // Xpos, Ypos, Xsize, Ysize 

				hs.setOnMouseClicked(e -> {
					this.lastModifiedIndex = this.getChildrenUnmodifiable().indexOf(hs);
				});
				this.getChildren().add(hs);
			} // for row
		} // for col
	} // END
	
	/**
	 * Scraps and recreates a new randomly-generated set of HillStep objects using the given parameters.
	 * @param hillsteps Number of HillStep objects, N, on each side of the HillField. Limited by {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * @param heightLimit Highest allowed value for HillSteps' height values. Limited by {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public void regenEntireField(int hillSteps, int heightLimit){
		System.out.println("[HillField] Entire HillField regenerated.");
		this.heightLimit = heightLimit;
		this.hillSteps = hillSteps;
		populateHillSteps();
		setTerrain();
		syncTerrainColors();
	} // END
	
	
	
	/**
	 * Overridden toString() method.
	 * */
	public String toString(){
		String s = "Size, Px  : " + this.getSizePixels() + "\t"
				+ "Steps/side: " + this.getSizeHillSteps() + "\t"
				+ "Step Color: " + this.getColorPrimary() + "\t"
				+ "Max Height: " + this.getHeightLimit() + "\t"
				+ "Clicked Last: " + this.getLastModifiedIndex();
		if( this.getLastModifiedIndex() > -1){
			s = s.concat("\t" + "Terrain: " + 
					((HillStep)this.getChildrenUnmodifiable().get(this.getLastModifiedIndex())).getTerrainType());
		}
		else{
			s = s.concat("\t" + "Terrain: -1");
		}
		
		return s;
		
	} // END
	
	public int[] getValidMoves(int position){
		
		int[] validMoves = {-1, -1, -1, -1};
		
		int hillSteps = (int) this.getSizeHillSteps();
		
		int upIndex = position - hillSteps;
		int downIndex = position + hillSteps;
		int leftIndex = position - 1;
		int rightIndex = position + 1;
		
		if(upIndex >= 0){
			validMoves[0] = upIndex;
		}
		
		if(downIndex < (this.getSizeHillSteps() * this.getSizeHillSteps())){
			validMoves[1] = downIndex;
		}

		if((leftIndex / hillSteps) == (position / hillSteps)){
			validMoves[2] = leftIndex;
		}
		
		if((rightIndex / hillSteps) == (position / hillSteps)){
			validMoves[3] = rightIndex;
		}
		
		return validMoves;
	} 
	
	public void printheights(){
		
		System.out.println("[Printheights]");
		System.out.println("\tHeight map");
		
		System.out.print("     ");
		for(int col=0; col<this.getSizeHillSteps(); col++){
			if(col/10 == 0){
				System.out.print(col + "  ");
			}
			else{
				System.out.print(col + " ");
			}
		} // for col
		
		System.out.print("\n     ");
		for(int col=0; col<this.getSizeHillSteps(); col++){
			System.out.print("|" + "  ");
		} // for col
		System.out.print("\n" + " " + 0 + " - ");
		
		
		int currentRow = 0;
		int previousRow = 0;
		for(int i=0; i<this.getChildrenUnmodifiable().size(); i++){
			
			currentRow = i / this.getSizeHillSteps();
			
			if(currentRow != previousRow){
				previousRow = currentRow;
				
				if(currentRow/10 == 0){
					System.out.print("\n" + " " + currentRow + " - ");
				}
				else{
					System.out.print("\n" + currentRow + " - ");
				}
				
				
			}
			
			int TerrainType = ((HillStep) this.getChildren().get(i)).getTerrainType();
			
			if(TerrainType/10 == 0){
				System.out.print(TerrainType + "  ");
			}
			else{
				System.out.print(TerrainType + " ");
			}
			
		} // for i
		System.out.println();
	} // END
} // END
