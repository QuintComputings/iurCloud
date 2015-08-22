package click.quint.iurcloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceListAdapter extends BaseAdapter {

    private ArrayList<Device> mData = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;

    public DeviceListAdapter(Context context, ArrayList<Device> data) {
        mData = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // This method is used to get the View for each row defined in row_layout_device.xml
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // If convertView is not null it can be reused directly
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.row_layout_device, null);

            // Populate the ViewHolder
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text_name);
            holder.type = (TextView) convertView.findViewById(R.id.text_type);
            holder.sw = (Switch) convertView.findViewById(R.id.switch_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind data to ViewHolder
        holder.name.setText(mData.get(position).getName());
        holder.type.setText(mData.get(position).getType());
        holder.sw.setChecked(mData.get(position).getStatus());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView type;
        Switch sw;
    }
}
