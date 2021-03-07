package ch.oliatmo.mappers;

import com.jayway.restassured.path.json.JsonPath;

import java.util.HashMap;

public class HomeCoachResponseMapper {

    public int getCo2(String response) {
        HashMap<String, Object> map = JsonPath.from(response).get("body.devices[0].dashboard_data");
        return (int) map.get("CO2");
    }
}
