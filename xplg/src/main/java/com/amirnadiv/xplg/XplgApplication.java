package com.amirnadiv.xplg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class XplgApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(XplgApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException, ParseException {

        File file = new File("output.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Object read = JsonPath.read(file,"$.features[*]" );

        Collection<Feature> records = objectMapper.readValue(String.valueOf(read), new TypeReference<Collection<Feature>>() {});

        records.parallelStream().forEach(feature -> feature.properties.dayMonthYear = new SimpleDateFormat("dd/MM/yyyy").format(feature.properties.time));

        Map<String,Feature> stringFeatureMap = new HashMap<>();

        for (Feature feature:records) {

            String day = feature.properties.dayMonthYear;

            if ((!stringFeatureMap.containsKey(day)) || (stringFeatureMap.get(day).properties.mag < feature.properties.mag)) {
                stringFeatureMap.put(day, feature);
            }
        }

        TreeMap<String, Feature> stringFeatureTreeMap = new TreeMap<>(stringFeatureMap);
        List<String> sortedKeys = sortDates(stringFeatureTreeMap.keySet());
        for (String key:sortedKeys) {
            System.out.println(key+" "+stringFeatureTreeMap.get(key).properties.mag +", location: "+stringFeatureTreeMap.get(key).properties.title);
        }
    }
    private ArrayList<String> sortDates(Set<String> dates) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Map <Date, String> dateFormatMap = new TreeMap<>();
        for (String date: dates)
            dateFormatMap.put(f.parse(date), date);
        return new ArrayList<>(dateFormatMap.values());
    }
}