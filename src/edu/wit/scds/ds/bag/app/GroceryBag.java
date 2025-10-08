/* @formatter:off
 *
 * © David M Rosenberg
 * Summer 2025
 *
 * COMP 2000 ~ Data Structures
 * Demonstration: Bag App ~ Grocery Bagger
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


package edu.wit.scds.ds.bag.app ;


import static edu.wit.scds.ds.bag.app.GroceryBagLimits.* ;
import edu.wit.scds.ds.bag.BagInterface ;
import edu.wit.scds.ds.bag.adt.ArrayBag ;

import java.util.Arrays ;
import java.util.Objects ;

/**
 * A collection of {@code GroceryItem}s using a bag as the backing store.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2017-05-23 Initial implementation
 * @version 1.1 2020-02-05
 *     <ul>
 *     <li>clean up formatting
 *     <li>complete documentation
 *     </ul>
 * @version 1.2 2022-10-12 miscellaneous cleanup for this semester
 * @version 2.0 2024-02-10
 *     <ul>
 *     <li>reduce to a single bag implementation (ArrayBag)
 *     <li>update to match the assignment for this semester
 *     <li>significant cleanup
 *     </ul>
 * @version 3.0 2024-10-06
 *     <ul>
 *     <li>switch from Bag ADT to Unordered List
 *     <li>move compatibility checking to the {@code GroceryBaggerCompatibilityChecker} class
 *     </ul>
 * @version 3.1 2024-10-11 add fields to track student compatibility heuristics
 * @version 4.0 2025-02-08
 *     <ul>
 *     <li>switch back from Unordered List to Bag ADT
 *     <li>remove capacity limit: number of items
 *     <li>move {@code areCompatible()} here
 *     <li>remove {@code itemCount} in favor of the bag's {@code getCurrentSize()}
 *     <li>remove {@code remainingItemsAvailable} deprecate {@code getRemainingItemsAvailable()}
 *     <li>update {@code areCompatible()} rules to track my sample compatibility heuristics
 *     <li>strip out pieces for students to implement
 *     </ul>
 * 
 * @author Zach Beaupre                                   // DONE
 * 
 * @version 4.1 2025-06-05 complete the implementation  // DONE  
 * 
 * @since 1.0
 */
