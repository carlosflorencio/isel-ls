package utils;

public class Arrays2 {
    
	
    public static int indexOfLowerBound(int[] a, int n) {
    	if(a.length == 0) return -1;
    	if(n > a[a.length - 1]) return -a.length -1;
    	
    	int beginIndex = 0;
    	int endIndex = a.length;
    	int mid;
    	
    	while( beginIndex <= endIndex ){
    		mid = beginIndex + (endIndex - beginIndex) / 2;
    		
    		if( a[mid] < n ) beginIndex = mid + 1;
    		else endIndex = mid - 1;
    	}
    	
		if(a[beginIndex] == n) 
			return beginIndex;
		else 
			return -beginIndex-1; 
			
    }


}
