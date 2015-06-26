package br.com.cadu.checklisthq;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import br.com.cadu.checklisthq.model.Item;

public class HtmlParserTest {

	@Test
	public void test() throws IOException {

		String proximaPagina = "http://www.paninicomics.com.br/web/guest/checklist?p_p_id=ns_negozio_searchItem_WAR_nsnegozio_INSTANCE_1EW7&p_p_lifecycle=1&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_ns_negozio_searchItem_WAR_nsnegozio_INSTANCE_1EW7__spage=%2Fportlet_action%2FdisplayItem%2FviewSearch";
		Integer anoInicial = 2013;
		Integer mesInicial = 4;
		String targetHQ = "VINGADORES VS X-MEN - ED. 1";

		Document document = Jsoup.connect("http://www.paninicomics.com.br/web/guest/checklist?year=2013&month=1").get();

		document.select("div.item").stream().map(element -> {
			String nome = element.select("div.cover a").first().attr("title");
			String data = element.select("div.desc h3").first().val();
			String imageUrl = element.select("div.cover img").first().attr("src");
			return new Item(nome, data, imageUrl);
		}).forEach(item -> System.out.println(item));

	}
}
