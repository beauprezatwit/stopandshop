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


package edu.wit.scds.ds.bag.app;

import java.io.BufferedReader ;
import java.io.FileNotFoundException ;
import java.io.FileReader ;

import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.Collections ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Scanner ;

/**
 * Driver for the Grocery Bagger application.
 * 
 * @author David M Rosenberg
 * 
 * @version 1.0 2017-05-23 Initial implementation
 * @version 1.1 2020-02-09
 *     <ul>
 *     <li>clean up formatting
 *     <li>complete documentation
 *     </ul>
 * @version 2.0 2024-02-10 significant restructuring and cleanup
 * @version 3.0 2025-02-08
 *     <ul>
 *     <li>significant restructuring and cleanup for this semester
 *     <li>renamed class from {@code GroceryBagger} to {@code BagIt}
 *     </ul>
 * 
 * @author Samaksh Ghai                                    // TODO
 * 
 * @version 3.1 2025-06-05 complete the implementation  // TODO
 * 
 */
public final class BagIt
    {
    
    /*
     * constructors
     */
    private BagIt()
        {
        // prevent instantiation
        }

    /**
     * bag all the grocery items on the conveyor belt and display the results
     * 
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        
        // NOTE do not modify this method
        

        // our groceries will be taken from the conveyor belt and placed in grocery bags in the
        // bagging area which are then moved to the shopping cart
        
        List<GroceryBag> shoppingCart = null ;

        // Load the bag(s) from the 'conveyor belt' (list of groceries)
        System.out.printf( "Bagging items:%n%n" ) ;
        
        try ( Scanner conveyorBelt = new Scanner(
                                         new BufferedReader(
                                             new FileReader( "./data/conveyor-belt.data" ) ) ) ; )
            {
            shoppingCart = bagItems( conveyorBelt ) ;
            }
        catch ( final FileNotFoundException e )
            {
            System.out.println( "Can't load groceries: " + e.getMessage() ) ;
            
            // nothing to do but exit
            return ;
            }
        catch ( BaggingException |
                NotBaggableException |
                InvalidSpecificationException e )
            {
            // should never occur/reach here - indicates a bug
            System.out.println( "Apparently there's a bug here: " +
                                e.getMessage() ) ;

            // re-throw the exception
            throw e ;
            }   // end try/catch

        // assertion: all grocery items have been bagged and all bags are in the shopping cart
        System.out.printf( "%n----------%n%n" ) ;

        // Display the contents of each grocery bag in the shopping cart
        displayAllBags( shoppingCart ) ;


        System.out.printf( "%n----------%n" ) ;

        System.out.printf( "%nDone.%n" ) ;
            
        }   // end main()
    
    
    /**
     * bag all the items on the conveyor belt
     * 
     * @param conveyorBelt
     *     the source of grocery items
     * 
     * @return the shopping cart containing the bagged grocery items
     */
    private static List<GroceryBag> bagItems( final Scanner conveyorBelt ) 
        {

        final List<GroceryBag> baggingArea = new ArrayList<>() ;    // bags being filled
        final List<GroceryBag> shoppingCart = new ArrayList<>() ;   // bags to return to the caller

        int itemCount = 0 ;

        // skip header line
        conveyorBelt.nextLine() ;
        
        GroceryItem item ;   // the grocery item object

        while ( conveyorBelt.hasNextLine() )
            {
            GroceryBag baggedIn = null ;    // reference to the GroceryBag the item was placed in
                                            // or null indicating not yet bagged

            // get the next item description and convert it to an object
            item = new GroceryItem( conveyorBelt.nextLine() ) ;
            
            itemCount++ ;

            // find a bag to put it in and do so

            
            // TODO implement this - set baggedIn to reference the bag which accepted the item
            // TODO baggingArea is iterable so use an enhanced for/foreach loop
            for(GroceryBag bag : baggingArea) {
            	if (bag.add(item)) {
            		baggedIn = bag;
            		break;
            	}
            }
            
            if(baggedIn == null) {
            	GroceryBag newBag = new GroceryBag();
            	baggingArea.add(newBag);
            	newBag.add(item);
            	baggedIn = newBag;
            }
            

            
            if ( baggedIn == null ) {
                // couldn't add the item to an already open bag so add it to a new one
                final GroceryBag newBag = new GroceryBag() ;
                System.out.printf( "Opened bag %,d%n", newBag.bagId ) ;

                
             // TODO implement this 
             // TODO if the baggingArea doesn't accept the bag, throw a BaggingException 
             // TODO if the newBag doesn't accept the item, throw a BaggingException
               
            }

            // assertion: the grocery item was bagged in the grocery bag referenced by baggedIn
            
            // TODO once you are setting baggedIn, delete the conditional test but not the body
            // - this condition will always be false until you implement the TODOs above
            // - then it will always be true - ignore the warnings regarding null comparisons
            if ( baggedIn != null )
                {   // TODO delete up to and including this line
                
            System.out.printf( "  Added %s to bag %,d%n",
                               item.name,
                               baggedIn.bagId ) ;

            
            // if the bag is now full, move it from the bagging area to the shopping cart
            if ( baggedIn.isFull() )
                {
                System.out.printf( "Bag %,d is full, moving it to the shopping cart%n",
                                   baggedIn.bagId ) ;

                // move the bag from the bagging area to the shopping cart
                // no point continuing to check it

                // note that we're emulating the real world process but it would be safer to
                // perform the add() then the remove() so we don't inadvertently lose the bag
                baggingArea.remove( baggedIn ) ;

                shoppingCart.add( baggedIn ) ;
                }
                
                // TODO when you delete the if statement above, delete the next line too
                }   // end if

            }   // end while()
        
        // assertion: the conveyor belt is empty and all grocery items are in grocery bags
        
        System.out.printf( "%nBagged %,d grocery items%n%n", itemCount ) ;

        // move the rest of the grocery bags from the bagging area into the shopping cart
        final Iterator<GroceryBag> bagIterator = baggingArea.iterator() ;

        while ( bagIterator.hasNext() )
            {
            final GroceryBag aBag = bagIterator.next() ;
            
            System.out.printf( "moving bag %,d from the bagging area to the shopping cart%n",
                               aBag.bagId ) ;
            
            bagIterator.remove() ;

            shoppingCart.add( aBag ) ;
            }
        
        // assertion: the conveyor belt and bagging area are empty and all items have been bagged


        return shoppingCart ;

        }   // end bagItems()
    
    
    /**
     * display the contents of a grocery bag
     * 
     * @param aGroceryBag
     *     the grocery bag to display
     */
    private static void displayABag( final GroceryBag aGroceryBag )
        {
        
        // NOTE do not modify this method
        
        
        final List<String> bagDescription = new ArrayList<>() ;
        
        if ( aGroceryBag.hasBreakableItems() )
            {
            bagDescription.add( "breakables" ) ;
            }
        
        if ( aGroceryBag.hasHeavyItems() )
            {
            bagDescription.add( "heavy items" ) ;
            }
        
        if ( aGroceryBag.hasLargeItems() )
            {
            bagDescription.add( "large items" ) ;
            }
        
        if ( aGroceryBag.hasPerishableItems() )
            {
            bagDescription.add( "perishables" ) ;
            }
        
        if ( aGroceryBag.hasRigidItems() )
            {
            bagDescription.add( "rigid items" ) ;
            }
        
        if ( aGroceryBag.hasSoftItems() )
            {
            bagDescription.add( "soft items" ) ;
            }
        
        if ( aGroceryBag.isFull() )
            {
            bagDescription.add( "is full" ) ;
            }
        else
            {
            bagDescription.add( "not full" ) ;
            }
        
        System.out.printf( "Bag %,d contains %,d item%s (%s):%n",
                           aGroceryBag.bagId,
                           aGroceryBag.getItemCount(),
                           ( aGroceryBag.getItemCount() == 1
                               ? ""
                               : "s" ),
                           String.join( ", ", bagDescription ) ) ;

        // NOTE this is the only permissible use of an array
        final GroceryItem[] baggedItems = aGroceryBag.toArray() ;
        GroceryItem previousItem = null ;  // initially no previous item - used to skip duplicates

        Arrays.sort( baggedItems ) ;
        
        for ( final GroceryItem groceryItem : baggedItems )
            {

            if ( ( previousItem != null ) &&                // is this the first item or
                 ( groceryItem.equals( previousItem ) ) )   // the same as the previous item
                {
                // skip duplicate items
                }
            else    // display the item and the quantity in the bag
                {
                System.out.printf( "(%d) %s%n",
                                   aGroceryBag.getNumberOf( groceryItem ),
                                   groceryItem ) ;
                }   // end if/else
            
            previousItem = groceryItem ;   // remember this item so we can skip duplicates
            
            }   // end for()
        
        }   // end method displayABag()
 
    
    /**
     * display the contents of all grocery bags
     * 
     * @param shoppingCart
     *     a collect of grocery bags
     */
    private static void displayAllBags( final List<GroceryBag> shoppingCart )
        {
        
        // NOTE do not modify this method
        
        
        // make sure we have a shopping cart
        if ( shoppingCart == null )
            {
            System.out.printf( "%nno shopping cart provided%n" ) ;
            
            return ;
            }
        
        
        // assertion: we have a shopping cart (list of bags)
        
        System.out.printf( "Loaded %,d bag%s:%n",
                           shoppingCart.size(), 
                           ( shoppingCart.size() == 1
                               ? ""
                               : "s" ) ) ;

        // put the grocery bags in order by bag id for display
        Collections.sort( shoppingCart ) ;
        
        int itemCount = 0 ;

        for ( final GroceryBag aGroceryBag : shoppingCart )
            {
            System.out.println() ;
            
            itemCount += aGroceryBag.getItemCount() ;

            displayABag( aGroceryBag ) ;
            }   // end for()
        
        System.out.printf( "%nBagged %,d grocery items%n", itemCount ) ;
        
        }   // end displayAllBags()

    }   // end class BagIt