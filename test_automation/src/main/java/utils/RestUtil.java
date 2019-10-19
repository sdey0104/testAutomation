package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.deps.com.google.gson.JsonParseException;
import gherkin.deps.com.google.gson.JsonParser;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class RestUtil {
    public static String convertResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream responseStream = httpResponse.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    public static <T> T convertResponseToEntity(HttpResponse httpResponse,  Class<T> classType) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        Class<T> object = objectMapper.readValue(httpResponse.getEntity().getContent(), classType.getClass());
        return (T) object;
    }

    /*
     * TODO
     */
    public static Long findTransactionIdInJson(String jsonString){
        Long id = null;
        try {
            id = new JsonParser().parse(jsonString).getAsJsonArray().get(0).getAsJsonObject().get("runningTransaction").getAsJsonObject().get("id").getAsLong();
        }catch (JsonParseException e){
            //handle it well
        }
        return id;
    }
}
