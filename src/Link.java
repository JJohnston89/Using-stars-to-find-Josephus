/**
 * This class holds the data that is inserted into a linked list.
 * Date: 03/01/2019
 * @author Joshua Johnston
 *
 */

public class Link {
	
	private int data;
	private Link next;
	
	/**
	 * A constructor to create a new link with given key.
	 * @param data : int
	 */
	public Link(int data){
		this.data = data;
	}
	
	/**
	 * A setter method for next.
	 * @param next : Link
	 */
	public void setNext(Link next){
		this.next = next;
	}
	
	/**
	 * A getter method for next.
	 * @return next : Link
	 */	
	public Link getNext(){
		return next;
	}
	
	/**
	 * A getter method for data : int
	 * @return data : int
	 */
	public int getData(){
		return data;
	}
	
	/**
	 * A setter method for data.
	 * @param data : int
	 */
	public void setData(int data){
		this.data = data;
	}

}
