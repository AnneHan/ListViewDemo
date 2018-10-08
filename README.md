# ListViewDemo
Android Studio列表用法之一：ListView图文列表显示（实例）

***
Author  | AnneHan
--      | --
E-mail  | lilyhyl@163.com
***

# 效果图

<img src="https://github.com/AnneHan/ListViewDemo/blob/master/effectpic/list.jpg" height="500" alt="图片"/>

# 实现思路
1、该功能是用fragment来做布局的，首先创建一个fragment.xml布局文件，在里面添加一个ListView控件；

2、由于List里面既要呈现图片，也要呈现文字，所以再创建一个fragment_item.xml布局文件，在里面添加ImageView、TextView，用来显示图片和文字；

3、使用SimpleAdapter来绑定数据；

# 代码解析
在OneFragment.java中，数据的绑定是写死的，动态取值可查看代码中的getData()方法，使用的数据库是SQLite
```java
//写死的数据，用于测试
List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>(); //存储数据的数组列表
int[] image_expense = new int[]{R.mipmap.detail_income, R.mipmap.detail_payout }; //存储图片
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

//从数据库中查询数据
//getData(); 
```

# 备注
1、本文中的案例是在另一篇博文【Android Studio 使用ViewPager + Fragment实现滑动菜单Tab效果 --简易版】(https://www.cnblogs.com/AnneHan/p/9702365.html) 的基础上进行添加的

2、对于SQLite数据库的使用可以参考我的另一篇博文【Android Studio 通过一个登录功能介绍SQLite数据库的使用】(https://www.cnblogs.com/AnneHan/p/9724688.html)
