package org.mopo.utils;

public class StringUtils {
    
    /**
     * Get whther a str is empty.
     * 
     * @param str the specfied str
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        if(str == null || str.trim().equals("")){
            return true;
        }
        return false;
    }

}
