package sample;

import java.util.HashMap;
import java.util.List;

public class LoopsFinder {
    HashMap<String,Integer> loops ;

    public LoopsFinder(HashMap<String,Integer> loops){
        this.loops = loops;
    }
    public void findLoops(List nodes){
        findSmallLoops(nodes);
        findBigLoops(nodes);
    }
    private void findBigLoops(List nodes){
        for(int initial_i=0;initial_i<nodes.size();initial_i++){
            int i = initial_i;
            for(int initial_j=initial_i+1;initial_j<nodes.size()+1;initial_j++) {
                int j = initial_j;
                for (int k = initial_j+1; k <nodes.size()+1;k++){
                    int[][] array = (int[][])nodes.get(i);
                    int value = array[1][k];
                    String str = ""+i+"-";
                    Loop(nodes,i,j,k,value,str);
                }
            }
        }
    }

    private boolean Loop(List nodes, int i, int j, int k, int value, String str) {
        boolean flag ;
        if(j>k){return true;}
        int[][]array = (int[][]) nodes.get(i);
        value = value*array[0][j];
        str = str+j+"-";
        if(j==k&&value!=0){
            str = str+str.charAt(0);
            loops.put(str,value);
            return true;
        }
        if(value==0) {
            return true;
        }
        flag = Loop(nodes,j,j+1,k,value,str);
        if(flag){
            while(j<k) {
                j++;
                Loop(nodes, i+1, j + 1, k, value, str);
            }
        }
        return true;
    }
    private void findSmallLoops(List nodes){
        for(int i=0;i<nodes.size();i++){
            int[][]arr = (int[][])nodes.get(i);
            if(arr[1][i]!=0){
                String str = ""+i+"-"+i;
                int value = arr[1][i];
                loops.put(str,value);
            }
            for(int j=i+1;j<nodes.size()+1;j++) {
                if(arr[0][j]*arr[1][j]!=0){
                    String str = ""+i+"-"+j+"-"+i;
                    int value = arr[0][j]*arr[1][j];
                    loops.put(str,value);
                }
            }
        }
    }
}
