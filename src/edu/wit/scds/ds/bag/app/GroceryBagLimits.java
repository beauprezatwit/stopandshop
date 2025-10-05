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


/**
 * Limits for GroceryBag contents
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2024-02-01 Initial implementation
 * @version 2.0 2025-02-08
 *     <ul>
 *     <li>removed GROCERY_BAG_MAX_ITEM_COUNT
 *     <li>updates for spring 2025 assignment
 *     </ul>
 */
public class GroceryBagLimits
    {

    /** maximum volume (cumulative sizes) of contained GroceryItems a GroceryBag can hold */
    public final static int GROCERY_BAG_MAX_VOLUME = 25 ;

    /** maximum total weight (cumulative weight) of contained GroceryItems a GroceryBag can hold */
    public final static int GROCERY_BAG_MAX_WEIGHT = 15 ;

    }   // end class GroceryBagLimits