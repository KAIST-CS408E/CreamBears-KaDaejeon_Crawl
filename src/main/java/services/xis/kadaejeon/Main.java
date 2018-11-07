package services.xis.kadaejeon;

import java.util.ArrayList;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.List;

import scala.collection.Seq;

import com.twitter.penguin.korean.TwitterKoreanProcessor;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;

import java.lang.Thread;

class Main {

	static class CrawlThread extends Thread {
		private int index;

		CrawlThread (int index) {
			this.index = index;
		}

		public void run () {
			for (int i = 0; i < 10; i++) {
				//Article article = getArticleContent(this.index + i);
				//writeArticleFile(article);
				//System.out.println(this.index + i + "finished");
				int ind = this.index + i;
				System.out.println("" + ind);
				getArticleContent(this.index + i);
			}
		}	
	}

    static String url = "https://talkyou.in/pages/KaDaejeon/posts/";
	static String cookie = "_ga=GA1.2.1295979066.1540732371; _gid=GA1.2.101481836.1541589024; _gat_gtag_UA_116575140_1=1; csrftoken=39d4Zm5LNKp0a47D5VTyUJGG5Q5rRx0dXq6EroREob33algvdRdhWxQzIzsZcC0W; sessionid=ilabv4aa4bp07valqpnq1ekmt3r3s1n9";
	static int max_index = 0;

	public static StringBuffer getPageContent (String link) {
		try {
			URL obj = new URL(link);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		    con.setRequestMethod("GET");
			con.setRequestProperty("Cookie", cookie);
			con.setUseCaches(true); 
			con.connect();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
            }
            in.close();

			return response;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static Article getArticleContent (int index) {
		try {
			StringBuffer response = getPageContent(url + index + "/");
			Document doc = Jsoup.parse(response.toString());

			String title = "";
			String content = "";
			ArrayList comments_list = new ArrayList<String>() ;
	
			Elements header_elems = doc.getElementsByTag("h1");
			for (Element elem : header_elems) {
				title = elem.text();
			}

			Elements content_elems = doc.getElementsByClass("forum-content justify-content-center");
			for (Element elem : content_elems) {
				content = elem.text();
			}

			Elements comment_elems = doc.getElementsByClass("comment-body");
			for (Element elem : comment_elems) {
				Elements comment_content_elems = elem.getElementsByClass("float-left");
				for(Element comment_content_elem : comment_content_elems) {
					comments_list.add(comment_content_elem.text());
				}
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter("write_result/" + index + ".txt"));
			writer.write(title);
			writer.write("\n");
			writer.write(content);
			writer.write("\n");

			for (Object elem : comments_list) {
				writer.write(elem.toString());
				writer.write("\n");
			}
			writer.close();
			return null;

			//Article article = new Article(title, content, comments_list, index);
			//return article;
		}
		catch (Exception e) {
			return null;
		}	
	}

    public static int getMaxPageIndex () {
        try {
			StringBuffer response = getPageContent(url);
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
			System.out.println("max_index : " + max_index);
			return max_index;
			/*
			for (int  i = 1; i <= max_index; i++) {
				System.out.println(i);
				writeArticleFile(getArticleContent(i));			
			}
			*/
        }
        catch (Exception e) {
            System.out.println("Excpetion : " + e);
			return -1;
        }
    }

	public static void koreanAnalyze (Article article) {

		String text = article.getTitle();
		// Normalize
		CharSequence normalized = TwitterKoreanProcessorJava.normalize(text);
		System.out.println(normalized);
		// 한국어를 처리하는 예시입니다ㅋㅋ #한국어

		// Tokenize
		Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
		System.out.println(TwitterKoreanProcessorJava.tokensToJavaStringList(tokens));
		// [한국어, 를, 처리, 하는, 예시, 입니, 다, ㅋㅋ, #한국어]
		System.out.println(TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(tokens));
		// [한국어(Noun: 0, 3), 를(Josa: 3, 1),  (Space: 4, 1), 처리(Noun: 5, 2), 하는(Verb: 7, 2),  (Space: 9, 1), 예시(Noun: 10, 2), 입니(Adjective: 12, 2), 다(Eomi: 14, 1), ㅋㅋ(KoreanParticle: 15, 2),  (Space: 17, 1), #한국어(Hashtag: 18, 4)]


		// Stemming
		Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
		System.out.println(TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed));
		// [한국어, 를, 처리, 하다, 예시, 이다, ㅋㅋ, #한국어]
		System.out.println(TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed));
		// [한국어(Noun: 0, 3), 를(Josa: 3, 1),  (Space: 4, 1), 처리(Noun: 5, 2), 하다(Verb: 7, 2),  (Space: 9, 1), 예시(Noun: 10, 2), 이다(Adjective: 12, 3), ㅋㅋ(KoreanParticle: 15, 2),  (Space: 17, 1), #한국어(Hashtag: 18, 4)]


		// Phrase extraction
		List<KoreanPhraseExtractor.KoreanPhrase> phrases = TwitterKoreanProcessorJava.extractPhrases(tokens, true, true);
		System.out.println(phrases);
		 // [한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2), #한국어(Hashtag: 18, 4)]
	}

	public static void writeArticleFile (Article article) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./write_result/" + article.getIndex() + ".txt"));
			writer.write(article.getTitle());
			writer.write("\n");
			writer.write(article.getContent());
			writer.write("\n");
			for (Object elem : article.getComments()) {
				writer.write(elem.toString());
				writer.write("\n");
			}
			writer.close();
		}
		catch (Exception e) {

		}		
	}

    public static void main (String[] args) {
        System.out.println("Hello world!");
		int maxIndex = getMaxPageIndex();
		for (int i = 1; i <= maxIndex; i++) {
			getArticleContent(i);
			System.out.println("" + i);
		}		
/*
        int maxIndex = getMaxPageIndex();
		int limit = maxIndex / 10;
		for (int i = 0; i < limit; i++) {
			CrawlThread ct = new CrawlThread(i * 10 + 1);
			ct.start();
		}		
		int remainder = maxIndex - 10 * limit;
		for (int i = 0 ; i < remainder; i++) {
			//Article article = getArticleContent(limit * 10 + i);
			//writeArticleFile(article);
			//System.out.println(limit * 10 + i + "finished");
			getArticleContent(limit * 10 + i);
		}
*/
    }
}
