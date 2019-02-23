
function Objeto(nome) {
	this.nome = nome;
	this.posicoes = new Array();
}

function Posicao(chave, dataHora, latitude, longitude, endereco, velocidade) {
	this.chave = chave;
	this.dataHora = dataHora;
	this.latitude = latitude;
	this.longitude = longitude;
	this.endereco = endereco;
	this.velocidade = velocidade;
	this.latLng = this.latitude+this.longitude;
}