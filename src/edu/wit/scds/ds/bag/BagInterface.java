/* @formatter:off
 *
 * David M Rosenberg
 * Summer 2025
 *
 * COMP 2000 ~ Data Structures
 * Demonstration: unordered lists (bags)
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my 
 * sections of this course
 * 
 * Violation of these usage restrictions will be considered a violation of
 * Wentworth Institute of Technology's Academic Honesty Policy.  Unauthorized posting
 * or use of this code may also be considered copyright infringement and may subject
 * the poster and/or the owners/operators of said websites to legal and/or financial
 * penalties.  Students are permitted to store this code in a private repository
 * or other private cloud-based storage.
 *
 * Do not modify or remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.bag ;

/**
 * An interface that describes the operations of a bag of objects.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 *
 * @version 5.0
 *
 * @author David M Rosenberg
 *
 * @version 5.1 reformat per class standard
 * @version 6.0 2024-09-17 rename to UnorderedList
 * @version 7.0 2025-01-28 rename back to BagInterface
 *
 * @param <T>
 *     The class of item a Bag can hold.
 */
public interface BagInterface<T>
    {

    /**
     * Adds a new entry to this bag.
     *
     * @param newEntry
     *     The object to be added as a new entry.
     *
     * @return true if the addition is successful, or false if not.
     */
    public boolean add( T newEntry ) ;


    /**
     * Removes all entries from this bag.
     */
    public void clear() ;


    /**
     * Tests whether this bag contains a given entry.
     *
     * @param anEntry
     *     The entry to find.
     *
     * @return true if the bag contains the specified entry, or false if not.
     */
    public boolean contains( T anEntry ) ;


    /**
     * Gets the current number of entries in this bag.
     *
     * @return The integer number of entries currently in the bag.
     */
    public int getCurrentSize() ;


    /**
     * Counts the number of times a given entry appears in this bag.
     *
     * @param anEntry
     *     The entry to be counted.
     *
     * @return The number of times the specified entry appears in the bag.
     */
    public int getFrequencyOf( T anEntry ) ;


    /**
     * Sees whether this bag is empty.
     *
     * @return true if the bag is empty, or false if not.
     */
    public boolean isEmpty() ;


    /**
     * Removes one unspecified entry from this bag, if possible.
     *
     * @return Either the removed entry, if the removal was successful, or null.
     */
    public T remove() ;


    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     *
     * @param anEntry
     *     The entry to be removed.
     *
     * @return true if the removal was successful, or false if not.
     */
    public boolean remove( T anEntry ) ;


    /**
     * Retrieves all entries that are in this bag.
     *
     * @return A newly allocated array of all the entries in the bag. Note: If the unordered list is
     *     empty, the returned array is empty.
     */
    public T[] toArray() ;

    } // end interface BagInterface