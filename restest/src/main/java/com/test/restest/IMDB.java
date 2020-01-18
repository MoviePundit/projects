package com.test.restest;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Movie Search !
 *
 */
public class IMDB {
	public static void main(String[] args) throws UnirestException, IOException {

		String hostomdb = "http://www.omdbapi.com/";
		String charset = "UTF-8";
		String x_omdb_host = "www.omdbapi.com";
		String x_omdb_key = "797a3062";
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		sc.close();
		String query = String.format("s=%s", URLEncoder.encode(s, charset));
		HttpResponse<JsonNode> response = Unirest.get(hostomdb + "?" + query+ "&apikey="+x_omdb_key).asJson();

		JSONObject jsonObj = new JSONObject(response.getBody().toString());
		JSONArray ja_data = jsonObj.getJSONArray("Search");
		JSONObject jaobject = (JSONObject) ja_data.get(0);
		URL url = new URL(jaobject.get("Poster").toString());
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
		System.out.println("\n\nprinting poster\n\n");
		BufferedImage c = ImageIO.read(url);
		ImageIcon image = new ImageIcon(c);
		ImageIcon icon = new ImageIcon(c);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(320, 500);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
