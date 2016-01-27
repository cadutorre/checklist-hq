package br.com.cadu.checklisthq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.MessageFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListaQuadrinhos {

	private static final String WWW_COMIX_COM_BR = "http://www.comix.com.br/{0}";

	public void execute() throws Exception {

		String avanteVingadoresPath = "129_454_455";

		URL avanteVingadoreURL = new URL(MessageFormat.format(getListaRevitaURL(),avanteVingadoresPath));
		Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("192.168.62.224",3128));

		HttpURLConnection uc = (HttpURLConnection) avanteVingadoreURL.openConnection(proxy);

		uc.connect();

		String line = null;
		StringBuffer tmp = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (line = in.readLine()) != null) {
			tmp.append(line);
		}

		Document document = Jsoup.parse(String.valueOf(tmp));

		Elements listaProdutos = document.select(".productListing tr");
		System.out.println(listaProdutos.size());
		listaProdutos.stream().filter(e -> !e.cssSelector().contains("nth-child(1)")).map(e -> teste(e)).forEach(q -> System.out.println(q));

	}

	private String getListaRevitaURL() {
		return MessageFormat.format(WWW_COMIX_COM_BR,"index.php?cPath={0}");
	}

	private QuadrinhoVO teste(Element e) {
		Elements revista = e.select("td");
		String imagem = MessageFormat.format(WWW_COMIX_COM_BR,revista.get(0).select("a img").attr("src"));
		String detalhe = limpaDetalhe(revista.get(0).select("a").attr("href"));
		String nome = revista.get(1).select("a").text();
		String preco = revista.get(2).select(".preco").text().replace("R$","");
		// System.out.println("Imagem : " + imagem);
		// System.out.println("Detalhe : " + detalhe);
		// System.out.println("Nome : " + nome);
		// System.out.println("Preço : " + preco);
		return new QuadrinhoVO(nome,detalhe,imagem,Double.valueOf(preco.trim()));
	}

	public String limpaDetalhe(String detalhe) {
		return detalhe.contains("&osCsid") ? detalhe.substring(0,detalhe.indexOf("osCsid")) : detalhe;
	}

}
