package br.com.localizador.enums;

public enum PerfilTypeEnum {
	
	ADMIN_GRUPO("USERS", "GROUP"),
	USUARIO_GRUPO("USERS", "GROUP");
	
	private String group;
	private String roleType;
	
	private PerfilTypeEnum(String group, String roleType) {
		this.setGroup(group);
		this.setRoleType(roleType);
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
