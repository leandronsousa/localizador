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

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.PessoaFisica;
import br.com.localizador.entity.PessoaJuridica;
import br.com.localizador.entity.Usuario;
import br.com.localizador.enums.ConstantesMensagem;
import br.com.localizador.security.Administrador;
import br.com.localizador.security.CustomIdentity;
import br.com.localizador.security.UsuarioMaster;
import br.com.localizador.service.ProprietarioService;
import br.com.localizador.service.UsuarioService;
import br.com.localizador.to.ProprietarioTO;
import br.com.localizador.view.NavegacaoView;

@Named
@ConversationScoped
public class ProprietarioViewHelper extends NavegacaoView {
	
	private static final long serialVersionUID = 6391102495640308680L;

	private @Inject Conversation conversation;
	private @Inject MessageFactory factory;
	private @Inject Messages messages;

	private @Inject ProprietarioService proprietarioService;
	private @Inject UsuarioService usuarioService;
	private @Inject CustomIdentity identity;
	
	private ProprietarioTO proprietarioTO;

	@PostConstruct
	private void init() throws Exception {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
		this.inicializar();
	}
	
	@Administrador
	@UsuarioMaster
	private void inicializar() throws Exception {
		if (this.identity.getUsuario() != null && this.identity.getUsuario().getChave() != null) {
			getProprietarioTO().setProprietario(this.proprietarioService.recuperar(
					this.identity.getUsuario().getProprietario()));
		}
	}

