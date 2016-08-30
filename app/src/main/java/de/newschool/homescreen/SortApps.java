package de.newschool.homescreen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortApps {
    public void exchangeSort(List<AppDetail> list) {

        Collections.sort(list, new Comparator<AppDetail>() {
            @Override
            public int compare(AppDetail lhs, AppDetail rhs) {
                return lhs.label.compareToIgnoreCase(rhs.label);
            }
        });
    }
}
