//Componente para escalamento de opções do menu, facilitando manutenção, recebe o nome da opção, valor a ser enviado ao backend, e função onClick do frontend
export default function Opcao({nome, valor, funcao}){
    return(
        <button
            id={valor}
            className="bg-green-500 hover:bg-green-600 text-black text-stro font-mono px-4 py-2 rounded-lg text-sm"
            onClick={funcao}
        >{nome}</button>
    )
}