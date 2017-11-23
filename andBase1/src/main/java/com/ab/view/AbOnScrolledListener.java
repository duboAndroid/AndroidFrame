/*
 * 
 */
package com.ab.view;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving abOnScrolled events.
 * The class that is interested in processing a abOnScrolled
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAbOnScrolledListener<code> method. When
 * the abOnScrolled event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AbOnScrolledEvent
 */
public interface AbOnScrolledListener {
    
    /**
     * On scroll.
     *
     * @param position the position
     */
    public void onScroll(int position); 

}