	public String gravar() throws Exception {
		try {
			if (this.getProprietarioTO().getProprietario().getChave() == null 
					|| this.getProprietarioTO().getProprietario().getChave().longValue()
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
			this.proprietarioService.alterar(this.getProprietarioTO().getProprietario());
			this.messages.info(new BundleKey("resources", 
					ConstantesMensagem.REGISTRO_ALTERADO_COM_SUCESSO.getKey())).build();
		} else {
			throw new Exception();
		}
	}

	public boolean isCadastroValido() throws Exception {
		return !this.getProprietarioTO().getProprietario().getListaObjetoRastreado().isEmpty()
				&& !this.getProprietarioTO().getProprietario().getListaUsuario().isEmpty();
	}

	private void incluir() throws Exception {
		if (isCadastroValido()) {
			this.proprietarioService.cadastrar(this.getProprietarioTO().getProprietario());
			this.getProprietarioTO().setProprietario(null);
			this.messages.info(new BundleKey("resources", 
				ConstantesMensagem.REGISTRO_CADASTRADO_COM_SUCESSO.getKey())).build();
		} else {
			this.messages.info(new BundleKey("resources", 
					ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
		}
	}

	public void incluirObjetoRastreado() throws Exception {
		if (isObjetoRastreadoPreenchido() && 
				!this.getProprietarioTO().getProprietario().getListaObjetoRastreado().contains(
						this.getProprietarioTO().getObjetoRastreado())) {
			this.getProprietarioTO().getObjetoRastreado().setProprietario(
					this.getProprietarioTO().getProprietario());
			this.getProprietarioTO().getProprietario().getListaObjetoRastreado().add(
					this.getProprietarioTO().getObjetoRastreado());
			this.getProprietarioTO().setObjetoRastreado(null);
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
		}
	}
	
	private boolean isObjetoRastreadoPreenchido() {
		return this.getProprietarioTO().getObjetoRastreado().getDdd() != null
				&& this.getProprietarioTO().getObjetoRastreado().getDdd().intValue() 
						!= NumberUtils.INTEGER_ONE.intValue()
				&& !StringUtils.isEmpty(this.getProprietarioTO().getObjetoRastreado().getImei())
				&& !StringUtils.isEmpty(this.getProprietarioTO().getObjetoRastreado().getNome())
				&& this.getProprietarioTO().getObjetoRastreado().getTelefone().intValue()
						!= NumberUtils.INTEGER_ONE.intValue();
	}

	public void incluirUsuario() throws Exception {
		if (this.isUsuarioPreenchido() &&
				!this.getProprietarioTO().getProprietario().getListaUsuario().contains(
						this.getProprietarioTO().getUsuario())) {
			this.getProprietarioTO().getUsuario().setProprietario(
					this.getProprietarioTO().getProprietario());
			this.getProprietarioTO().getUsuario().setSenha(DigestUtils.shaHex(
					this.getProprietarioTO().getUsuario().getSenha()));
			this.getProprietarioTO().getProprietario().getListaUsuario().add(
					this.getProprietarioTO().getUsuario());
			this.getProprietarioTO().setUsuario(null);
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build();
		}
	}
	
	private boolean isUsuarioPreenchido() {
		return !StringUtils.isEmpty(this.getProprietarioTO().getUsuario().getNome())
				&& !StringUtils.isEmpty(this.getProprietarioTO().getUsuario().getLogin())
				&& !StringUtils.isEmpty(this.getProprietarioTO().getUsuario().getSenha())
				&& this.getProprietarioTO().getUsuario().getPerfilUsuario().getChave() != null
				&& this.getProprietarioTO().getUsuario().getPerfilUsuario().getChave().longValue() 
						!= NumberUtils.LONG_ZERO.longValue();
	}

	public void editarObjetoRastreado(RowEditEvent event) throws Exception {
		this.messages.info(new BundleKey("resources", ConstantesMensagem.REGISTRO_ALTERADO_COM_SUCESSO.getKey())).build();
	}
	
	public void cancelarEdicaoObjetoRastreado(RowEditEvent event) throws Exception {
		this.messages.info(new BundleKey("resources", ConstantesMensagem.EDICAO_CANCELADA.getKey())).build();
	}

	public void selecionarTipoPessoa(ValueChangeEvent event) throws Exception {
		String tipoPessoa = event.getNewValue().toString();
		if (tipoPessoa.equals(factory.info(new BundleKey("resources", "label.geral.pessoaFisica")).build().getText())) {
			this.getProprietarioTO().getProprietario().setPessoaFisica(new PessoaFisica());
			this.getProprietarioTO().getProprietario().setPessoaJuridica(null);
		} else if (tipoPessoa.equals(factory.info(new BundleKey("resources", "label.geral.pessoaJuridica")).build().getText())) {
			this.getProprietarioTO().getProprietario().setPessoaJuridica(new PessoaJuridica());
			this.getProprietarioTO().getProprietario().setPessoaFisica(null);
		}
	}
	
	public void validarLogin(ValueChangeEvent event) throws Exception {
		if (this.usuarioService.isLoginCadastrado(event.getNewValue().toString())) {
			this.getProprietarioTO().getUsuario().setLogin(StringUtils.EMPTY);
			this.messages.info(new BundleKey("resources", ConstantesMensagem.LOGIN_JA_CADASTRADO.getKey())).build();
		}
	}
	
	public void excluirObjetoRastreado(ObjetoRastreado objetoRastreado) throws Exception {
		if (getProprietarioTO().getProprietario().getListaObjetoRastreado().remove(objetoRastreado)) {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.REGISTRO_EXCLUIDO_COM_SUCESSO.getKey())).build();
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.NENHUM_REGISTRO_ENCONTRADO.getKey())).build();
		}
	}
	
	public void excluirUsuario(Usuario usuario) throws Exception {
		if (getProprietarioTO().getProprietario().getListaUsuario().remove(usuario)) {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.REGISTRO_EXCLUIDO_COM_SUCESSO.getKey())).build();
		} else {
			this.messages.info(new BundleKey("resources", ConstantesMensagem.NENHUM_REGISTRO_ENCONTRADO.getKey())).build();
		}
	}
	
	public ProprietarioTO getProprietarioTO() {
		if (proprietarioTO == null) {
			proprietarioTO = new ProprietarioTO();
		}
		return proprietarioTO;
	}

	public void setProprietarioTO(ProprietarioTO proprietarioTO) {
		this.proprietarioTO = proprietarioTO;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	
}