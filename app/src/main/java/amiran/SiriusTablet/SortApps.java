package amiran.SiriusTablet;

/**
 * Created by amirt on 10.02.2016.
 */
public class SortApps {
    public void exchangeSort(AppDetail[] list){
        int i,j;

        AppDetail temp;



        for(i=0; i<list.length-1; i++){
            for(j=i; j<list.length; j++){
                if(list[i].label.toString().compareToIgnoreCase(list[j].label.toString())>0){

                    //swapping
                    temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;

                }
            }
        }
    }
}

