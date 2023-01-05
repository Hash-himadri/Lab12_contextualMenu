package com.himadri_parikh.lab12_contextualmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvList;
    ArrayList<String> HEROS = new ArrayList<>();
    ArrayAdapter<String> adt;
    ArrayList<String> userSelection = new ArrayList<>();
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvList = findViewById(R.id.lvList);

        HEROS.add("Dr. Kalam");
        HEROS.add("Subhash Chandra Bose");
        HEROS.add("Chandrashekhar Azad");
        HEROS.add("Lala lajpat Rai");
        HEROS.add("Maharana Pratap");
        HEROS.add("Shivaji Maharaj");
        HEROS.add("Gandhiji");
        HEROS.add("Bhagat Singh");

        adt = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                HEROS
        );
        lvList.setAdapter(adt);
        lvList.setChoiceMode
                (AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvList.setMultiChoiceModeListener(modeListener);
    }

    AbsListView.MultiChoiceModeListener modeListener
            = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int index = position;
            if(userSelection.contains(HEROS.get(index))){
               userSelection.remove(HEROS.get(index));
            }
            else{
                userSelection.add(HEROS.get(index));
            }
            mode.setTitle(userSelection.size()+" items selected");

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.my_contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            info = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();

            switch (item.getItemId()){
                case R.id.menuDelete:
                    removeItems(userSelection);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            userSelection.clear();
        }
    };
    private void removeItems(ArrayList<String> userSelection) {
        for(String str: userSelection){
            HEROS.remove(str);
        }
        adt.notifyDataSetChanged();
    }
}