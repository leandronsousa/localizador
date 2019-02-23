package br.com.localizador.view.viewhelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;
import org.primefaces.event.RowEditEvent;

import br.com.localizador.entity.PessoaFisica;
import br.com.localizador.entity.PessoaJuridica;
import br.com.localizador.enums.ConstantesMensagem;
import br.com.localizador.security.Administrador;
import br.com.localizador.service.PessoaFisicaService;
import br.com.localizador.service.PessoaJuridicaService;
import br.com.localizador.service.ProprietarioService;
import br.com.localizador.service.UsuarioService;
import br.com.localizador.to.AdminTO;
import br.com.localizador.view.NavegacaoView;

@Named
@ConversationScoped
public class AdminViewHelper extends NavegacaoView {
	
	private static final long serialVersionUID = 6391102495640308680L;

	private @Inject Conversation conversation;
	private @Inject MessageFactory factory;
	private @Inject Messages messages;

	private @Inject ProprietarioService proprietarioService;
	private @Inject UsuarioService usuarioService;
	private @Inject PessoaFisicaService pessoaFisicaService;
	private @Inject PessoaJuridicaService pessoaJuridicaService;
	
	private AdminTO adminTO;

	@PostConstruct
	private void init() throws Exception {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
		this.inicializar();
	}
	
	private @Administrador void inicializar() throws Exception {
		this.getAdminTO().setListaProprietario(
				this.proprietarioService.pesquisar(getAdminTO().getProprietario()));
	}

