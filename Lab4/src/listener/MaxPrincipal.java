package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements HttpSessionAttributeListener {
	
	private static double maxPrincipal;
	
    /**
     * Default constructor. 
     */
    public MaxPrincipal() {
    		maxPrincipal = 0.0;
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  {
    		updateMaxPrincipal(arg0);
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
    	updateMaxPrincipal(arg0);
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 	
    		updateMaxPrincipal(arg0);
    }
    /**
     * updates the maximum principal if it is larger than the current maximum
     */
    public void updateMaxPrincipal(HttpSessionBindingEvent arg0) {
    		if (arg0.getName().equals("principal")) { 
    		
			double newValue = Double.parseDouble(arg0.getValue().toString());
			if (newValue > maxPrincipal) {
				maxPrincipal = newValue;
			}
		}
    }
    
    public static double getMax() {
    		return maxPrincipal;
    }
	
}