package br.gov.ma.tce.reunire.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;

import br.gov.ma.tce.reunire.service.RelatorioService;

public class RelatorioViewModel {
	
	/** Parâmetros vindos da URL **/
	
	private String urlRetorno;
	private String formatoRelatorio;
	private Integer tipoPecaRelatorio;
	private Integer ente;
	private Integer orgao;
	private Integer unidadeGestora;
	private Integer exercicio;
	
	private List<String> formatosRelatorio = Arrays.asList(new String [] {"CSV", "XLS", "HTML", "PDF"});
	
	private List<?> result;
	
	/** Propriedade ZK **/
	
	private Media media;
	
	private static final String PATH_RELATORIOS = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
	
	@Init
	@NotifyChange({"formatosRelatorio", "urlRetorno", "formatoRelatorio", "media", "result"})
	public void init() {
		
		tipoPecaRelatorio = Integer.parseInt(Executions.getCurrent().getParameter("tipoRelatorio"));
		formatoRelatorio = Executions.getCurrent().getParameter("formato");
		
		urlRetorno = "http://br.yahoo.com";
		
		gerarRelatorio();
		exportarRelatorio();
	}
	
	@Command
	@NotifyChange("media")
	public void exportarRelatorio() {
		
		if (result != null && result.size() > 0) {
			
			File arquivo = RelatorioService.gerarArquivo(PATH_RELATORIOS, tipoPecaRelatorio, result, formatoRelatorio);
			
			try {
				
				String formato = formatoRelatorio.toUpperCase();
				
				if (formato.equals("PDF")) {
					media = new AMedia("teste", "pdf", "application/pdf", arquivo, true);
					
				} else if (formato.equals("CSV")) {
					media = new AMedia("teste", "csv", "application/csv", arquivo, true);
					
				} else if (formato.equals("XLS")) {
					media = new AMedia("teste", "xls", "application/vnd.ms-excel", arquivo, true);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void gerarRelatorio() {
		result = RelatorioService.recuperarDados(tipoPecaRelatorio, ente, orgao, unidadeGestora, exercicio);
	}
	
	@Command
	public void imprimir() {
		System.out.println("Printing...");
	}
	
	@Command
	public void voltar() {
		Executions.getCurrent().sendRedirect(urlRetorno);
	}
	
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

	public Integer getTipoPecaRelatorio() {
		return tipoPecaRelatorio;
	}

	public void setTipoPecaRelatorio(Integer tipoPecaRelatorio) {
		this.tipoPecaRelatorio = tipoPecaRelatorio;
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