import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class is to create animation from a sprite sheet.
 * Each sprite on the sheet must be of the same height and width.
 * Date: 03/02/2019
 * @author Joshua Johnston
 *
 */
public class SpriteAnimation extends Transition {
	
	private int row;              //rows on the sheet
	private int col;              //columns on the sheet
	private int frameCount;       //how many sprites are on the sheet
	private int lastFrame;
	
	private Rectangle2D[] clips;	
	
	private ImageView imageView;
	
	
	/**
	 * A constructor that makes a new object based on the sprite sheet.
	 * @param row : int
	 * @param col : int
	 * @param frameCount : int
	 * @param imageView  : ImageView
	 * @param duration   : Duration
	 */
	public SpriteAnimation(int row, int col, int frameCount, ImageView imageView, Duration duration){
		this.row = row;
		this.col = col;
		this.frameCount = frameCount;		
		
		this.imageView = imageView;	
		
		this.buildClips();
		this.setInterpolator(Interpolator.LINEAR);
		this.setCycleDuration(duration);
	}
	
	/**
	 * This method creates the clips by extracting each sprite's coordinates.
	 */
	private void buildClips(){
		
		clips = new Rectangle2D[frameCount];
		
		double frameHeight = imageView.getImage().getHeight() / row;
		double frameWidth = imageView.getImage().getWidth() / col;
		
		int count = 0;		
		
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
					
				if(count < frameCount)
					clips[count++] = new Rectangle2D(j * frameWidth, i * frameHeight, frameWidth, frameHeight);
				
			}
		}		
	}
	
    /**
     * This method is called during each frame of the animation to update when needed.
     * The time runs from 0.0 to 1.0
     */
	@Override
	protected void interpolate(double currTime) {
		
		final int index = Math.min((int) Math.floor(currTime * frameCount), frameCount - 1);
		
		if(index != lastFrame){
			
			imageView.setViewport(clips[index]);     //shows the current sprite
			lastFrame = index;
		}
		
	}

}
