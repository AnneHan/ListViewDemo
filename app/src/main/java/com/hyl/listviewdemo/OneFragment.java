package com.hyl.listviewdemo;

import com.hyl.dao.DBOpenHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @programName: OneFragment.java
 * @programFunction:
 * @createDate: 2018/09/25
 * @author: AnneHan
 * @version:
 * xx.   yyyy/mm/dd   ver    author    comments
 * 01.   2018/09/25   1.00   AnneHan   New Create
 */
public class OneFragment extends Fragment {

    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>(); //存储数据的数组列表
    int[] image_expense = new int[]{R.mipmap.detail_income, R.mipmap.detail_payout }; //存储图片

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        //写死的数据，用于测试
        String[] expense_category = new String[] {"发工资", "买衣服"};
        String[] expense_money = new String[] {"30000.00", "1500.00"};
        for (int i = 0; i < image_expense.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image_expense", image_expense[i]);
            map.put("expense_category", expense_category[i]);
            map.put("expense_money", expense_money[i]);
            listitem.add(map);
        }
        //getData(); //query data from a database

        SimpleAdapter adapter = new SimpleAdapter(getActivity()
                , listitem
                , R.layout.fragment_one_item
                , new String[]{"expense_category", "expense_money", "image_expense"}
                , new int[]{R.id.tv_expense_category, R.id.tv_expense_money, R.id.image_expense});
        // 第一个参数是上下文对象
        // 第二个是listitem
        // 第三个是指定每个列表项的布局文件
        // 第四个是指定Map对象中定义的两个键（这里通过字符串数组来指定）
        // 第五个是用于指定在布局文件中定义的id（也是用数组来指定）

        ListView listView = (ListView) v.findViewById(R.id.lv_expense);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                //在点击某笔明细的时候，Tip出明细内容
                Toast.makeText(getActivity(), map.get("expense_category").toString(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    /**
     * 从数据库获得适配器数据
     */
    private void getData(){
        //call DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{"11111111111"},null,null,null);
        c.moveToFirst();
        int iColCount = c.getColumnCount();
        int iNumber = 0;
        String strType = "";
        while (iNumber < c.getCount()){
            Map<String, Object> map = new HashMap<String, Object>();

            strType = c.getString(c.getColumnIndex("Type"));
            map.put("image_expense", image_expense[Integer.parseInt(strType)]);
            map.put("expense_category", c.getString(c.getColumnIndex("item")));
            if(strType.equals("0")){
                map.put("expense_money", "+" + c.getString(c.getColumnIndex("cost")));
            }else{
                map.put("expense_money", "-" + c.getString(c.getColumnIndex("cost")));
            }

            c.moveToNext();
            listitem.add(map);
            iNumber++;
            System.out.println(listitem);
        }
        c.close();
        db.close();
    }
}