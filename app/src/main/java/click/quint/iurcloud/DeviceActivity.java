package click.quint.iurcloud;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        FragmentManager fm = getFragmentManager();
        // Check if DeviceListFragment is already added
        if (fm.findFragmentById(android.R.id.content) == null) {
            DeviceListFragment list = new DeviceListFragment();
            // Add the DeviceListFragment to View
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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

    public static class DeviceListFragment extends ListFragment
    {
        private DeviceListAdapter mAdapter;
        private ArrayList<Device> mData;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Read all devices from database into mData ArrayList
            getDevices();

            // Create new custom adapter
            mAdapter = new DeviceListAdapter(getActivity(), mData);
            setListAdapter(mAdapter);

            return super.onCreateView(inflater, container, savedInstanceState);
        }

        private void getDevices() {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            mData = new ArrayList<>();

            List<Device> devices = db.getAllDevices();

            for (Device dev : devices) {
                mData.add(dev);
            }
        }
    }
}
