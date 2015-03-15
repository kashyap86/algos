package org.kash.algos.dp;

public class MinimumEditDistance {
	 
    public static void main(String args[]) {
        String s1 = "kate";
        String s2 = "cats";
         
        int[][] dist = new int[s2.length()+1][s1.length()+1];
         
        dist[0][0] = 0;
         
        for(int i = 0; i<s2.length(); i++)
            dist[i][0] = i;
        for(int j = 0; j<s1.length(); j++)
            dist[0][j] = j;
         
        for(int k = 1; k<=s2.length(); k++){
            for(int l = 1; l<=s1.length(); l++){
                int cost = 0;
                if(s2.charAt(k-1) != s1.charAt(l-1)) cost = 2;
                dist[k][l] = minimum(dist[k-1][l]+1, dist[k][l-1]+1, dist[k-1][l-1]+cost);
            }
        }
        System.out.println(dist[s2.length()][s1.length()]);
    }
 
    private static int minimum(int i, int j, int k) {
        /*if(i <= j && i <= k) return i;
        else if (j <= i && j <= k) return j;
        return k;
        */
        return i <= j ? (i<=k?i:k) : (i > k ? (k <= j ? k : j):j); 
    }
}
