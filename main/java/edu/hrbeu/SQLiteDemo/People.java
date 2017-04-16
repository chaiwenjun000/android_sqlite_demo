package edu.hrbeu.SQLiteDemo;

public class People {
	public int ID = -1;
	public String Name = "";
	public int Age = 0;
	public float Height = 0;
	
	@Override
	public String toString(){
		String result = "";
		result += "ID：" + this.ID + "，";
		result += "姓名：" + this.Name + "，";
		result += "年龄：" + this.Age + "， ";
		result += "身高：" + this.Height + "，";
		return result;
	}
}
