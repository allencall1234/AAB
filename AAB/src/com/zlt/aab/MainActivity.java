package com.zlt.aab;

import java.text.DecimalFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.tablemanager.Connector;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView = null;
	
	private final static String URL = "http://www.kegg.jp/dbget-bin/www_bget?cpd:C";
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			int text = msg.arg1;
			textView.setText(String.format("正在获取第%1$d条消息", text));
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SQLiteDatabase db = Connector.getDatabase();
		
		RequestQueue mQueue = Volley.newRequestQueue(this);
		
		
		textView = (TextView) findViewById(R.id.showtext);
		
		
		
		for (int i = 1; i < 20000; i++) {
			final int num = i;
			 DecimalFormat df=new DecimalFormat("00000");
			final String ids = URL + df.format(i);
			Log.i("zlt", "ids = " + ids);
			StringRequest request = new StringRequest(ids, new Listener<String>() {
				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					Item item = getItem(arg0);
					item.save();
					Message msg = Message.obtain();
					msg.arg1 = num;
					mHandler.sendMessage(msg);
				}
			}, new ErrorListener() {
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			mQueue.add(request);
		}
	}
	
	public Item getItem(String str) {
		// Log.e("URL---->", str);
		// 获取文档对象
		Document doc = Jsoup.parse(str);
		
		Elements list20 = doc.getElementsByClass("td20");
		Elements list21 = doc.getElementsByClass("td21");

		Item item = new Item();
		if (list20.size()>1) {
			item.setEntry(list20.get(0).select("nobr").text().substring(0, 6));
		}
//		System.out.println("item.getEntry = " + item.getEntry());
		if (list20.size()>1) {
			item.setFormula(list20.get(1).select("div").text());
		}
//		System.out.println("item.getFormula = " + item.getFormula());
		if (list20.size()>2) {
			item.setMass(list20.get(2).select("div").text());
		}
//		System.out.println("item.getMass = " + item.getMass());
		if (list21.size()>1) {
			String name = list21.get(0).select("div").text();
			item.setName(name.substring(0, name.length() / 2));
		}
//		System.out.println("item.getName = " + item.getName());
		if (list21.size()>1) {
			item.setWeight(list21.get(1).select("div").text());
		}
//		System.out.println("item.getWeight = " + item.getWeight());
//		String dbs = list21.get(3).select("div").text();
//		item.setDbs(dbs.substring(0, dbs.length() / 2));
//		System.out.println("item.getDbs = " + item.getDbs());

		return item;
	}

}