public final class GroceryBag implements Comparable<GroceryBag>
    {
    
    // NOTE do not add, remove, or modify the definition of the state variables
    

    /*
     * symbolic constants
     */

    // none


    /*
     * static variables
     */

    private static int bagCount = 0 ;     // used to assign each bag a unique id


    /*
     * data fields
     */

    /** the GroceryBag's unique id for display via toString() and testing */
    public final int bagId ;


    // capacity fields - accessible via getters

    private int remainingWeightAvailable ;
    private int remainingSpaceAvailable ;


    // compatibility fields - accessible via getters

    private int breakableItemCount ;
    private int perishableItemCount ;
    private int rigidItemCount ;
    
    // sizes
    private int smallItemCount ;
    private int largeItemCount ;
    
    // weights
    private int lightItemCount ;
    private int heavyItemCount ;
    
    // firmnesses
    private int softItemCount ;
    private int hardItemCount ;


    // backing store

    private final BagInterface<GroceryItem> bag ;

    private boolean integrityOk = false ;


    /**
     * initialize a grocery bag to a valid empty state with specified limits
     */
    public GroceryBag()
        {
        
        // NOTE do not modify this method
        
        this.integrityOk = false ;  // not usable yet

        this.bagId = ++bagCount ;   // set unique bag id

        // validate configuration parameters
        validateLimits() ;

        initializeCounters() ;

        this.bag = new ArrayBag<>() ;
        
        this.integrityOk = true ;   // now we're usable

        }   // end no-arg constructor


    /*
     * API methods
     */


    /**
     * add a grocery item to the grocery bag assuming:
     * <ol>
     * <li>there is adequate capacity to hold the grocery item
     * <li>the grocery item is compatible with any items already in the bag
     * </ol>
     *
     * @param newItem
     *     the grocery item to add
     *
     * @return {@code true} if the grocery item was added, {@code false} otherwise
     */
    public boolean add( final GroceryItem newItem )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        if ( !canAddItem( newItem ) )
            {
            // the bag is full, or has inadequate capacity for or is incompatible with the new item
            return false ;
            }

        // we can take this grocery item

        if ( !this.bag.add( newItem ) )
            {
            throw new NotBaggableException( String.format( "failed to save the grocery item '%s' in bag %,d",
                                            newItem,
                                            this.bagId ) ) ;
            }

        accountForAddedItem( newItem ) ;

        return true ;   // successfully added the grocery item

        }   // end add()


    // @formatter:off
    // DONE in the Javadoc comment, replace "rule 1", etc. with your rules (keep them brief)
    //          (e.g., "don't bag perishables with non-perishables")
    //
    // NOTE edit the comment block carefully!
    //          <ul>..</ul> is the HTML for an unordered (bulleted) list
    //          <li> is the tag for a list element
    //
    // 
    // Remember: these checks are bi-directional:
    //     - "attribute A can't go with attribute B" means:
    //     - an item with attribute A can't go in a bag which contains any items with attribute B
    //   and
    //     - an item with attribute B can't go in a bag which contains any items with attribute A
    //
    // @formatter:on
    /**
     * enable application to determine if this grocery bag can hold the specified grocery item
     * <p>
     * our heuristics are:
     * <ul>
     * <li>rule 1: Breakable items can't go with heavy items.
     * <li>rule 2: Soft items can't go with hard items.
     * <li>rule 3: Breakable items can't go with hard items.
     * <li>rule 4: Perishables can't go with large items.
     * </ul>
     * <p>
     * note: these checks are bi-directional
     *
     * @param candidateItem
     *     the grocery item to test for compatibility with the given grocery bag
     *
     * @return {@code true} if the grocery bag and grocery item are compatible with each other,
     *     {@code false} otherwise
     */
    public boolean areCompatible( final GroceryItem candidateItem )
        {

        checkIntegrity() ;
//i think its done idk
        // there can't be any compatibility issues with an empty grocery bag
        if ( isEmpty() ){
            return true ;
            }

        // there's at least one item in the bag so we have potential incompatibility

        // filter out incompatible items
        
        // rule 1: Breakable items can't go with heavy items.
        
        // DONE implement this
        if(candidateItem.isBreakable && hasHeavyItems() || candidateItem.isHeavy && hasBreakableItems()) { return false; }
        
        // rule 2: Soft items can't go with hard items.
      
        // DONE implement this
        if((candidateItem.isSoft && hasHardItems()) || (candidateItem.isHard && hasSoftItems()) ) { return false; }
        
        // rule 3: Breakable items cant go with hard items.
        // DONE implement this
        if(candidateItem.isBreakable && hasHardItems() || candidateItem.isHard && hasBreakableItems()) { return false; }
        
               // rule 4: Perishables cant go with large items.
        if(candidateItem.isPerishable && hasLargeItems() || candidateItem.isLarge && hasPerishableItems()) { return false; }
        
        
        // DONE implement this
        //hidden rule dont use
        // rule 5: light items cant go with heavy items
        // NOTE do not modify the rest of this method

        // nothing else to check - passed all compatibility filters
            
        return true ;

        }   // end areCompatible()


    /**
     * enable application to determine if a grocery item can be added to this grocery bag
     *
     * @param candidateItem
     *     the grocery item to check
     *
     * @return {@code true} if we can accept the item, {@code false} otherwise
     */
    public boolean canAddItem( final GroceryItem candidateItem )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        // hasCapacity() implicitly tests for full
        return hasCapacity( candidateItem ) && areCompatible( candidateItem ) ;

        }   // end canAddItem()


    @Override
    public int compareTo( final GroceryBag otherBag )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        // enables grocery bags to be sorted according to their ids

        if ( otherBag == null )
            {
            throw new NullPointerException() ;
            }

        return ( this.bagId - otherBag.bagId ) ;

        }   // end compareTo()


    /**
     * determine if we're holding at least one matching grocery item
     *
     * @param itemToFind
     *     the item to check for
     *
     * @return {@code true} if there's at least one matching item, {@code false} otherwise
     */
    public boolean contains( final GroceryItem itemToFind )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.bag.contains( itemToFind ) ;

        }   // end contains()


    @Override
    public boolean equals( final Object otherObject )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        // an instance is always equal to itself 
        if ( this == otherObject )
            {
            return true ;
            }

        // if given another GroceryBag, compare them
        if ( otherObject instanceof final GroceryBag otherGroceryBag )
            {
            // compare the grocery bags' ids - must be consistent with compareTo()
            return this.bagId == otherGroceryBag.bagId ;
            }

        // otherBag isn't a GroceryBag or is null
        return false ;

        }   // end equals()


    /**
     * determine the number of grocery items currently in the bag
     *
     * @return the number of grocery items
     */
    public int getItemCount()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.bag.getCurrentSize() ;

        }   // end getItemCount()


    /**
     * determine the number of times a grocery item appears in the bag
     *
     * @param itemToFind
     *     the item to count
     *
     * @return the number of times it occurs in the bag
     */
    public int getNumberOf( final GroceryItem itemToFind )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.bag.getFrequencyOf( itemToFind ) ;

        }   // end getNumberOf()


    /**
     * determine the remaining number of grocery items this grocery bag can still accept
     * 
     * @return the quantity of additional grocery items this grocery bag can accept
     *
     * @since 3.0
     * 
     * @deprecated this method is no longer meaningful since the quantity limit has been removed
     */
    @SuppressWarnings( "static-method" )
    @Deprecated( since = "4.0", forRemoval = true )
    public int getRemainingItemsAvailable()
        {
        
        // NOTE do not modify this method
        
        throw new UnsupportedOperationException() ;

        }   // end getRemainingItemsAvailable()


    /**
     * determine the remaining amount of space available in this grocery bag
     * 
     * @return the amount of space in 'units'
     *
     * @since 3.0
     */
    public int getRemainingSpaceAvailable()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.remainingSpaceAvailable ;
        
        }   // end getRemainingSpaceAvailable()
    

    /**
     * determine the remaining amount of weight available in this grocery bag
     * 
     * @return the amount of weight in 'units'
     *
     * @since 3.0
     */
    public int getRemainingWeightAvailable()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.remainingWeightAvailable ;
        
        }   // end getRemainingWeightAvailable()


    /**
     * determine if there are any breakable items in the bag
     *
     * @return {@code true} if there are any breakable items in the bag, {@code false} otherwise
     */
    public boolean hasBreakableItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.breakableItemCount > 0 ;

        }   // end hasBreakableItems()


    /**
     * determine if there are any hard (or harder) items in the bag
     *
     * @return {@code true} if there are any hard items in the bag, {@code false} otherwise
     */
    public boolean hasHardItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.hardItemCount > 0 ;

        }   // end hasHardItems()


    @Override
    public int hashCode()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        // enable grocery bags to be keys for hash tables, etc.
        
        return Objects.hashCode( this.bagId ) ;

        }   // end hashCode()


    /**
     * determine if there are any heavy (or heavier) items in the bag
     *
     * @return {@code true} if there are any heavy items in the bag, {@code false} otherwise
     */
    public boolean hasHeavyItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.heavyItemCount > 0 ;

        }   // end hasHeavyItems()


    /**
     * determine if there are any large (or bigger) items in the bag
     *
     * @return {@code true} if there are any large items in the bag, {@code false} otherwise
     */
    public boolean hasLargeItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.largeItemCount > 0 ;

        }   // end hasLargeItems()


    /**
     * determine if there are any light (or lighter) items in the bag
     *
     * @return {@code true} if there are any light items in the bag, {@code false} otherwise
     */
    public boolean hasLightItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.lightItemCount > 0 ;

        }   // end hasLightItems()


    /**
     * determine if there are any perishable items in the bag
     *
     * @return {@code true} if there are any perishable items in the bag, {@code false} otherwise
     */
    public boolean hasPerishableItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.perishableItemCount > 0 ;

        }   // end hasPerishableItems()


    /**
     * determine if there are any rigid items in the bag
     *
     * @return {@code true} if there are any rigid items in the bag, {@code false} otherwise
     */
    public boolean hasRigidItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.rigidItemCount > 0 ;

        }   // end hasRigidItems()


    /**
     * determine if there are any small (or smaller) items in the bag
     *
     * @return {@code true} if there are any small items in the bag, {@code false} otherwise
     */
    public boolean hasSmallItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return this.smallItemCount > 0 ;

        }   // end hasSmallItems()


    /**
     * determine if there are any soft (or softer) items in the bag
     *
     * @return {@code true} if there are any soft items in the bag, {@code false} otherwise
     */
    public boolean hasSoftItems()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.softItemCount > 0 ;

        }   // end hasSoftItems()


    /**
     * determine if the grocery bag is empty
     *
     * @return {@code true} if there are no grocery items in the bag, {@code false} otherwise
     */
    public boolean isEmpty()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;
        
        return this.bag.isEmpty() ;

        }   // end isEmpty()


    /**
     * determine if the grocery bag has reached one or more capacity limits
     *
     * @return {@code true} if the bag is full (can't accept any more grocery items), {@code false}
     *     otherwise
     */
    public boolean isFull()
        {

        checkIntegrity() ;
        
        // DONE implement this - do all the work in a single return statement
        
        return (this.getRemainingSpaceAvailable() == 0 || this.getRemainingWeightAvailable() == 0);  // STUB value - delete this comment

        }   // end isFull()


    /**
     * remove an unspecified grocery item from the grocery bag
     *
     * @return a reference to the removed item if successful, {@code null} otherwise
     */
    public GroceryItem remove()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        final GroceryItem removedItem = this.bag.remove() ;

        if ( removedItem != null )  // we successfully removed an item from the bag
                                    // so update the grocery bag's state
            {
            accountForRemovedItem( removedItem ) ;
            }

        return removedItem ;

        }   // end remove()


    /**
     * remove the specified item from the grocery bag
     *
     * @param itemToRemove
     *     item the item to remove
     *
     * @return {@code true} if the item was in the grocery bag, {@code false} otherwise
     */
    public boolean remove( final GroceryItem itemToRemove )
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        // if we can remove the item, update the grocery bag's status
        final boolean wasInBag = this.bag.remove( itemToRemove ) ;

        if ( wasInBag ) // the item was in the bag so adjust all grocery bag metrics
            {
            accountForRemovedItem( itemToRemove ) ;
            }   // end if

        return wasInBag ;

        }   // end remove()


    /**
     * retrieve all grocery items from the grocery bag<br>
     * the resulting grocery bag is empty
     *
     * @return references to all the grocery items in the bag
     */
    public GroceryItem[] removeAll()
        {
        
        // NOTE do not modify this method
        
        
        checkIntegrity() ;

        final GroceryItem[] allItems = toArray() ;  // we'll return an array of the items

        // reset all grocery bag state
        this.bag.clear() ;   // remove everything the bag

        // reset state to empty
        initializeCounters() ;

        return allItems ;

        }   // end removeAll()


    /**
     * return an inventory of grocery items to the caller<br>
     * the grocery bag's contents are unchanged
     *
     * @return the grocery items currently in the grocery bag
     */
    public GroceryItem[] toArray()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        // 2-step process avoids a ClassCastException from Object[] to GroceryItem[]
        final GroceryItem[] allItems = new GroceryItem[ this.bag.getCurrentSize() ] ;

        System.arraycopy( this.bag.toArray(),
                          0,
                          allItems,
                          0,
                          this.bag.getCurrentSize() ) ;

        return allItems ;

        }   // end toArray()


    @Override
    public String toString()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        final StringBuilder bagContents = new StringBuilder( toStringHeader() +
                                                             "[" ) ;

        final GroceryItem[] allItems = toArray() ;
        GroceryItem previousItem = null ;

        // put the grocery items in alphabetic order for display - enables folding if duplicates
        Arrays.sort( allItems ) ;

        for ( final GroceryItem item : allItems )
            {

            if ( ( previousItem != null ) && ( item.equals( previousItem ) ) )
                {
                // skip first item because there's no previous item to check against
                // and skip items already displayed
                }
            else
                {
                bagContents.append( ( previousItem == null
                                         ? ""
                                         : ", " ) +
                                    "(" + this.bag.getFrequencyOf( item ) + ") " + item ) ;
                }   // end if/else

            previousItem = item ;
            }   // end for

        bagContents.append( "]" ) ;

        return bagContents.toString() ;

        }   // end toString()


    /**
     * format the header/summary for the grocery bag
     *
     * @return the formatted info about this bag
     */
    public String toStringHeader()
        {
        
        // NOTE do not modify this method
        
        checkIntegrity() ;

        return String.format( """
                              Bag %,d: \
                              %,d of %,d units size; \
                              %,d of %,d units weight; \
                              %s breakables; \
                              %s perishables; \
                              %s rigid items; \
                              %s light items; \
                              %s heavy items; \
                              %s small items; \
                              %s large items; \
                              %s soft items; \
                              %s hard items; \
                              %s empty; \
                              %s full:""",

                              this.bagId,
                              
                              GROCERY_BAG_MAX_VOLUME - this.remainingSpaceAvailable,
                              GROCERY_BAG_MAX_VOLUME,
                              
                              GROCERY_BAG_MAX_WEIGHT - this.remainingWeightAvailable,
                              GROCERY_BAG_MAX_WEIGHT,
                              
                              ( hasBreakableItems()
                                  ? "has"
                                  : "no" ),
                              ( hasPerishableItems()
                                  ? "has"
                                  : "no" ),
                              ( hasRigidItems()
                                  ? String.format( "has %,d",
                                                   this.rigidItemCount )
                                  : "no" ),

                              ( hasLightItems()
                                  ? String.format( "has %,d",
                                                   this.lightItemCount )
                                  : "no" ),
                              ( hasHeavyItems()
                                  ? String.format( "has %,d",
                                                   this.heavyItemCount )
                                  : "no" ),

                              ( hasSmallItems()
                                  ? String.format( "has %,d",
                                                   this.smallItemCount )
                                  : "no" ),
                              ( hasLargeItems()
                                  ? String.format( "has %,d",
                                                   this.largeItemCount )
                                  : "no" ),


                              ( hasSoftItems()
                                  ? String.format( "has %,d",
                                                   this.softItemCount )
                                  : "no" ),
                              ( hasHardItems()
                                  ? String.format( "has %,d",
                                                   this.hardItemCount )
                                  : "no" ),
                              ( isEmpty()
                                  ? "is"
                                  : "not" ),
                              ( isFull()
                                  ? "is"
                                  : "not" ) ) ;

        }   // end toStringHeader()


    /*
     * private utility methods
     */


    /**
     * update the grocery bag state to reflect the addition of a grocery item
     *
     * @param newItem
     *     the item being added
     */
    private void accountForAddedItem( final GroceryItem newItem )
        {
        
        // NOTE you must use either increment/decrement or composite operators as appropriate

        
        // capacity fields

        // DONE adjust remaining space and weight counters
        remainingWeightAvailable -= newItem.weight.weightValue;
        remainingSpaceAvailable -= newItem.size.sizeValue;

        // compatibility fields

        // DONE adjust breakability, perishability, and rigidity counters

        breakableItemCount += (newItem.isBreakable) ? 1 : 0;
        perishableItemCount += (newItem.isPerishable) ? 1 : 0;
        rigidItemCount += (newItem.isRigid) ? 1 : 0;
        
        // sizes

        // DONE adjust small and large counters

        smallItemCount += (newItem.isSmall) ? 1 : 0; 
        largeItemCount += (newItem.isLarge) ? 1 : 0;
        
        // weights

        // DONE adjust light and heavy counters
        lightItemCount += (newItem.isLight) ? 1 : 0;
        heavyItemCount += (newItem.isHeavy) ? 1 : 0;
        
        // firmnesses
        
        // DONE adjust soft and hard counters
        softItemCount += (newItem.isSoft) ? 1 : 0;
        hardItemCount += (newItem.isHard) ? 1 : 0;
        }   // end accountForAddedItem()


    /**
     * update the grocery bag state to reflect the removal of a grocery item
     *
     * @param removedItem
     *     the item being removed
     */
    private void accountForRemovedItem( final GroceryItem removedItem )
        {
        
        // NOTE you must use either increment/decrement or composite operators as appropriate
        
        // DONE adjust remaining space and weight counters
        remainingWeightAvailable += removedItem.weight.weightValue;
        remainingSpaceAvailable += removedItem.size.sizeValue;

        // compatibility fields

        // DONE adjust breakability, perishability, and rigidity counters

        breakableItemCount -= (removedItem.isBreakable) ? 1 : 0;
        perishableItemCount -= (removedItem.isPerishable) ? 1 : 0;
        rigidItemCount -= (removedItem.isRigid) ? 1 : 0;
        
        // sizes

        // DONE adjust small and large counters

        smallItemCount -= (removedItem.isSmall) ? 1 : 0; 
        largeItemCount -= (removedItem.isLarge) ? 1 : 0;
        // weights

        // DONE adjust light and heavy counters
        lightItemCount -= (removedItem.isLight) ? 1 : 0;
        heavyItemCount -= (removedItem.isHeavy) ? 1 : 0;
        
        // firmnesses
        
        // DONE adjust soft and hard counters
        softItemCount -= (removedItem.isSoft) ? 1 : 0;
        hardItemCount -= (removedItem.isHard) ? 1 : 0;
        }   // end accountForRemovedItem()


    /**
     * prevent continued execution unless the grocery bag's state is valid
     *
     * @throws SecurityException
     *     indicates the state is invalid
     */
    private void checkIntegrity() throws SecurityException
        {
        
        // NOTE do not modify this method
        
        if ( !this.integrityOk )
            {
            throw new SecurityException( "GroceryBag state is invalid" ) ;
            }

        }   // end checkIntegrity()


    /**
     * determine if there is adequate capacity in the bag for the specified item
     *
     * @param candidateItem
     *     the grocery item to test for fit
     *
     * @return {@code true} if there's room for this grocery item, {@code false} otherwise
     */
    private boolean hasCapacity( final GroceryItem candidateItem ) {
    //DONE
        return (remainingSpaceAvailable - candidateItem.size.sizeValue) >= 0 && (remainingWeightAvailable - candidateItem.weight.weightValue) >= 0;
    }   // end hasCapacity()


    /**
     * set all counters and limits to reflect an empty grocery bag
     * <p>
     * note: this doesn't affect the contents of the {@code groceryBag}
     */
    private void initializeCounters() {
        
        // NOTE do not modify this method
        
        
        // capacity fields

        // per specifications
        this.remainingSpaceAvailable = GROCERY_BAG_MAX_VOLUME ;
        this.remainingWeightAvailable = GROCERY_BAG_MAX_WEIGHT ;

        // compatibility fields
        this.breakableItemCount = 0 ;
        this.perishableItemCount = 0 ;
        this.rigidItemCount = 0 ;
        
        // sizes
        this.smallItemCount = 0 ;
        this.largeItemCount = 0 ;
        
        // weights
        this.lightItemCount = 0 ;
        this.heavyItemCount = 0 ;
        
        // firmnesses
        this.softItemCount = 0 ;
        this.hardItemCount = 0 ;

        }   // end initializeCounters()


    /**
     * ensure the configuration file specifies usable limits
     *
     * @throws InvalidSpecificationException
     *     if any limit is too small
     */
    private static void validateLimits() throws InvalidSpecificationException {
        
        // NOTE do not modify this method
        
        // NOTE limit variable is needed to test the constants from GroceryBagLimits
        // - avoids an 'unused' compiler warning
        int limit ;

        limit = GROCERY_BAG_MAX_VOLUME ;
        if ( limit <= 0 )
            {
            throw new InvalidSpecificationException( String.format( "maximum total size/volume: %,d",
                                                                    GROCERY_BAG_MAX_VOLUME ) ) ;
            }

        limit = GROCERY_BAG_MAX_WEIGHT ;
        if ( limit <= 0 )
            {
            throw new InvalidSpecificationException( String.format( "maximum total weight: %,d",
                                                                    GROCERY_BAG_MAX_WEIGHT ) ) ;
            }

        }   // end validateLimits()


    /*
     * for testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        
        // NOTE use this to test your implementation
        
        final GroceryBag testBag = new GroceryBag() ;
        System.out.printf( "testBag contents: %s%n",
                           Arrays.toString( testBag.toArray() ) ) ;
        System.out.printf( "testBag: %s%n", testBag ) ;
        System.out.printf( "testBag.equals(testBag): %b%n", testBag.equals( testBag ) ) ;
        System.out.printf( "testBag.equals(null): %b%n", testBag.equals( null ) ) ;

        }	// end main()

    }   // end class GroceryBag