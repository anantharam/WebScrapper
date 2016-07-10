package webparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JustDialScrapper {

	public void parseJustDial(String city) throws IOException {
		CompanyModel model = null;
		List<CompanyModel> restList = new ArrayList<CompanyModel>();
		CompanyModel model2 = new CompanyModel();
		model2.setName("Name");
		model2.setPhone(Arrays.asList("Phone Numbers"));
		model2.setAddress("Full Address");
		model2.setLandmark("LandMark");
		model2.setPincode("Pin Code");
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
		Elements name = null;
		List<String> locations = new ArrayList<String>();
		// locations.add("Ahmedabad");
		// locations.add("Bangalore");
		// locations.add("Chandigarh");
		// locations.add("Chennai");
		// locations.add("Coimbatore");
		// locations.add("Delhi");
		// locations.add("Goa");
		// locations.add("Gurgaon");
		// locations.add("Hyderabad");
		// locations.add("Indore");
		// locations.add("Jaipur");
		// locations.add("Kolkata");
		// locations.add("Mumbai");
		// locations.add("Noida");
		// locations.add("Pune");

		// List<String> bruteAlphabets = new ArrayList<String>();
		// for (char firstChar = 'A'; firstChar <= 'Z'; firstChar++) {
		// for (char secondChar = 'a'; secondChar <= 'z'; secondChar++) {
		// bruteAlphabets.add("" + firstChar + secondChar);
		// }
		// }
		// for (String bruteAlphabet : bruteAlphabets) {
		// Document locationDoc = Jsoup
		// .connect("http://www.justdial.com/webmain/autosuggest.php?cases=city&scity=Bangalorehenn&search="
		// + bruteAlphabet + "&s=1")
		// .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
		// + " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
		// .timeout(15000).get();
		//
		// Connection con = HttpConnection
		// .connect("http://www.justdial.com/webmain/autosuggest.php?cases=city&scity=Chennai&search="
		// + bruteAlphabet + "&s=1");
		// Response resp = con.execute();
		// String body = resp.body();
		//
		// try {
		// JSONObject resultObject = new JSONObject(body);
		// JSONArray jsonarray = (JSONArray) resultObject.get("results");
		// for (int locationCount = 0; locationCount < jsonarray.length();
		// locationCount++) {
		// JSONObject jsonobject = jsonarray.getJSONObject(locationCount);
		// String locationValue = jsonobject.getString("value");
		// String locationText = jsonobject.getString("text");
		// locations.add(locationText);
		// }
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// Scanner inFile1 = new Scanner(new File("src//IndiaCities.txt"));
		//
		// StringBuilder sb = new StringBuilder();
		// while(inFile1.hasNext()) {
		// sb.append(inFile1.nextLine());
		// }
		//
		// String[] allIndiaCities = sb.toString().split(",");
		// for(String indiaCity : allIndiaCities) {
		// locations.add(indiaCity.replace("\"", ""));
		// }

		InputStream in = ClassLoader.getSystemResourceAsStream("jdinput/JustDialUrls.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		List<String> categoryUrls = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			String urlTemplate = line;
			if (urlTemplate != null && urlTemplate.length() > 0)
				categoryUrls.add(urlTemplate);
		}
		locations.add(city);
		for (String location : locations) {
			String field = "Education";
			int urlCount = 0;
			for (String categoryUrl : categoryUrls) {
				try {
					urlCount++;
					String[] categories = categoryUrl.split("/");
					String category = categories[categories.length - 1];
					category = category.replace("-", "_");
					String excelFilePath = "JustDialInfo_" + location + "_" + category + ".xls";
					System.out.println("Hitting : " + categoryUrl);
					System.out.println("################################################");
					int pageNo = 1;
					do {
						String categoryUrlPaged = categoryUrl.replace("MyJDLocation", location);
						String urlToHit = categoryUrlPaged + "page-" + pageNo;
						doc = Jsoup.connect(urlToHit)
								.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
										+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
								.timeout(500000).get();
						System.out.println("pageno." + pageNo);
						if (doc != null) {
							Element aTag = doc.getElementById("tab_block");
							name = aTag.getElementsByClass("store-name");
							Elements phones = aTag.getElementsByClass("contact-info");
							Elements ratings = aTag.getElementsByClass("rt_count");
							Elements years = aTag.getElementsByClass("year");
							for (Element mainElement : name) {
								Elements elementTitle = mainElement.getElementsByAttribute("title");
								if (!elementTitle.text().isEmpty()) {
									System.out.println("name : " + mainElement.getElementsByAttribute("title").text());
									String detailsPageLink = elementTitle.attr("href");
									model = new CompanyModel();
									model.setMoreInfoLink(detailsPageLink);
									Document detailDoc = Jsoup.connect(detailsPageLink)
											.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
													+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
											.timeout(2550000).get();

									Elements nameElements = detailDoc.getElementsByClass("fn");
									for (Element nameElement : nameElements) {
										model.setName(nameElement.text());
									}

									Elements ratingValueElements = detailDoc.getElementsByClass("value-titles");
									for (Element ratingValueElement : ratingValueElements) {
										model.setRatingValue(ratingValueElement.text());
										break;
									}
									Element detailsPageLeftPane = detailDoc.getElementsByClass("leftdt").first();
									if (detailsPageLeftPane != null) {
										Element hoursOfOperationElement = detailsPageLeftPane.getElementById("statHr");
										if (hoursOfOperationElement != null) {
											Elements hourDetails = hoursOfOperationElement
													.getElementsByClass("mreinfli");
											String hoursOfOperation = "";
											for (Element hourDetail : hourDetails) {
												Element dayDetail = hourDetail.getElementsByClass("mreinflispn1")
														.first();
												Element timeDetail = hourDetail.getElementsByClass("mreinflispn2")
														.first();
												hoursOfOperation += dayDetail.text() + " : " + timeDetail.text()
														+ ";\n";
											}
											model.setHoursOfOperation(hoursOfOperation);
										}

										Element leftTopElement = detailsPageLeftPane.getElementById("comp-contact");
										Elements tels = leftTopElement.getElementsByClass("tel");
										List<String> phoneNumbers = new ArrayList<String>();
										for (Element tel : tels) {
											phoneNumbers.add(tel.text());
										}
										model.setPhone(phoneNumbers);

										Element addressElement = leftTopElement.getElementById("fulladdress");
										if (addressElement != null) {
											String fullAddress = addressElement.text();
											parseFullAddress(fullAddress, model);
										}

										Element tagBlock = leftTopElement.select("span.comp-text.also-list.showmore")
												.first();
										if (tagBlock != null) {
											Elements tagElements = tagBlock.select("a");
											List<String> tags = new ArrayList<String>();
											for (Element tagElement : tagElements) {
												tags.add(tagElement.text());
											}
											model.setTags(tags);
										}
									}
									// Elements imageBlocks =
									// detailDoc.getElementsByClass("catyimgul");
									// for(Element imageBlock : imageBlocks) {
									// Elements images = imageBlock.select("a");
									// List<String> photos = new
									// ArrayList<String>();
									// for(Element image : images) {
									// String photoValue =
									// image.attr("data-original");
									// String photoPath =
									// photoValue.substring(0,
									// photoValue.lastIndexOf(".")+3);
									// photos.add(photoPath);
									// }
									// model.setPhotos(photos);
									// break;
									// }

								}
								// for (Element phone : phones) {
								// model.setPhone(phone.getElementsByTag("b").text());
								// if
								// (phone.getElementsByTag("b").text().isEmpty())
								// {
								// model.setPhone(phone.getElementsByTag("a").text());
								// }
								// phones.remove(phone);
								// break;
								//
								// }
								// for (Element address : add) {
								// model.setAddress(address.getElementsByTag("span").text());
								// add.remove(address);
								// break;
								// }
								for (Element rating : ratings) {
									String ratingCount = rating.getElementsByClass("rt_count").text();
									model.setRating(ratingCount.trim().replace(" Ratings", ""));
									ratings.remove(rating);
									break;
								}
								for (Element year : years) {
									model.setYear(year.getElementsByClass("year").text());
									years.remove(year);
									break;
								}
								restList.add(model);
							}
						}

						System.out.println("---------------------------------------------");
						pageNo++;
					} while (name.size() != 0);
					System.out.println("Writing to File : " + excelFilePath);
					ExcelHelper.writeExcel(restList, excelFilePath);
				} catch (Exception ex) {
					System.out.println("************************************************************************");
					System.out.println("Error while parsing " + categoryUrl + " : " + ex.getMessage());
					ex.printStackTrace(System.out);
					System.out.println("************************************************************************");
				}
			}
		}
	}

	private void parseFullAddress(String fullAddress, CompanyModel model) {
		try {
			String parsedAddress = fullAddress.trim().replace("(Map)", "");
			String[] addressElements = parsedAddress.split(" - [0-9]{6,6}");
			String address = addressElements[0];
			model.setAddress(address);
			if (addressElements.length > 1) {
				String landmark = addressElements[1];
				if (landmark != null && landmark.length() > 2) {
					model.setLandmark(landmark.substring(2));
				}
			}
			Pattern zipPattern = Pattern.compile("\\b\\d{6}\\b");
			Matcher zipMatcher = zipPattern.matcher(parsedAddress);
			if (zipMatcher.find()) {
				String zip = zipMatcher.group(0);
				model.setPincode(zip);
			}
		} catch (Exception e) {
			model.setAddress(fullAddress);
		}
	}

	public static void main(String[] args) throws IOException {
//		InetAddress IP = InetAddress.getLocalHost();
//		System.out.println("Ip address from internal API" + IP.toString());
//
//		String ip = getAwsIP();
//		System.out.println("Got IP address from aws as : " + ip);
//
//		String whatismyip = getWhatsMyIP();
//		System.out.println("Got IP address from whatsmyip as : " + whatismyip);
//
//		String ipifyIp = getIpifyIP();
//		System.out.println("Got IP address from ipify as : " + ipifyIp);

		if (args != null && args.length > 0) {
			String city = args[0];
			JustDialScrapper jdExtractor = new JustDialScrapper();
			jdExtractor.parseJustDial(city);
		} else {
			System.out.println("Please enter a city name as argument!");
		}

	}

	public static String getWhatsMyIP() throws IOException {
		String str = null;
		Document document = Jsoup.connect("http://www.whatsmyip.org/")
				.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21"
						+ " (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
				.timeout(500000).get();
		Element hostNameElement = document.getElementById("id");
		str = hostNameElement.text();
		return str;
	}

	public static String getIpifyIP() throws IOException {
		URL connection = new URL("https://api.ipify.org");
		URLConnection con = connection.openConnection();
		String str = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		str = reader.readLine();
		return str;
	}

	public static String getAwsIP() throws IOException {
		URL connection = new URL("http://checkip.amazonaws.com/");
		URLConnection con = connection.openConnection();
		String str = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		str = reader.readLine();
		return str;
	}

	public static String getWhatIsMyIP() throws IOException {
		String str = null;
		java.net.URL URL = new java.net.URL("http://www.whatismyip.org/");
		java.net.HttpURLConnection Conn = (HttpURLConnection) URL.openConnection();
		java.io.InputStream InStream = Conn.getInputStream();
		java.io.InputStreamReader Isr = new java.io.InputStreamReader(InStream);
		java.io.BufferedReader Br = new java.io.BufferedReader(Isr);
		str = Br.readLine();
		return str;
	}

	public static String getIP() {
		try {
			Process p = Runtime.getRuntime().exec("curl http://169.254.169.254/latest/meta-data/public-ipv4");
			int returnCode = p.waitFor();
			if (returnCode == 0) {
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String ip = r.readLine();
				r.close();
				return ip;
			} else {
				System.out.println("Error while getting IP, return code is : " + returnCode);
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Error getting IP Address.");
			ex.printStackTrace(System.out);
		}
		return null;
	}
}
