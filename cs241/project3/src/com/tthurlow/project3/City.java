package com.tthurlow.project3;

public class City {
	private int id;
	private String code;
	private String name;
	private int population;
	private int elevation;

	public City(int id, String code, String name, int population, int elevation) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.population = population;
		this.elevation = elevation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	@Override
	public String toString() {
		return id + " " + code + " " + name + " " + population + " " + elevation;
		//return "(" + id + ") " + name + " (" + code + "), pop " + population + ", elevation " + elevation + "ft.";
	}
}
