package br.com.cadu.checklisthq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

import br.com.cadu.checklisthq.model.Item;

@Ignore
public class HtmlParserTest {

	String proximaPagina = "http://www.paninicomics.com.br/web/guest/checklist?p_p_id=ns_negozio_searchItem_WAR_nsnegozio_INSTANCE_1EW7&p_p_lifecycle=1&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_ns_negozio_searchItem_WAR_nsnegozio_INSTANCE_1EW7__spage=%2Fportlet_action%2FdisplayItem%2FviewSearch";
	Integer anoInicial = 2013;
	Integer mesInicial = 4;
	String targetHQ = "VINGADORES VS X-MEN - ED. 1";
	private final HttpHost proxy = new HttpHost("192.168.62.224",3128);

	@Test
	public void test() throws IOException {

		Document document = Jsoup.connect("http://www.paninicomics.com.br/web/guest/checklist?year=2013&month=1").timeout(5000).get();

		printHq(document);

	}

	@Test
	public void testProxy() throws IOException {

		printHq(Jsoup.parse(gethtml("http://www.paninicomics.com.br/web/guest/checklist?year=2013&month=1")));
		System.out.println("#############");
		printHq(Jsoup.parse(gethtml(proximaPagina)));

	}

	private void printHq(Document document) {
		document.select("div.item").stream().map(element -> {
			String nome = element.select("div.cover a").first().attr("title");
			String data = element.select("div.desc h3").first().html();
			String imageUrl = element.select("div.cover img").first().attr("src");
			return new Item(nome,data,imageUrl);
		}).forEach(item -> System.out.println(item));
	}

	private String gethtml(String urlString) throws MalformedURLException,IOException {
		URL url = new URL(urlString);
		Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("192.168.62.224",3128));
		HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);

		uc.connect();

		String line = null;
		StringBuilder tmp = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (line = in.readLine()) != null) {
			tmp.append(line);
		}
		return tmp.toString();
	}

	@Test
	public void testName() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet("http://www.paninicomics.com.br/web/guest/checklist?year=2013&month=1");

		RequestConfig.custom().setProxy(proxy).build();

		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();

			String line = null;
			StringBuilder tmp = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(entity1.getContent()));
			while ( (line = in.readLine()) != null) {
				tmp.append(line);
			}

			printHq(Jsoup.parse(tmp.toString()));

			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}

		// HttpPost httpPost = new HttpPost("http://targethost/login");
		// List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		// nvps.add(new BasicNameValuePair("username", "vip"));
		// nvps.add(new BasicNameValuePair("password", "secret"));
		// httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// CloseableHttpResponse response2 = httpclient.execute(httpPost);
		//
		// try {
		// System.out.println(response2.getStatusLine());
		// HttpEntity entity2 = response2.getEntity();
		// // do something useful with the response body
		// // and ensure it is fully consumed
		// EntityUtils.consume(entity2);
		// } finally {
		// response2.close();
		// }
	}
}
