package com.sap.trackganges;

public class Database_Helper 
{
	public String tag ="TrackOndevice";
	static String DBname = "TRACK_ON";
	static String Retailer_Table = "RETAILER";
	static String History_Table = "HISTORY";
	static String Register_Table = "REGISTER";
	
	
	//For Retailer Table
	public static String KEY1 = "_id";
	public static String Col1 = "RET_NAME";
	public static String Col2 = "Phone_num";
	public static String Col3 = "ODM";
	public static String Col4 = "DOJ";
	public static String Col5 = "PAN";
	public static String Col6 = "ADDRESS_PROOF";
	public static String Col7 = "SHOP_PROOF";
	public static String Col8 = "FMCG";
	public static String Col9 = "CITY";
	public static String Col10 = "STREET";
	public static String Col11 = "AREA";
	public static String Col12 = "DEV_ID";
	public static String Col13 = "LATITUDE";
	public static String Col14 = "LONGITUDE";
	
	//For History Table
	public static String T2KEY = "_id";
	public static String T2Col2 = "DEV_ID";
	public static String T2Col3 = "LOCATION";
	public static String T2Col4 = "DATE";
	
	//For Device registartion
	public static String  T3KEY = "_id";
	public static String  T3Col2 = "DEV_ID";
	

}
