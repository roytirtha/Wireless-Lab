package application;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class SimulatorController {

	public int citySize, areaType, cellType;
	
	@FXML
    private JFXTextField area;

    @FXML
    private JFXTextField radius;

    @FXML
    private JFXTextField totalFreq;

    @FXML
    private JFXTextField reuseFact;

    @FXML
    private JFXButton buttonCalc1;

    @FXML
    private JFXRadioButton macroCell;
    
    @FXML
    private ToggleGroup groupCell;

    @FXML
    private JFXRadioButton microCell;

    @FXML
    private JFXTextField cellNumber;

    @FXML
    private JFXTextField channelsPerCell;

    @FXML
    private JFXTextField totalChannelCap;

    @FXML
    private JFXTextField totalCall;

    @FXML
    private JFXTextField carrierFreq;

    @FXML
    private JFXTextField heightTransmit;

    @FXML
    private JFXTextField heightReceive;

    @FXML
    private JFXTextField propDistance;

    @FXML
    private JFXRadioButton smallCity;
    
    @FXML
    private ToggleGroup groupCity;

    @FXML
    private JFXRadioButton mediumCity;

    @FXML
    private JFXRadioButton largeCity;

    @FXML
    private JFXRadioButton urbanArea;
    
    @FXML
    private ToggleGroup groupArea;

    @FXML
    private JFXRadioButton subUrbArea;

    @FXML
    private JFXRadioButton openArea;

    @FXML
    private JFXButton buttonCalc2;

    @FXML
    private JFXTextField pathLoss;
    
    @FXML
    void calculate1(ActionEvent event) {

    	try {
    		
    		Double areaSize = Double.parseDouble(area.getText());
    		Double cellRadius = Double.parseDouble(radius.getText());
    		Double factor = Double.parseDouble(reuseFact.getText());
    		Double freq = Double.parseDouble(totalFreq.getText());
    		boolean valid = checkFactor(factor);
    		
    		
    		
    		Double reqCell = (double) Math.round(areaSize/(1.5*(Math.sqrt(3))*(Math.pow(cellRadius, 2))));
    		Double numberOfChannelsPerCell = (double) Math.round(freq/factor);
    		Double capacity = numberOfChannelsPerCell*reqCell; 
    		Double concurrentCalls = capacity;
 
    		area.clear();
    		radius.clear();
    		reuseFact.clear();
    		totalFreq.clear();
 
    		if(valid == true) {
    			
    			result1(reqCell, numberOfChannelsPerCell, capacity, concurrentCalls );
    		}
    		else {
    			cellNumber.setText("Invalid Reuse Factor");
    			channelsPerCell.setText("Invalid Reuse Factor");
    	    	totalChannelCap.setText("Invalid Reuse Factor");
    	    	totalCall.setText("Invalid Reuse Factor");
    		}
    		
    	}catch(Exception e) {
    		
    	}
    }
    
    private boolean checkFactor(Double factor){
        for(int i=0;i<10000;i++){
            for(int j=0;j<10000;j++){
                long val = (long) 0 + i*i + i*j + j*j ;
                if(val == factor) return true;
            }
        }
        return false;
    }
    
    void result1 (Double required, Double numberOfChannelsPerCell, Double capacity, Double concurrentCalls ) {
    	cellNumber.setText(required.toString());
    	channelsPerCell.setText(numberOfChannelsPerCell.toString());
    	totalChannelCap.setText(capacity.toString());
    	totalCall.setText(concurrentCalls.toString());
    }

    @FXML
    void calculate2(ActionEvent event) {

    	try {
    		Double carrFreq = Double.parseDouble(carrierFreq.getText());
    		Double heightTr = Double.parseDouble(heightTransmit.getText());
    		Double heightRec= Double.parseDouble(heightReceive.getText());
    		Double dist = Double.parseDouble(propDistance.getText());
    		
    		int citySz = citySize;
    		int areaTp = areaType;
    		
    		Double ahr; 
    		Double loss = (double) -20;
    		
    		Double logFreq = Math.log10(carrFreq);
    		Double logTrHeight = Math.log10(heightTr);
    		Double logDist = Math.log10(dist);
    		
    		if(carrFreq<150||carrFreq>1500||heightTr<30||heightTr>300||heightRec<1||heightRec>10||dist<1||dist>20 ) {
    			pathLoss.setText("Invalid input");
    		}
    		else {
	    		if(citySz == 2|| citySz == 3) {
	    			ahr = ((((1.1*logFreq)-0.7)*heightRec)-((1.56*logFreq)-0.8));
	    		}
	    		else {
	    			if(carrFreq <= 300) {
	 
	    				ahr = (((Math.pow((Math.log10((1.54*heightTr))),2))*8.29)-1.1); 
	 
	    			}else {
	 
	    				ahr = (((Math.pow((Math.log10((11.75*heightTr))),2))*3.2)-4.97);
	 
	    			}
	    		
	    		 
	    		Double L = (69.55 + (26.16*logFreq) - (13.82*logTrHeight) - ahr + ((44.9 - (6.55*logTrHeight))*logDist));
	    		
	    		
	    		if(areaTp == 1) {
	    			
	    			loss = L;
	    		}
	    		else if(areaTp == 2) {
	    			
	    			loss = (L - (2*(Math.pow((Math.log10((carrFreq/28))),2)))-5.4);
	    		}
	    		else if(areaTp == 3) {
	    			
	    			loss = (L - (4.78*(Math.pow(logFreq,2))) -(18.733*logFreq) -40.98);
	    		}
	 
	    		carrierFreq.clear();
	    		heightTransmit.clear();
	    		heightReceive.clear();
	    		propDistance.clear();
	    		
	    		result2(loss);
	    		}
    		}
    	}
    		catch(Exception e) {
    		
    	}
    }
    
    void result2(Double loss) {
    	pathLoss.setText(loss.toString());
    }
    
    public void citySizeSelect(ActionEvent event) {
    	if(smallCity.isSelected()) {
    		citySize = 1;
    	}
    	if(mediumCity.isSelected()) {
    		citySize = 2;
    	}
    	if(largeCity.isSelected()) {
    		citySize = 3;
    	}
    }
    
    public void areaSizeSelect(ActionEvent event) {
    	if(urbanArea.isSelected()) {
    		areaType = 1;
    	}
    	if(subUrbArea.isSelected()) {
    		areaType = 2;
    	}
    	if(openArea.isSelected()) {
    		areaType = 3;
    	}
    }
    
    public void cellTypeSelect(ActionEvent event) {
    	if(macroCell.isSelected()) {
    		cellType = 1;
    	}
    	if(subUrbArea.isSelected()) {
    		cellType = 2;
    	}
    }
    

}
