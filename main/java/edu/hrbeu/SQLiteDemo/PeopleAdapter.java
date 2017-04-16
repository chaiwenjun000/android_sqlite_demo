package edu.hrbeu.SQLiteDemo;

/**
 * Created by 92303 on 2017/4/16.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 92303 on 2016/10/25.
 */

public class PeopleAdapter extends ArrayAdapter<People> {

    private int resourceId;
    private SQLiteDemo mainActivity;
    public PeopleAdapter(Context context, int resource, List<People> objects) {
        super(context, resource, objects);
        resourceId  = resource;
        mainActivity = SQLiteDemo.getMainActivity();
    }
    class ViewHolder{
        TextView lid;
        TextView lname;
        TextView lage;
        TextView lheight;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final People people = getItem(position);
        final int tmpPosition = position;
        View view;
        ViewHolder viewHolder = null;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.lid = (TextView) view.findViewById(R.id.lid);
            viewHolder.lname= (TextView) view.findViewById(R.id.lname);
            viewHolder.lage= (TextView) view.findViewById(R.id.lage);
            viewHolder.lheight= (TextView) view.findViewById(R.id.lheight);

            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.lid.setText(people.ID + "");
        viewHolder.lname.setText(people.Name + "");
        viewHolder.lage.setText(people.Age + "");
        viewHolder.lheight.setText(people.Height +"");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mainActivity,"sd",Toast.LENGTH_LONG).show();
                mainActivity.setData(people.ID,people.Name,people.Age,people.Height);

            }
        });

        Log.d("ttt","oooo");
        return view;
    }

}
