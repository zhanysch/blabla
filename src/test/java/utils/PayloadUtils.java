package utils;

public class PayloadUtils {

    public static  String getPathPayload(int petId,String pathName){

        String petPayload = "{\n" +
                "  \"id\": "+petId+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"blabla\"\n" +
                "  },\n" +
                "  \"name\": \""+pathName+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://s3.amazon.com\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    \n" +
                "  ],\n" +
                "  \"status\": \"fishing\"\n" +
                "}";

        return  petPayload;
    }

    public static  String getSlackPayLoad(String message){

        String slackPayload = "{\n" +
                "  \"channel\": \"C05H5S8A50V\",\n" +
                "  \"text\": \"Janysh: " +message+"\"\n" +
                "}";

        return  slackPayload;
    }

    public static  String getSlackPayLoadAirport(String name1, String name){

        String slackPayload = "{\n" +
                "    \"from\": \""+name1+"\",\n" +
                "    \"to\": \""+name+"\"\n" +
                "}";

        return  slackPayload;
    }
}
