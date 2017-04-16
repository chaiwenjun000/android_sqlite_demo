package edu.hrbeu.SQLiteDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteDemo extends Activity {
    /** Called when the activity is first created. */
	private DBAdapter dbAdepter ;
	private EditText nameText;
	private EditText ageText;
	private EditText heightText;
	private EditText idEntry;
	public  long nowId = -1;
	private static SQLiteDemo mainActivity = null; //设置一个静态变量，全局访问
	public SQLiteDemo() {
		mainActivity = this;
	} //为静态变量赋初值
	public static SQLiteDemo getMainActivity() {
		return mainActivity;
	}
	private TextView labelView;
	PeopleAdapter adapter;
	List<People> data = new ArrayList<People>();
	ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        nameText = (EditText)findViewById(R.id.name);
        ageText = (EditText)findViewById(R.id.age);
        heightText = (EditText)findViewById(R.id.height);

        
        labelView = (TextView)findViewById(R.id.label);



        listView = (ListView) findViewById(R.id.listview);

		dbAdepter = new DBAdapter(this);
		dbAdepter.open();
		People[] peoples = dbAdepter.queryAllData();
		if (peoples == null){
			labelView.setText("数据库中没有数据");
		}else{
			labelView.setText("数据库：");
			for (int i = 0 ; i<peoples.length; i++){
				data.add(peoples[i]);
			}
		}


		adapter = new PeopleAdapter(SQLiteDemo.this,R.layout.item,data);


		//实现列表的显示
		listView.setAdapter(adapter);


		Button addButton = (Button)findViewById(R.id.add);

        
        Button queryButton = (Button)findViewById(R.id.query);
        Button deleteButton = (Button)findViewById(R.id.delete);
        Button updateButton = (Button)findViewById(R.id.update);
        
        
        addButton.setOnClickListener(addButtonListener); 

        
        queryButton.setOnClickListener(queryButtonListener);
        deleteButton.setOnClickListener(deleteButtonListener);
        updateButton.setOnClickListener(updateButtonListener);
        

    }
	public void getData(){
		People[] peoples = dbAdepter.queryAllData();
		if (peoples == null){
			labelView.setText("数据库中没有数据");
			data.clear();
			listView.setAdapter(adapter);
			return ;
		}
		else{
			labelView.setText("数据库：");
			data.clear();
			for (int i = 0 ; i<peoples.length; i++){
				data.add(peoples[i]);
			}
			listView.setAdapter(adapter);
		}

	}



    OnClickListener addButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			People people = new People();
			people.Name = nameText.getText().toString();
			people.Age = Integer.parseInt(ageText.getText().toString());
			people.Height = Float.parseFloat(heightText.getText().toString());
			long colunm = dbAdepter.insert(people);
			if (colunm == -1 ){
				labelView.setText("添加过程错误！");
			} else {
				labelView.setText("成功添加数据，ID："+String.valueOf(colunm));
				getData();
			}
		}	
    };
    


    
    OnClickListener queryButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			String name = ""+nameText.getText();
			People[] peoples = dbAdepter.queryOneData(name);
			
			if (peoples == null){
				labelView.setText("数据库中没有NAME为"+name+"的数据");
				return;
			}
			Toast.makeText(mainActivity,peoples[0].toString(),Toast.LENGTH_LONG).show();
			labelView.setText("数据库：");

		}
    };
    
    OnClickListener deleteButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			long id = nowId;
			long result = 0;
			if(id != -1)
				result = dbAdepter.deleteOneData(id);
			String msg = "删除ID为"+id+"的数据" + (result>0?"成功":"失败");
			labelView.setText(msg);
			getData();
		}	
    };
    
    OnClickListener updateButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			People people = new People();
			people.Name = nameText.getText().toString();
			people.Age = Integer.parseInt(ageText.getText().toString());
			people.Height = Float.parseFloat(heightText.getText().toString());
			long id = nowId;
			long count = dbAdepter.updateOneData(id, people);
			if (count == -1 ){
				labelView.setText("更新错误！");
			} else {
				labelView.setText("更新成功，更新数据"+String.valueOf(count)+"条");
				getData();
			}
		}
    };

	public void setData(int id,String name,int age,float height){
		nowId = id;
		nameText.setText(name);
		ageText.setText(age + "");
		heightText.setText(height + "");
	}


}