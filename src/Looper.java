import processing.core.*;
import oscP5.*;
import netP5.*;

public class Looper extends PApplet  {
	Radio[] radios;
	OscP5 oscP5;
	NetAddress serialPyIO;
	
	public void setup(){
		// setup Monome
		oscP5 = new OscP5(this,8000);
		serialPyIO = new NetAddress("127.0.0.1",8080);
		this.oscClearAllLeds();	

		// configure node layout
		radios = new Radio[4];
		radios[0] = new Radio(0,0,3,3,2,2,2,2);		
		radios[1] = new Radio(4,0,7,3,2,2,2,2);		
		radios[2] = new Radio(8,0,11,3,2,2,2,2);		
		radios[3] = new Radio(12,0,15,3,2,2,2,2);
	}

	public void draw(){
		background(0);
		/*
		this.oscLed(1,1,1);
		delay(500);
		this.oscLed(1,1,0);
		delay(500);
		*/
	}

	/* incoming osc message are forwarded to the oscEvent method. */
	public void oscEvent(OscMessage theOscMessage) {
		// read incoming message
		String pattern = theOscMessage.addrPattern();
		if (pattern.equals("/playhaus/press")){
			// read button info
			Object[] argList = theOscMessage.arguments();
			int x = ((Integer)argList[0]).intValue();
			int y = ((Integer)argList[1]).intValue();
			int z = ((Integer)argList[2]).intValue();
			
			// is button being pressed down (not up)
			if (z == 1){
				// send press to radio arrays
				for (Radio r : radios){
					if (r.press(x,y)){
						// a new button has been pressed,
						// so re-draw the lights
						int[] clearme = r.bounds();
						this.oscSquareDark(clearme);
						int[] lightme = r.activeBounds();
						this.oscSquareLight(lightme);
					}
				}
			}
		}
	}

	void oscLed(int x, int y, int z){
		OscMessage m = new OscMessage("/playhaus/led");
		m.add(x);
		m.add(y);
		m.add(z);
		oscP5.send(m, serialPyIO); 		
	}

	void oscLedSquare(int x1, int y1, int x2, int y2, int z){
		for (int x = x1; x <= x2; x++){
			for (int y = y1; y <= y2; y++){
				this.oscLed(x, y, z);
			}
		}		
	}
	
	void oscSquareDark(int[] bounds){
		this.oscLedSquare(bounds[0], bounds[1], bounds[2], bounds[3], 0);
	}
	
	void oscSquareLight(int[] bounds){
		this.oscLedSquare(bounds[0], bounds[1], bounds[2], bounds[3], 1);
	}
	
	void oscClearAllLeds(){
		int[] bounds = {0,0,15,3};
		this.oscSquareDark(bounds);
	}
}

