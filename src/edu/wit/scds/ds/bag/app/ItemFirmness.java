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
 * Enumeration of grocery item firmness for the GroceryBagger application.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 initial version for GroceryBagger application
 * @version 1.1 2023-02-13 updates for spring 2023 assignment
 * @version 2.0 2025-02-08 renamed from{@code GroceryItemFirmness}
 */
public enum ItemFirmness
    {

    // Firmness - Display Name
    /** Soft item */
    SOFT ( "Soft" ),

    /** Firm item */
    FIRM ( "Firm" ),

    /** Hard item */
    HARD ( "Hard" );


    /*
     * data fields
     */


    /** nicely formatted name for display */
    public final String displayName ;


    /**
     * @param itemFirmnessDisplayName
     *     nicely formatted name for display
     */
    private ItemFirmness( final String itemFirmnessDisplayName )
        {
        this.displayName = itemFirmnessDisplayName ;

        } // end constructor


    /**
     * Parse a text description of firmness
     * <p>
     * WARNING: This is a very rudimentary implementation and may produce unexpected results.
     *
     * @param firmnessDescription
     *     a name to parse
     *
     * @return the corresponding enum constant or FIRM if the name is unrecognized
     */
    public static ItemFirmness interpretDescription( final String firmnessDescription )
        {
        // This is a very rudimentary implementation

        return switch ( firmnessDescription.toLowerCase().charAt( 0 ) )
                {
                case 's' -> SOFT ;
                case 'f' -> FIRM ;
                case 'h' -> HARD ;
                default -> FIRM ;
                } ;

        }   // end interpretDescription()


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
                           ItemFirmness.class.getSimpleName() ) ;

        // display column headers
        System.out.printf( "%-5s %-15s %-15s %-15s %-15s%n",
                           "#",
                           "Item Firmness",
                           "Name",
                           "Display Name",
                           "Interpreted Firmness" ) ;

        // display each element of the enumeration
        for ( final ItemFirmness anItemFirmness : ItemFirmness.values() )
            {
            System.out.printf( "%-5d %-15s %-15s %-15s %-15s%n",
                               anItemFirmness.ordinal(),
                               anItemFirmness,
                               anItemFirmness.name(),
                               anItemFirmness.displayName,
                               interpretDescription( anItemFirmness.toString() ) ) ;
            }   // end for()

        }   // end main()

    }   // end enum ItemFirmness