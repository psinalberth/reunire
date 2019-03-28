package br.gov.ma.tce.reunire.viewmodel;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import br.gov.ma.tce.reunire.service.RelatorioService;

public class RelatorioViewModel {
	
	/** Parâmetros os quais podem ser passados na URL */
	
	private String urlRetorno;
	private Map<String, Object> params;
	
	/** Coleção de formatos disponíveis para exportação */
	
	private List<String> formatosRelatorio = Arrays.asList(new String [] {"CSV", "XLS", "HTML", "PDF"});
	
	/** Coleção de objetos recuperados na consulta: mantidos aqui para evitar acessos consecutivos ao mesmo registro durante exportação */
	
	private List<?> dados;
	private Map<String, List<?>> dadosLote;
	
	/** Propriedades ZK */
	
	private Media media;
	
	/** Diretório em que se localizam os arquivos .jasper */
	
	private static final String PATH_RELATORIOS = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
	
	private RelatorioService service;
	
	private String formatoRelatorio;
	
	@Init
	@NotifyChange("*")
	public void init() {
		
		params = new HashMap<String, Object>();
		
		params.put("reportDir", PATH_RELATORIOS);
		params.put("formato", Executions.getCurrent().getParameter("formato") != null ? Executions.getCurrent().getParameter("formato") : "PDF");
		params.put("exercicio", Executions.getCurrent().getParameter("exercicio") != null ? Integer.valueOf(Executions.getCurrent().getParameter("exercicio")) : 2017);
		
		String [] result = Executions.getCurrent().getParameter("tipoRelatorio").split(",");
		
		if (result.length > 1) {
			params.put("tipoRelatorio", result);
			
		} else {
			params.put("tipoRelatorio", result[0]);
		}
		
		formatoRelatorio = params.get("formato").toString().toUpperCase();
		
		if (Executions.getCurrent().getParameter("unidade") != null) {
			params.put("unidadeId", Integer.parseInt(Executions.getCurrent().getParameter("unidade")));
			
		} else if (Executions.getCurrent().getParameter("ente") != null) {
			params.put("enteId", Integer.parseInt(Executions.getCurrent().getParameter("ente")));
		}
		
		if (Executions.getCurrent().getParameter("orgao") != null) {
			params.put("orgaoId", Integer.parseInt(Executions.getCurrent().getParameter("orgao")));
		}
		
		if (Executions.getCurrent().getParameter("poder") != null) {
			params.put("poderId", Integer.parseInt(Executions.getCurrent().getParameter("poder")));
		}
		
		if (Executions.getCurrent().getParameter("modulo") != null) {
			params.put("modulo", Integer.parseInt(Executions.getCurrent().getParameter("modulo")));
			
		} else {
			params.put("modulo", Integer.valueOf(1));
		}
		
		service = new RelatorioService(params);
		
		if (result.length == 1) {
			gerarRelatorio();
			
		} else {
			gerarRelatorioLote();
		}
		
		exportarRelatorio();
	}
	
	@Command
	@NotifyChange("media")
	public void exportarRelatorio() {
		
		if (dados != null) {
			
			if (params.get("formato") != null && formatoRelatorio == null) {
				formatoRelatorio = (String) params.get("formato");
			}
			
			Properties properties = service.getProperties(dados, params, formatoRelatorio);
			
			String titulo = String.valueOf(properties.get("nome"));
			String extensao = String.valueOf(properties.get("extensao"));
			String formato = String.valueOf(properties.get("formato"));
			ByteArrayOutputStream arquivo = (ByteArrayOutputStream) properties.get("arquivo");
				
			media = new AMedia(titulo, extensao, formato, arquivo.toByteArray());
			
		} else if (dadosLote != null) {
			
			Properties properties = service.getProperties(dadosLote, params, formatoRelatorio);
			
			String titulo = String.valueOf(properties.get("nome"));
			String extensao = String.valueOf(properties.get("extensao"));
			String formato = String.valueOf(properties.get("formato"));
			ByteArrayOutputStream arquivo = (ByteArrayOutputStream) properties.get("arquivo");
				
			media = new AMedia(titulo, extensao, formato, arquivo.toByteArray());
		}
	}
	
	@Command
	@NotifyChange("params")
	public void imprimirRelatorio(@BindingParam("params") Map<String, Object> params, @BindingParam("tipoRelatorio") String tipoRelatorio) {
		
		this.params = new HashMap<String, Object>();
		this.params.put("reportDir", PATH_RELATORIOS);
		
		if (params.get("ente") != null) {
			this.params.put("enteId", Integer.valueOf(String.valueOf(params.get("ente"))));
		}
		
		if (params.get("exercicio") != null) {
			this.params.put("exercicio", Integer.valueOf(String.valueOf(params.get("exercicio"))));
		}
		
		if (params.get("modulo") != null) {
			this.params.put("modulo", params.get("modulo"));
		}
		
		if (tipoRelatorio != null && tipoRelatorio.trim().length() > 0) {
			this.params.put("tipoRelatorio", tipoRelatorio.toLowerCase());
		}
		
		this.params.put("formato", "PDF");
		
		service = new RelatorioService(this.params);
		
		gerarRelatorio();
		exportarRelatorio();
	}
	
	public void gerarRelatorio() {
		dados = service.recuperarDados();
	}
	
	public void gerarRelatorioLote() {
		dadosLote = service.recuperarDadosLote();
	}
	
	@Command
	public void imprimir() {
		Clients.evalJavaScript("iframe.print()");
	}
	
	@Command
	public void voltar() {
		
		String url = "/?";
		
		if (urlRetorno != null && urlRetorno.trim().length() > 0) {
			Executions.getCurrent().sendRedirect(urlRetorno);
			
		} else {
			
			if (params.get("unidadeId") != null) {
				url = url + "unidade=" + params.get("unidadeId");
				
			} else if (params.get("enteId") != null) {
				url = url + "ente=" + params.get("enteId");
			}
			
			if (params.get("modulo") != null) {
				url = url + "&modulo=" + params.get("modulo");
			}
			
			if (params.get("exercicio") != null) {
				url = url + "&exercicio=" + params.get("exercicio");
			}
			
			Executions.getCurrent().sendRedirect(url);
		}
	}
	
	// Getters e Setters
	
	public List<String> getFormatosRelatorio() {
		return formatosRelatorio;
	}
	
	public void setFormatosRelatorio(List<String> formatosRelatorio) {
		this.formatosRelatorio = formatosRelatorio;
	}

	public String getUrlRetorno() {
		return urlRetorno;
	}

	public void setUrlRetorno(String urlRetorno) {
		this.urlRetorno = urlRetorno;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
	
	public String getFormatoRelatorio() {
		return formatoRelatorio;
	}
	
	public void setFormatoRelatorio(String formatoRelatorio) {
		this.formatoRelatorio = formatoRelatorio;
	}
}