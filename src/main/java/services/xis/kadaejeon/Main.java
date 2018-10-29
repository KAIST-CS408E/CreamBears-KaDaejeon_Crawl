package services.xis.kadaejeon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

class Main {
    static String url = "https://talkyou.in/pages/KaDaejeon/posts/";
	static String cookie = "_ga=GA1.2.1295979066.1540732371; _gid=GA1.2.894918707.1540732371; csrftoken=njSmcPEycY44hHMIqb1kkwEghfJNgqiaU230y5cXygpUI6qLmbNDJtsFHCkEmrDZ; sessionid=lt4v8bqjzwk4oi49vensuymkdrph8np1; _gat_gtag_UA_116575140_1=1";
	static int max_index = 0;

	public static void getPageContent (int index) {

	}

    public static void getMaxPageIndex () {
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
				response.append(inputLine);
            }
            in.close();

			Document doc = Jsoup.parse(response.toString());
			Elements table = doc.select("table");

			for (Element row : table.select("tr")) {
				String href = row.attr("data-href");
				String[] href_elems = href.split("/");
				if (href_elems.length == 5) {
					int page_index = Integer.parseInt(href_elems[4]);
					if (max_index < page_index) {
						max_index = page_index;
					}
				}
			}
            System.out.println("response code : " + responseCode);
			System.out.println("max_index : " + max_index);
			con.disclose();
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
