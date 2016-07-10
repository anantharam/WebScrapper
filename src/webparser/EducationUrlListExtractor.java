package webparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EducationUrlListExtractor {
	public static void main(String[] args) throws IOException {
		CompanyModel model = null;
		List<CompanyModel> restList = new ArrayList<CompanyModel>();
		CompanyModel model2 = new CompanyModel();
		model2.setName("Name");
		model2.setPhone(Arrays.asList("Phone Numbers"));
		model2.setAddress("Full Address");
		model2.setRating("Rating Count");
		model2.setRatingValue("Rating");
		model2.setYear("Estd.in Year");
		model2.setTags(Arrays.asList("Tags"));
		model2.setMoreInfoLink("JustDial Link");
		model2.setHoursOfOperation("Hours Of Operation");
		restList.add(model2);
		CompanyModel model1 = new CompanyModel();
		model1.setName("");
		model1.setPhone(Arrays.asList(""));
		model1.setAddress("");
		model1.setRating("");
		model1.setYear("");
		restList.add(model1);
		Document doc = null;
		int pageNo = 1;
		Elements name = null;
		List<String> locations = new ArrayList<String>();

		locations.add("Ahmedabad");
		locations.add("Bangalore");
		locations.add("Chandigarh");
		locations.add("Chennai");
		locations.add("Coimbatore");
		locations.add("Delhi");
		locations.add("Goa");
		locations.add("Gurgaon");
		locations.add("Hyderabad");
		locations.add("Indore");
		locations.add("Jaipur");
		locations.add("Kolkata");
		locations.add("Mumbai");
		locations.add("Noida");
		locations.add("Pune");
		
//		List<String> bruteAlphabets = new ArrayList<String>();
//		for (char firstChar = 'A'; firstChar <= 'Z'; firstChar++) {
//			for (char secondChar = 'a'; secondChar <= 'z'; secondChar++) {
//				bruteAlphabets.add("" + firstChar + secondChar);
//			}
//		}
//		for (String bruteAlphabet : bruteAlphabets) {
//			Document locationDoc = Jsoup
//					.connect("http://www.justdial.com/webmain/autosuggest.php?cases=city&scity=Bangalorehenn&search="
//							+ bruteAlphabet + "&s=1")
//					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
//							+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
//					.timeout(15000).get();
//
//			Connection con = HttpConnection
//					.connect("http://www.justdial.com/webmain/autosuggest.php?cases=city&scity=Chennai&search="
//							+ bruteAlphabet + "&s=1");
//			Response resp = con.execute();
//			String body = resp.body();
//			
//			try {
//				JSONObject resultObject = new JSONObject(body);
//				JSONArray jsonarray = (JSONArray) resultObject.get("results");
//				for (int locationCount = 0; locationCount < jsonarray.length(); locationCount++) {
//					JSONObject jsonobject = jsonarray.getJSONObject(locationCount);
//					String locationValue = jsonobject.getString("value");
//					String locationText = jsonobject.getString("text");
//					locations.add(locationText);
//				}
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		Scanner inFile1 = new Scanner(new File("src//IndiaCities.txt"));
//
//		StringBuilder sb = new StringBuilder();
//		while(inFile1.hasNext()) {
//		    sb.append(inFile1.nextLine());
//		}
//
//		String[] allIndiaCities = sb.toString().split(",");
//		for(String indiaCity : allIndiaCities) {
//			locations.add(indiaCity.replace("\"", ""));
//		}
		Set<String> categoryUrls = new TreeSet<String>();
		
		for(String location : locations) {
			String field = "Education";
			String excelFilePath = "src//JustDialInfo_" + location + "_" + field + ".xls";
			
			Document topLevelDoc = Jsoup.connect("http://www.justdial.com/"+location+"/58/Education_fil")
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
							+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(15000).get();
			Element documentElement = topLevelDoc.getElementById("mnintrnlbnr");
			Elements urlElements = documentElement.select("a");
			List<String> topLevelUrls = new ArrayList<String>();
			for(Element urlElement : urlElements) {
				String urlValue = urlElement.attr("href");
				topLevelUrls.add(urlValue);
			}
			
			for(String topLevelUrl : topLevelUrls) {
				Document categoryDoc = Jsoup.connect(topLevelUrl)
						.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
								+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
						.timeout(15000).get();
				Element docElement = categoryDoc.getElementById("mnintrnlbnr");
				if(docElement==null) {
					String urlTemplate = extractUrlTemplate(location, topLevelUrl);
					categoryUrls.add(urlTemplate);
				} else {
					Elements aElements = docElement.select("a");
					for(Element urlElement : aElements) {
						String urlValue = urlElement.attr("href");
						String urlTemplate = extractUrlTemplate(location, urlValue);
						categoryUrls.add(urlTemplate);
					}
				}
			}
		}
		
		
		try {
			File file = new File("src//JustDialUrls.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(String categoryUrl : categoryUrls) {
				bw.write(categoryUrl+"\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param location
	 * @param urlValue
	 * @return
	 */
	private static String extractUrlTemplate(String location, String urlValue) {
		String urlGeneric = urlValue.replace("/"+location+"/", "/MyJDLocation/");
		String urlTemplate = urlGeneric.substring(0, urlGeneric.lastIndexOf('/')+1);
		if(urlTemplate.contains("/58/"))
			return "";
		return urlTemplate;
	}
}
