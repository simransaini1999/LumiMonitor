package com.example.lumimonitor;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Graph_Humidity extends AppCompatActivity {

    private LineChart linechart;
    private FirebaseDatabase database = null;
    private DatabaseReference ref = null;
    private int N = 20;
    String[] XLabels = new String[N];
    List<DataStructure> firebaseData = new ArrayList<>();
    private boolean firstTimeDrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_humidity);
        setupTitleandHomeButton();
        firstTimeDrew = false;
        //Find the chart
        linechart = findViewById(R.id.firebaseline_chart);

        //Find the Database.
        database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String path = "userdata/" + mAuth.getUid();
        ref = database.getReference(path);

        //Load the data from database.
        loadDatabase(ref);


    }

    private void drawGraph() {


        if (firebaseData.size() > N)  // Should have a guard to make sure we always draw the most recent N numbers.
            firebaseData = firebaseData.subList(firebaseData.size()-N, firebaseData.size());

        Collections.sort(firebaseData);
        for (int i=0; i< firebaseData.size(); i++){
            //Define the XLabels of the chart.
            try{
               // XLabels[i] = firebaseData.get(i).getTimestamp();
              XLabels[i] = convertTimestamp(firebaseData.get(i).getAwakenTime());
            }
            catch (Exception e){
                Log.d("MapleLeaf", "Error Happened: " + e.getMessage());
            }
        }
        //Set text description of the xAxis
        Description desc = new Description();
        desc.setText("LineChart from Firebase");
        desc.setTextSize(15);
        linechart.setDescription(desc);

        linechart.animateXY(2000,2000);

        setAndValidateLabels();

        int i = 0;
        List<Entry> entrylist = new ArrayList();
        for (DataStructure ds: firebaseData){
            // Get the humidity from the firebase.
            Entry e = new Entry (i++, Float.parseFloat(ds.getHumidity()));
            entrylist.add(e);
        }

        //Find the dataset of the ArrayList.
        LineDataSet dataset = new LineDataSet(entrylist, "Humidity");
        dataset.setColor(R.color.colorAccent);  // set the color of this chart.
        dataset.setValueTextSize(14);
        //Get the LineData Object from dataset.
        LineData linedata = new LineData(dataset);
        linechart.setData(linedata);
        //This is a must to refresh the chart.
        linechart.invalidate(); // refresh
    }

    private void setAndValidateLabels() {
        //Set the labels to be displayed.
        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-30f);  // rotate the xAxis label for 30 degrees.
        xAxis.setValueFormatter(new IAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // Seems to be a bug in the code value should not be less than 0 or more than N-1
                if ((value <0) || (value >N-1))return "";
                return XLabels[(int) value];
            }
        });
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 3
    }

    private void loadDatabase(DatabaseReference ref) {
        // Last N data entries from Database, these are automatically the N most recent data

        Query recentPostsQuery = ref.limitToLast(N);  // get the most recent N data
        // NOTICE: Firebase Value event is always called after the ChildAdded event.
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MapleLeaf", "finished");
                //Now all the query data is in List firebaseData, Follow the similar procedure in Line activity.
                drawGraph();

                firstTimeDrew = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recentPostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    //handle all the returned data. Similar to the Firebase read structure event.
                    //This part of the code is to handle if there is any data changed on Firebase.
                    DataStructure dataStructure = new DataStructure();
                    dataStructure.setTemperature(dataSnapshot.getValue(DataStructure.class).getTemperature());
                    dataStructure.setHumidity(dataSnapshot.getValue(DataStructure.class).getHumidity());
                    String timestamp = dataSnapshot.getValue(DataStructure.class).getAwakenTime();
                    dataStructure.setAwakenTime(timestamp);
                    boolean updated = false;
                    for (int i = 0; i<firebaseData.size(); i++){
                        if (firebaseData.get(i).getAwakenTime().equals(dataStructure.getAwakenTime()))
                        {
                            firebaseData.set(i, dataStructure);
                            updated = true;
                        }
                    }

                    firebaseData.add(dataStructure);  // now all the data is in arraylist.

                    Log.d("MapleLeaf", "dataStructure at " + dataStructure.getAwakenTime() + " Updated");
                }
                //Now all the query data is in List firebaseData, Follow the similar procedure in Line activity.
                drawGraph();
            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    //handle all the returned data. Similar to the Firebase read structure event.
                    //This part of the code is to handle the new data is added to the code.
                    DataStructure dataStructure = new DataStructure();
                    dataStructure.setTemperature(dataSnapshot.getValue(DataStructure.class).getTemperature());
                    dataStructure.setHumidity(dataSnapshot.getValue(DataStructure.class).getHumidity());
                    String timestamp = dataSnapshot.getValue(DataStructure.class).getAwakenTime();
                    dataStructure.setAwakenTime(timestamp);

                    firebaseData.add(dataStructure);  // now all the data is in arraylist.
                    Log.d("MapleLeaf", "dataStructure " + dataStructure.getAwakenTime());
                }
                try{
                    //If already drew but still come to here, there is only one possibility that a new node is added to the database.
                    if (firstTimeDrew)
                        drawGraph();
                }
                catch (Error e){
                    Log.d("MapleLeaf", "Error Happened: " + e.getMessage());


                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String convertTimestamp(String timestamp){

        // Convert timestamp to text.

        long yourSeconds = (long)Double.parseDouble(timestamp);
        Date mDate = new Date(yourSeconds*1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        // df.setTimeZone(TimeZone.getTimeZone("Etc/GMT-5"));
        DateFormat df1 = new SimpleDateFormat("hh:mm:ss");
        Log.d("MapleLeaf", df.format(mDate) +System.lineSeparator() + df1.format(mDate));
        return df.format(mDate) +System.lineSeparator() + df1.format(mDate);
    }

    private void setupTitleandHomeButton() {
        getSupportActionBar().setSubtitle("Humidity Graph");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
