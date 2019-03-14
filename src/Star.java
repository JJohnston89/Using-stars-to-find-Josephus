import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.Label;

/**
 * This class creates a star.
 * Date: 03/02/2019
 * @author Joshua Johnston
 *
 */
public class Star extends Pane{	
	
	private SpriteAnimation starMoving;
	private SpriteAnimation starExploding;
	
	private Image movingSpriteSheet;
	private Image explodingSpriteSheet;	
	
	private ImageView paint;
	
	private int orderNumber;
	
	/**
	 * A constructor that creates a new star with a number that represents its ordering.
	 * @param orderNumber : int
	 */
	public Star(int orderNumber){			
		this.movingSpriteSheet = new Image("starSpriteSheet1.png");
		this.explodingSpriteSheet = new Image("explodeSpriteSheet.png");
		this.orderNumber = orderNumber;		
		
		this.movingStar();		
		
	}
	
	/**
	 * This method draws a star.
	 */
	public void drawStar(){		
		
		paint = new ImageView(explodingSpriteSheet);
		paint.setViewport(new Rectangle2D(0, 0, 65, 65));   //First sprite on the sheet.
		
		this.getChildren().add(paint);		
	}
	
	/**
	 * This method plays the animation of the star dancing.
	 */
	public void movingStar(){
		this.getChildren().clear();
		
		paint = new ImageView(movingSpriteSheet);
		paint.setViewport(new Rectangle2D(0, 0, 65, 65));
		
		starMoving = new SpriteAnimation(2, 1, 2, paint, Duration.millis(1000));		
		
		starMoving.setCycleCount(Animation.INDEFINITE);
		starMoving.play();
		
		this.getChildren().add(paint);
	}
	
	/**
	 * This method plays the star exploding.
	 */
	public void explodingStar(){
		this.getChildren().clear();
		
		paint = new ImageView(explodingSpriteSheet);
		paint.setViewport(new Rectangle2D(0, 0, 65 ,65));
		
		starExploding = new SpriteAnimation(4, 4, 15, paint, Duration.millis(2500));
		starExploding.setDelay(Duration.seconds(1));
		starExploding.play();
		
		this.getChildren().add(paint);
		
		starExploding.setOnFinished(e -> {
			
			this.getChildren().clear();
			
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(-10);
			
			paint = new ImageView(new Image("rip.png"));
			vbox.getChildren().addAll(new Label(String.valueOf(orderNumber)), paint);
			
			this.getChildren().add(vbox);
		});
		
	}
	
	/**
	 * A getter method for starMoving.
	 * @return starMoving : SpriteAnimation
	 */
	public SpriteAnimation getStarMoving(){
		
		return starMoving;
	}
	
	/**
	 * A getter method for starExploding.
	 * @return starExploding : SpriteAnimation
	 */
	public SpriteAnimation getStarExploding(){
		
		return starExploding;
	}
	
	/**
	 * A getter method for orderNumber.
	 * @return orderNumber : int
	 */
	public int getOrderNumber(){
		return orderNumber;
	}		
}
	