	public String gravar() throws Exception {
		try {
			if (this.getAdminTO().getProprietario().getChave() == null 
					|| this.getAdminTO().getProprietario().getChave().longValue()
							== NumberUtils.LONG_ZERO.longValue()) {
				this.incluir();
			} else {
				this.alterar();
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.messages.info(new BundleKey("resources",
					ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
			return "fail";
		}
	}

	private void alterar() throws Exception {
		if (isCadastroValido()) {
			this.proprietarioService.alterar(this.getAdminTO().getProprietario());
			this.messages.info(new BundleKey("resources", 
					ConstantesMensagem.REGISTRO_ALTERADO_COM_SUCESSO.getKey())).build();
		} else {
			throw new Exception();
		}
	}

	public boolean isCadastroValido() throws Exception {
		return !this.getAdminTO().getProprietario().getListaObjetoRastreado().isEmpty()
				&& !this.getAdminTO().getProprietario().getListaUsuario().isEmpty();
	}

	private void incluir() throws Exception {
		if (isCadastroValido()) {
			this.proprietarioService.cadastrar(this.getAdminTO().getProprietario());
			this.getAdminTO().setProprietario(null);
			this.messages.info(new BundleKey("resources", 
				ConstantesMensagem.REGISTRO_CADASTRADO_COM_SUCESSO.getKey())).build();
		} else {
			throw new Exception();
		}
	}

	public void incluirObjetoRastreado() throws Exception {
		if (isObjetoRastreadoPreenchido() && 
				!this.getAdminTO().getProprietario().getListaObjetoRastreado().contains(
						this.getAdminTO().getObjetoRastreado())) {
			this.getAdminTO().getObjetoRastreado().setProprietario(
					this.getAdminTO().getProprietario());
			this.getAdminTO().getProprietario().getListaObjetoRastreado().add(
					this.getAdminTO().getObjetoRastreado());
			this.getAdminTO().setObjetoRastreado(null);
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
		}
	}
	
	private boolean isObjetoRastreadoPreenchido() {
		return this.getAdminTO().getObjetoRastreado().getDdd() != null
				&& this.getAdminTO().getObjetoRastreado().getDdd().intValue() 
						!= NumberUtils.INTEGER_ONE.intValue()
				&& !StringUtils.isEmpty(this.getAdminTO().getObjetoRastreado().getImei())
				&& !StringUtils.isEmpty(this.getAdminTO().getObjetoRastreado().getNome())
				&& this.getAdminTO().getObjetoRastreado().getTelefone().intValue()
						!= NumberUtils.INTEGER_ONE.intValue();
	}

	public void incluirUsuario() throws Exception {
		if (this.isUsuarioPreenchido() &&
				!this.getAdminTO().getProprietario().getListaUsuario().contains(
						this.getAdminTO().getUsuario())) {
			this.getAdminTO().getUsuario().setProprietario(
					this.getAdminTO().getProprietario());
			this.getAdminTO().getUsuario().setSenha(DigestUtils.shaHex(
					this.getAdminTO().getUsuario().getSenha()));
			this.getAdminTO().getProprietario().getListaUsuario().add(
					this.getAdminTO().getUsuario());
			this.getAdminTO().setUsuario(null);
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
		}
	}
	
	private boolean isUsuarioPreenchido() {
		return !StringUtils.isEmpty(this.getAdminTO().getUsuario().getNome())
				&& !StringUtils.isEmpty(this.getAdminTO().getUsuario().getLogin())
				&& !StringUtils.isEmpty(this.getAdminTO().getUsuario().getSenha())
				&& this.getAdminTO().getUsuario().getPerfilUsuario().getChave() != null
				&& this.getAdminTO().getUsuario().getPerfilUsuario().getChave().longValue() 
						!= NumberUtils.LONG_ZERO.longValue();
	}

	public void editarObjetoRastreado(RowEditEvent event) throws Exception {
		this.messages.info(new BundleKey("resources", ConstantesMensagem.REGISTRO_ALTERADO_COM_SUCESSO.getKey())).build();
	}
	
	public void cancelarEdicaoObjetoRastreado(RowEditEvent event) throws Exception {
		this.messages.info(new BundleKey("resources", ConstantesMensagem.REGISTRO_ALTERADO_COM_SUCESSO.getKey())).build();
	}

	public void selecionarTipoPessoa(ValueChangeEvent event) throws Exception {
		String tipoPessoa = event.getNewValue().toString();
		if (tipoPessoa.equals(factory.info(new BundleKey("resources", "label.geral.pessoaFisica")).build().getText())) {
			this.getAdminTO().getProprietario().setPessoaFisica(new PessoaFisica());
			this.getAdminTO().getProprietario().setPessoaJuridica(null);
		} else if (tipoPessoa.equals(factory.info(new BundleKey("resources", "label.geral.pessoaJuridica")).build().getText())) {
			this.getAdminTO().getProprietario().setPessoaJuridica(new PessoaJuridica());
			this.getAdminTO().getProprietario().setPessoaFisica(null);
		}
	}
	
	public void validarCpfProprietario(ValueChangeEvent event) throws Exception {
		if (this.pessoaFisicaService.isCPFCadastrado(event.getNewValue().toString())) {
			this.getAdminTO().getProprietario().getPessoaFisica().setCpf(StringUtils.EMPTY);
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CPF_JA_CADASTRADO.getKey())).build();
		}
	}
	
	public void validarCnpjProprietario(ValueChangeEvent event) throws Exception {
		if (this.pessoaJuridicaService.isCNPJCadastrado(event.getNewValue().toString())) {
			this.getAdminTO().getProprietario().getPessoaJuridica().setCnpj(StringUtils.EMPTY);
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CNPJ_JA_CADASTRADO.getKey())).build();
		}
	}
	
	public void validarLogin(ValueChangeEvent event) throws Exception {
		if (this.usuarioService.isLoginCadastrado(event.getNewValue().toString())) {
			this.getAdminTO().getUsuario().setLogin(StringUtils.EMPTY);
			this.messages.info(new BundleKey("resources", ConstantesMensagem.LOGIN_JA_CADASTRADO.getKey())).build();
		}
	}
	
	public AdminTO getAdminTO() {
		if (adminTO == null) {
			adminTO = new AdminTO();
		}
		return adminTO;
	}

	public void setAdminTO(AdminTO adminTO) {
		this.adminTO = adminTO;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	
}