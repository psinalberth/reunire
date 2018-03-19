package br.gov.ma.tce.reunire.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;

import br.gov.ma.tce.reunire.service.RelatorioBuilder;
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
	
	private List<String> formatosRelatorio = Arrays.asList(new String [] {"CSV", "Excel", "HTML", "PDF"});
	
	private List<?> result;
	
	/** Propriedade ZK **/
	
	private Media media;
	
	private static final String PATH_RELATORIOS = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
	
	@Init
	@NotifyChange({"formatosRelatorio", "urlRetorno", "formatoRelatorio", "media"})
	public void init() {
		
		tipoPecaRelatorio = Integer.parseInt(Executions.getCurrent().getParameter("tipoRelatorio"));
		formatoRelatorio = Executions.getCurrent().getParameter("formato");
		
		urlRetorno = "http://br.yahoo.com";
		
		gerarRelatorio();
	}
	
	private void gerar() {
		
	}
	
	private File recuperarArquivo() {
		return null;
	}
	
	/*@Command
	@NotifyChange("conteudo")*/
	public void gerarRelatorio() {
		
		file = RelatorioBuilder.criar()
			.tipoRelatorio(tipoPecaRelatorio)
			.ente(ente)
			.exercicio(exercicio)
			.formato(formatoRelatorio).getService().getFile();
		// conteudo = "http://www.axmag.com/download/pdfurl-guide.pdf";
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