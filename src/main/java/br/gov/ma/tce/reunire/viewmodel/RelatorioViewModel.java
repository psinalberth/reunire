package br.gov.ma.tce.reunire.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;

import br.gov.ma.tce.reunire.service.RelatorioService;

public class RelatorioViewModel {
	
	/** Parâmetros os quais podem ser passados na URL */
	
	private String urlRetorno;
	private String formatoRelatorio;
	private String tipoRelatorio;
	private Integer ente;
	private Integer orgao;
	private Integer unidadeGestora;
	private Integer exercicio;
	
	/** Coleção de formatos disponíveis para exportação */
	
	private List<String> formatosRelatorio = Arrays.asList(new String [] {"CSV", "XLS", "HTML", "PDF"});
	
	/** Coleção de objetos recuperados na consulta: mantidos aqui para evitar acessos consecutivos ao mesmo registro durante exportação */
	
	private List<?> dados;
	
	/** Propriedades ZK */
	
	private Media media;
	
	/** Diretório em que se localizam os arquivos .jasper */
	
	private static final String PATH_RELATORIOS = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
	
	private RelatorioService service;
	
	@Init
	@NotifyChange("*")
	public void init() {
		
		tipoRelatorio = Executions.getCurrent().getParameter("tipoRelatorio");
		formatoRelatorio = Executions.getCurrent().getParameter("formato");
		
		urlRetorno = "http://br.yahoo.com";
		
		Executions.getCurrent().getParameterMap().entrySet().forEach(item -> System.out.println(item.getKey() + " --> " + item.getValue()[0]));
		
		service = new RelatorioService(PATH_RELATORIOS, tipoRelatorio, ente, orgao, unidadeGestora, exercicio);
		
		gerarRelatorio();
		exportarRelatorio();
	}
	
	@Command
	@NotifyChange("media")
	public void exportarRelatorio() {
		
		if (dados != null && dados.size() > 0) {
			
			Properties properties = service.getProperties(dados, formatoRelatorio);
			
			String titulo = String.valueOf(properties.get("nome"));
			String extensao = String.valueOf(properties.get("extensao"));
			String formato = String.valueOf(properties.get("formato"));
			File arquivo = (File) properties.get("arquivo");
			
			try {
				
				media = new AMedia(titulo, extensao, formato, arquivo, true);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void gerarRelatorio() {
		dados = service.recuperarDados();
	}
	
	@Command
	public void imprimir() {
		System.out.println("Printing...");
	}
	
	@Command
	public void voltar() {
		Executions.getCurrent().sendRedirect(urlRetorno);
	}
	
	// Getters e Setters
	
	public String getFormatoRelatorio() {
		return formatoRelatorio;
	}
	
	public void setFormatoRelatorio(String formatoRelatorio) {
		this.formatoRelatorio = formatoRelatorio;
	}
	
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

	public String getTipoPecaRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoPecaRelatorio(String tipoPecaRelatorio) {
		this.tipoRelatorio = tipoPecaRelatorio;
	}

	public Integer getEnte() {
		return ente;
	}

	public void setEnte(Integer ente) {
		this.ente = ente;
	}

	public Integer getOrgao() {
		return orgao;
	}

	public void setOrgao(Integer orgao) {
		this.orgao = orgao;
	}

	public Integer getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(Integer unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	public Integer getExercicio() {
		return exercicio;
	}

	public void setExercicio(Integer exercicio) {
		this.exercicio = exercicio;
	}
	
	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
}