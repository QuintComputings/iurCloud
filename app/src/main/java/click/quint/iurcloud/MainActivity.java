package click.quint.iurcloud;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        // Check if MainListFragment is already added
        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager();
            MainListFragment mainListFragment = new MainListFragment();
            // Add the MainListFragment to View
            fragmentManager.beginTransaction().add(android.R.id.content, mainListFragment).commit();
        }

        //******* Database Testing ************/
        DatabaseHelper db = new DatabaseHelper(this);
        this.deleteDatabase("iurData"); // Delete Database

        // Insert devices and plugins into db
        Log.d(null, "Inserting ...");
        db.addDevice(new Device("Android", "Smartphone", "12:34:56:78:91", true));
        db.addDevice(new Device("Debian", "PC", "AB:CD:EF:GH:IJ", false));
        db.addPlugin(new Plugin("Screen Copy", "/usr/local/iurCloud/db/sc.zip", true));

        // Reading all devices and plugins from db
        Log.d(null, "Read:");
        List<Device> devices = db.getAllDevices();
        List<Plugin> plugins = db.getAllPlugins();

        for (Device dev : devices) {
            String log = "Name: " + dev.getName() + ", Type: " + dev.getType() + ", MAC: " + dev.getMac()
                    + ", Enable: " + dev.getStatus();
            // Writing devices to log
            Log.d("Name: ", log);
        }
        for (Plugin pl : plugins) {
            String log = "Name: " + pl.getName() + ", Path: " + pl.getPath() + ", Enable: " + pl.getStatus();
            // Writing plugins to log
            Log.d("Name: ", log);
        }
        //**************************************/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MainListFragment extends ListFragment {

        private static final int CLOUDED = 0;
        private static final int NEW_DEVICE = 1;
        private static final int PLUGINS = 2;
        private static final int ABOUT = 3;

        // Menu items shown in ListView
        private static final String[] MENU_ITEMS = new String[] { "Clouded Devices", "Cloud new Device",
                "Plugins", "About" };

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Intent intent;

            // Handle ListView clicks and open new activities via intents
            switch(position) {
                case CLOUDED:
                    intent = new Intent(getActivity(), DeviceActivity.class);
                    startActivity(intent);
                    break;
                case NEW_DEVICE:

                    break;
                case PLUGINS:

                    break;
                case ABOUT:
                    // intent = new Intent(getActivity(), AboutActivity.class);
                    // startActivity(intent);
                    break;
                default:
                    break;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Using simple array adapter to show the menu items
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(inflater.getContext(),
                    android.R.layout.simple_list_item_1, MENU_ITEMS);
            setListAdapter(arrayAdapter);

            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
