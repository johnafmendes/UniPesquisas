var timer;

function excluir() {
	return confirm("Deseja excluir o registro?");
}

function DisplaySessionTimeout(sessionTimeout) {
    document.getElementById("contador").innerText = sessionTimeout;
    sessionTimeout = sessionTimeout - 1;

    if (sessionTimeout >= 0)
        timer = setTimeout("DisplaySessionTimeout(" + sessionTimeout + ")", 1000);
    else {
        alert("Your current Session is over.");
    }
}

function atualizarSessao(e){
	clearTimeout(timer);
	DisplaySessionTimeout(document.getElementById("tempoSessao").value);
}

function confirmar() {
	return confirm("Deseja confirmar?");
}