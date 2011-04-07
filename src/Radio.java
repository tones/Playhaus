class Radio {
	int x1,
		y1,
		x2,
		y2,
		buttonCountX,
		buttonCountY,
		buttonSizeX,
		buttonSizeY,
		activeButton;

	// constructor
	Radio(int x1, int y1, int x2, int y2, int buttonCountX, int buttonCountY, int buttonSizeX, int buttonSizeY){
		// make sure assigned variables are valid
		if (x2 < x1)
			throw new IllegalArgumentException("Illegal dimensions: x1 must be less than or equal to x2.");
		if (y2 < y1)
			throw new IllegalArgumentException("Illegal dimensions: y1 must be less than or equal to y2.");
		if (((x2-x1+1) / buttonSizeX) != buttonCountX) 
			throw new IllegalArgumentException("Illegal dimensions: Button x-size and x-count must match master x-dimension.");
		if (((y2-y1+1) / buttonSizeY) != buttonCountY) 
			throw new IllegalArgumentException("Illegal dimensions: Button y-size and y-count must match master y-dimension.");
		
		// assign all the variables
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.buttonCountX = buttonCountX;
		this.buttonCountY = buttonCountY;
		this.buttonSizeX = buttonSizeX;
		this.buttonSizeY = buttonSizeY;
		this.activeButton = -1;
	}
	
	// informs the radio that a node has been pressed
	public boolean press(int x, int y){
		// check whether this click is in-bounds
		if ((x >= x1) && (x <= x2) && (y >= y1) && (y <= y2)){
			// press is in bounds, so figure out which button was pressed
			int xButton = (int)Math.floor((x - x1) / buttonSizeX);
			int yButton = (int)Math.floor((y - y1) / buttonSizeY);
			activeButton = (yButton * buttonCountX) + xButton;
			return true;
		} else {
			// press is out of bounds
			return false;
		}
	}
	
	public int[] bounds(){
		int[] bounds = {x1, y1, x2, y2};
		return bounds;
	}
	
	public int[] activeBounds(){
		int xButton = activeButton % buttonCountX;
		int yButton = activeButton / buttonCountY;
		int x1 = (xButton * buttonSizeX) + this.x1;
		int y1 = (yButton * buttonSizeY) + this.y1;
		int x2 = x1 + buttonSizeX - 1;
		int y2 = y1 + buttonSizeY - 1;
		int[] activeBounds = {x1, y1, x2, y2};
		return activeBounds;
	}
}
