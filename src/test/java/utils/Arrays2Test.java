package utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Arrays2Test {
   private static int[] getDuplicateInts(int len) {
        int[] res = new int[len];
        for(int i = 0 ; i<len ; ++i) {
            res[i] = i/2;
        }
        return res;
    }

    private static int[] getInts(int len, int value) {
        int[] res = new int[len];
        Arrays.fill(res, value);
        return res;
    }

    @Test
    public void indexOfLowerBound_value_in_index() {
        final int len = 10;
        final int index = 7;
        // Arrange
        int[] v = getDuplicateInts(len);
        
        // Act
        int res = Arrays2.indexOfLowerBound(v, v[index]);
 
        // Assert
        assertEquals(v[index], v[res]);        
        assertEquals(v[res], v[res+1]);        
    }

    @Test
    public void indexOfLowerBound_empty_array() {
        int[] v = {};
        
        assertEquals(-1, Arrays2.indexOfLowerBound(v, 7));        
    }
    
    @Test
    public void indexOfLowerBound_one_value() {
        final int len = 10;
        final int value = 1;
        // Arrange
        int[] v = getInts(len, value);
      
        // Assert
        assertEquals(0, Arrays2.indexOfLowerBound(v, value));        
        assertEquals(-1, Arrays2.indexOfLowerBound(v, value-1));        
        assertEquals(-v.length-1, Arrays2.indexOfLowerBound(v, value+1));        
     }
    
    @Test
    public void leftBound_match_first_element_in_array() {
        // Arrange
        int[] v = {5,6,7,8,9,55};
      
        // Assert
        assertEquals(0, Arrays2.indexOfLowerBound(v, 5));        
    }
    
    @Test
    public void lowerBound_of_a_inexistent_value() {
    	// Arrange
        int[] v = {0,1,3,4,6};
      
        // Assert
        assertEquals(-3, Arrays2.indexOfLowerBound(v, 2));
    }
    
    @Test
    public void indexOf_match_last_element_in_array() {
    	int[] v = {1, 1, 2, 4, 6, 7};
    	assertEquals(v.length-1, Arrays2.indexOfLowerBound(v, 7) );
    }

}
