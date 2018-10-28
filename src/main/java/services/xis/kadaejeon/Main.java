package services.xis.kadaejeon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Main {
    static String url = "https://talkyou.in/pages/KaDaejeon/posts/15577/";
	static String cookie = "_ga=GA1.2.1295979066.1540732371; _gid=GA1.2.894918707.1540732371; csrftoken=njSmcPEycY44hHMIqb1kkwEghfJNgqiaU230y5cXygpUI6qLmbNDJtsFHCkEmrDZ; sessionid=lt4v8bqjzwk4oi49vensuymkdrph8np1; _gat_gtag_UA_116575140_1=1";

    public static void getConnection () {
        try {
            URL obj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		    con.setRequestMethod("GET");
			con.setRequestProperty("Cookie", cookie);
			con.setUseCaches(true); 
			con.connect();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();

            System.out.println("response code : " + responseCode);
        }
        catch (Exception e) {
            System.out.println("Excpetion : " + e);
        }
    }

    public static void main (String[] args) {
        System.out.println("Hello world!");
        getConnection();
    }
}
