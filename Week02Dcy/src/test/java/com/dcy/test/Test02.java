package com.dcy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.dcy.bean.User;
import com.dcy.utils.RandomUitl;
import com.dcy.utils.StringUtil;

import redis.clients.jedis.Jedis;

public class Test02 {
	// 使用JDK系列化方式保存十万个user随机对象到Redis，并计算耗时
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.148.111", 6379);

		List<User> list = new ArrayList<User>();

		for (int i = 1; i <= 100000; i++) {
			User u = new User();
			// ID使用1-10万序号。
			u.setId(i);
			// 姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法。
			u.setName(StringUtil.generateChineseName() + StringUtil.randomChineseString(1));
			// 性别在女和男两个值中随机。
			u.setSex(StringUtil.generateSex());
			// 手机以13开头+9位随机数模拟。
			u.setTel("13" + RandomUitl.randomString(9));
			// 邮箱以3-20个随机字母+@qq.com、@163.com、@sian.com、@gmail.com、@sohu.com、@hotmail.com模拟。
			u.setEmial("@qq.com" + RandomUitl.randomString(9));
			// 生日要模拟18-70岁之间。
			u.setBirthday(RandomUitl.randomBirthday());
			list.add(u);
		}

		long start = System.currentTimeMillis();

		for (User user : list) {
			jedis.sadd("key_userJDK", String.valueOf(user));
		}

		long end = System.currentTimeMillis();

		System.out.println("jdk保存十万条数据一共消耗时间:" + (end - start) / 1000);
	}
}
