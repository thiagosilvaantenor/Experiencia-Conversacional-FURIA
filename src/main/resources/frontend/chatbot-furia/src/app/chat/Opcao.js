export default function Opcao({nome, valor, funcao}){
    return(
        <button
            id={valor}
            className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-lg text-sm"
            onClick={funcao}
        >{nome}</button>
    )
}