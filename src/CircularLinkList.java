
/**
 * This class is an implement of a circular linked list. 
 * A datafield count is used to keep track of how many links are in the list, and this is used to "break out" of the circle when needed.
 * Date: 03/01/2019
 * @author Joshua Johnston
 *
 */
public class CircularLinkList {
	
	private Link current;
	int count;
	
	/**
	 * A constructor that sets up the new list to null, and the count of links to 0;
	 */
	public CircularLinkList(){
		current = null;
		count = 0;
	}
	
	/**
	 * This method checks to see if the list is empty or not.
	 * @return (true or false) : boolean
	 */
	public boolean isEmpty(){
		
		return (count == 0);
	}
	
	/**
	 * This method adds a new link into the circular linked list at the front of the current link, 
	 * and then current is set to the new link.
	 * @param key : int
	 */
	public void add(int key){
		
		if(isEmpty()){
			
			current = new Link(key);
			current.setNext(current);    //Only one link is in the list, so the link points to itself.
			count++;
		}
		else{
			
			Link newLink = new Link(key);
			Link temp = current.getNext();
			current.setNext(newLink);
			newLink.setNext(temp);
			current = newLink;
			count++;
			
		}
	}
	
	/**
	 * This method deletes the current link, and sets current to the next link.
	 * @param pre : Link
	 * @return the deleted link data : int or -1 for error
	 */
	public int delete(Link pre){	
		
		if(pre != null){
			  Link temp = current;
			 //System.out.print(current.getData() + " ");
			 pre.setNext(current.getNext());
			 current = pre.getNext();
			 count--;
			 
			 return temp.getData();
			
		}
		return -1;
	}
	
	/**
	 * This method finds the link with a given key.
	 * If the link is found then the current link is set to this link.
	 * 
	 * @param key : int
	 */
	public void find(int key){
		
		if(count == 0){           //The list is empty.
			return;
		}
		else{			
			
			for(int i = 0; i < count; i++){				 
				
				if(current.getData() == key){
					break;
					
				}
				else{					
					current = current.getNext();     //If the link wasn't found then current will still be current
				}                                    //since the last link is pointing to current.
			}
			
		}
	}
	
	/**
	 * This method returns link from a given count off from the previous link.
	 * @param key : int
	 * @return link
	 */
	public Link step(int key){
		
		if(count <= 1){      
			return null;
		}
		else{			
			Link pre = null;
			
			for(int i = 0; i < key; i++){
			    pre = current;				
				current = current.getNext();
			}
			return pre;
		}
	}
	
	/**
	 * This method is a getter method for count.
	 * @return count : int
	 */
	public int getCount(){
		return count;
	}
	
	/**
	 * This method is a getter method for current.
	 * @return current : Link
	 */
	public Link getCurrent(){
		return current;
	}

}
