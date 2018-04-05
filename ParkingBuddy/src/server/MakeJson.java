package server;

import java.util.ArrayList;

import com.google.gson.Gson;

import database.User;

public class MakeJson {
	public MakeJson()
	{
		
	}
	
	public String makeJsonString(ArrayList <User> myArray)
	{
		Gson gson = new Gson();
		return gson.toJson(myArray);
		
	}
}
