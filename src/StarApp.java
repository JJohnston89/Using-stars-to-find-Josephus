import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the main class that creates the GUI, and simulates the stars exploding one by one until one is left alive.
 * Date: 03/02/2019
 * @author Joshua Johnston
 *
 */
public class StarApp extends Application{	
	
	private TextField getStarNum = new TextField();
	private TextField getStep = new TextField();
	private TextField getStart = new TextField();
	
	private TextArea output = new TextArea("The order is: \n");
	
	private Label status = new Label();
	
	private Timeline finalAnimation;
	
	public static void main(String[] args){
		
		Application.launch(args);		
	}
	
	/**
	 * This method is the start of the application.
	 */
	@Override
	public void start(Stage primaryStage){
		
		//Stage
		primaryStage.setTitle("Stars");		
		//primaryStage.setResizable(false);
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(true);						
		
		//Buttons
		Button submit = new Button("Submit");
		
		//text area size		
		output.setPrefSize(15, 512);  	
		
		//Grid layout
		GridPane gridpane = new GridPane();
		
		gridpane.setVgap(5);
		gridpane.setHgap(5);
		gridpane.setAlignment(Pos.TOP_LEFT);
		gridpane.setPadding(new Insets(10, 10, 10, 10));
		
		gridpane.add(new Text("Enter how many stars (4-40): "), 0, 0);
		gridpane.add(getStarNum, 1, 0);
		gridpane.add(new Text("Enter the step (x > 0): "), 0, 1);
		gridpane.add(getStep, 1, 1);
		gridpane.add(new Text("Enter the starting star (x >= 0): "), 0, 2);
		gridpane.add(getStart, 1, 2);		
		gridpane.add(submit, 0, 3);		
		gridpane.add(status, 0, 4);
		gridpane.add(output, 1, 6);		
		
		CircularLayout layout = new CircularLayout();
		
		//Button event
		submit.setOnMouseClicked(e -> {			
			
			if(finalAnimation != null){
				finalAnimation.stop();
				finalAnimation.getKeyFrames().clear();
			}
			
			layout.getChildren().clear();
			output.clear();
			output.setText("The order is: \n");
			
			if(validate()){		      //checking user input		
				
				status.setStyle(null);
				status.setText("");
				
				int starNumber = Integer.parseInt(getStarNum.getText());
				int first = Integer.parseInt(getStart.getText());
				int step = Integer.parseInt(getStep.getText());
				
				for(int i = 1; i <= starNumber; i++){			
					
					layout.getChildren().add(new Star(i));	 //creating the stars		
				}
				
				layout.setLayout(true);
				
				int[] orderList = getOrder(starNumber, first, step);  //The order the stars explode.
				
				finalAnimation = buildFrames(layout, orderList);
				finalAnimation.setDelay(Duration.seconds(3));
				finalAnimation.play();				
			}
			else{	
				
				status.setText("Error: invalid input");
				status.setStyle("-fx-border-color: red");
			}			
		});		
		
		//Border layout
		BorderPane borderpane = new BorderPane();		
		
		borderpane.setCenter(layout);
		borderpane.setRight(gridpane);		
		
		//Scene 
		Scene main = new Scene(borderpane);
		primaryStage.setScene(main);
		primaryStage.show();
		
	}
	
	/**
	 * This method checks the user input.
	 * @return (true or false) : boolean
	 */
	private boolean validate(){
		
		if(getStarNum.getText().isEmpty() || getStep.getText().isEmpty() || getStart.getText().isEmpty()){
			
			return false;
		}
		else{
			
			if(!getStarNum.getText().matches("[0-9]+") || !getStep.getText().matches("[0-9]+") || !getStart.getText().matches("[0-9]+")){
				
				return false;
			}				
			else if(Integer.parseInt(getStarNum.getText()) < 4 || Integer.parseInt(getStarNum.getText()) > 40){
				
				return false;
			}
			else if(Integer.parseInt(getStep.getText()) < 1){
				
				return false;
			}
			else if(Integer.parseInt(getStart.getText()) < 0){
				
				return false;
			}
			else{
				return true;
			}
		}		
	}
	
	
	/**
	 * This method gets the order in which the stars explode.
	 * @param numOfPeople : int
	 * @param first : int
	 * @param countOff : int
	 * @return array : int[]
	 */
	private int[] getOrder(int numOfPeople, int first, int countOff){
		
		int[] orderList = new int[numOfPeople];
		
		CircularLinkList list = new CircularLinkList();
		
		for(int i = 1; i <= orderList.length; i++){
			list.add(i);  
		}
		
		list.find(first);
		
		int index = 0;	
		while(list.getCount() > 1){
			
			orderList[index++] = list.delete(list.step(countOff));
		}
		orderList[index] = list.getCurrent().getData();
		
		return orderList;		
	}
	
	/**
	 * This method builds key frames on the order of the stars, and add the frames to the time line.
	 * @param list : CircularLayout
	 * @param order : int[]
	 * @return timeline : Timeline
	 */
	private Timeline buildFrames(CircularLayout list, int[] order){
		
		Timeline timeline = new Timeline();
		
		int i;
		int index = 0;
		
		for(i = 0; i < list.getChildren().size(); i++){
			
			index = order[i] - 1;
			Star current = (Star)list.getChildren().get(index);
			
			if(!(i >= list.getChildren().size() - 1)){
				
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i + 1), e -> {
				
					current.explodingStar();
					output.setText(output.getText() + current.getOrderNumber() + " is dead\n");	
					
					
				}));	
				
			}
			else{
				//This frame is for the star that is still alive.
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i + 1), e -> {
					current.getStarMoving().stop();
					current.getChildren().clear();
					current.drawStar();
					output.setText(output.getText() + current.getOrderNumber() + " is alive\n");					
				}));				
			}			
			
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i + 2), e -> {				
				//A pause
				
			}));
			
		}		
		return timeline;		
	}	
	
	/**
	 * This class is an inner class that creates the layout of the stars. The stars are equally laid out around a circle.
	 * Date: 03/02/2019
	 * @author Joshua Johnston
	 *
	 */
	class CircularLayout extends Pane{
		
		final double height = 700;
		final double width = 1200;		
		
		private double radius;			
		
		public CircularLayout(){			
			
			this.radius = 350;
			this.prefHeight(height);
			this.prefWidth(width);			
		}
		
		/**
		 * This is a helper method for makeLayout().
		 * @param flag : boolean
		 */
		public void setLayout(boolean flag){			
			
			if(flag)
				makeLayout();
		}
		
		/**
		 * This method lays the stars out around a circle.
		 */
		private void makeLayout(){			
			
			double deg = 0;
			double step = 360 / getChildren().size();		
			
			for(int i = 0; i < getChildren().size(); i++){				
				
				double x = (radius * Math.cos(Math.toRadians(deg))) + (width / 2);
				double y = (radius * Math.sin(Math.toRadians(deg))) + (height / 2);				
				
				this.layoutInArea(getChildren().get(i), x, y, 65, 65, 0.0, HPos.CENTER, VPos.CENTER);
				
				deg += step;				
			}			
		}		
	}
}
