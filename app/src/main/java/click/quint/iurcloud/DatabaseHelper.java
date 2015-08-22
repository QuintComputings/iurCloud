package click.quint.iurcloud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "iurData";

    // Table Names
    private static final String TABLE_DEVICES = "devices";
    private static final String TABLE_PLUGINS = "plugins";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ENABLE = "enabled";

    // Device table - column names
    private static final String KEY_TYPE = "type";
    private static final String KEY_MAC = "mac";

    // Plugin table - column names
    private static final String KEY_PATH = "path";

    // Table Create Statements
    // Device table create statement
    private static final String CREATE_TABLE_DEVICES = "CREATE TABLE "
            + TABLE_DEVICES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_TYPE + " TEXT," + KEY_MAC
            + " TEXT," + KEY_ENABLE + " INTEGER" + ")";

    // Plugins table create statement
    private static final String CREATE_TABLE_PLUGINS = "CREATE TABLE "
            + TABLE_PLUGINS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_PATH + " TEXT," + KEY_ENABLE
            + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create required tables
        db.execSQL(CREATE_TABLE_DEVICES);
        db.execSQL(CREATE_TABLE_PLUGINS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLUGINS);

        // Create new tables
        onCreate(db);
    }

    // Adding new Device
    public void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, device.getName());
        values.put(KEY_TYPE, device.getType());
        values.put(KEY_MAC, device.getMac());
        values.put(KEY_ENABLE, (device.getStatus() ? 1 : 0)); // Store int 1 if true otherwise 0

        // Inserting Row
        db.insert(TABLE_DEVICES, null, values);
        db.close(); // Close database
    }

    // Adding new Plugin
    public void addPlugin(Plugin plugin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plugin.getName());
        values.put(KEY_PATH, plugin.getPath());
        values.put(KEY_ENABLE, (plugin.getStatus() ? 1 : 0)); // Store int 1 if true, false as 0

        // Inserting Row
        db.insert(TABLE_PLUGINS, null, values);
        db.close(); // Close database
    }

    // Get all devices
    public List<Device> getAllDevices() {
        List<Device> deviceList = new ArrayList<>();

        // Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                // Cursor 0 reads the database id
                device.setName(cursor.getString(1));
                device.setType(cursor.getString(2));
                device.setMac(cursor.getString(3));
                device.setStatus((Integer.parseInt(cursor.getString(4)) == 0) ? false : true);

                // Adding device to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        // Return device list
        return deviceList;
    }

    // Get all plugins
    public List<Plugin> getAllPlugins() {
        List<Plugin> pluginList = new ArrayList<>();

        // Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_PLUGINS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Plugin plugin = new Plugin();
                // Cursor 0 reads the database id
                plugin.setName(cursor.getString(1));
                plugin.setPath(cursor.getString(2));
                plugin.setStatus((Integer.parseInt(cursor.getString(3)) == 0) ? false : true);

                // Adding plugin to list
                pluginList.add(plugin);
            } while (cursor.moveToNext());
        }

        // Return plugin list
        return pluginList;
    }

    // Delete single device specified through mac adress
    public void deleteDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICES, KEY_MAC + " = ?",
                new String[] { String.valueOf(device.getMac()) });
        db.close();
    }

    // Delete single plugin specified through plugin name
    public void deletePlugin(Plugin plugin) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLUGINS, KEY_NAME + " = ?",
                new String[] { String.valueOf(plugin.getName()) });
        db.close();
    }
}
