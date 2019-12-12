package com.telusko.demorest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class AlienRepository 
{
	Connection con = null;

	public AlienRepository() {
		String url = "jdbc:mysql://localhost:3306/restdb";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(url,username,password);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public List<Alien> getAliens(){
		List<Alien> aliens = new ArrayList<>();
		String sql = "select * from alien";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				Alien a = new Alien();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
				
				aliens.add(a);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return aliens;
	}
	
	//method overloading (Passing with id parameter)
	public Alien getAlien(int id) {
		String sql = "select * from alien where id="+id;
		Alien a = new Alien();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
				
				
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return a;
	}

	public void create(Alien a1) 
	{
		String sql = "insert into alien values (?,?,?)";
		try {
			PreparedStatement st = (PreparedStatement) con.prepareStatement(sql);
			st.setInt(1,a1.getId());
			st.setString(2,a1.getName());
			st.setInt(3,a1.getPoints());
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void update(Alien a1) 
	{
		String sql = "update alien set name=?,points=? where id=?";
		try {
			PreparedStatement st = (PreparedStatement) con.prepareStatement(sql);
			st.setString(1,a1.getName());
			st.setInt(2,a1.getPoints());
			st.setInt(3,a1.getId());
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public void delete(int id) {		
		String sql = "delete from alien where id=?";
		try {
			PreparedStatement st = (PreparedStatement) con.prepareStatement(sql);
			st.setInt(1,id);
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
