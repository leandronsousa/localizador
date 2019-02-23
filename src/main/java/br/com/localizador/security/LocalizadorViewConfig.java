package br.com.localizador.security;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface LocalizadorViewConfig {

	enum Pages {

//		@ViewPattern("/index.xhtml")
//		@AccessDeniedView("/login.xhtml")
//		@LoginView("/login.xhtml")
//		@UsuarioCadastrado
//		INDEX,
		
		@FacesRedirect
		@UrlMapping(pattern="/pages/#{cid}/")
		@ViewPattern("/pages/mapa/*")
		@AccessDeniedView("/login.xhtml")
		@LoginView("/login.xhtml")
		@UsuarioCadastrado
		MAPA,
		
		@FacesRedirect
		@UrlMapping(pattern="/pages/#{cid}/")
		@ViewPattern("/pages/admin/*")
		@AccessDeniedView("/login.xhtml")
		@LoginView("/login.xhtml")
		@Administrador
		ADMIN,
		
		@FacesRedirect
		@UrlMapping(pattern="/#{cid}/")
		@ViewPattern("/*")
		@AccessDeniedView("/login.xhtml")
		@LoginView("/login.xhtml")
		ALL;
		
	}
	
}