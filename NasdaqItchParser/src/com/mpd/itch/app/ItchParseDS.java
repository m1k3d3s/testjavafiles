package com.mpd.itch.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public final class ItchParseDS {
	private final Map<Object,Object> yMap;
	private Map<Object,Object> fMap;
	private Map<Object, Object> mMap;
	private final Yaml yaml;
	@SuppressWarnings("unchecked")
	ItchParseDS(String yamlconfigfile) throws FileNotFoundException {
		yaml = new Yaml();
		InputStream yInput;
		yInput = new FileInputStream(new File(yamlconfigfile));
		// load the yaml data struct
		Object y = yaml.load(yInput);
		// convert the data structure to a map
		yMap = (Map<Object, Object>) y;
		buildFormats();
		buildMessages();
		
	}
	@SuppressWarnings("unchecked")
	public void buildFormats() {
		fMap = new HashMap<>();
		ArrayList<Object> fArray = (ArrayList<Object>) yMap.get("formats");
		fArray.stream().map((fArray1) -> (Map<Object, Object>) fArray1).forEach((tempMap) -> {
            fMap.put(tempMap.keySet().toArray()[0], tempMap.values().toArray()[0]);
        });

	}
	@SuppressWarnings("unchecked")
	public void buildMessages() {
		mMap = (Map<Object, Object>) yMap.get("messages");
		
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getFields(String mType){
		Map<Object, Object> tempMap = (Map<Object, Object>) mMap.get(mType);
		return (ArrayList<Object>) tempMap.get("fields");
		
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getFormat(String field) {
		return (ArrayList<Object>) fMap.get(field);
	}
	@SuppressWarnings("unchecked")
	public String getFieldName(String mType) {
		Map<Object, Object> tempMap = (Map<Object, Object>) mMap.get(mType);
		return tempMap.get("name").toString();
		
	}
}
