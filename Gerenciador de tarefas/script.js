document.getElementById("tarefa").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        adicionarTarefa(); 
    }
});

function adicionarTarefa() {
    let tarefa = document.getElementById("tarefa").value; 
    let lista = document.getElementById("lista");

    if (!tarefa) {
        alert("Digite uma tarefa válida!");
        return;
    }

    tarefa = titulo(tarefa);

    let elemento = document.createElement("li"); 
    let texto = document.createElement("span"); 
    let botao = document.createElement("button"); 

    botao.textContent = "Remover"; 
    texto.textContent = tarefa; 

    botao.onclick = function () {
        lista.removeChild(elemento); 
        atualizarLocalStorage(); 
    };

    elemento.appendChild(botao); 
    elemento.appendChild(texto); 
    lista.appendChild(elemento); 

    salvarLocalStorage(tarefa); 

    document.getElementById("tarefa").value = ""; 
}

function carregarTarefas() {
    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    let lista = document.getElementById("lista");

    tarefas.forEach(tarefa => {
        let elemento = document.createElement("li");
        let texto = document.createElement("span");
        let botao = document.createElement("button");

        botao.textContent = "Remover";
        texto.textContent = tarefa;

        botao.onclick = function () {
            lista.removeChild(elemento);
            atualizarLocalStorage();
        };

        elemento.appendChild(botao);
        elemento.appendChild(texto);
        lista.appendChild(elemento);
    });
}

function salvarLocalStorage(tarefa) {
    let tarefas = JSON.parse(localStorage.getItem("tarefas")) || [];
    tarefas.push(tarefa);
    localStorage.setItem("tarefas", JSON.stringify(tarefas));
}

function atualizarLocalStorage() {
    let tarefas = [];
    let listaTarefas = document.querySelectorAll("#lista li span"); 

    listaTarefas.forEach(span => {
        tarefas.push(span.textContent.trim());
    });

    localStorage.setItem("tarefas", JSON.stringify(tarefas));
}

function titulo(tarefa){
    dividido = tarefa.split("");
    dividido = dividido[0].toUpperCase()+ dividido.slice(1).join("");
    return dividido;
}

carregarTarefas();