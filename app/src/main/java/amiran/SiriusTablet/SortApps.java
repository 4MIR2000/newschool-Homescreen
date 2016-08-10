package amiran.SiriusTablet;

class SortApps {
    public void exchangeSort(AppDetail[] list) {
        int i, j;
        AppDetail temp;

        for (i = 0; i < list.length - 1; i++) {
            for (j = i; j < list.length; j++) {
                if (list[i].label.compareToIgnoreCase(list[j].label) > 0) {
                    //swapping
                    temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }
    }
}
