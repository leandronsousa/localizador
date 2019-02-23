package br.com.localizador.enums;


public enum PerfilEnum {

	ADMINISTRADOR(1, PerfilTypeEnum.ADMIN_GRUPO.getGroup(), PerfilTypeEnum.ADMIN_GRUPO.getRoleType()),
	USUARIO_MASTER(2, PerfilTypeEnum.USUARIO_GRUPO.getGroup(), PerfilTypeEnum.USUARIO_GRUPO.getRoleType()),
	USUARIO(3, PerfilTypeEnum.USUARIO_GRUPO.getGroup(), PerfilTypeEnum.USUARIO_GRUPO.getRoleType());
	
	private Integer chave;
	private String group;
	private String roleType;
	
	private PerfilEnum(Integer chave, String group, String roleType) {
		this.chave = chave;
		this.group = group;
		this.roleType = roleType;
	}
	
	public Integer getChave() {
		return chave;
	}
	
	public void setChave(Integer chave) {
		this.chave = chave;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}