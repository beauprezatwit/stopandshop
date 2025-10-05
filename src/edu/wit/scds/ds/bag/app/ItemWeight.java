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
 * Enumeration of grocery item weights for the GroceryBagger application.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 initial version for GroceryBagger application
 * @version 1.1 2023-02-13 updates for spring 2023 assignment
 * @version 2.0 2025-02-08
 *     <ul>
 *     <li>renamed from {@code GroceryItemWeight}
 *     <li>updates for spring 2025 assignment
 *     </ul>
 */
public enum ItemWeight
    {

    // Weight - Display Name - Numeric Value
    /** Light item */
    LIGHT ( "Light", 2 ),

    /** Medium item */
    MEDIUM ( "Medium", 3 ),

    /** Heavy item */
    HEAVY ( "Heavy", 10 ),

    /** Very Heavy item */
    VERY_HEAVY ( "Very Heavy", 15 );


    /*
     * data fields
     */


    /** nicely formatted name for display */
    public final String displayName ;

    /** integer equivalent of the weight */
    public final int weightValue ;


    /**
     * @param itemWeightDisplayName
     *     nicely formatted name for display
     * @param itemWeightValue
     *     integer equivalent of the weight
     */
    private ItemWeight( final String itemWeightDisplayName, final int itemWeightValue )
        {
        this.displayName = itemWeightDisplayName ;
        this.weightValue = itemWeightValue ;

        } // end constructor


    /**
     * Parse a text description of weight
     * <p>
     * WARNING: This is a very rudimentary implementation and may produce unexpected results.
     *
     * @param weightDescription
     *     a name to parse
     *
     * @return the corresponding enum constant or MEDIUM if the name is unrecognized
     */
    public static ItemWeight interpretDescription( final String weightDescription )
        {
        // This is a very rudimentary implementation

        return switch ( weightDescription.toLowerCase().charAt( 0 ) )
                {
                case 'l' -> LIGHT ;
                case 'm' -> MEDIUM ;
                case 'h' -> HEAVY ;
                case 'v' -> VERY_HEAVY ;
                default -> MEDIUM ;
                } ;

        }   // end interpretDescription()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return this.displayName ;

        }   // end toString()


    /**
     * Test driver - displays all constants for this enumeration
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // display introductory message
        System.out.printf( "Members of the %s enumeration%n%n",
                           ItemWeight.class.getSimpleName() ) ;

        // display column headers
        System.out.printf( "%-5s %-15s %-15s %-15s %-15s %-15s%n",
                           "#",
                           "Item Weight",
                           "Name",
                           "Display Name",
                           "Weight Value",
                           "Interpreted Weight" ) ;

        // display each element of the enumeration
        for ( final ItemWeight anItemWeight : ItemWeight.values() )
            {
            System.out.printf( "%-5d %-15s %-15s %-15s %-15d %-15s%n",
                               anItemWeight.ordinal(),
                               anItemWeight,
                               anItemWeight.name(),
                               anItemWeight.displayName,
                               anItemWeight.weightValue,
                               interpretDescription( anItemWeight.toString() ) ) ;
            }   // end for()

        }   // end main()

    }   // end enum ItemWeight