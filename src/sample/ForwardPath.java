package sample;

import java.util.HashMap;
import java.util.List;

public class ForwardPath {

    HashMap<String,Integer> forwardPaths ;

    public ForwardPath(HashMap<String,Integer> forwardPaths){
        this.forwardPaths = forwardPaths;
    }
    public void findForwardPaths(List nodes){
            int i = 0;
            for(int initial_j=1;initial_j<nodes.size()+1;initial_j++) {
                int j = initial_j;
                int[][] array = (int[][])nodes.get(i);
                int value =1 ;
                String str = ""+i;
                Loop(nodes,i,j,value,str);
            }

    }

    private boolean Loop(List nodes, int i, int j,  int value, String str) {
        boolean flag ;
        if(j>nodes.size()){return true;}
        int[][]array = (int[][]) nodes.get(i);
        value = value*array[0][j];
        str = str+"-"+j;
        if(j==nodes.size()&&value!=0){
            forwardPaths.put(str,value);
            return true;
        }
        if(value==0) {
            return true;
        }
        flag = Loop(nodes,j,j+1,value,str);
        if(flag){
            while(j<nodes.size()) {
                j++;
                Loop(nodes, i+1, j + 1,value, str);
            }
        }
        return true;
    }


}
