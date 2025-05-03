//Função para lidar com links, vai buscar no texto do json recebido se tem um link, se tiver separa texto de link
export function extrairTextoELink(texto) {
  const regex = /(https?:\/\/[^\s]+)/;
  const partes = texto.split(regex);

  if (partes.length > 1) {
    return {
      texto: partes[0].trim(),
      link: partes[1].trim(),
    };
  }

  return { texto, link: null };
}