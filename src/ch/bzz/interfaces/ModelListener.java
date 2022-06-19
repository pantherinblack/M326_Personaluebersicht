package ch.bzz.interfaces;

/**
 * interface to collect all Models to update them
 *
 * @author Kevin
 * @version 1.0
 * @since 18.05.2022
 */
public interface ModelListener {

    /**
     * updates elements
     *
     * @param source the <code>ListModel</code> that changed, typically "this"
     * @param index0 one end of the new interval
     * @param index1 the other end of the new interval
     */
    void fireContentsChanged(Object source, int index0, int index1);
}
